package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.businessentities.GlusterTaskOperation;
import org.ovirt.engine.core.common.glustercommands.ReplaceGlusterVolumeBrickVDSParameters;

public class ReplaceGlusterVolumeBrickVDSCommand extends GlusterBrokerCommand<ReplaceGlusterVolumeBrickVDSParameters> {

    public ReplaceGlusterVolumeBrickVDSCommand(ReplaceGlusterVolumeBrickVDSParameters parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteVdsBrokerCommand() {
        String sourceBrick = getParameters().getSourceBrick().getQualifiedName();
        String targetBrick = getParameters().getTargetBrick().getQualifiedName();
        GlusterTaskOperation operation = getParameters().getOperation();

        switch (operation) {
        case START:
            status =
                    getBroker().glusterVolumeReplaceBrickStart(getParameters().getVolumeName(),
                            sourceBrick,
                            targetBrick);
            break;
        case ABORT:
            status =
                    getBroker().glusterVolumeReplaceBrickAbort(getParameters().getVolumeName(),
                            sourceBrick,
                            targetBrick);
            break;
        case STATUS:
            status =
                    getBroker().glusterVolumeReplaceBrickStatus(getParameters().getVolumeName(),
                            sourceBrick,
                            targetBrick);
            break;
        case PAUSE:
            status =
                    getBroker().glusterVolumeReplaceBrickPause(getParameters().getVolumeName(),
                            sourceBrick,
                            targetBrick);
            break;
        case COMMIT:
            status =
                    getBroker().glusterVolumeReplaceBrickCommit(getParameters().getVolumeName(),
                            sourceBrick,
                            targetBrick);
            break;

        }
        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();
    }

}
