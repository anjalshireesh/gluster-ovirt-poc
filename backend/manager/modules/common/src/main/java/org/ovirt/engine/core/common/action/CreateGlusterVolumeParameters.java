/**
 *
 */
package org.ovirt.engine.core.common.action;

import org.ovirt.engine.core.common.businessentities.GlusterVolume;
import org.ovirt.engine.core.compat.Guid;

/**
 *
 */
@SuppressWarnings("serial")
public class CreateGlusterVolumeParameters extends VdsGroupParametersBase {
    private GlusterVolume volume;

    public CreateGlusterVolumeParameters() {
    }

    public CreateGlusterVolumeParameters(Guid vdsGroupId, GlusterVolume volume) {
        super(vdsGroupId);
        setVolume(volume);
    }

    public GlusterVolume getVolume() {
        return volume;
    }

    public void setVolume(GlusterVolume volume) {
        this.volume = volume;
    }
}
