package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.glustercommands.AddBricksToGlusterVolumeVDSParameters;

public class AddBricksToGlusterVolumeVDSCommand extends GlusterBrokerCommand<AddBricksToGlusterVolumeVDSParameters> {
    public AddBricksToGlusterVolumeVDSCommand(AddBricksToGlusterVolumeVDSParameters parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteIrsBrokerCommand() {
        status = getIrsProxy().glusterVolumeAddBrick(getParameters().getVolumeName(), getParameters().getBricks());

     // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();
    }
}
