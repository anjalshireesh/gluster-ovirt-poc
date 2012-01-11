package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.vdscommands.VdsIdVDSCommandParametersBase;
import org.ovirt.engine.core.vdsbroker.vdsbroker.GlusterDiskListReturnForXmlRpc;
import org.ovirt.engine.core.vdsbroker.vdsbroker.StatusForXmlRpc;
import org.ovirt.engine.core.vdsbroker.vdsbroker.VdsBrokerCommand;

public class ListGlusterHostDisksVDSCommand<P extends VdsIdVDSCommandParametersBase> extends VdsBrokerCommand<P> {
    private GlusterDiskListReturnForXmlRpc result;

    public ListGlusterHostDisksVDSCommand(P parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteVdsBrokerCommand() {
        result = getBroker().glusterDisksList();
        ProceedProxyReturnValue();
        setReturnValue(result.disks);
    }

    @Override
    protected StatusForXmlRpc getReturnStatus() {
        return result.mStatus;
    }

    @Override
    protected Object getReturnValueFromBroker() {
        return result;
    }
}
