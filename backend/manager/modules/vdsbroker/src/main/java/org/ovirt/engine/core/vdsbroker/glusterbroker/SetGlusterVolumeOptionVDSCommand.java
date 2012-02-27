/**
 *
 */
package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.glustercommands.GlusterVolumeOptionVDSParameters;
import org.ovirt.engine.core.vdsbroker.vdsbroker.VdsBrokerCommand;

/**
 *
 */
public class SetGlusterVolumeOptionVDSCommand<P extends GlusterVolumeOptionVDSParameters> extends VdsBrokerCommand<P> {

    public SetGlusterVolumeOptionVDSCommand(P parameters) {
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
