package org.ovirt.engine.core.common.businessentities;

public class GlusterTaskStatusEntity  {
    private String taskState;
    private String taskMessage;

    public GlusterTaskStatusEntity() {
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getTaskState() {
        return taskState;
    }

    public String getTaskMessage() {
        return taskMessage;
    }

    public void setTaskMessage(String taskMessage) {
        this.taskMessage = taskMessage;
    }
}
