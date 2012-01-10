package org.ovirt.engine.core.common.glusteractions;

import org.ovirt.engine.core.compat.Guid;

public class AddBricksToGlusterVolumeParameters extends GlusterParametersBase {
    private String volumeName;
    private String bricks;

    public AddBricksToGlusterVolumeParameters(Guid vdsGroupId, String volumeName, String bricks) {
        super(vdsGroupId);
        setVolumeName(volumeName);
        setBricks(bricks);
    }

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }

    public String getBricks() {
        return bricks;
    }

    public void setBricks(String bricks) {
        this.bricks = bricks;
    }
}
