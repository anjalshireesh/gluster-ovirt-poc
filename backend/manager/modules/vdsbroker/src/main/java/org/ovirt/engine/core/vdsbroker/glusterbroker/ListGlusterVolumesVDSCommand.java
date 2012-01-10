/**
 *
 */
package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.glustercommands.GlusterBaseVDSCommandParameters;
import org.ovirt.engine.core.vdsbroker.vdsbroker.StatusForXmlRpc;

/**
 *
 */
public class ListGlusterVolumesVDSCommand extends GlusterBrokerCommand<GlusterBaseVDSCommandParameters> {
    private StatusForXmlRpc status;

    public ListGlusterVolumesVDSCommand(GlusterBaseVDSCommandParameters parameters) {
        super(parameters);
    }

    @Override
    protected StatusForXmlRpc getReturnStatus() {
        return status;
    }

    @Override
    protected void ExecuteIrsBrokerCommand() {
        GlusterVolumeListReturnForXmlRpc returnValue = getIrsProxy().glusterVolumesList();
        status = returnValue.mStatus;
        setReturnValue(returnValue.volumes);

        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();
    }
}
