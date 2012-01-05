/**
 * 
 */
package org.ovirt.engine.core.common.action;

import org.ovirt.engine.core.common.businessentities.GlusterVolume;
import org.ovirt.engine.core.compat.Guid;

/**
 *
 */
public class CreateGlusterVolumeParameters extends StoragePoolParametersBase {
    private GlusterVolume volume;

    public CreateGlusterVolumeParameters() {
    }
    
    public CreateGlusterVolumeParameters(Guid storagePoolId, GlusterVolume volume) {
        super(storagePoolId);
        setVolume(volume);
    }
    
    public GlusterVolume getVolume() {
        return volume;
    }

    public void setVolume(GlusterVolume volume) {
        this.volume = volume;
    }
}
