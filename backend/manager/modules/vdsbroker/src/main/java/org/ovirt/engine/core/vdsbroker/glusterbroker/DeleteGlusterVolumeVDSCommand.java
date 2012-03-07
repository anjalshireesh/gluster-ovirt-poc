package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.glustercommands.GlusterVolumeVDSParameters;

public class DeleteGlusterVolumeVDSCommand extends GlusterBrokerCommand<GlusterVolumeVDSParameters> {

    public DeleteGlusterVolumeVDSCommand(GlusterVolumeVDSParameters parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteVdsBrokerCommand() {
        status = getBroker().glusterVolumeDelete(getParameters().getVolumeName());
        ProceedProxyReturnValue();
    }
}
