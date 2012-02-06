package org.ovirt.engine.core.bll.gluster;

import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.glusteractions.GlusterVolumeParameters;
import org.ovirt.engine.core.common.glustercommands.GlusterVolumeVDSParameters;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VDSReturnValue;
import org.ovirt.engine.core.dal.VdcBllMessages;

public class DeleteGlusterVolumeCommand extends GlusterCommandBase<GlusterVolumeParameters> {

    public DeleteGlusterVolumeCommand(GlusterVolumeParameters params) {
        super(params);
        setGlusterVolumeName(getParameters().getVolumeName());
    }

    @Override
    protected boolean canDoAction() {
        addCanDoActionMessage(VdcBllMessages.VAR__ACTION__REMOVE);
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
                                VDSCommandType.DeleteGlusterVolume,
                                new GlusterVolumeVDSParameters(getVdsGroup().getstorage_pool_id().getValue(),
                                        getParameters().getVolumeName()));
        setSucceeded(returnValue.getSucceeded());
    }

    @Override
    public AuditLogType getAuditLogTypeValue() {
        if(getSucceeded()) {
            return AuditLogType.GLUSTER_VOLUME_DELETE;
        } else {
            return AuditLogType.GLUSTER_VOLUME_DELETE_FAILED;
        }
    }

}
