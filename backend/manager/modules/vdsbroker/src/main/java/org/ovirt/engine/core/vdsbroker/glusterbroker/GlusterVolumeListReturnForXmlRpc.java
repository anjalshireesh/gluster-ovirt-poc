package org.ovirt.engine.core.vdsbroker.glusterbroker;

import java.util.Map;

import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.VOLUME_STATUS;
import org.ovirt.engine.core.vdsbroker.irsbroker.StatusReturnForXmlRpc;

public final class GlusterVolumeListReturnForXmlRpc extends StatusReturnForXmlRpc {
    private static final String GLUSTER_VOLUMES = "glusterVolumes";

    // We are ignoring missing fields after the status, because on failure it is
    // not sent.
    // [XmlRpcMissingMapping(MappingAction.Ignore), XmlRpcMember("vmlist")]
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
        volume.setName(volumeMap.get("volumeName").toString());
        volume.setVolumeType(volumeMap.get("volumeType").toString());
        volume.setTransportType(volumeMap.get("transportType").toString());
        volume.setStatus(VOLUME_STATUS.valueOf(volumeMap.get("status").toString()));

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

        String[] bricksData = (String[])volumeMap.get("bricks");
        if(bricksData != null && bricksData.length > 0) {
            for(String brickData : bricksData) {
                volume.addBrick(brickData);
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
