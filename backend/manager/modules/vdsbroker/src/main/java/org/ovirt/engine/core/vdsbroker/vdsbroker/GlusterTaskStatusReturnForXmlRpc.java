package org.ovirt.engine.core.vdsbroker.vdsbroker;

import java.util.Map;

import org.ovirt.engine.core.common.businessentities.GlusterTaskStatusEntity;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.utils.ObjectDescriptor;
import org.ovirt.engine.core.vdsbroker.irsbroker.StatusReturnForXmlRpc;

public final class GlusterTaskStatusReturnForXmlRpc extends StatusReturnForXmlRpc {
    private static final String GLUSTER_TASK = "task";
    private static final String TASK_STATE = "rebalance"; //"task_state";
    private static final String TASK_MESSAGE = "message";

    private GlusterTaskStatusEntity glusterTaskStatusEntity;
    private String task;
    private String taskState;
    private String taskMessage;

    @Override
    public String toString() {
        return ObjectDescriptor.toString(this);
    }

    public GlusterTaskStatusReturnForXmlRpc(Map<String, Object> innerMap) {
        super(innerMap);
        task = (String) innerMap.get(GLUSTER_TASK);
        taskState = (String) innerMap.get(TASK_STATE);
        taskMessage = (String) innerMap.get(TASK_MESSAGE);
        setGlusterTaskStatusEntity();
    }

    public void setGlusterTaskStatusEntity() {
        glusterTaskStatusEntity = new GlusterTaskStatusEntity();
        glusterTaskStatusEntity.setId(Guid.NewGuid());
        glusterTaskStatusEntity.setCode(mStatus.mCode);
        glusterTaskStatusEntity.setMessage(mStatus.mMessage);
        glusterTaskStatusEntity.setName(task);
        glusterTaskStatusEntity.setTaskState(taskState);
        glusterTaskStatusEntity.setTaskMessage(taskMessage);
    }

    public GlusterTaskStatusEntity getGlusterTaskStatusEntity() {
        return glusterTaskStatusEntity;
    }

}
