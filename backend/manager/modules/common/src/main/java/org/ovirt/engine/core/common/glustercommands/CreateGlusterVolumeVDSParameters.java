/**
 *
 */
package org.ovirt.engine.core.common.glustercommands;

import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.compat.Guid;

/**
 * Command parameters for the "Create Volume" action
 */
public class CreateGlusterVolumeVDSParameters extends GlusterBaseVDSCommandParameters {
    private GlusterVolumeEntity volume;

    public CreateGlusterVolumeVDSParameters(Guid storagePoolId, GlusterVolumeEntity volume) {
        super(storagePoolId);
        setVolume(volume);
    }

    public GlusterVolumeEntity getVolume() {
        return volume;
    }

    public void setVolume(GlusterVolumeEntity volume) {
        this.volume = volume;
    }
}
