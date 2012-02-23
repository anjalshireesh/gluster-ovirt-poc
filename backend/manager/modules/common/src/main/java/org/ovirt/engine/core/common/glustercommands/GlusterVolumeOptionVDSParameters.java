package org.ovirt.engine.core.common.glustercommands;

import org.ovirt.engine.core.common.businessentities.GlusterVolumeOption;
import org.ovirt.engine.core.compat.Guid;

public class GlusterVolumeOptionVDSParameters extends GlusterVolumeVDSParameters {
    private final GlusterVolumeOption volumeOption;

    public GlusterVolumeOptionVDSParameters(Guid storagePoolId, String volumeName,  GlusterVolumeOption volumeOption) {
        super(storagePoolId, volumeName);
        this.volumeOption = volumeOption;
    }

    public GlusterVolumeOption getVolumeOption() {
        return volumeOption;
    }
}
