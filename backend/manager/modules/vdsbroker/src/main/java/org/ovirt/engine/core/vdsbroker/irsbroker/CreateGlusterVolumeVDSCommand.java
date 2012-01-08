/**
 *
 */
package org.ovirt.engine.core.vdsbroker.irsbroker;

import java.util.HashMap;
import java.util.Map;

import org.ovirt.engine.core.common.businessentities.GlusterVolume;
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
        // TODO: Prepare and pass the GlusterVolume object
        GlusterVolume volume = getParameters().getVolume();

        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("name", volume.getName());
        parameters.put("replicaCount", volume.getReplicaCount());
        parameters.put("stripeCount", volume.getStripeCount());

        getIrsProxy().createGlusterVolume(parameters);
    }
}
