package org.ovirt.engine.ui.webadmin.section.main.view.tab;

import org.ovirt.engine.core.common.businessentities.GlusterVolume;
import org.ovirt.engine.ui.uicommonweb.UICommand;
import org.ovirt.engine.ui.uicommonweb.models.gluster.VolumeListModel;
import org.ovirt.engine.ui.webadmin.idhandler.ElementIdHandler;
import org.ovirt.engine.ui.webadmin.section.main.presenter.tab.MainTabVolumePresenter;
import org.ovirt.engine.ui.webadmin.section.main.view.AbstractMainTabWithDetailsTableView;
import org.ovirt.engine.ui.webadmin.uicommon.model.MainModelProvider;
import org.ovirt.engine.ui.webadmin.widget.action.UiCommandButtonDefinition;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;

public class MainTabVolumeView extends AbstractMainTabWithDetailsTableView<GlusterVolume, VolumeListModel> implements MainTabVolumePresenter.ViewDef {

    interface ViewIdHandler extends ElementIdHandler<MainTabVolumeView> {
        ViewIdHandler idHandler = GWT.create(ViewIdHandler.class);
    }

    @Inject
    public MainTabVolumeView(MainModelProvider<GlusterVolume, VolumeListModel> modelProvider) {
        super(modelProvider);
        ViewIdHandler.idHandler.generateAndSetIds(this);
        initTable();
        initWidget(getTable());
    }

    void initTable() {
        getTable().addActionButton(new UiCommandButtonDefinition<GlusterVolume>("Create Volume") {
            @Override
            protected UICommand resolveCommand() {
                return getMainModel().getCreateVolumeCommand();
            }
        });
        getTable().addActionButton(new UiCommandButtonDefinition<GlusterVolume>("Remove") {
            @Override
            protected UICommand resolveCommand() {
                return getMainModel().getRemoveVolumeCommand();
            }
        });
    }

}
