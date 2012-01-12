package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.glustercommands.GlusterHostVDSParameters;

public class AddGlusterHostVDSCommand extends GlusterBrokerCommand<GlusterHostVDSParameters>{
    public AddGlusterHostVDSCommand(GlusterHostVDSParameters parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteIrsBrokerCommand() {
        status = getIrsProxy().glusterHostAdd(getParameters().getHostName());

        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();
    }
}
