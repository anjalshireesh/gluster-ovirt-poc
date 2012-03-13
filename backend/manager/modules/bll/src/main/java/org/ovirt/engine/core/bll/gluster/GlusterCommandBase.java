package org.ovirt.engine.core.bll.gluster;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.ovirt.engine.core.bll.VdsGroupCommandBase;
import org.ovirt.engine.core.common.VdcObjectType;
import org.ovirt.engine.core.common.action.VdsGroupParametersBase;
import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.common.businessentities.VDS;
import org.ovirt.engine.core.common.businessentities.VDSStatus;
import org.ovirt.engine.core.common.errors.VdcBLLException;
import org.ovirt.engine.core.common.errors.VdcBllErrors;
import org.ovirt.engine.core.dal.VdcBllMessages;
import org.ovirt.engine.core.dal.dbbroker.DbFacade;
import org.ovirt.engine.core.dao.VdsDAO;

public abstract class GlusterCommandBase<T extends VdsGroupParametersBase> extends VdsGroupCommandBase<T> {
    public GlusterCommandBase(T params) {
        super(params);
        setVdsGroupId(params.getVdsGroupId());
    }

    @Override
    protected boolean canDoAction() {
        boolean canDoAction = super.canDoAction();
        if (canDoAction && getVdsGroup() == null) {
            canDoAction = false;
            addCanDoActionMessage(VdcBllMessages.VDS_CLUSTER_IS_NOT_VALID);
        }

        if(canDoAction && getVdsGroup().getstorage_pool_id() == null) {
            canDoAction = false;
            addCanDoActionMessage(VdcBllMessages.ACTION_TYPE_FAILED_STORAGE_POOL_NOT_EXIST);
        }
        return canDoAction;
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

    public VDS getOnlineHost() {
        List<VDS> hosts =
                DbFacade.getInstance()
                        .getVdsDAO()
                        .getAllForVdsGroupWithStatus(getVdsGroup().getID().getValue(), VDSStatus.Up);
        return hosts.get(0);
    }

    protected void updateHostIdsInBricks(List<GlusterBrickEntity> bricks) {
        VdsDAO hostDAO = DbFacade.getInstance().getVdsDAO();
        for (GlusterBrickEntity brick : bricks) {
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
}
