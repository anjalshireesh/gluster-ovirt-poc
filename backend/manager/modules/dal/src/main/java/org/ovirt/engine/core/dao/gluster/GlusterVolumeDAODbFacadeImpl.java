package org.ovirt.engine.core.dao.gluster;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity.BRICK_STATUS;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.ACCESS_PROTOCOL;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.TRANSPORT_TYPE;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.VOLUME_STATUS;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.VOLUME_TYPE;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeOption;
import org.ovirt.engine.core.common.utils.StringUtil;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.dao.BaseDAODbFacade;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class GlusterVolumeDAODbFacadeImpl extends BaseDAODbFacade implements
        GlusterVolumeDAO {

    private static final ParameterizedRowMapper<GlusterVolumeEntity> volumeRowMapper = createVolumeRowMapper();
    private static final ParameterizedRowMapper<GlusterBrickEntity> brickRowMapper = createBrickRowMapper();
    private static final ParameterizedRowMapper<GlusterVolumeOption> optionRowMapper = createOptionRowMapper();
    private static final ParameterizedRowMapper<ACCESS_PROTOCOL> accessProtocolRowMapper = createAccessProtocolRowMapper();

    private void insertVolumeEntity(GlusterVolumeEntity volume) {
        getCallsHandler().executeModification(
                "InsertGlusterVolume",
                getCustomMapSqlParameterSource()
                        .addValue("id", volume.getId())
                        .addValue("cluster_id", volume.getClusterId())
                        .addValue("vol_name", volume.getName())
                        .addValue("vol_type", volume.getVolumeType())
                        .addValue("transport_type", volume.getTransportType())
                        .addValue("status", volume.getStatus())
                        .addValue("replica_count", volume.getReplicaCount())
                        .addValue("stripe_count", volume.getStripeCount())
                        .addValue(
                                "cifs_users",
                                StringUtil.collectionToString(
                                        volume.getCifsUsers(), ",")));
    }

    private void insertVolumeBricks(GlusterVolumeEntity volume) {
        List<GlusterBrickEntity> bricks = volume.getBricks();
        for (GlusterBrickEntity brick : bricks) {
            getCallsHandler().executeModification(
                    "InsertGlusterVolumeBrick",
                    getCustomMapSqlParameterSource()
                            .addValue("volume_id", volume.getId())
                            .addValue("host_id", brick.getServerId())
                            .addValue("brick_dir", brick.getBrickDirectory())
                            .addValue("status", volume.getStatus()));
        }
    }

    private void insertVolumeOptions(GlusterVolumeEntity volume) {
        Collection<GlusterVolumeOption> options = volume.getOptions();
        for (GlusterVolumeOption option : options) {
            getCallsHandler().executeModification(
                    "InsertGlusterVolumeOption",
                    getCustomMapSqlParameterSource()
                            .addValue("volume_id", volume.getId())
                            .addValue("option_key", option.getKey())
                            .addValue("option_val", option.getValue()));
        }
    }

    private void insertVolumeAccessProtocols(GlusterVolumeEntity volume) {
        for (ACCESS_PROTOCOL protocol : volume.getAccessProtocols()) {
            getCallsHandler().executeModification(
                    "InsertGlusterVolumeAccessProtocol",
                    getCustomMapSqlParameterSource().addValue("volume_id",
                            volume.getId()).addValue("access_protocol",
                            protocol.ordinal()));
        }
    }

    @Override
    public void save(GlusterVolumeEntity volume) {
        insertVolumeEntity(volume);
        insertVolumeBricks(volume);
        insertVolumeOptions(volume);
        insertVolumeAccessProtocols(volume);
    }

    @Override
    public GlusterVolumeEntity getById(Guid id) {
        GlusterVolumeEntity volume = getCallsHandler().executeRead(
                "GetGlusterVolumeById", volumeRowMapper,
                createVolumeIdParamSource(id));

        if (volume != null) {
            volume.setBricks(getBricksOfVolume(id));
            volume.setOptions(getOptionsOfVolume(id));
            volume.setAccessProtocols(new HashSet<GlusterVolumeEntity.ACCESS_PROTOCOL>(getAccessProtocolsOfVolume(id)));
        }
        return volume;
    }

    @Override
    public GlusterVolumeEntity getByName(Guid clusterId, String volName) {
        GlusterVolumeEntity volume = getCallsHandler().executeRead(
                "GetGlusterVolumeByName", volumeRowMapper,
                getCustomMapSqlParameterSource().addValue("cluster_id", clusterId).addValue("vol_name", volName));

        if (volume != null) {
            volume.setBricks(getBricksOfVolume(volume.getId()));
            volume.setOptions(getOptionsOfVolume(volume.getId()));
            volume.setAccessProtocols(new HashSet<GlusterVolumeEntity.ACCESS_PROTOCOL>(getAccessProtocolsOfVolume(volume.getId())));
        }
        return volume;
    }

    private MapSqlParameterSource createVolumeIdParamSource(Guid id) {
        return getCustomMapSqlParameterSource().addValue("volume_id", id);
    }

    private List<GlusterBrickEntity> getBricksOfVolume(Guid volumeId) {
        List<GlusterBrickEntity> bricks = getCallsHandler().executeReadList(
                "GetBricksByGlusterVolumeGuid", brickRowMapper,
                createVolumeIdParamSource(volumeId));

        for(GlusterBrickEntity brick : bricks) {
            brick.setServerName(dbFacade.getVdsStaticDAO().get(brick.getServerId()).gethost_name());
        }

        return bricks;
    }

    private List<GlusterVolumeOption> getOptionsOfVolume(Guid volumeId) {
        return getCallsHandler().executeReadList(
                "GetOptionsByGlusterVolumeGuid", optionRowMapper,
                createVolumeIdParamSource(volumeId));
    }

    private List<ACCESS_PROTOCOL> getAccessProtocolsOfVolume(Guid volumeId) {
        return getCallsHandler().executeReadList(
                "GetAccessProtocolsByGlusterVolumeGuid",
                accessProtocolRowMapper,
                createVolumeIdParamSource(volumeId));
    }

    private static ParameterizedRowMapper<GlusterBrickEntity> createBrickRowMapper() {
        ParameterizedRowMapper<GlusterBrickEntity> mapper = new ParameterizedRowMapper<GlusterBrickEntity>() {
            @Override
            public GlusterBrickEntity mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                GlusterBrickEntity entity = new GlusterBrickEntity();
                entity.setServerId(Guid.createGuidFromString(rs.getString("host_id")));
                entity.setBrickDirectory(rs.getString("brick_dir"));
                entity.setStatus(BRICK_STATUS.values()[rs.getInt("status")]);
                return entity;
            }
        };
        return mapper;
    }

    private static ParameterizedRowMapper<GlusterVolumeOption> createOptionRowMapper() {
        ParameterizedRowMapper<GlusterVolumeOption> mapper = new ParameterizedRowMapper<GlusterVolumeOption>() {
            @Override
            public GlusterVolumeOption mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                GlusterVolumeOption entity = new GlusterVolumeOption();
                entity.setKey(rs.getString("option_key"));
                entity.setValue(rs.getString("option_val"));
                return entity;
            }
        };
        return mapper;
    }

    private static ParameterizedRowMapper<ACCESS_PROTOCOL> createAccessProtocolRowMapper() {
        ParameterizedRowMapper<ACCESS_PROTOCOL> mapper = new ParameterizedRowMapper<ACCESS_PROTOCOL>() {
            @Override
            public ACCESS_PROTOCOL mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                return ACCESS_PROTOCOL.values()[rs.getInt("access_protocol")];
            }
        };
        return mapper;
    }

    private static ParameterizedRowMapper<GlusterVolumeEntity> createVolumeRowMapper() {
        ParameterizedRowMapper<GlusterVolumeEntity> mapper = new ParameterizedRowMapper<GlusterVolumeEntity>() {
            @Override
            public GlusterVolumeEntity mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                GlusterVolumeEntity entity = new GlusterVolumeEntity();
                entity.setId(Guid.createGuidFromString(rs.getString("id")));
                entity.setClusterId(Guid.createGuidFromString(rs
                        .getString("cluster_id")));
                entity.setName(rs.getString("vol_name"));
                entity.setVolumeType(VOLUME_TYPE.values()[rs.getInt("vol_type")]);
                entity.setTransportType(TRANSPORT_TYPE.values()[rs
                        .getInt("transport_type")]);
                entity.setStatus(VOLUME_STATUS.values()[rs.getInt("status")]);
                entity.setReplicaCount(rs.getInt("replica_count"));
                entity.setStripeCount(rs.getInt("stripe_count"));
                entity.setCifsUsers(rs.getString("cifs_users"));
                return entity;
            }
        };
        return mapper;
    }

    @Override
    public void remove(Guid id) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(GlusterVolumeEntity volume) {
        // TODO Auto-generated method stub

    }

    @Override
    public void getVolumesByClusterGuid(Guid clusterId) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addBrickToVolume(Guid volumeId, GlusterBrickEntity brick) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeBrickFromVolume(Guid volumeId, GlusterBrickEntity brick) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setVolumeOption(Guid volumeId, GlusterVolumeOption option) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<GlusterVolumeEntity> getAllWithQuery(String query) {
        List<GlusterVolumeEntity> volumes = new SimpleJdbcTemplate(jdbcTemplate).query(query, volumeRowMapper);

        // Update all fetched volumes with their elements like bricks, options and access protocols
        for(GlusterVolumeEntity volume : volumes) {
            volume.setBricks(getBricksOfVolume(volume.getId()));
            volume.setOptions(getOptionsOfVolume(volume.getId()));
            volume.setAccessProtocols(new HashSet<GlusterVolumeEntity.ACCESS_PROTOCOL>(getAccessProtocolsOfVolume(volume.getId())));
        }
        return volumes;
    }

    @Override
    public void updateVolumeStatus(Guid vdsGroupId, String volumeName, VOLUME_STATUS status) {
        getCallsHandler().executeModification("UpdateGlusterVolumeStatus",
                getCustomMapSqlParameterSource()
                        .addValue("cluster_id", vdsGroupId)
                        .addValue("vol_name", volumeName)
                        .addValue("status", status.getValue()));
    }
}
