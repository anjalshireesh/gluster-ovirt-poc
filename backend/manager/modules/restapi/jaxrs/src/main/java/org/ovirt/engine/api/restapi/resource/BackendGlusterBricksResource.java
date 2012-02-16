/**
 *
 */
package org.ovirt.engine.api.restapi.resource;

import javax.ws.rs.core.Response;

import org.ovirt.engine.api.model.GlusterBrick;
import org.ovirt.engine.api.model.GlusterBricks;
import org.ovirt.engine.api.model.GlusterVolume;
import org.ovirt.engine.api.resource.GlusterVolumeBricksResource;
import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;

/**
 *
 */
public class BackendGlusterBricksResource extends AbstractBackendCollectionResource<GlusterBrick, GlusterBrickEntity>
implements GlusterVolumeBricksResource {

    private final BackendGlusterVolumeResource parent;
    private final String clusterId;

    public BackendGlusterBricksResource(String clusterId, BackendGlusterVolumeResource parent) {
        super(GlusterBrick.class, GlusterBrickEntity.class);
        this.parent = parent;
        this.clusterId = clusterId;
    }

    @Override
    public GlusterBricks list() {
        GlusterVolume volume = parent.get();
        return volume.getGlusterBricks();
    }

    public String getClusterId() {
        return clusterId;
    }

    @Override
    protected Response performRemove(String id) {
        // TODO Auto-generated method stub
        return null;
    }
}
