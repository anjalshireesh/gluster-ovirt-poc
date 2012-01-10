package org.ovirt.engine.core.bll.gluster;

import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.common.glusteractions.AddBricksToGlusterVolumeParameters;
import org.ovirt.engine.core.common.glustercommands.AddBricksToGlusterVolumeVDSParameters;
import org.ovirt.engine.core.common.utils.StringUtil;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VDSReturnValue;
import org.ovirt.engine.core.dal.VdcBllMessages;

public class AddBricksToGlusterVolumeAction extends GlusterCommandBase<AddBricksToGlusterVolumeParameters> {

    public AddBricksToGlusterVolumeAction(AddBricksToGlusterVolumeParameters params) {
        super(params);
    }

    @Override
    protected boolean canDoAction() {
        addCanDoActionMessage(VdcBllMessages.VAR__ACTION__ADD);
        addCanDoActionMessage(VdcBllMessages.VAR__TYPE__GLUSTER_BRICK);
        return super.canDoAction();
    }

    @Override
    protected void executeCommand() {
        VDSReturnValue returnValue =
                Backend
                        .getInstance()
                        .getResourceManager()
                        .RunVdsCommand(
                                VDSCommandType.AddBricksToGlusterVolume,
                                new AddBricksToGlusterVolumeVDSParameters(getVdsGroup().getstorage_pool_id().getValue(),
                                        getParameters().getVolumeName(),
                                        StringUtil.extractList(getParameters().getBricks(), ",").toArray(new String[0])));
        setSucceeded(returnValue.getSucceeded());
    }
}
