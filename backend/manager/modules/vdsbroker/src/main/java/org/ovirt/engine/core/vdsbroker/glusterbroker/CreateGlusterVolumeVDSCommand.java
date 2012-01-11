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

/**
 *
 */
public class CreateGlusterVolumeVDSCommand extends GlusterBrokerCommand<CreateGlusterVolumeVDSParameters> {

    public CreateGlusterVolumeVDSCommand(CreateGlusterVolumeVDSParameters parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteIrsBrokerCommand() {
        GlusterVolumeEntity volume = getParameters().getVolume();

        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("volumeName", volume.getName());
        parameters.put("volumeType", volume.getVolumeType().toString());
        parameters.put("replicaCount", volume.getReplicaCount());
        parameters.put("stripeCount", volume.getStripeCount());

        Object transportType = volume.getTransportType();
        if(transportType == null || transportType.toString().isEmpty()) {
            transportType = TRANSPORT_TYPE.ETHERNET.toString();
        }
        parameters.put("transportType", transportType.toString());
        parameters.put("bricks", volume.getBrickDirectories().toArray());

        status = getIrsProxy().glusterVolumeCreate(parameters);

        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();

        String accessControlList = volume.getAccessControlList();
        if(accessControlList != null && !accessControlList.trim().isEmpty()) {
            status = getIrsProxy().glusterVolumeSet(volume.getName(), GlusterVolumeEntity.OPTION_AUTH_ALLOW, accessControlList.trim());
            ProceedProxyReturnValue();
        }

        if(!volume.isNfsEnabled()) {
            status = getIrsProxy().glusterVolumeSet(volume.getName(), GlusterVolumeEntity.OPTION_NFS_DISABLE, "on");
            ProceedProxyReturnValue();
        }

        for(GlusterVolumeOption option : volume.getOptions().getOptions()) {
            status = getIrsProxy().glusterVolumeSet(volume.getName(), option.getKey(), option.getValue());
            ProceedProxyReturnValue();
        }

        // TODO: handle CIFS related options
    }
}
