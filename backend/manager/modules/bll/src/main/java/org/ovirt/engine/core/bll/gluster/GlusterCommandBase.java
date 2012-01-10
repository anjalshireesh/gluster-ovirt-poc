package org.ovirt.engine.core.bll.gluster;

import org.ovirt.engine.core.bll.VdsGroupCommandBase;
import org.ovirt.engine.core.common.action.VdsGroupParametersBase;
import org.ovirt.engine.core.dal.VdcBllMessages;

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
            addCanDoActionMessage(VdcBllMessages.VAR__TYPE__GLUSTER_VOLUME);
        }

        if(canDoAction && getVdsGroup().getstorage_pool_id() == null) {
            canDoAction = false;
            addCanDoActionMessage(VdcBllMessages.ACTION_TYPE_FAILED_STORAGE_POOL_NOT_EXIST);
            addCanDoActionMessage(VdcBllMessages.VAR__TYPE__GLUSTER_VOLUME);
        }
        return canDoAction;
    }
}
