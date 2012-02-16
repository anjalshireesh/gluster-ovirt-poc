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

    @Override
    protected Response performRemove(String id) {
        // TODO Invoke VDSM to remove the brick from the volume
        return null;
    }

    @Override
    public Response add(GlusterBricks glusterbricks) {
        //validateParameters(glusterbricks, "glusterBricks.host", "glusterBricks.brickDirectory");

        try {
            String brickList = "";
            for (GlusterBrick brick : glusterbricks.getGlusterBricks()) {
                String brickQualifiedName = brick.getHost() + ":" + brick.getHost();
                brickList += (brickList.isEmpty() ? "," : "") + brick.getHost() + ":" + brick.getBrickDirectory();
            }
            //            return performAction(VdcActionType.AddBricksToGlusterVolume,
            //                    new GlusterVolumeBricksParameters(Guid.createGuidFromString(getClusterId()), parent.get()
            //                            .getVolumeName(), brickList));
            return null;
        } catch (Exception e) {
            return handleError(e, false);
        }
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
