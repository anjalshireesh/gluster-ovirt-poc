package org.ovirt.engine.core.vdsbroker.vdsbroker;

import java.util.Map;

import org.ovirt.engine.core.common.businessentities.GlusterTaskStatusEntity;
import org.ovirt.engine.core.utils.ObjectDescriptor;
import org.ovirt.engine.core.vdsbroker.irsbroker.StatusReturnForXmlRpc;

public final class GlusterTaskStatusReturnForXmlRpc extends StatusReturnForXmlRpc {
    //private static final String GLUSTER_TASK = "task";
    private static final String TASK_STATE = "rebalance"; //"task_state";
    private static final String TASK_MESSAGE = "message";

    private GlusterTaskStatusEntity glusterTaskStatusEntity;

    @Override
    public String toString() {
        return ObjectDescriptor.toString(this);
    }

    public GlusterTaskStatusReturnForXmlRpc(Map<String, Object> innerMap) {
        super(innerMap);
        glusterTaskStatusEntity = new GlusterTaskStatusEntity();
        glusterTaskStatusEntity.setTaskState((String) innerMap.get(TASK_STATE));
        glusterTaskStatusEntity.setTaskMessage((String) innerMap.get(TASK_MESSAGE));
    }

    public GlusterTaskStatusEntity getGlusterTaskStatusEntity() {
        return glusterTaskStatusEntity;
    }

}
