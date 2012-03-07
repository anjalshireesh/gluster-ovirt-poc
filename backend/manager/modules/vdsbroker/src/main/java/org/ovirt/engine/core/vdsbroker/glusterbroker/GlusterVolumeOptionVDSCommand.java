/**
 *
 */
package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.glustercommands.GlusterVolumeOptionVDSParameters;

/**
 *
 */
public class GlusterVolumeOptionVDSCommand extends GlusterBrokerCommand<GlusterVolumeOptionVDSParameters> {

    public GlusterVolumeOptionVDSCommand(GlusterVolumeOptionVDSParameters parameters) {
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
