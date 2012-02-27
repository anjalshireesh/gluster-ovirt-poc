package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.common.glustercommands.GlusterVolumeBricksVDSParameters;

public class AddBricksToGlusterVolumeVDSCommand extends GlusterBrokerCommand<GlusterVolumeBricksVDSParameters> {
    public AddBricksToGlusterVolumeVDSCommand(GlusterVolumeBricksVDSParameters parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteIrsBrokerCommand() {
        String[] brickList = new String[getParameters().getBricks().size()];

        int i=0;
        for (GlusterBrickEntity brick : getParameters().getBricks()) {
            brickList[i++] = brick.getQualifiedName();
        }
        status = getIrsProxy().glusterVolumeAddBrick(getParameters().getVolumeName(), brickList);

        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();
    }
}
