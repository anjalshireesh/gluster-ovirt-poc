package org.ovirt.engine.core.common.glusteractions;

import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
<<<<<<< HEAD
import org.ovirt.engine.core.common.constants.GlusterConstants.GLUSTER_TASK_OPERATION;
=======
import org.ovirt.engine.core.common.constants.GlusterConstants.VOLUME_OPERATION;
>>>>>>> a502934177dd603f83b8e3e1c41b8e91e3968a98
import org.ovirt.engine.core.compat.Guid;

public class GlusterVolumeReplaceBrickParameters extends GlusterVolumeParameters {

    private GlusterBrickEntity sourceBrick;
    private GlusterBrickEntity targetBrick;
    private GLUSTER_TASK_OPERATION operation;

    public GlusterVolumeReplaceBrickParameters(Guid vdsGroupId,
            String volumeName,
            GlusterBrickEntity sourceBrick,
            GlusterBrickEntity targetBrick,
            GLUSTER_TASK_OPERATION operation) {
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

    public void setOperation(GLUSTER_TASK_OPERATION operation) {
        this.operation = operation;
    }

    public GLUSTER_TASK_OPERATION getOperation() {
        return operation;
    }
}
