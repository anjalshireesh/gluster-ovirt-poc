package org.ovirt.engine.core.bll.gluster;

import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeOption;
import org.ovirt.engine.core.common.errors.VdcBLLException;
import org.ovirt.engine.core.common.glusteractions.GlusterVolumeOptionParameters;
import org.ovirt.engine.core.common.glustercommands.GlusterVolumeOptionVDSParameters;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VDSReturnValue;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.dal.VdcBllMessages;
import org.ovirt.engine.core.dal.dbbroker.DbFacade;
import org.ovirt.engine.core.utils.transaction.TransactionMethod;
import org.ovirt.engine.core.utils.transaction.TransactionSupport;

public class SetGlusterVolumeOptionCommand extends GlusterCommandBase<GlusterVolumeOptionParameters> {

    public SetGlusterVolumeOptionCommand(GlusterVolumeOptionParameters params) {
        super(params);
        setGlusterVolumeName(getParameters().getVolumeName());
    }

    @Override
    protected boolean canDoAction() {
        addCanDoActionMessage(VdcBllMessages.VAR__ACTION__SET);
        addCanDoActionMessage(VdcBllMessages.VAR__TYPE__GLUSTER_VOLUME_OPTION);

        return super.canDoAction();
    }

    @Override
    protected void executeCommand() {
        try {
            TransactionSupport.executeInNewTransaction(new TransactionMethod<Void>() {

                @Override
                public Void runInTransaction() {
                    updateGlusterVolumeOptionInDb(getVdsGroupId(),
                            getParameters().getVolumeName(),
                            getParameters().getVolumeOption());
                    VDSReturnValue returnValue =
                            Backend
                                    .getInstance()
                                    .getResourceManager()
                                    .RunVdsCommand(
                                            VDSCommandType.SetGlusterVolumeOption,
                                            new GlusterVolumeOptionVDSParameters(getOnlineHost().getvds_id(),
                                                    getParameters().getVolumeName(), getParameters().getVolumeOption()));
                    setSucceeded(returnValue.getSucceeded());
                    return null;
                }
            });
        } catch (VdcBLLException e) {
            getReturnValue().getExecuteFailedMessages().add(e.getErrorCode().toString());
            setSucceeded(false);
        }
    }

    private void updateGlusterVolumeOptionInDb(Guid vdsGroupId, String volumeName, GlusterVolumeOption volumeOption) {
        GlusterVolumeEntity volume = DbFacade.getInstance().getGlusterVolumeDAO().getByName(vdsGroupId, volumeName);
        DbFacade.getInstance().getGlusterVolumeDAO().setVolumeOption(volume.getId(), volumeOption);
    }

    @Override
    public AuditLogType getAuditLogTypeValue() {
        if (getSucceeded()) {
            return AuditLogType.GLUSTER_VOLUME_OPTION_SET;
        } else {
            return AuditLogType.GLUSTER_VOLUME_OPTION_SET_FAILED;
        }
    }
}
