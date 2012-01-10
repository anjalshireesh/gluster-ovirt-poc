package org.ovirt.engine.ui.webadmin.section.main.view.tab.gluster;

import javax.inject.Inject;

import org.ovirt.engine.core.common.businessentities.AuditLog;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.ui.uicommonweb.models.gluster.VolumeEventListModel;
import org.ovirt.engine.ui.uicommonweb.models.gluster.VolumeListModel;
import org.ovirt.engine.ui.webadmin.section.main.presenter.tab.gluster.SubTabVolumeEventPresenter;
import org.ovirt.engine.ui.webadmin.section.main.view.AbstractSubTabEventView;
import org.ovirt.engine.ui.webadmin.uicommon.model.SearchableDetailModelProvider;

public class SubTabVolumeEventView extends AbstractSubTabEventView<GlusterVolumeEntity, VolumeListModel, VolumeEventListModel>
        implements SubTabVolumeEventPresenter.ViewDef {

    @Inject
    public SubTabVolumeEventView(SearchableDetailModelProvider<AuditLog, VolumeListModel, VolumeEventListModel> modelProvider) {
        super(modelProvider);
        initWidget(getTable());
    }

}
