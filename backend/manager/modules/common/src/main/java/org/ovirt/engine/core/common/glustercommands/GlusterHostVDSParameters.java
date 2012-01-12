package org.ovirt.engine.core.common.glustercommands;

import org.ovirt.engine.core.compat.Guid;

public class GlusterHostVDSParameters extends GlusterBaseVDSCommandParameters {
    private final String hostName;

    public GlusterHostVDSParameters(Guid storagePoolId, String hostName) {
        super(storagePoolId);
        this.hostName = hostName;
    }

    public String getHostName() {
        return hostName;
    }
}
