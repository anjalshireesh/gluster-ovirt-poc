package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.common.glustercommands.GlusterVolumeBricksVDSParameters;

public class RemoveBricksFromGlusterVolumeVDSCommand extends GlusterBrokerCommand<GlusterVolumeBricksVDSParameters> {

    public RemoveBricksFromGlusterVolumeVDSCommand(GlusterVolumeBricksVDSParameters parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteIrsBrokerCommand() {
        String[] brickList = new String[getParameters().getBricks().size()];

        int i=0;
        for (GlusterBrickEntity brick : getParameters().getBricks()) {
            brickList[i++] = brick.getQualifiedName();
        }

        status = getIrsProxy().glusterVolumeRemoveBrick(getParameters().getVolumeName(), brickList);

        // IMPORTANT! This handles errors if any
        ProceedProxyReturnValue();
    }

}
