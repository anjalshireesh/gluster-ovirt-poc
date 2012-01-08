package org.ovirt.engine.api.restapi.resource;

import static org.ovirt.engine.api.restapi.resource.BackendVmsResource.SUB_COLLECTIONS;

import javax.ws.rs.core.Response;

import org.ovirt.engine.api.model.Action;
import org.ovirt.engine.api.model.GlusterVolume;
import org.ovirt.engine.api.model.VM;
import org.ovirt.engine.api.resource.GlusterVolumeResource;

public class BackendGlusterVolumeResource extends AbstractBackendActionableResource<VM, org.ovirt.engine.core.common.businessentities.VM> implements
        GlusterVolumeResource {

    private final BackendGlusterVolumesResource parent;

    public BackendGlusterVolumeResource(String id, BackendGlusterVolumesResource parent) {
        super(id, VM.class, org.ovirt.engine.core.common.businessentities.VM.class, SUB_COLLECTIONS);
        this.parent = parent;
    }

    @Override
    public GlusterVolume get() {
        // return performGet(VdcQueryType.GetVmByVmId, new GetVmByVmIdParameters(guid));
        // TODO Mechanism to fetch the given volume details
        return null;
    }

    @Override
    public Response start(Action action) {
        // TODO Invoke VDSM command to start the volume
        return null;
    }

    @Override
    public Response stop(Action action) {
        // TODO Invoke VDSM command to stop the volume
        return null;
    }

    @Override
    public Response rebalance(Action action) {
        // TODO Invoke VDSM command to trigger volume rebalance
        return null;
    }
}
