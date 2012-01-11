package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.glustercommands.GlusterVolumeVDSParameters;

public class StartGlusterVolumeVDSCommand extends GlusterBrokerCommand<GlusterVolumeVDSParameters> {
    public StartGlusterVolumeVDSCommand(GlusterVolumeVDSParameters parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteIrsBrokerCommand() {
        status = getIrsProxy().glusterVolumeStart(getParameters().getVolumeName());

        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();
    }
}
