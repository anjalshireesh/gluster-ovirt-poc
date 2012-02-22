package org.ovirt.engine.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.ovirt.engine.core.common.businessentities.Snapshot;
import org.ovirt.engine.core.common.businessentities.Snapshot.SnapshotStatus;
import org.ovirt.engine.core.common.businessentities.Snapshot.SnapshotType;
import org.ovirt.engine.core.common.utils.EnumUtils;
import org.ovirt.engine.core.compat.Guid;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class SnapshotDaoDbFacadeImpl extends DefaultGenericDaoDbFacade<Snapshot, Guid> implements SnapshotDao {

    private static final ParameterizedRowMapper<Snapshot> ROW_MAPPER = new ParameterizedRowMapper<Snapshot>() {

        @Override
        public Snapshot mapRow(ResultSet rs, int rowNum) throws SQLException {
            Snapshot snapshot = new Snapshot();

            snapshot.setId(Guid.createGuidFromString(rs.getString("snapshot_id")));
            snapshot.setVmId(new Guid(rs.getString("vm_id")));
            snapshot.setType(SnapshotType.valueOf(rs.getString("snapshot_type")));
            snapshot.setStatus(SnapshotStatus.valueOf(rs.getString("status")));
            snapshot.setDescription(rs.getString("description"));
            snapshot.setCreationDate(new Date(rs.getTimestamp("creation_date").getTime()));
            snapshot.setAppList(rs.getString("app_list"));
            snapshot.setVmConfiguration(rs.getString("vm_configuration"));

            return snapshot;
        }
    };

    @Override
    protected String getProcedureNameForUpdate() {
        return "UpdateSnapshot";
    }

    @Override
    protected String getProcedureNameForGet() {
        return "GetSnapshotBySnapshotId";
    }

    @Override
    protected String getProcedureNameForGetAll() {
        return "GetAllFromSnapshots";
    }

    @Override
    protected String getProcedureNameForSave() {
        return "InsertSnapshot";
    }

    @Override
    protected String getProcedureNameForRemove() {
        return "DeleteSnapshot";
    }

    @Override
    protected MapSqlParameterSource createIdParameterMapper(Guid id) {
        return getCustomMapSqlParameterSource().addValue("snapshot_id", id);
    }

    @Override
    protected MapSqlParameterSource createFullParametersMapper(Snapshot entity) {
        return createIdParameterMapper(entity.getId())
                .addValue("vm_id", entity.getVmId())
                .addValue("snapshot_type", EnumUtils.nameOrNull(entity.getType()))
                .addValue("status", EnumUtils.nameOrNull(entity.getStatus()))
                .addValue("description", entity.getDescription())
                .addValue("creation_date", entity.getCreationDate())
                .addValue("app_list", entity.getAppList())
                .addValue("vm_configuration", entity.getVmConfiguration());
    }

    @Override
    protected ParameterizedRowMapper<Snapshot> createEntityRowMapper() {
        return ROW_MAPPER;
    }

    @Override
    public void updateStatus(Guid id, SnapshotStatus status) {
        MapSqlParameterSource parameterSource = createIdParameterMapper(id)
                .addValue("status", EnumUtils.nameOrNull(status));
        getCallsHandler().executeModification("UpdateSnapshotStatus", parameterSource);
    }

    @Override
    public void updateId(Guid snapshotId, Guid newSnapshotId) {
        MapSqlParameterSource parameterSource = getCustomMapSqlParameterSource()
                .addValue("snapshot_id", snapshotId)
                .addValue("new_snapshot_id", newSnapshotId);
        getCallsHandler().executeModification("UpdateSnapshotId", parameterSource);
    }

    @Override
    public Guid getId(Guid vmId, SnapshotType type) {
        MapSqlParameterSource parameterSource = getCustomMapSqlParameterSource()
                .addValue("vm_id", vmId)
                .addValue("snapshot_type", EnumUtils.nameOrNull(type));

        return getCallsHandler().executeRead("GetSnapshotIdsByVmIdAndType",
                createGuidMapper(),
                parameterSource);
    }

    @Override
    public Guid getId(Guid vmId, SnapshotType type, SnapshotStatus status) {
        MapSqlParameterSource parameterSource = getCustomMapSqlParameterSource()
                .addValue("vm_id", vmId)
                .addValue("snapshot_type", EnumUtils.nameOrNull(type))
                .addValue("status", EnumUtils.nameOrNull(status));

        return getCallsHandler().executeRead("GetSnapshotIdsByVmIdAndTypeAndStatus",
                createGuidMapper(),
                parameterSource);
    }

    @Override
    public List<Snapshot> getForVm(Guid vmId) {
        MapSqlParameterSource parameterSource = getCustomMapSqlParameterSource().addValue("vm_id", vmId);

        return getCallsHandler().executeReadList("GetAllFromSnapshotsByVmId", createEntityRowMapper(), parameterSource);
    }

    @Override
    public boolean exists(Guid vmId, SnapshotType type) {
        MapSqlParameterSource parameterSource = getCustomMapSqlParameterSource()
                .addValue("vm_id", vmId)
                .addValue("snapshot_type", EnumUtils.nameOrNull(type));

        return getCallsHandler().executeRead("CheckIfSnapshotExistsByVmIdAndType",
                createBooleanMapper(),
                parameterSource);
    }

    @Override
    public boolean exists(Guid vmId, SnapshotStatus status) {
        MapSqlParameterSource parameterSource = getCustomMapSqlParameterSource()
                .addValue("vm_id", vmId)
                .addValue("status", EnumUtils.nameOrNull(status));

        return getCallsHandler().executeRead("CheckIfSnapshotExistsByVmIdAndStatus",
                createBooleanMapper(),
                parameterSource);
    }

    @Override
    public boolean exists(Guid vmId, Guid snapshotId) {
        MapSqlParameterSource parameterSource = getCustomMapSqlParameterSource()
                .addValue("vm_id", vmId)
                .addValue("snapshot_id", snapshotId);

        return getCallsHandler().executeRead("CheckIfSnapshotExistsByVmIdAndSnapshotId",
                createBooleanMapper(),
                parameterSource);
    }
}