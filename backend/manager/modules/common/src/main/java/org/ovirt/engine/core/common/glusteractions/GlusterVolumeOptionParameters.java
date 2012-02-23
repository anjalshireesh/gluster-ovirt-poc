package org.ovirt.engine.core.common.glusteractions;

import org.ovirt.engine.core.common.businessentities.GlusterVolumeOption;
import org.ovirt.engine.core.compat.Guid;

public class GlusterVolumeOptionParameters extends GlusterVolumeParameters {
    private GlusterVolumeOption volumeOption;

    public GlusterVolumeOptionParameters(Guid vdsGroupId, String volumeName, GlusterVolumeOption volumeOption) {
        super(vdsGroupId, volumeName);
        setVolumeOption(volumeOption);
    }

    public GlusterVolumeOption getVolumeOption() {
        return volumeOption;
    }

    public void setVolumeOption(GlusterVolumeOption volumeOption) {
        this.volumeOption = volumeOption;
    }
}
