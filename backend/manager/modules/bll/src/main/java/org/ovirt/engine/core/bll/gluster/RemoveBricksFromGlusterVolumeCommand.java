package org.ovirt.engine.core.bll.gluster;

import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.glusteractions.GlusterVolumeBricksParameters;
import org.ovirt.engine.core.common.glustercommands.GlusterVolumeBricksVDSParameters;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VDSReturnValue;
import org.ovirt.engine.core.dal.VdcBllMessages;

public class RemoveBricksFromGlusterVolumeCommand extends GlusterCommandBase<GlusterVolumeBricksParameters> {

    public RemoveBricksFromGlusterVolumeCommand(GlusterVolumeBricksParameters params) {
        super(params);
    }

    @Override
    protected boolean canDoAction() {
        addCanDoActionMessage(VdcBllMessages.VAR__ACTION__REMOVE);
        addCanDoActionMessage(VdcBllMessages.VAR__TYPE__GLUSTER_BRICK);
        return super.canDoAction();
    }

    @Override
    protected void executeCommand() {
        VDSReturnValue returnValue =
            Backend.getInstance()
            .getResourceManager()
            .RunVdsCommand(VDSCommandType.RemoveBricksFromGlusterVolume,
                    new GlusterVolumeBricksVDSParameters(getOnlineHost().getId(),
                            getParameters().getVolumeName(),
                            getParameters().getBricks()));
        setSucceeded(returnValue.getSucceeded());
    }

    @Override
    public AuditLogType getAuditLogTypeValue() {
        if(getSucceeded()) {
            return AuditLogType.GLUSTER_VOLUME_REMOVE_BRICK;
        } else {
            return AuditLogType.GLUSTER_VOLUME_REMOVE_BRICK_FAILED;
        }
    }
}
