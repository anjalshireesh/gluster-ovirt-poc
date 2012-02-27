package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.glustercommands.GlusterVolumeVDSParameters;
import org.ovirt.engine.core.vdsbroker.vdsbroker.VdsBrokerCommand;

public class StartGlusterVolumeVDSCommand<P extends GlusterVolumeVDSParameters> extends VdsBrokerCommand<P> {
    public StartGlusterVolumeVDSCommand(P parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteVdsBrokerCommand() {
        status = getBroker().glusterVolumeStart(getParameters().getVolumeName());

        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();
    }
}
