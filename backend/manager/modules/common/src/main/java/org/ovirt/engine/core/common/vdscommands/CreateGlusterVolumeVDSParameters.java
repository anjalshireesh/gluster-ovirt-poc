/**
 * 
 */
package org.ovirt.engine.core.common.vdscommands;

import org.ovirt.engine.core.common.businessentities.GlusterVolume;
import org.ovirt.engine.core.compat.Guid;

/**
 * Command parameters for the "Create Volume" action
 */
public class CreateGlusterVolumeVDSParameters extends IrsBaseVDSCommandParameters {
	private GlusterVolume volume;

	public CreateGlusterVolumeVDSParameters() {
	}
	
	public CreateGlusterVolumeVDSParameters(Guid storagePoolId, GlusterVolume volume) {
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
