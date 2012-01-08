/**
 *
 */
package org.ovirt.engine.api.restapi.resource;

import javax.ws.rs.core.Response;

import org.ovirt.engine.api.model.GlusterVolume;
import org.ovirt.engine.api.model.GlusterVolumes;
import org.ovirt.engine.api.resource.GlusterVolumeResource;
import org.ovirt.engine.api.resource.GlusterVolumesResource;
import org.ovirt.engine.core.common.action.CreateGlusterVolumeParameters;
import org.ovirt.engine.core.common.action.VdcActionType;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.VOLUME_TYPE;
import org.ovirt.engine.core.compat.Guid;

/**
 *
 */
public class BackendGlusterVolumesResource extends AbstractBackendCollectionResource<GlusterVolume, org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity>
        implements GlusterVolumesResource {
    static final String[] SUB_COLLECTIONS = { "bricks", "options" };
    private String clusterId;

    public BackendGlusterVolumesResource(String clusterId) {
        super(GlusterVolume.class, org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.class, SUB_COLLECTIONS);
        setClusterId(clusterId);
    }

    @Override
    public GlusterVolumes list() {
        // TODO: Fetch volume list from VDSM
        return null;
    }

    @Override
    @SingleEntityResource
    public GlusterVolumeResource getGlusterVolumeSubResource(String id) {
        return inject(new BackendGlusterVolumeResource(id, this));
    }

    @Override
    public Response add(GlusterVolume vm) {
        validateParameters(vm, "volumeName", "volumeType", "transportType", "bricks");

        String volumeType = vm.getVolumeType();
        if (volumeType.equals(VOLUME_TYPE.REPLICATE.toString())
                || volumeType.equals(VOLUME_TYPE.DISTRIBUTED_REPLICATE.toString())) {

        }

        try {
            GlusterVolumeEntity volumeEntity = getMapper(GlusterVolume.class, GlusterVolumeEntity.class).map(vm, null);
            return performAction(VdcActionType.CreateGlusterVolume,
                    new CreateGlusterVolumeParameters(Guid.createGuidFromString(getClusterId()), volumeEntity));
        } catch (Exception e) {
            return handleError(e, false);
        }
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    @Override
    protected Response performRemove(String id) {
        // TODO Invoke VDSM to remove the volume
        return null;
    }
}
