package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.vdscommands.VdsIdVDSCommandParametersBase;
import org.ovirt.engine.core.vdsbroker.vdsbroker.VdsBrokerCommand;

public class GlusterBrokerCommand<T extends VdsIdVDSCommandParametersBase> extends VdsBrokerCommand<T> {

    public GlusterBrokerCommand(T parameters) {
        super(parameters);
    }

    @Override
    protected void ExecuteVdsBrokerCommand() {
        // TODO Auto-generated method stub

    }

}
