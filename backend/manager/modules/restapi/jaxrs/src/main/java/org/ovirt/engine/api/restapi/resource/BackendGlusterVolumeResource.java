package org.ovirt.engine.api.restapi.resource;

import static org.ovirt.engine.api.restapi.resource.BackendVmsResource.SUB_COLLECTIONS;

import javax.ws.rs.core.Response;

import org.ovirt.engine.api.model.Action;
import org.ovirt.engine.api.model.GlusterVolume;
import org.ovirt.engine.api.model.GlusterVolumes;
import org.ovirt.engine.api.model.VM;
import org.ovirt.engine.api.resource.GlusterVolumeBricksResource;
import org.ovirt.engine.api.resource.GlusterVolumeResource;
import org.ovirt.engine.core.common.action.VdcActionType;
import org.ovirt.engine.core.common.glusteractions.GlusterVolumeParameters;
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
        for(GlusterVolume volume : volumes.getGlusterVolumes()) {
            if(volume.getId().equals(id)) {
                return volume;
            }
        }

        return null;
    }

    @Override
    public Response start(Action action) {
        String volumeName = get().getVolumeName();

        try {
            return performAction(VdcActionType.StartGlusterVolume,
                    new GlusterVolumeParameters(Guid.createGuidFromString(getClusterId()), volumeName));
        } catch (Exception e) {
            return handleError(e, false);
        }
    }

    @Override
    public Response stop(Action action) {
        String volumeName = get().getVolumeName();

        try {
            return performAction(VdcActionType.StopGlusterVolume,
                    new GlusterVolumeParameters(Guid.createGuidFromString(getClusterId()), volumeName));
        } catch (Exception e) {
            return handleError(e, false);
        }
    }

    @Override
    public Response rebalanceStart(Action action) {
        String volumeName = get().getVolumeName();

        try {
            return performAction(VdcActionType.RebalanceGlusterVolumeStart,
                    new GlusterVolumeParameters(Guid.createGuidFromString(getClusterId()), volumeName));
        } catch (Exception e) {
            return handleError(e, false);
        }
    }

    @Override
    public Response rebalanceStop(Action action) {
        String volumeName = get().getVolumeName();

        try {
            return performAction(VdcActionType.RebalanceGlusterVolumeStop,
                    new GlusterVolumeParameters(Guid.createGuidFromString(getClusterId()), volumeName));
        } catch (Exception e) {
            return handleError(e, false);
        }
    }

    @Override
    public GlusterVolumeBricksResource getBricksResource() {
        return inject(new BackendGlusterBricksResource(getClusterId(), this));
    }

    public String getClusterId() {
        return clusterId;
    }
}
