package org.ovirt.engine.core.common.glusteractions;

import java.util.List;

import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.compat.Guid;

public class GlusterVolumeBricksParameters extends GlusterVolumeParameters {

    private List<GlusterBrickEntity> bricks;

    public GlusterVolumeBricksParameters(Guid clusterId, String volumeName, List<GlusterBrickEntity> Bricks) {
        super(clusterId, volumeName);
        setBricks(Bricks);
    }

    public void setBricks(List<GlusterBrickEntity> bricks) {
        this.bricks = bricks;
    }
    public List<GlusterBrickEntity> getBricks() {
        return bricks;
    }


}
