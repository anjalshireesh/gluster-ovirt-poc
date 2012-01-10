package org.ovirt.engine.core.vdsbroker.glusterbroker;

import org.ovirt.engine.core.common.vdscommands.IrsBaseVDSCommandParameters;
import org.ovirt.engine.core.vdsbroker.irsbroker.IrsBrokerCommand;

public class GlusterBrokerCommand<T extends IrsBaseVDSCommandParameters> extends IrsBrokerCommand<T> {

    public GlusterBrokerCommand(T parameters) {
        super(parameters);
    }

}
