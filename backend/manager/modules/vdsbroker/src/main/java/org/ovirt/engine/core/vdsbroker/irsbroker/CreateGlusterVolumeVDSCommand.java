/**
 *
 */
package org.ovirt.engine.core.vdsbroker.irsbroker;

import java.util.HashMap;
import java.util.Map;

import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.common.vdscommands.CreateGlusterVolumeVDSParameters;

/**
 *
 */
public class CreateGlusterVolumeVDSCommand<T extends CreateGlusterVolumeVDSParameters> extends IrsBrokerCommand<CreateGlusterVolumeVDSParameters> {

    public CreateGlusterVolumeVDSCommand(T parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteIrsBrokerCommand() {
        GlusterVolumeEntity volume = getParameters().getVolume();

        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("volumeName", volume.getName());
        parameters.put("replicaCount", volume.getReplicaCount());
        parameters.put("stripeCount", volume.getStripeCount());
        parameters.put("transportType", volume.getTransportType().toString());
        parameters.put("bricks", volume.getBrickDirectories().toArray());

        status = getIrsProxy().glusterVolumeCreate(parameters);

        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();

        // TODO: invoke additional calls to set options, access protocols, access control list and CIFS users (if required)
//        parameters.put("volumeType", volume.getVolumeType().toString());
//        parameters.put("options", StringUtil.collectionToString(volume.getOptions().getOptions(), ","));
//        parameters.put("accessProtocols", StringUtil.collectionToString(volume.getAccessProtocols(), ","));
//        parameters.put("accessControlList", volume.getAccessControlList());
//        parameters.put("cifsUsers", StringUtil.collectionToString(volume.getCifsUsers(), ","));
    }
}
