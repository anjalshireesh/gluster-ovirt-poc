package org.ovirt.engine.core.vdsbroker.glusterbroker;

<<<<<<< HEAD
import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.common.glustercommands.GlusterVolumeBricksVDSParameters;
=======
import java.util.List;

import org.ovirt.engine.core.common.glustercommands.GlusterVolumeBricksVDSParameters;
import org.ovirt.engine.core.common.utils.GlusterCoreUtil;
>>>>>>> New VDS Remove Brick Command

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
