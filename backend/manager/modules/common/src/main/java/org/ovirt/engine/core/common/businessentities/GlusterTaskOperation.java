package org.ovirt.engine.core.common.businessentities;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "GlusterTaskOperation")
public enum GlusterTaskOperation {
        START,
        PAUSE,
        STOP,
        ABORT,
        STATUS,
        COMMIT;

    public int getValue() {
        return this.ordinal();
    }

    public static GlusterTaskOperation forValue(int value) {
        return values()[value];
    }
}
