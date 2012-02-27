package org.ovirt.engine.core.vdsbroker.glusterbroker;

import java.util.List;

import org.ovirt.engine.core.common.glustercommands.GlusterVolumeBricksVDSParameters;
import org.ovirt.engine.core.common.utils.GlusterCoreUtil;
import org.ovirt.engine.core.vdsbroker.vdsbroker.VdsBrokerCommand;

public class RemoveBricksFromGlusterVolumeVDSCommand<P extends GlusterVolumeBricksVDSParameters> extends VdsBrokerCommand<P> {

    public RemoveBricksFromGlusterVolumeVDSCommand(P parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteVdsBrokerCommand() {
        List<String> bricks = GlusterCoreUtil.getQualifiedBrickList(getParameters().getBricks());

        status = getBroker().glusterVolumeRemoveBrick(getParameters().getVolumeName(), bricks.toArray(new String[0]));

        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();
    }
}
