package org.ovirt.engine.core.common.glustercommands;

import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.common.constants.GlusterConstants.GLUSTER_TASK_OPERATION;
import org.ovirt.engine.core.compat.Guid;

public class ReplaceGlusterVolumeBrickVDSParameters extends GlusterVolumeVDSParameters {
    private final GlusterBrickEntity sourceBrick;
    private final GlusterBrickEntity targetBrick;
    private final GLUSTER_TASK_OPERATION operation;

    public ReplaceGlusterVolumeBrickVDSParameters(Guid storagePoolId,
            String volumeName,
            GlusterBrickEntity sourceBrick,
            GlusterBrickEntity targetBrick,
            GLUSTER_TASK_OPERATION operation) {
        super(storagePoolId, volumeName);
        this.sourceBrick = sourceBrick;
        this.targetBrick = targetBrick;
        this.operation = operation;
    }

    public GlusterBrickEntity getSourceBrick() {
        return sourceBrick;
    }

    public GlusterBrickEntity getTargetBrick() {
        return targetBrick;
    }

    public GLUSTER_TASK_OPERATION getOperation() {
        return operation;
    }
}
