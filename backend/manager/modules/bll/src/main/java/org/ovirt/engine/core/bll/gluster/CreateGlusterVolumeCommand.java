/**
 *
 */
package org.ovirt.engine.core.bll.gluster;

import java.util.Collections;
import java.util.Map;

import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.bll.CommandBase;
import org.ovirt.engine.core.common.VdcObjectType;
import org.ovirt.engine.core.common.action.CreateGlusterVolumeParameters;
import org.ovirt.engine.core.common.vdscommands.CreateGlusterVolumeVDSParameters;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VDSReturnValue;
import org.ovirt.engine.core.dal.VdcBllMessages;

/**
 *
 */
public class CreateGlusterVolumeCommand<T extends CreateGlusterVolumeParameters> extends CommandBase<CreateGlusterVolumeParameters> {

    CreateGlusterVolumeCommand(T params) {
        super(params);
        setVdsGroupId(params.getVdsGroupId());
    }

    @Override
    protected boolean canDoAction() {
        boolean canDoAction = super.canDoAction();
        if (canDoAction && getVdsGroup() == null) {
            canDoAction = false;
            addCanDoActionMessage(VdcBllMessages.ACTION_TYPE_FAILED_STORAGE_POOL_NOT_EXIST);
            addCanDoActionMessage(VdcBllMessages.VAR__ACTION__CREATE);
        }
        return canDoAction;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.ovirt.engine.core.bll.CommandBase#executeCommand()
     */
    @Override
    protected void executeCommand() {
        VDSReturnValue returnValue =
                Backend
                        .getInstance()
                        .getResourceManager()
                        .RunVdsCommand(
                                VDSCommandType.CreateGlusterVolume,
                                new CreateGlusterVolumeVDSParameters(getParameters().getVdsGroupId(),
                                        getParameters().getVolume()));
        setSucceeded(returnValue.getSucceeded());
    }

    /*
     * (non-Javadoc)
     *
     * @see org.ovirt.engine.core.bll.CommandBase#getPermissionCheckSubjects()
     */
    @Override
    public Map getPermissionCheckSubjects() {
        return Collections.singletonMap(getVdsGroupId(), VdcObjectType.VdsGroups);
    }
}
