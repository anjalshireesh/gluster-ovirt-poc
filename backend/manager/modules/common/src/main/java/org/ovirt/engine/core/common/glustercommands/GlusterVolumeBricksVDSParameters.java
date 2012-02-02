package org.ovirt.engine.core.common.glustercommands;

import java.util.List;

import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.compat.Guid;

public class GlusterVolumeBricksVDSParameters extends GlusterVolumeVDSParameters {
    private final List<GlusterBrickEntity> bricks;

    public GlusterVolumeBricksVDSParameters(Guid storagePoolId, String volumeName, List<GlusterBrickEntity> bricks) {
        super(storagePoolId, volumeName);
        this.bricks = bricks;
    }

    public List<GlusterBrickEntity> getBricks() {
        return bricks;
    }
}
