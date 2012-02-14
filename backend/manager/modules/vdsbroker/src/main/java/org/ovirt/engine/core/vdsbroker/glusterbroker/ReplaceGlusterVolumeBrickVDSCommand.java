package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.constants.GlusterConstants.GLUSTER_TASK_OPERATION;
import org.ovirt.engine.core.common.glustercommands.ReplaceGlusterVolumeBrickVDSParameters;

public class ReplaceGlusterVolumeBrickVDSCommand extends GlusterBrokerCommand<ReplaceGlusterVolumeBrickVDSParameters> {

    public ReplaceGlusterVolumeBrickVDSCommand(ReplaceGlusterVolumeBrickVDSParameters parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteIrsBrokerCommand() {
        String sourceBrick = getParameters().getSourceBrick().getQualifiedName();
        String targetBrick = getParameters().getTargetBrick().getQualifiedName();
        GLUSTER_TASK_OPERATION operation = getParameters().getOperation();

        switch (operation) {
        case START:
            status =
                getIrsProxy().glusterVolumeReplaceBrickStart(getParameters().getVolumeName(),
                        sourceBrick,
                        targetBrick);
            break;
        case ABORT:
            status =
                getIrsProxy().glusterVolumeReplaceBrickAbort(getParameters().getVolumeName(),
                        sourceBrick,
                        targetBrick);
            break;
        case STATUS:
            status =
                getIrsProxy().glusterVolumeReplaceBrickStatus(getParameters().getVolumeName(),
                        sourceBrick,
                        targetBrick);
            break;
        case PAUSE:
            status =
                getIrsProxy().glusterVolumeReplaceBrickPause(getParameters().getVolumeName(),
                        sourceBrick,
                        targetBrick);
            break;
        case COMMIT:
            status =
                getIrsProxy().glusterVolumeReplaceBrickCommit(getParameters().getVolumeName(),
                        sourceBrick,
                        targetBrick);
            break;

        }
        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();
    }

}
