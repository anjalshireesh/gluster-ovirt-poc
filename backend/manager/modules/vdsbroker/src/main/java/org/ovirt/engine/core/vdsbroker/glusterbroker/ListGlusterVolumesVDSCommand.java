/**
 *
 */
package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.vdscommands.VdsIdVDSCommandParametersBase;
import org.ovirt.engine.core.vdsbroker.vdsbroker.StatusForXmlRpc;

/**
 *
 */
public class ListGlusterVolumesVDSCommand extends GlusterBrokerCommand<VdsIdVDSCommandParametersBase> {
    private StatusForXmlRpc status;

    public ListGlusterVolumesVDSCommand(VdsIdVDSCommandParametersBase parameters) {
        super(parameters);
    }

    @Override
    protected StatusForXmlRpc getReturnStatus() {
        return status;
    }

    @Override
    protected void ExecuteVdsBrokerCommand() {
        GlusterVolumeListReturnForXmlRpc returnValue = getBroker().glusterVolumesList();
        status = returnValue.mStatus;
        setReturnValue(returnValue.volumes);

        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();
    }

}
