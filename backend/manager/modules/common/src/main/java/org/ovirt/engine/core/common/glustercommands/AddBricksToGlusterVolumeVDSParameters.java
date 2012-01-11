package org.ovirt.engine.core.common.glustercommands;

import org.ovirt.engine.core.compat.Guid;

public class AddBricksToGlusterVolumeVDSParameters extends GlusterBaseVDSCommandParameters {
    private final String volumeName;
    private final String[] bricks;

    public AddBricksToGlusterVolumeVDSParameters(Guid storagePoolId, String volumeName, String[] bricks) {
        super(storagePoolId);
        this.volumeName = volumeName;
        this.bricks = bricks;
    }

    public String getVolumeName() {
        return volumeName;
    }

    public String[] getBricks() {
        return bricks;
    }
}
