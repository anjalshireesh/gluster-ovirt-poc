/**
 *
 */
package org.ovirt.engine.core.bll.gluster;

import java.util.List;

import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.common.AuditLogType;
import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.common.businessentities.VDS;
import org.ovirt.engine.core.common.errors.VdcBLLException;
import org.ovirt.engine.core.common.errors.VdcBllErrors;
import org.ovirt.engine.core.common.glusteractions.CreateGlusterVolumeParameters;
import org.ovirt.engine.core.common.glustercommands.CreateGlusterVolumeVDSParameters;
import org.ovirt.engine.core.common.interfaces.VDSBrokerFrontend;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VDSReturnValue;
import org.ovirt.engine.core.dal.VdcBllMessages;
import org.ovirt.engine.core.dal.dbbroker.DbFacade;
import org.ovirt.engine.core.dao.VdsDAO;
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
                        VDSBrokerFrontend vdsBroker = Backend.getInstance()
                                .getResourceManager();

                        VDSReturnValue returnValue = vdsBroker.RunVdsCommand(
                                VDSCommandType.CreateGlusterVolume,
                                new CreateGlusterVolumeVDSParameters(
                                        getVdsGroup().getstorage_pool_id()
                                                .getValue(), getParameters()
                                                .getVolume()));

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

        // volume fetched from VDSM doesn't contain host id since GlusterFS is not aware of the host id concept
        updateHostIdsInBricks(createdVolume);

        DbFacade.getInstance().getGlusterVolumeDAO().save(createdVolume);
    }

    private void updateHostIdsInBricks(GlusterVolumeEntity createdVolume) {
        VdsDAO hostDAO = DbFacade.getInstance().getVdsDAO();
        for (GlusterBrickEntity brick : createdVolume.getBricks()) {
            // TODO: UI must send server name without spaces
            String serverName = brick.getServerName().trim();

            // TODO: Should probably introduce a new method to get host with given name/ip from given cluster id
            List<VDS> hosts = hostDAO.getAllForHostname(serverName);
            if (hosts == null || hosts.isEmpty()) {
                hosts = hostDAO.getAllWithIpAddress(serverName);
                if (hosts == null || hosts.isEmpty()) {
                    throw new VdcBLLException(VdcBllErrors.GLUSTER_BRICK_HOST_NOT_FOUND);
                }
            }
            brick.setServerId(hosts.get(0).getvds_id());
        }
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
