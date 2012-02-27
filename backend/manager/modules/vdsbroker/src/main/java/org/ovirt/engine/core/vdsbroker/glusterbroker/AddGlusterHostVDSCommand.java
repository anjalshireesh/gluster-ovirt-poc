package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.glustercommands.GlusterHostVDSParameters;

public class AddGlusterHostVDSCommand extends GlusterBrokerCommand<GlusterHostVDSParameters>{
    public AddGlusterHostVDSCommand(GlusterHostVDSParameters parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteVdsBrokerCommand() {
        status = getBroker().glusterHostAdd(getParameters().getHostName());

        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();
    }

}
