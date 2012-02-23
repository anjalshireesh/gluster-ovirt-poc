package org.ovirt.engine.ui.common.widget.uicommon.template;

import java.util.Date;

import org.ovirt.engine.core.common.businessentities.AuditLog;
import org.ovirt.engine.ui.common.system.ClientStorage;
import org.ovirt.engine.ui.common.uicommon.model.SearchableTableModelProvider;
import org.ovirt.engine.ui.common.widget.table.column.BaseAuditLogSeverityColumn;
import org.ovirt.engine.ui.common.widget.table.column.FullDateTimeColumn;
import org.ovirt.engine.ui.common.widget.table.column.TextColumnWithTooltip;
import org.ovirt.engine.ui.common.widget.uicommon.AbstractModelBoundTableWidget;
import org.ovirt.engine.ui.uicommonweb.models.events.EventListModel;

import com.google.gwt.event.shared.EventBus;

/**
 * Table rendering ({@link AuditLog}s).
 *
 * @param <T>
 *            Detail model type.
 */
public class AuditLogModelTable<T extends EventListModel> extends AbstractModelBoundTableWidget<AuditLog, T> {

    private final BaseAuditLogSeverityColumn auditLogCoulmn;

    public AuditLogModelTable(
            SearchableTableModelProvider<AuditLog, T> modelProvider,
            EventBus eventBus, ClientStorage clientStorage,
            BaseAuditLogSeverityColumn auditLogCoulmn) {
        super(modelProvider, eventBus, clientStorage, false);
        this.auditLogCoulmn = auditLogCoulmn;
    }

    public void initTable() {
        getTable().addColumn(auditLogCoulmn, "", "20px");

        TextColumnWithTooltip<AuditLog> logTimeColumn = new FullDateTimeColumn<AuditLog>() {
            @Override
            protected Date getRawValue(AuditLog object) {
                return object.getlog_time();
            }
        };
        getTable().addColumn(logTimeColumn, "Time");

        TextColumnWithTooltip<AuditLog> messageColumn = new TextColumnWithTooltip<AuditLog>() {
            @Override
            public String getValue(AuditLog object) {
                return object.getmessage();
            }
        };
        getTable().addColumn(messageColumn, "Message");
    }

}