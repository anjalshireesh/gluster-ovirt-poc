package org.ovirt.engine.core.bll.gluster;

import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.VOLUME_STATUS;
import org.ovirt.engine.core.common.glusteractions.GlusterVolumeParameters;
import org.ovirt.engine.core.common.glustercommands.GlusterVolumeVDSParameters;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VDSReturnValue;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.dal.VdcBllMessages;
import org.ovirt.engine.core.dal.dbbroker.DbFacade;
import org.ovirt.engine.core.utils.transaction.TransactionMethod;
import org.ovirt.engine.core.utils.transaction.TransactionSupport;

public class StopGlusterVolumeCommand extends GlusterCommandBase<GlusterVolumeParameters> {

    public StopGlusterVolumeCommand(GlusterVolumeParameters params) {
        super(params);
        setGlusterVolumeName(getParameters().getVolumeName());
    }

    @Override
    protected boolean canDoAction() {
        addCanDoActionMessage(VdcBllMessages.VAR__ACTION__STOP);
        addCanDoActionMessage(VdcBllMessages.VAR__TYPE__GLUSTER_VOLUME);
        return super.canDoAction();
    }

    @Override
    protected void executeCommand() {
        TransactionSupport.executeInNewTransaction(new TransactionMethod<Void>() {

            @Override
            public Void runInTransaction() {
                updateVolumeStatusInDb(getVdsGroupId(), getParameters().getVolumeName());

                VDSReturnValue returnValue =
                        Backend
                                .getInstance()
                                .getResourceManager()
                                .RunVdsCommand(
                                        VDSCommandType.StopGlusterVolume,
                                        new GlusterVolumeVDSParameters(getOnlineHost().getvds_id(),
                                                getParameters().getVolumeName()));
                setSucceeded(returnValue.getSucceeded());

                return null;
            }
        });
    }

    private void updateVolumeStatusInDb(Guid vdsGroupId, String volumeName) {
        DbFacade.getInstance().getGlusterVolumeDAO().updateVolumeStatus(vdsGroupId, volumeName, VOLUME_STATUS.OFFLINE);
    }

    @Override
    public AuditLogType getAuditLogTypeValue() {
        if(getSucceeded()) {
            return AuditLogType.GLUSTER_VOLUME_STOP;
        } else {
            return AuditLogType.GLUSTER_VOLUME_STOP_FAILED;
        }
    }
}
