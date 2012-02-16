package org.ovirt.engine.core.vdsbroker.glusterbroker;

import java.util.Map;

import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.TRANSPORT_TYPE;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.VOLUME_STATUS;
import org.ovirt.engine.core.vdsbroker.irsbroker.StatusReturnForXmlRpc;
import org.ovirt.engine.core.compat.Guid;

public final class GlusterVolumeListReturnForXmlRpc extends StatusReturnForXmlRpc {
    private static final String GLUSTER_VOLUMES = "volumes";

    // We are ignoring missing fields after the status, because on failure it is
    // not sent.
    // [XmlRpcMissingMapping(MappingAction.Ignore), XmlRpcMember("volumes")]
    public GlusterVolumeEntity[] volumes;

    @SuppressWarnings("unchecked")
    public GlusterVolumeListReturnForXmlRpc(Map<String, Object> innerMap) {
        super(innerMap);
        Object[] temp = (Object[]) innerMap.get(GLUSTER_VOLUMES);
        if (temp != null) {
            volumes = new GlusterVolumeEntity[temp.length];
            for (int i = 0; i < temp.length; i++) {
                volumes[i] = prepareVolumeEntity((Map<String, Object>)temp[i]);
            }
        }
    }

    private GlusterVolumeEntity prepareVolumeEntity(Map<String, Object> volumeMap) {
        GlusterVolumeEntity volume = new GlusterVolumeEntity();
        volume.setId(Guid.createGuidFromString(volumeMap.get("uuid").toString()));
        volume.setName(volumeMap.get("volumeName").toString());
        volume.setVolumeType(volumeMap.get("volumeType").toString());

        Object[] transportTypes = (Object[])volumeMap.get("transportType");
        if(transportTypes != null && transportTypes.length > 0) {
            // Though VDSM (gluster) can potentially return multiple transport types for a given volume,
            // we are currently showing only one. This needs to be enhanced, starting with adding a list
            // of transport types in the volume entity model instead of a single value
            volume.setTransportType(TRANSPORT_TYPE.valueOf(transportTypes[0].toString()));
        }

        volume.setStatus(VOLUME_STATUS.valueOf(volumeMap.get("volumeStatus").toString()));

        switch(volume.getVolumeType()) {
        case REPLICATE:
        case DISTRIBUTED_REPLICATE:
            volume.setReplicaCount(Integer.valueOf(volumeMap.get("replicaCount").toString()));
            break;
        case STRIPE:
        case DISTRIBUTED_STRIPE:
            volume.setStripeCount(Integer.valueOf(volumeMap.get("stripeCount").toString()));
            break;
        }

        Object[] bricksData = (Object[])volumeMap.get("bricks");
        if(bricksData != null && bricksData.length > 0) {
            for(Object brickData : bricksData) {
                volume.addBrick(brickData.toString());
            }
        }

        Map<String, Object> options = (Map<String, Object>)volumeMap.get("options");
        if(options != null && options.size() > 0) {
            for(String key : options.keySet()) {
                volume.setOption(key, options.get(key).toString());
            }
        }

        return volume;
    }
}
