package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.glustercommands.RebalanceGlusterVolumeVDSParameters;
import org.ovirt.engine.core.vdsbroker.vdsbroker.GlusterTaskStatusReturnForXmlRpc;
import org.ovirt.engine.core.vdsbroker.vdsbroker.StatusForXmlRpc;

public class RebalanceGlusterVolumeVDSCommand extends GlusterBrokerCommand<RebalanceGlusterVolumeVDSParameters> {
    private StatusForXmlRpc status;

    public RebalanceGlusterVolumeVDSCommand(RebalanceGlusterVolumeVDSParameters parameters) {
        super(parameters);
    }

    @Override
    protected StatusForXmlRpc getReturnStatus() {
        return status;
    }

    @Override
    protected void ExecuteVdsBrokerCommand() {
        status = null;
        switch (getParameters().getOperation()) {
        case START:
            status =
                    getBroker().glusterVolumeRebalanceStart(getParameters().getVolumeName(),
                            getParameters().getRebalanceMode(),
                            getParameters().isForce().toString()).mStatus;
            setReturnValue(getReturnStatus());
            break;
        case STOP:
            status = getBroker().glusterVolumeRebalanceStop(getParameters().getVolumeName()).mStatus;
            setReturnValue(getReturnStatus());
            break;
        case STATUS:
            GlusterTaskStatusReturnForXmlRpc returnValue =
                    getBroker().glusterVolumeRebalanceStatus(getParameters().getVolumeName());
            status = returnValue.mStatus;
            setReturnValue(returnValue.getGlusterTaskStatusEntity());
            break;
        }
        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();
    }

}
