package org.ovirt.engine.core.bll.gluster;

import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.errors.VdcBLLException;
import org.ovirt.engine.core.common.glusteractions.GlusterVolumeParameters;
import org.ovirt.engine.core.common.glustercommands.GlusterVolumeVDSParameters;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VDSReturnValue;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.dal.VdcBllMessages;
import org.ovirt.engine.core.dal.dbbroker.DbFacade;
import org.ovirt.engine.core.utils.transaction.TransactionMethod;
import org.ovirt.engine.core.utils.transaction.TransactionSupport;

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
        try {
            TransactionSupport.executeInNewTransaction(new TransactionMethod<Void>() {

                @Override
                public Void runInTransaction() {
                    deleteGlusterVolumeInDb(getVdsGroupId(), getParameters().getVolumeName());

                    VDSReturnValue returnValue =
                            Backend
                                    .getInstance()
                                    .getResourceManager()
                                    .RunVdsCommand(
                                            VDSCommandType.DeleteGlusterVolume,
                                            new GlusterVolumeVDSParameters(getOnlineHost().getId(),
                                                    getParameters().getVolumeName()));
                    setSucceeded(returnValue.getSucceeded());
                    return null;
                }
            });
        } catch (VdcBLLException e) {
            getReturnValue().getExecuteFailedMessages().add(e.getErrorCode().toString());
        }
    }

    private void deleteGlusterVolumeInDb(Guid vdsGroupId, String volumeName) {
        DbFacade.getInstance().getGlusterVolumeDAO().deleteGlusterVolumeByName(vdsGroupId, volumeName);
    }

    @Override
    public AuditLogType getAuditLogTypeValue() {
        if (getSucceeded()) {
            return AuditLogType.GLUSTER_VOLUME_DELETE;
        } else {
            return AuditLogType.GLUSTER_VOLUME_DELETE_FAILED;
        }
    }
}
