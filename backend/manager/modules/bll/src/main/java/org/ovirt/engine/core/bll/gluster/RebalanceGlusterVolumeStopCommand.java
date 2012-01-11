package org.ovirt.engine.core.bll.gluster;

import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.common.glusteractions.GlusterVolumeParameters;
import org.ovirt.engine.core.common.glustercommands.GlusterVolumeVDSParameters;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VDSReturnValue;
import org.ovirt.engine.core.dal.VdcBllMessages;

public class RebalanceGlusterVolumeStopCommand extends GlusterCommandBase<GlusterVolumeParameters> {

    public RebalanceGlusterVolumeStopCommand(GlusterVolumeParameters params) {
        super(params);
    }

    @Override
    protected boolean canDoAction() {
        addCanDoActionMessage(VdcBllMessages.VAR__ACTION__REBALANCE_STOP);
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
                                VDSCommandType.RebalanceGlusterVolumeStop,
                                new GlusterVolumeVDSParameters(getVdsGroup().getstorage_pool_id().getValue(),
                                        getParameters().getVolumeName()));
        setSucceeded(returnValue.getSucceeded());
    }
}
