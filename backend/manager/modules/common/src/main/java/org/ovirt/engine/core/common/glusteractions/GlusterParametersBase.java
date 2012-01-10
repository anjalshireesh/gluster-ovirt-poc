package org.ovirt.engine.core.common.glusteractions;

import org.ovirt.engine.core.common.action.VdsGroupParametersBase;
import org.ovirt.engine.core.compat.Guid;

/**
 * Common parameters base class for Gluster related BLL commands
 */
public class GlusterParametersBase extends VdsGroupParametersBase {

    public GlusterParametersBase(Guid vdsGroupId) {
        super(vdsGroupId);
    }
}
