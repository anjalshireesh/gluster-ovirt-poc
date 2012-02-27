package org.ovirt.engine.api.restapi.resource;

import static org.ovirt.engine.api.restapi.resource.BackendVmsResource.SUB_COLLECTIONS;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.ovirt.engine.api.model.Action;
import org.ovirt.engine.api.model.GlusterBrick;
import org.ovirt.engine.api.model.GlusterBricks;
import org.ovirt.engine.api.model.GlusterVolume;
import org.ovirt.engine.api.model.GlusterVolumes;
import org.ovirt.engine.api.model.VM;
import org.ovirt.engine.api.model.VolumeOption;
import org.ovirt.engine.api.resource.GlusterVolumeBricksResource;
import org.ovirt.engine.api.resource.GlusterVolumeResource;
import org.ovirt.engine.core.common.action.VdcActionType;
import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeOption;
import org.ovirt.engine.core.common.constants.GlusterConstants.GLUSTER_TASK_OPERATION;
import org.ovirt.engine.core.common.glusteractions.GlusterVolumeBricksParameters;
import org.ovirt.engine.core.common.glusteractions.GlusterVolumeOptionParameters;
import org.ovirt.engine.core.common.glusteractions.GlusterVolumeParameters;
import org.ovirt.engine.core.common.glusteractions.GlusterVolumeReplaceBrickParameters;
import org.ovirt.engine.core.compat.Guid;

public class BackendGlusterVolumeResource extends AbstractBackendActionableResource<VM, org.ovirt.engine.core.common.businessentities.VM> implements
GlusterVolumeResource {

    private final BackendGlusterVolumesResource parent;
    private final String clusterId;

    public BackendGlusterVolumeResource(String clusterId, String id, BackendGlusterVolumesResource parent) {
        super(id, VM.class, org.ovirt.engine.core.common.businessentities.VM.class, SUB_COLLECTIONS);
        this.parent = parent;
        this.clusterId = clusterId;
    }

    @Override
    public GlusterVolume get() {
        // return performGet(VdcQueryType.GetVmByVmId, new GetVmByVmIdParameters(guid));

        // FIXME: Hack to get given volume without writing VDSM code. Ideal way is to
        // invoke a separate VDSM command to fetch details of a single volume
        GlusterVolumes volumes = parent.list();
        for (GlusterVolume volume : volumes.getGlusterVolumes()) {
            if (volume.getId().equals(id)) {
                return volume;
            }
        }

        return null;
    }

    @Override
    public Response start(Action action) {
        return performAction(VdcActionType.StartGlusterVolume,
                new GlusterVolumeParameters(Guid.createGuidFromString(getClusterId()), get().getVolumeName()));
    }

    @Override
    public Response stop(Action action) {
        return performAction(VdcActionType.StopGlusterVolume,
                new GlusterVolumeParameters(Guid.createGuidFromString(getClusterId()), get().getVolumeName()));
    }

    @Override
    public Response rebalanceStart(Action action) {
        return performAction(VdcActionType.RebalanceGlusterVolumeStart,
                new GlusterVolumeParameters(Guid.createGuidFromString(getClusterId()), get().getVolumeName()));
    }

    @Override
    public Response rebalanceStop(Action action) {
        return performAction(VdcActionType.RebalanceGlusterVolumeStop,
                new GlusterVolumeParameters(Guid.createGuidFromString(getClusterId()), get().getVolumeName()));
    }

    @Override
    public GlusterVolumeBricksResource getBricksResource() {
        return inject(new BackendGlusterBricksResource(getClusterId(), this));
    }

    public String getClusterId() {
        return clusterId;
    }

    @Override
    public Response addBrick(Action action) {
        return performAction(VdcActionType.AddBricksToGlusterVolume,
                new GlusterVolumeBricksParameters(Guid.createGuidFromString(getClusterId()), get()
                        .getVolumeName(), mapCollection(action.getGlusterBricks())));
    }

    @Override
    public Response removeBrick(Action action) {
        return performAction(VdcActionType.RemoveBricksFromGlusterVolume,
                new GlusterVolumeBricksParameters(Guid.createGuidFromString(getClusterId()), get()
                        .getVolumeName(), mapCollection(action.getGlusterBricks())));
    }

    @Override
    public Response setVolumeOption(Action action) {
        return performAction(VdcActionType.SetGlusterVolumeOption,
                new GlusterVolumeOptionParameters(Guid.createGuidFromString(getClusterId()),
                        get().getVolumeName(),
                        getMapper(VolumeOption.class, GlusterVolumeOption.class).map(action.getVolumeOption(), null)));
    }

    protected List<GlusterBrickEntity> mapCollection(GlusterBricks brickList) {
        List<GlusterBrickEntity> collection = new ArrayList<GlusterBrickEntity>();
        for (GlusterBrick brick : brickList.getGlusterBricks()) {
            GlusterBrickEntity brickEntity = getMapper(GlusterBrick.class, GlusterBrickEntity.class).map(brick, null);
            collection.add(brickEntity);
        }
        return collection;
    }

    protected GlusterBrickEntity mapCollection(GlusterBrick brick) {
        return getMapper(GlusterBrick.class, GlusterBrickEntity.class).map(brick, null);
    }

    @Override
    public Response replaceBrick(Action action) {
        try {
            return performAction(VdcActionType.ReplaceGlusterVolumeBrick,
                    new GlusterVolumeReplaceBrickParameters(Guid.createGuidFromString(getClusterId()),
                            get()
                            .getVolumeName(),
                            mapCollection(action.getSourceBrick()),
                            mapCollection(action.getTargetBrick()),
                            GLUSTER_TASK_OPERATION.valueOf(action.getOperation().toUpperCase())));
        } catch (Exception e) {
            return handleError(e, false);
        }
    }
}
