package org.ovirt.engine.api.restapi.types;

import org.ovirt.engine.api.model.VolumeOption;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeOption;

public class VolumeOptionMapper {
    @Mapping(from = VolumeOption.class, to = GlusterVolumeOption.class)
    public static GlusterVolumeOption map(VolumeOption fromOption, GlusterVolumeOption toOption) {
        GlusterVolumeOption option = (toOption == null) ? new GlusterVolumeOption() : toOption;
        option.setKey(fromOption.getKey());
        option.setValue(fromOption.getValue());
        return option;
    }

    @Mapping(from = GlusterVolumeOption.class, to = VolumeOption.class)
    public static VolumeOption map(GlusterVolumeOption fromOption, VolumeOption toOption) {
        VolumeOption option = (toOption == null) ? new VolumeOption() : toOption;
        option.setKey(fromOption.getKey());
        option.setValue(fromOption.getValue());
        return option;
    }
}
