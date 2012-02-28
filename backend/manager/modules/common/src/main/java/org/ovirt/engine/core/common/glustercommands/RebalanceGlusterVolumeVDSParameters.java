package org.ovirt.engine.core.common.glustercommands;

import org.ovirt.engine.core.common.constants.GlusterConstants.GLUSTER_TASK_OPERATION;
import org.ovirt.engine.core.compat.Guid;

public class RebalanceGlusterVolumeVDSParameters extends GlusterVolumeVDSParameters {
    private GLUSTER_TASK_OPERATION operation;
    private String reblanceOption;
    private Boolean force;

    public RebalanceGlusterVolumeVDSParameters(Guid storagePoolId,
            String volumeName,
            GLUSTER_TASK_OPERATION operation,
            String rebalanceOption,
            Boolean force) {
        super(storagePoolId, volumeName);
        setOperation(operation);
        setReblanceOption(rebalanceOption);
        setForce(force);
    }

    public void setOperation(GLUSTER_TASK_OPERATION operation) {
        this.operation = operation;
    }

    public GLUSTER_TASK_OPERATION getOperation() {
        return operation;
    }

    public void setReblanceOption(String reblanceOption) {
        this.reblanceOption = reblanceOption;
    }

    public String getReblanceOption() {
        return reblanceOption;
    }

    public void setForce(Boolean force) {
        this.force = force;
    }

    public Boolean isForce() {
        return force;
    }
}
