/**
 *
 */
package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.glustercommands.GlusterVolumeOptionVDSParameters;
import org.ovirt.engine.core.vdsbroker.vdsbroker.VdsBrokerCommand;

/**
 *
 */
public class GlusterVolumeOptionVDSCommand<P extends GlusterVolumeOptionVDSParameters> extends VdsBrokerCommand<P> {

    public GlusterVolumeOptionVDSCommand(P parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteVdsBrokerCommand() {
        status =
                getBroker().glusterVolumeSet(getParameters().getVolumeName(),
                        getParameters().getVolumeOption().getKey(),
                        getParameters().getVolumeOption().getValue());
        ProceedProxyReturnValue();
    }
}
