/**
 *
 */
package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.glustercommands.GlusterBaseVDSCommandParameters;

/**
 *
 */
public class ListGlusterVolumesVDSCommand extends GlusterBrokerCommand<GlusterBaseVDSCommandParameters> {

    public ListGlusterVolumesVDSCommand(GlusterBaseVDSCommandParameters parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteIrsBrokerCommand() {
        GlusterVolumeListReturnForXmlRpc returnValue = getIrsProxy().glusterVolumesList();

        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();

        setReturnValue(returnValue.volumes);
    }
}
