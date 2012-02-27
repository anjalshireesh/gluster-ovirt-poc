package org.ovirt.engine.core.common.glustercommands;

import org.ovirt.engine.core.common.vdscommands.VdsIdVDSCommandParametersBase;
import org.ovirt.engine.core.compat.Guid;

/**
 * Base parameters class for Gluster related VDSM commands
 *
 */
public class GlusterBaseVDSCommandParameters extends VdsIdVDSCommandParametersBase {
    public GlusterBaseVDSCommandParameters(Guid vdsId) {
        super(vdsId);
    }
}
