package org.ovirt.engine.api.restapi.types;

import org.ovirt.engine.api.model.GlusterBrick;
import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;

public class GlusterBrickMapper {
    @Mapping(from = GlusterBrick.class, to = GlusterBrickEntity.class)
    public static GlusterBrickEntity map(GlusterBrick fromBrick, GlusterBrickEntity toBrick) {
        GlusterBrickEntity brick = (toBrick == null) ? new GlusterBrickEntity() : toBrick;
        brick.setServerName(fromBrick.getHost());
        brick.setBrickDirectory(fromBrick.getBrickDirectory());
        return brick;
    }

    @Mapping(from = GlusterBrickEntity.class, to = GlusterBrick.class)
    public static GlusterBrick map(GlusterBrickEntity fromBrick, GlusterBrick toBrick) {
        GlusterBrick brick = (toBrick == null) ? new GlusterBrick() : toBrick;
        brick.setHost(fromBrick.getServerName());
        brick.setBrickDirectory(fromBrick.getBrickDirectory());
        return brick;
    }
}
