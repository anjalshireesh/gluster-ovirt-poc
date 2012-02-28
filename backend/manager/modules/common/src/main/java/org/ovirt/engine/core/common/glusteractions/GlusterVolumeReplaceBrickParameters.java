package org.ovirt.engine.core.common.glusteractions;

import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.common.businessentities.GlusterTaskOperation;
import org.ovirt.engine.core.compat.Guid;

public class GlusterVolumeReplaceBrickParameters extends GlusterVolumeParameters {

    private GlusterBrickEntity sourceBrick;
    private GlusterBrickEntity targetBrick;
    private GlusterTaskOperation operation;

    public GlusterVolumeReplaceBrickParameters(Guid vdsGroupId,
            String volumeName,
            GlusterBrickEntity sourceBrick,
            GlusterBrickEntity targetBrick,
            GlusterTaskOperation operation) {
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

    public void setOperation(GlusterTaskOperation operation) {
        this.operation = operation;
    }

    public GlusterTaskOperation getOperation() {
        return operation;
    }
}
