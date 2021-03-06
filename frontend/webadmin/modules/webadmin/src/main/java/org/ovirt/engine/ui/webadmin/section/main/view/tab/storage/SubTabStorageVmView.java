package org.ovirt.engine.ui.webadmin.section.main.view.tab.storage;

import java.util.Date;

import org.ovirt.engine.core.common.businessentities.VM;
import org.ovirt.engine.core.common.businessentities.storage_domains;
import org.ovirt.engine.ui.uicommonweb.models.storage.StorageListModel;
import org.ovirt.engine.ui.uicommonweb.models.storage.StorageVmListModel;
import org.ovirt.engine.ui.webadmin.section.main.presenter.tab.storage.SubTabStorageVmPresenter;
import org.ovirt.engine.ui.webadmin.section.main.view.AbstractSubTabTableView;
import org.ovirt.engine.ui.webadmin.uicommon.model.SearchableDetailModelProvider;
import org.ovirt.engine.ui.webadmin.widget.table.column.GeneralDateTimeColumn;
import org.ovirt.engine.ui.webadmin.widget.table.column.TextColumnWithTooltip;
import org.ovirt.engine.ui.webadmin.widget.table.column.VmStatusColumn;

import com.google.inject.Inject;

public class SubTabStorageVmView extends AbstractSubTabTableView<storage_domains, VM, StorageListModel, StorageVmListModel>
        implements SubTabStorageVmPresenter.ViewDef {

    @Inject
    public SubTabStorageVmView(SearchableDetailModelProvider<VM, StorageListModel, StorageVmListModel> modelProvider) {
        super(modelProvider);
        initTable();
        initWidget(getTable());
    }

    void initTable() {

        getTable().addColumn(new VmStatusColumn(), "", "30px");

        TextColumnWithTooltip<VM> nameColumn = new TextColumnWithTooltip<VM>() {
            @Override
            public String getValue(VM object) {
                return object.getvm_name();
            }
        };
        getTable().addColumn(nameColumn, "Name");

        TextColumnWithTooltip<VM> diskColumn = new TextColumnWithTooltip<VM>() {
            @Override
            public String getValue(VM object) {
                return String.valueOf(object.getDiskMap().size());
            }
        };
        getTable().addColumn(diskColumn, "Disks");

        TextColumnWithTooltip<VM> templateColumn = new TextColumnWithTooltip<VM>() {
            @Override
            public String getValue(VM object) {
                return object.getvmt_name();
            }
        };
        getTable().addColumn(templateColumn, "Template");

        TextColumnWithTooltip<VM> vSizeColumn = new TextColumnWithTooltip<VM>() {
            @Override
            public String getValue(VM object) {
                return String.valueOf(object.getDiskSize());
            }
        };
        getTable().addColumn(vSizeColumn, "V-Size");

        TextColumnWithTooltip<VM> actualSizeColumn = new TextColumnWithTooltip<VM>() {
            @Override
            public String getValue(VM object) {
                return String.valueOf(object.getActualDiskWithSnapshotsSize());
            }
        };
        getTable().addColumn(actualSizeColumn, "Actual Size");

        TextColumnWithTooltip<VM> creationDateColumn = new GeneralDateTimeColumn<VM>() {
            @Override
            protected Date getRawValue(VM object) {
                return object.getvm_creation_date();
            }
        };
        getTable().addColumn(creationDateColumn, "Creation Date");
    }

}
