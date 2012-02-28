package org.ovirt.engine.core.bll.gluster;

import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.businessentities.GlusterTaskOperation;
import org.ovirt.engine.core.common.glusteractions.GlusterVolumeReplaceBrickParameters;
import org.ovirt.engine.core.common.glustercommands.ReplaceGlusterVolumeBrickVDSParameters;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VDSReturnValue;
import org.ovirt.engine.core.dal.VdcBllMessages;

public class ReplaceGlusterVolumeBrickCommand extends GlusterCommandBase<GlusterVolumeReplaceBrickParameters> {

    public ReplaceGlusterVolumeBrickCommand(GlusterVolumeReplaceBrickParameters params) {
        super(params);
    }

    @Override
    protected boolean canDoAction() {
        addCanDoActionMessage(VdcBllMessages.VAR__ACTION__REPLACE);
        addCanDoActionMessage(VdcBllMessages.VAR__TYPE__GLUSTER_BRICK);
        return super.canDoAction();
    }

    @Override
    protected void executeCommand() {
        VDSReturnValue returnValue =
            Backend.getInstance()
            .getResourceManager()
            .RunVdsCommand(VDSCommandType.ReplaceGlusterVolumeBrick,
                    new ReplaceGlusterVolumeBrickVDSParameters(getOnlineHost().getvds_id(),
                            getParameters().getVolumeName(),
                            getParameters().getSourceBrick(),
                            getParameters().getTargetBrick(), getParameters().getOperation()));
        setSucceeded(returnValue.getSucceeded());
    }



    @Override
    public AuditLogType getAuditLogTypeValue() {
        GlusterTaskOperation operation = getParameters().getOperation();
        if (getSucceeded()) {
            return getSuccessAuditLogType(operation);
        } else {
            return getFailureAuditLogType(operation);
        }
    }

    private AuditLogType getSuccessAuditLogType(GlusterTaskOperation operation) {
        AuditLogType returnAuditLogType = null;
        switch (operation) {
        case START:
            returnAuditLogType = AuditLogType.GLUSTER_VOLUME_REPLACE_BRICK_START;
            break;
        case ABORT:
            returnAuditLogType = AuditLogType.GLUSTER_VOLUME_REPLACE_BRICK_ABORT;
            break;
        case PAUSE:
            returnAuditLogType = AuditLogType.GLUSTER_VOLUME_REPLACE_BRICK_PAUSE;
            break;
        case COMMIT:
            returnAuditLogType = AuditLogType.GLUSTER_VOLUME_REPLACE_BRICK_COMMIT;
            break;
        }
        return returnAuditLogType;
    }

    private AuditLogType getFailureAuditLogType(GlusterTaskOperation operation) {
        AuditLogType returnAuditLogType = null;
        switch (operation) {
        case START:
            returnAuditLogType = AuditLogType.GLUSTER_VOLUME_REPLACE_BRICK_START_FAILED;
            break;
        case ABORT:
            returnAuditLogType = AuditLogType.GLUSTER_VOLUME_REPLACE_BRICK_ABORT_FAILED;
            break;
        case PAUSE:
            returnAuditLogType = AuditLogType.GLUSTER_VOLUME_REPLACE_BRICK_PAUSE_FAILED;
            break;
        case COMMIT:
            returnAuditLogType = AuditLogType.GLUSTER_VOLUME_REPLACE_BRICK_COMMIT_FAILED;
            break;
        }
        return returnAuditLogType;
    }
}
