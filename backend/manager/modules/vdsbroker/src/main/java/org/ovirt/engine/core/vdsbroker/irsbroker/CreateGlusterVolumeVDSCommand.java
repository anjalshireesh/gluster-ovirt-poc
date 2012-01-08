/**
 *
 */
package org.ovirt.engine.core.vdsbroker.irsbroker;

import java.util.HashMap;
import java.util.Map;

import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.common.utils.StringUtil;
import org.ovirt.engine.core.common.vdscommands.CreateGlusterVolumeVDSParameters;

/**
 *
 */
public class CreateGlusterVolumeVDSCommand<T extends CreateGlusterVolumeVDSParameters> extends IrsBrokerCommand<CreateGlusterVolumeVDSParameters> {

    protected CreateGlusterVolumeVDSCommand(T parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteIrsBrokerCommand() {
        GlusterVolumeEntity volume = getParameters().getVolume();

        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("name", volume.getName());
        parameters.put("replicaCount", volume.getReplicaCount());
        parameters.put("stripeCount", volume.getStripeCount());
        parameters.put("volumeType", volume.getVolumeType().toString());
        parameters.put("transportType", volume.getTransportType().toString());
        parameters.put("bricks", StringUtil.collectionToString(volume.getBricks(), ","));
        parameters.put("options", StringUtil.collectionToString(volume.getOptions().getOptions(), ","));
        parameters.put("accessProtocols", StringUtil.collectionToString(volume.getAccessProtocols(), ","));
        parameters.put("accessControlList", volume.getAccessControlList());
        parameters.put("cifsUsers", StringUtil.collectionToString(volume.getCifsUsers(), ","));

        getIrsProxy().glusterVolumeCreate(parameters);
    }
}
