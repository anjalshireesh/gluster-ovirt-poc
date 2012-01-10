package org.ovirt.engine.core.common.glustercommands;

import org.ovirt.engine.core.compat.Guid;

public class AddBricksToGlusterVolumeVDSParameters extends GlusterBaseVDSCommandParameters {
    private String volumeName;
    private String[] bricks;

    public AddBricksToGlusterVolumeVDSParameters(Guid storagePoolId, String volumeName, String[] bricks) {
        super(storagePoolId);
    }

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }

    public String[] getBricks() {
        return bricks;
    }

    public void setBricks(String[] bricks) {
        this.bricks = bricks;
    }
}
