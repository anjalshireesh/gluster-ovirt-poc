/**
 *
 */
package org.ovirt.engine.api.restapi.resource;

import java.util.List;

import javax.ws.rs.core.Response;

import org.ovirt.engine.api.model.GlusterVolume;
import org.ovirt.engine.api.model.GlusterVolumes;
import org.ovirt.engine.api.resource.GlusterVolumeResource;
import org.ovirt.engine.api.resource.GlusterVolumesResource;
import org.ovirt.engine.core.common.action.VdcActionType;
import org.ovirt.engine.core.common.action.VdcReturnValueBase;
import org.ovirt.engine.core.common.action.VdsGroupParametersBase;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.common.glusteractions.CreateGlusterVolumeParameters;
import org.ovirt.engine.core.common.glusteractions.GlusterVolumeParameters;
import org.ovirt.engine.core.compat.Guid;

/**
 *
 */
public class BackendGlusterVolumesResource extends AbstractBackendCollectionResource<GlusterVolume, GlusterVolumeEntity>
        implements GlusterVolumesResource {
    static final String[] SUB_COLLECTIONS = { "glusterbricks", "options" };
    private final String clusterId;

    public BackendGlusterVolumesResource(String clusterId) {
        super(GlusterVolume.class, org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.class, SUB_COLLECTIONS);
        this.clusterId = clusterId;
    }

    @Override
    public GlusterVolumes list() {
        VdcReturnValueBase result =
                backend.RunAction(VdcActionType.ListGlusterVolumes,
                        new VdsGroupParametersBase(Guid.createGuidFromString(getClusterId())));
        List<GlusterVolumeEntity> volumes = (List<GlusterVolumeEntity>)result.getActionReturnValue();
        return mapCollection(volumes);
    }

    protected GlusterVolumes mapCollection(List<GlusterVolumeEntity> entities) {
        GlusterVolumes collection = new GlusterVolumes();
        for (GlusterVolumeEntity entity : entities) {
            collection.getGlusterVolumes().add(populate(map(entity), entity));
        }
        return collection;
    }

    @Override
    @SingleEntityResource
    public GlusterVolumeResource getGlusterVolumeSubResource(String id) {
        return inject(new BackendGlusterVolumeResource(getClusterId(), id, this));
    }

    @Override
    public Response add(GlusterVolume volume) {
        validateParameters(volume, "volumeName", "volumeType", "glusterBricks");

        try {
            GlusterVolumeEntity volumeEntity = getMapper(GlusterVolume.class, GlusterVolumeEntity.class).map(volume, null);
            return performAction(VdcActionType.CreateGlusterVolume,
                    new CreateGlusterVolumeParameters(Guid.createGuidFromString(getClusterId()), volumeEntity));
        } catch (Exception e) {
            return handleError(e, false);
        }
    }

    public String getClusterId() {
        return clusterId;
    }

    @Override
    protected Response performRemove(String id) {
        //Delete Gluster Volume
        Response res = performAction(VdcActionType.DeleteGlusterVolume,
                new GlusterVolumeParameters(Guid.createGuidFromString(getClusterId()), getGlusterVolumeSubResource(id).get().getVolumeName()));
        return res;
     // return performAction(VdcActionType.DeleteGlusterVolume, new VdsActionParameters(asGuid(id)));
    }
}
