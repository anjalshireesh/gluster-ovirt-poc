package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.glustercommands.GlusterVolumeVDSParameters;
import org.ovirt.engine.core.vdsbroker.vdsbroker.VdsBrokerCommand;

public class StopGlusterVolumeVDSCommand<P extends GlusterVolumeVDSParameters> extends VdsBrokerCommand<P> {
    public StopGlusterVolumeVDSCommand(P parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteVdsBrokerCommand() {
        status = getBroker().glusterVolumeStop(getParameters().getVolumeName());

        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();
    }
}
