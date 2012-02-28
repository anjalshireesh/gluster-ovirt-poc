/*******************************************************************************
 * Copyright (c) 2006-2011 Gluster, Inc. <http://www.gluster.com>
 * This file is part of Gluster Management Console.
 *
 * Gluster Management Console is free software; you can redistribute
 * it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Gluster Management Console is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.ovirt.engine.core.common.businessentities;

import org.ovirt.engine.core.compat.Guid;

public class GlusterTaskStatusEntity extends GlusterEntity implements BusinessEntity<Guid> {
    private String taskState;
    private String taskMessage;
    private int code;
    private String message;

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

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void copyFrom(GlusterTaskStatusEntity taskStatus) {
        setName(taskStatus.getName());
        setTaskState(taskStatus.getTaskState());
        setTaskMessage(taskStatus.getTaskMessage());
        setCode(taskStatus.getCode());
        setMessage(taskStatus.getMessage());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GlusterTaskStatusEntity)) {
            return false;
        }
        GlusterTaskStatusEntity status = (GlusterTaskStatusEntity) obj;

        if (!(getCode() == status.getCode() || getMessage().equals(status.getMessage())
                || getName().equals(status.getName()) || getTaskState().equals(status.getTaskState())
                || getTaskMessage().equals(status.getTaskMessage()))) {
            return false;
        }
        return true;
    }
}
