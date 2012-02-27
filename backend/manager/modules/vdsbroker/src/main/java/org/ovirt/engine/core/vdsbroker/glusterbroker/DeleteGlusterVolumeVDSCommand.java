package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.glustercommands.GlusterVolumeVDSParameters;
import org.ovirt.engine.core.vdsbroker.vdsbroker.VdsBrokerCommand;

public class DeleteGlusterVolumeVDSCommand<P extends GlusterVolumeVDSParameters> extends VdsBrokerCommand<P> {

    public DeleteGlusterVolumeVDSCommand(P parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteVdsBrokerCommand() {
        status = getBroker().glusterVolumeDelete(getParameters().getVolumeName());
        ProceedProxyReturnValue();
    }
}
