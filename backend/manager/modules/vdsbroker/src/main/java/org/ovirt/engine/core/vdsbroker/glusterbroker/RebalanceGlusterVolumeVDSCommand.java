package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.glustercommands.RebalanceGlusterVolumeVDSParameters;
import org.ovirt.engine.core.vdsbroker.vdsbroker.GlusterTaskStatusReturnForXmlRpc;
import org.ovirt.engine.core.vdsbroker.vdsbroker.StatusOnlyReturnForXmlRpc;

public class RebalanceGlusterVolumeVDSCommand extends GlusterBrokerCommand<RebalanceGlusterVolumeVDSParameters> {
    public RebalanceGlusterVolumeVDSCommand(RebalanceGlusterVolumeVDSParameters parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteVdsBrokerCommand() {

        switch (getParameters().getOperation()) {
        case START:
            status =
                    getBroker().glusterVolumeRebalanceStart(getParameters().getVolumeName(),
                            getParameters().getReblanceOption(),
                            getParameters().isForce().toString());
            setReturnValue(getReturnStatus());
            break;
        case STOP:
            status = getBroker().glusterVolumeRebalanceStop(getParameters().getVolumeName());
            setReturnValue(getReturnStatus());
            break;
        case STATUS:
            GlusterTaskStatusReturnForXmlRpc returnValue =
                    getBroker().glusterVolumeRebalanceStatus(getParameters().getVolumeName());
            status = new StatusOnlyReturnForXmlRpc(returnValue.mStatus);
            setReturnValue(returnValue.getGlusterTaskStatusEntity());
            break;
        }
        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();
    }

}
