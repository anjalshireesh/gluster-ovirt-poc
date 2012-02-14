package org.ovirt.engine.core.common.glustercommands;

import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.common.constants.GlusterConstants.VOLUME_OPERATION;
import org.ovirt.engine.core.compat.Guid;

public class ReplaceGlusterVolumeBrickVDSParameters extends GlusterVolumeVDSParameters {
    private final GlusterBrickEntity sourceBrick;
    private final GlusterBrickEntity targetBrick;
    private final VOLUME_OPERATION operation;

    public ReplaceGlusterVolumeBrickVDSParameters(Guid storagePoolId,
            String volumeName,
            GlusterBrickEntity sourceBrick,
            GlusterBrickEntity targetBrick,
            VOLUME_OPERATION operation) {
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

    public VOLUME_OPERATION getOperation() {
        return operation;
    }
}
