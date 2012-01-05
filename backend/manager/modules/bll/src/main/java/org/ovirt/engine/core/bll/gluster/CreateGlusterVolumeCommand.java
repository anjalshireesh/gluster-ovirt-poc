/**
 * 
 */
package org.ovirt.engine.core.bll.gluster;

import java.util.Collections;
import java.util.Map;

import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.bll.CommandBase;
import org.ovirt.engine.core.bll.MultiLevelAdministrationHandler;
import org.ovirt.engine.core.common.VdcObjectType;
import org.ovirt.engine.core.common.action.CreateGlusterVolumeParameters;
import org.ovirt.engine.core.common.vdscommands.CreateGlusterVolumeVDSParameters;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VDSReturnValue;

/**
 *
 */
public class CreateGlusterVolumeCommand<T extends CreateGlusterVolumeParameters> extends CommandBase<CreateGlusterVolumeParameters> {

    CreateGlusterVolumeCommand(T params) {
        super(params);
    }

    @Override
    protected boolean canDoAction() {
        return super.canDoAction();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ovirt.engine.core.bll.CommandBase#executeCommand()
     */
    @Override
    protected void executeCommand() {
        VDSReturnValue returnValue = Backend
                .getInstance()
                .getResourceManager()
                .RunVdsCommand(
                        VDSCommandType.CreateGlusterVolume,
                        new CreateGlusterVolumeVDSParameters(getParameters().getStoragePoolId(),
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
        //return Collections.singletonMap(MultiLevelAdministrationHandler.SYSTEM_OBJECT_ID, VdcObjectType.System);
        // TODO: What do we return if we want to allow any user who has "Create Volume" permission at SYSTEM level?
        return null;
    }
}
