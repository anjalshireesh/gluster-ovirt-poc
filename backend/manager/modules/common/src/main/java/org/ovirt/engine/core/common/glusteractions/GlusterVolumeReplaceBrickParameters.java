package org.ovirt.engine.core.common.glusteractions;

import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.common.constants.GlusterConstants.VOLUME_OPERATION;
import org.ovirt.engine.core.compat.Guid;

public class GlusterVolumeReplaceBrickParameters extends GlusterVolumeParameters {

    private GlusterBrickEntity sourceBrick;
    private GlusterBrickEntity targetBrick;
    private VOLUME_OPERATION operation;

    public GlusterVolumeReplaceBrickParameters(Guid vdsGroupId,
            String volumeName,
            GlusterBrickEntity sourceBrick,
            GlusterBrickEntity targetBrick,
            VOLUME_OPERATION operation) {
        super(vdsGroupId, volumeName);
        setSourceBrick(sourceBrick);
        setTargetBrick(targetBrick);
        setOperation(operation);
    }

    public void setSourceBrick(GlusterBrickEntity sourceBrick) {
        this.sourceBrick = sourceBrick;
    }

    public GlusterBrickEntity getSourceBrick() {
        return sourceBrick;
    }

    public void setTargetBrick(GlusterBrickEntity targetBrick) {
        this.targetBrick = targetBrick;
    }

    public GlusterBrickEntity getTargetBrick() {
        return targetBrick;
    }

    public void setOperation(VOLUME_OPERATION operation) {
        this.operation = operation;
    }

    public VOLUME_OPERATION getOperation() {
        return operation;
    }
}
