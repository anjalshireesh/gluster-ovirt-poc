package org.ovirt.engine.core.common.glusteractions;

import org.ovirt.engine.core.common.businessentities.GlusterTaskOperation;
import org.ovirt.engine.core.compat.Guid;

public class RebalanceGlusterVolumeParameters extends GlusterVolumeParameters {
    private GlusterTaskOperation operation;
    private Boolean fixLayoutOnly;
    private Boolean force;

    public RebalanceGlusterVolumeParameters(Guid clusterId, String volumeName, GlusterTaskOperation operation, Boolean fixLayoutOnly, Boolean force) {
        super(clusterId, volumeName);
        setOperation(operation);
        setFixLayoutOnly(fixLayoutOnly);
        setForce(force);
    }

    public RebalanceGlusterVolumeParameters(Guid clusterId, String volumeName, GlusterTaskOperation operation) {
        super(clusterId, volumeName);
        setOperation(operation);
        setFixLayoutOnly(false);
        setForce(false);
    }

    public void setOperation(GlusterTaskOperation operation) {
        this.operation = operation;
    }
    public GlusterTaskOperation getOperation() {
        return operation;
    }
    public void setFixLayoutOnly(Boolean fixLayoutOnly) {
        this.fixLayoutOnly = fixLayoutOnly;
    }
    public Boolean isFixLayoutOnly() {
        return fixLayoutOnly;
    }
    public void setForce(Boolean force) {
        this.force = force;
    }
    public Boolean isForce() {
        return force;
    }

}
