/**
 *
 */
package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.glustercommands.GlusterVolumeOptionVDSParameters;

/**
 *
 */
public class SetGlusterVolumeOptionVDSCommand extends GlusterBrokerCommand<GlusterVolumeOptionVDSParameters> {

    public SetGlusterVolumeOptionVDSCommand(GlusterVolumeOptionVDSParameters parameters) {
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
