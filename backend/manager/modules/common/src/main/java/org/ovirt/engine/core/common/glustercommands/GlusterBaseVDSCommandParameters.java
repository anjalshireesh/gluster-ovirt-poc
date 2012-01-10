package org.ovirt.engine.core.common.glustercommands;

import org.ovirt.engine.core.common.vdscommands.IrsBaseVDSCommandParameters;
import org.ovirt.engine.core.compat.Guid;

/**
 * Base parameters class for Gluster related VDSM commands
 *
 */
public class GlusterBaseVDSCommandParameters extends IrsBaseVDSCommandParameters {
    public GlusterBaseVDSCommandParameters(Guid storagePoolId) {
        super(storagePoolId);
    }
}
