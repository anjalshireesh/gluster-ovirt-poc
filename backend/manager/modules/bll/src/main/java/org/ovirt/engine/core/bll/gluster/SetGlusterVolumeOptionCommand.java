package org.ovirt.engine.core.bll.gluster;

import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.errors.VdcBLLException;
import org.ovirt.engine.core.common.glusteractions.GlusterVolumeOptionParameters;
import org.ovirt.engine.core.common.glustercommands.GlusterVolumeOptionVDSParameters;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VDSReturnValue;
import org.ovirt.engine.core.dal.VdcBllMessages;

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
            VDSReturnValue returnValue =
                    Backend
                            .getInstance()
                            .getResourceManager()
                            .RunVdsCommand(
                                    VDSCommandType.SetGlusterVolumeOption,
                                    new GlusterVolumeOptionVDSParameters(getOnlineHost().getvds_id(),
                                            getParameters().getVolumeName(), getParameters().getVolumeOption()));
            setSucceeded(returnValue.getSucceeded());
        } catch (VdcBLLException e) {
            getReturnValue().getExecuteFailedMessages().add(e.getErrorCode().toString());
            setSucceeded(false);
        }
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
