package org.ovirt.engine.core.bll.gluster;

import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.glusteractions.RebalanceGlusterVolumeParameters;
import org.ovirt.engine.core.common.glustercommands.RebalanceGlusterVolumeVDSParameters;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VDSReturnValue;
import org.ovirt.engine.core.dal.VdcBllMessages;

public class RebalanceGlusterVolumeCommand extends GlusterCommandBase<RebalanceGlusterVolumeParameters> {

    public RebalanceGlusterVolumeCommand(RebalanceGlusterVolumeParameters params) {
        super(params);
        setGlusterVolumeName(params.getVolumeName());
    }

    @Override
    protected boolean canDoAction() {
        addCanDoActionMessage(VdcBllMessages.VAR__ACTION__REBALANCE_OPERATION);
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
                                VDSCommandType.RebalanceGlusterVolume,
                                new RebalanceGlusterVolumeVDSParameters(getOnlineHost().getvds_id(),
                                        getParameters().getVolumeName(),
                                        getParameters().getOperation(),
                                        getParameters().getRebalanceOption(),
                                        getParameters().isForce()
                                ));
        getReturnValue().setActionReturnValue(returnValue.getReturnValue());
        setSucceeded(returnValue.getSucceeded());
    }


    @Override
    public AuditLogType getAuditLogTypeValue() {
        switch(getParameters().getOperation()) {

        case START:
            if (getSucceeded()) {
                return AuditLogType.GLUSTER_VOLUME_REBALANCE_START;
            } else {
                return AuditLogType.GLUSTER_VOLUME_REBALANCE_START_FAILED;
            }

        case STOP:
            if (getSucceeded()) {
                return AuditLogType.GLUSTER_VOLUME_REBALANCE_STOP;
            } else {
                return AuditLogType.GLUSTER_VOLUME_REBALANCE_STOP_FAILED;
            }

        case STATUS:
            if (getSucceeded()) {
                return AuditLogType.GLUSTER_VOLUME_REBALANCE_STATUS;
            } else {
                return AuditLogType.GLUSTER_VOLUME_REBALANCE_STATUS_FAILED;
            }
        }
        return null;
    }
}
