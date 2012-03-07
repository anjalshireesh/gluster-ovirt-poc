package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.vdscommands.VdsIdVDSCommandParametersBase;
import org.ovirt.engine.core.vdsbroker.vdsbroker.VdsBrokerCommand;

public abstract class GlusterBrokerCommand<T extends VdsIdVDSCommandParametersBase> extends VdsBrokerCommand<T> {

    public GlusterBrokerCommand(T parameters) {
        super(parameters);
    }
}
