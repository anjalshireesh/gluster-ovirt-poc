/**
 *
 */
package org.ovirt.engine.core.bll.gluster;

import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.glusteractions.CreateGlusterVolumeParameters;
import org.ovirt.engine.core.common.glustercommands.CreateGlusterVolumeVDSParameters;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VDSReturnValue;
import org.ovirt.engine.core.dal.VdcBllMessages;
import org.ovirt.engine.core.dal.dbbroker.DbFacade;
import org.ovirt.engine.core.utils.transaction.TransactionMethod;
import org.ovirt.engine.core.utils.transaction.TransactionSupport;

/**
 *
 */
public class CreateGlusterVolumeCommand extends GlusterCommandBase<CreateGlusterVolumeParameters> {

    public CreateGlusterVolumeCommand(CreateGlusterVolumeParameters params) {
        super(params);
        setGlusterVolumeName(getParameters().getVolume().getName());
    }

    @Override
    protected boolean canDoAction() {
        addCanDoActionMessage(VdcBllMessages.VAR__ACTION__CREATE);
        addCanDoActionMessage(VdcBllMessages.VAR__TYPE__GLUSTER_VOLUME);

        return super.canDoAction();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.ovirt.engine.core.bll.CommandBase#executeCommand()
     */
    @Override
    protected void executeCommand() {
        TransactionSupport.executeInNewTransaction(new TransactionMethod<Void>() {
            @Override
            public Void runInTransaction() {
                VDSReturnValue returnValue =
                    Backend
                            .getInstance()
                            .getResourceManager()
                            .RunVdsCommand(
                                    VDSCommandType.CreateGlusterVolume,
                                    new CreateGlusterVolumeVDSParameters(getVdsGroup().getstorage_pool_id().getValue(),
                                            getParameters().getVolume()));

                AddVolumeToDb();
                //TODO: What is compensation all about? needs to be understood.
                //getCompensationContext().stateChanged();

                setSucceeded(returnValue.getSucceeded());
                return null;
            }

            private void AddVolumeToDb() {
                DbFacade.getInstance().getGlusterVolumeDAO().save(getParameters().getVolume());
            }
        });

    }

    @Override
    public AuditLogType getAuditLogTypeValue() {
        if(getSucceeded()) {
            return AuditLogType.GLUSTER_VOLUME_CREATE;
        } else {
            return AuditLogType.GLUSTER_VOLUME_CREATE_FAILED;
        }
    }
}
