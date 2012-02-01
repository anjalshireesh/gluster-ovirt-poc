package org.ovirt.engine.ui.webadmin.section.main.view.tab;

import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.ui.uicommonweb.UICommand;
import org.ovirt.engine.ui.uicommonweb.models.gluster.VolumeListModel;
import org.ovirt.engine.ui.webadmin.idhandler.ElementIdHandler;
import org.ovirt.engine.ui.webadmin.section.main.presenter.tab.MainTabVolumePresenter;
import org.ovirt.engine.ui.webadmin.section.main.view.AbstractMainTabWithDetailsTableView;
import org.ovirt.engine.ui.webadmin.uicommon.model.MainModelProvider;
import org.ovirt.engine.ui.webadmin.widget.action.UiCommandButtonDefinition;
import org.ovirt.engine.ui.webadmin.widget.table.column.TextColumnWithTooltip;
import org.ovirt.engine.ui.webadmin.widget.table.column.VolumeStatusColumn;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;

public class MainTabVolumeView extends AbstractMainTabWithDetailsTableView<GlusterVolumeEntity, VolumeListModel> implements MainTabVolumePresenter.ViewDef {

    interface ViewIdHandler extends ElementIdHandler<MainTabVolumeView> {
        ViewIdHandler idHandler = GWT.create(ViewIdHandler.class);
    }

    @Inject
    public MainTabVolumeView(MainModelProvider<GlusterVolumeEntity, VolumeListModel> modelProvider) {
        super(modelProvider);
        ViewIdHandler.idHandler.generateAndSetIds(this);
        initTable();
        initWidget(getTable());
    }

    void initTable() {
        getTable().addColumn(new VolumeStatusColumn(), "", "30px");
        TextColumnWithTooltip<GlusterVolumeEntity> nameColumn = new TextColumnWithTooltip<GlusterVolumeEntity>() {
            @Override
            public String getValue(GlusterVolumeEntity object) {
                return object.getName();
            }
        };
        getTable().addColumn(nameColumn, "Name");


        TextColumnWithTooltip<GlusterVolumeEntity> volumeTypeColumn = new TextColumnWithTooltip<GlusterVolumeEntity>() {
            @Override
            public String getValue(GlusterVolumeEntity object) {
                return object.getVolumeTypeStr();
            }
        };
        getTable().addColumn(volumeTypeColumn, "Volume Type");

        TextColumnWithTooltip<GlusterVolumeEntity> numOfBricksColumn = new TextColumnWithTooltip<GlusterVolumeEntity>() {
            @Override
            public String getValue(GlusterVolumeEntity object) {
                return Integer.toString(object.getBricks().size());
            }
        };
        getTable().addColumn(numOfBricksColumn, "Number of Bricks");

        TextColumnWithTooltip<GlusterVolumeEntity> transportColumn = new TextColumnWithTooltip<GlusterVolumeEntity>() {
            @Override
            public String getValue(GlusterVolumeEntity object) {
                return object.getTransportTypeStr();
            }
        };
        getTable().addColumn(transportColumn, "Transport Type");

        TextColumnWithTooltip<GlusterVolumeEntity> statusColumn = new TextColumnWithTooltip<GlusterVolumeEntity>() {
            @Override
            public String getValue(GlusterVolumeEntity object) {
                return object.getStatusStr();
            }
        };
        getTable().addColumn(statusColumn, "Status");

        getTable().addActionButton(new UiCommandButtonDefinition<GlusterVolumeEntity>("Create Volume") {
            @Override
            protected UICommand resolveCommand() {
                return getMainModel().getCreateVolumeCommand();
            }
        });
        getTable().addActionButton(new UiCommandButtonDefinition<GlusterVolumeEntity>("Remove", false, false) {
            @Override
            protected UICommand resolveCommand() {
                return getMainModel().getRemoveVolumeCommand();
            }
        });
        getTable().addActionButton(new UiCommandButtonDefinition<GlusterVolumeEntity>("Start") {
            @Override
            protected UICommand resolveCommand() {
                return getMainModel().getStartCommand();
            }
        });
        getTable().addActionButton(new UiCommandButtonDefinition<GlusterVolumeEntity>("Stop") {
            @Override
            protected UICommand resolveCommand() {
                return getMainModel().getStopCommand();
            }
        });
        getTable().addActionButton(new UiCommandButtonDefinition<GlusterVolumeEntity>("Rebalance") {
            @Override
            protected UICommand resolveCommand() {
                return getMainModel().getRebalanceCommand();
            }
        });
    }

}
