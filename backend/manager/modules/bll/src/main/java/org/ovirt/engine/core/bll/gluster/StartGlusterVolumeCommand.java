package org.ovirt.engine.core.bll.gluster;

import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.glusteractions.GlusterVolumeParameters;
import org.ovirt.engine.core.common.glustercommands.GlusterVolumeVDSParameters;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VDSReturnValue;
import org.ovirt.engine.core.dal.VdcBllMessages;

public class StartGlusterVolumeCommand extends GlusterCommandBase<GlusterVolumeParameters> {

    public StartGlusterVolumeCommand(GlusterVolumeParameters params) {
        super(params);
        setGlusterVolumeName(getParameters().getVolumeName());
    }

    @Override
    protected boolean canDoAction() {
        addCanDoActionMessage(VdcBllMessages.VAR__ACTION__START);
        addCanDoActionMessage(VdcBllMessages.VAR__TYPE__GLUSTER_VOLUME);
        return super.canDoAction();
    }

    @Override
    protected void executeCommand() {
        VDSReturnValue returnValue =
                Backend
                        .getInstance()
                        .getResourceManager()
                        .RunVdsCommand(
                                VDSCommandType.StartGlusterVolume,
                                new GlusterVolumeVDSParameters(getOnlineHost().getvds_id(),
                                        getParameters().getVolumeName()));
        setSucceeded(returnValue.getSucceeded());
    }

    @Override
    public AuditLogType getAuditLogTypeValue() {
        if(getSucceeded()) {
            return AuditLogType.GLUSTER_VOLUME_START;
        } else {
            return AuditLogType.GLUSTER_VOLUME_START_FAILED;
        }
    }
}
