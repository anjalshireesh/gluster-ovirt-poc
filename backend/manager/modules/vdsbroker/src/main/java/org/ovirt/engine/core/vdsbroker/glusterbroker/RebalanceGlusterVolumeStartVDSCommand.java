package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.glustercommands.GlusterVolumeVDSParameters;

public class RebalanceGlusterVolumeStartVDSCommand extends GlusterBrokerCommand<GlusterVolumeVDSParameters> {
    public RebalanceGlusterVolumeStartVDSCommand(GlusterVolumeVDSParameters parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteIrsBrokerCommand() {
        // TODO: The rebalance mode to be received from client and passed
        status = getIrsProxy().glusterVolumeRebalanceStart(getParameters().getVolumeName(), "migrate-data");

        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();
    }
}
