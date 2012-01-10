/**
 *
 */
package org.ovirt.engine.core.common.glusteractions;

import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.compat.Guid;

/**
 *
 */
@SuppressWarnings("serial")
public class CreateGlusterVolumeParameters extends GlusterParametersBase {
    private GlusterVolumeEntity volume;

    public CreateGlusterVolumeParameters(Guid vdsGroupId, GlusterVolumeEntity volume) {
        super(vdsGroupId);
        setVolume(volume);
    }

    public GlusterVolumeEntity getVolume() {
        return volume;
    }

    public void setVolume(GlusterVolumeEntity volume) {
        this.volume = volume;
    }
}
