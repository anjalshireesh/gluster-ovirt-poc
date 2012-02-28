package org.ovirt.engine.core.common.glustercommands;

import org.ovirt.engine.core.common.businessentities.GlusterTaskOperation;
import org.ovirt.engine.core.compat.Guid;

public class RebalanceGlusterVolumeVDSParameters extends GlusterVolumeVDSParameters {
    private GlusterTaskOperation operation;
    private Boolean fixLayoutOnly;
    private Boolean force;
    private String FIX_LAYOUT = "fix-layout";

    public RebalanceGlusterVolumeVDSParameters(Guid storagePoolId,
            String volumeName,
            GlusterTaskOperation operation,
            Boolean fixLayoutOnly,
            Boolean force) {
        super(storagePoolId, volumeName);
        setOperation(operation);
        setFixLayoutOnly(fixLayoutOnly);
        setForce(force);
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

    public String getRebalanceMode() {
        return (fixLayoutOnly) ? FIX_LAYOUT : "";
    }

    public void setForce(Boolean force) {
        this.force = force;
    }

    public Boolean isForce() {
        return force;
    }
}
