/**
 *
 */
package org.ovirt.engine.core.vdsbroker.glusterbroker;

import java.util.HashMap;
import java.util.Map;

import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.TRANSPORT_TYPE;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeOption;
import org.ovirt.engine.core.common.glustercommands.CreateGlusterVolumeVDSParameters;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.vdsbroker.irsbroker.OneUuidReturnForXmlRpc;
import org.ovirt.engine.core.vdsbroker.vdsbroker.StatusForXmlRpc;

/**
 *
 */
public class CreateGlusterVolumeVDSCommand extends GlusterBrokerCommand<CreateGlusterVolumeVDSParameters> {
    private StatusForXmlRpc status;

    public CreateGlusterVolumeVDSCommand(CreateGlusterVolumeVDSParameters parameters) {
        super(parameters);
    }

    @Override
    protected StatusForXmlRpc getReturnStatus() {
        return status;
    }

    @Override
    protected void ExecuteIrsBrokerCommand() {
        GlusterVolumeEntity volume = getParameters().getVolume();

        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("volumeName", volume.getName());
        parameters.put("volumeType", volume.getVolumeType().toString());
        parameters.put("replicaCount", volume.getReplicaCount());
        parameters.put("stripeCount", volume.getStripeCount());

        TRANSPORT_TYPE transportType = volume.getTransportType();
        parameters.put("transportType", transportType.toString());
        parameters.put("bricks", volume.getBrickDirectories().toArray());

        OneUuidReturnForXmlRpc uuidReturn = getIrsProxy().glusterVolumeCreate(parameters);
        status = uuidReturn.mStatus;
        volume.setId(Guid.createGuidFromString(uuidReturn.mUuid));

        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();

        // volume creation succeeded. Proceed with other work.

        String accessControlList = volume.getAccessControlList();
        if(accessControlList != null && !accessControlList.trim().isEmpty()) {
            status = getIrsProxy().glusterVolumeSet(volume.getName(), GlusterVolumeEntity.OPTION_AUTH_ALLOW, accessControlList.trim()).mStatus;
            ProceedProxyReturnValue();
        }

        if(!volume.isNfsEnabled()) {
            status = getIrsProxy().glusterVolumeSet(volume.getName(), GlusterVolumeEntity.OPTION_NFS_DISABLE, "on").mStatus;
            ProceedProxyReturnValue();
        }

        for(GlusterVolumeOption option : volume.getOptions()) {
            status = getIrsProxy().glusterVolumeSet(volume.getName(), option.getKey(), option.getValue()).mStatus;
            ProceedProxyReturnValue();
        }

        // TODO: handle CIFS related options

        // set the volume updated with id as the return value
        setReturnValue(volume);
    }
}
