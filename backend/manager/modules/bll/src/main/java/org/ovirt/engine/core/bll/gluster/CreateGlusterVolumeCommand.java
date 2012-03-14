/**
 *
 */
package org.ovirt.engine.core.bll.gluster;

import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.ACCESS_PROTOCOL;
import org.ovirt.engine.core.common.glusteractions.CreateGlusterVolumeParameters;
import org.ovirt.engine.core.common.glustercommands.CreateGlusterVolumeVDSParameters;
import org.ovirt.engine.core.common.interfaces.VDSBrokerFrontend;
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
        TransactionSupport
                .executeInNewTransaction(new TransactionMethod<Void>() {
                    @Override
                    public Void runInTransaction() {
                        GlusterVolumeEntity volume = getParameters().getVolume();
                        if(volume.getAccessProtocols() == null) {
                            volume.setAccessProtocol(ACCESS_PROTOCOL.GLUSTER);
                        }

                        VDSBrokerFrontend vdsBroker = Backend.getInstance()
                                .getResourceManager();

                        VDSReturnValue returnValue = vdsBroker.RunVdsCommand(
                                VDSCommandType.CreateGlusterVolume,
                                new CreateGlusterVolumeVDSParameters(
                                        getOnlineHost().getvds_id(), volume));

                        setSucceeded(returnValue.getSucceeded());
                        if (!getSucceeded()) {
                            return null;
                        }

                        addVolumeToDb((GlusterVolumeEntity) returnValue.getReturnValue());

                        // TODO: What is compensation all about? needs to be understood.
                        // getCompensationContext().stateChanged();

                        return null;
                    }
                });
    }

    private void addVolumeToDb(GlusterVolumeEntity createdVolume) {
        // volume fetched from VDSM doesn't contain cluster id GlusterFS is not aware of multiple clusters
        createdVolume.setClusterId(getVdsGroupId());

        DbFacade.getInstance().getGlusterVolumeDAO().save(createdVolume);
    }


    @Override
    public AuditLogType getAuditLogTypeValue() {
        if (getSucceeded()) {
            return AuditLogType.GLUSTER_VOLUME_CREATE;
        } else {
            return AuditLogType.GLUSTER_VOLUME_CREATE_FAILED;
        }
    }
}
