package org.ovirt.engine.core.common.glusteractions;

import org.ovirt.engine.core.common.constants.GlusterConstants.GLUSTER_TASK_OPERATION;
import org.ovirt.engine.core.compat.Guid;

public class RebalanceGlusterVolumeParameters extends GlusterVolumeParameters {

    private GLUSTER_TASK_OPERATION operation;
    private String rebalanceOption;
    private Boolean force;

    public RebalanceGlusterVolumeParameters(Guid clusterId, String volumeName, GLUSTER_TASK_OPERATION operation, String rebalanceOption, Boolean force) {
        super(clusterId, volumeName);
        setOperation(operation);
        setRebalanceOption(rebalanceOption);
        setForce(force);
    }

    public RebalanceGlusterVolumeParameters(Guid clusterId, String volumeName, GLUSTER_TASK_OPERATION operation) {
        super(clusterId, volumeName);
        setOperation(operation);
        setRebalanceOption("");
        setForce(false);
    }

    public void setOperation(GLUSTER_TASK_OPERATION operation) {
        this.operation = operation;
    }
    public GLUSTER_TASK_OPERATION getOperation() {
        return operation;
    }
    public void setRebalanceOption(String rebalanceOption) {
        this.rebalanceOption = rebalanceOption;
    }
    public String getRebalanceOption() {
        return rebalanceOption;
    }
    public void setForce(Boolean force) {
        this.force = force;
    }
    public Boolean isForce() {
        return force;
    }

}
