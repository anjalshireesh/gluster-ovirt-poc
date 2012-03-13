package org.ovirt.engine.core.vdsbroker.glusterbroker;

import java.util.List;

import org.ovirt.engine.core.common.glustercommands.GlusterVolumeBricksVDSParameters;
import org.ovirt.engine.core.common.utils.GlusterCoreUtil;

public class AddBricksToGlusterVolumeVDSCommand extends GlusterBrokerCommand<GlusterVolumeBricksVDSParameters> {
    public AddBricksToGlusterVolumeVDSCommand(GlusterVolumeBricksVDSParameters parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteVdsBrokerCommand() {
        List<String> bricks = GlusterCoreUtil.getQualifiedBrickList(getParameters().getBricks());

        status = getBroker().glusterVolumeAddBrick(getParameters().getVolumeName(), bricks.toArray(new String[0]));

        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();
    }
}
