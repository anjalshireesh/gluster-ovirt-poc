package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.glustercommands.GlusterVolumeVDSParameters;

public class StopGlusterVolumeVDSCommand extends GlusterBrokerCommand<GlusterVolumeVDSParameters> {
    public StopGlusterVolumeVDSCommand(GlusterVolumeVDSParameters parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteVdsBrokerCommand() {
        status = getBroker().glusterVolumeStop(getParameters().getVolumeName());

        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();
    }
}
