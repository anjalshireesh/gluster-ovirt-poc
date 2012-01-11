package org.ovirt.engine.ui.webadmin.widget.table.column;

import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;

import com.google.gwt.user.cellview.client.Column;

public class VolumeStatusColumn extends Column<GlusterVolumeEntity, GlusterVolumeEntity>  {

    public VolumeStatusColumn() {
        super(new VolumeStatusCell());
    }

    @Override
    public GlusterVolumeEntity getValue(GlusterVolumeEntity volume) {
        return volume;
    }

}
