package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.glustercommands.GlusterVolumeVDSParameters;

public class DeleteGlusterVolumeVDSCommand extends GlusterBrokerCommand<GlusterVolumeVDSParameters> {

    public DeleteGlusterVolumeVDSCommand(GlusterVolumeVDSParameters parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteIrsBrokerCommand() {

        status = getIrsProxy().glusterVolumeDelete( getParameters().getVolumeName() );

        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();

        // TODO: handle CIFS related options after delete gluster volume
    }
}
