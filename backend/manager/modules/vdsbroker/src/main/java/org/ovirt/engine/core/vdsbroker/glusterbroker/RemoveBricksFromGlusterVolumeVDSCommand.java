package org.ovirt.engine.core.vdsbroker.glusterbroker;

import java.util.List;

import org.ovirt.engine.core.common.glustercommands.GlusterVolumeBricksVDSParameters;
import org.ovirt.engine.core.common.utils.GlusterCoreUtil;

public class RemoveBricksFromGlusterVolumeVDSCommand extends GlusterBrokerCommand<GlusterVolumeBricksVDSParameters> {

    public RemoveBricksFromGlusterVolumeVDSCommand(GlusterVolumeBricksVDSParameters parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteIrsBrokerCommand() {
        List<String> bricks = GlusterCoreUtil.getQualifiedBrickList(getParameters().getBricks());

        status = getIrsProxy().glusterVolumeRemoveBrick(getParameters().getVolumeName(), bricks.toArray(new String[0]));

        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();
    }

}
