package org.ovirt.engine.core.common.glustercommands;

import org.ovirt.engine.core.common.vdscommands.VdsIdVDSCommandParametersBase;
import org.ovirt.engine.core.compat.Guid;

public class GlusterVolumeVDSParameters extends VdsIdVDSCommandParametersBase {
    private final String volumeName;

    public GlusterVolumeVDSParameters(Guid storagePoolId, String volumeName) {
        super(storagePoolId);
        this.volumeName = volumeName;
    }

    public String getVolumeName() {
        return volumeName;
    }
}
