package org.ovirt.engine.core.dao.gluster;

import java.util.List;

import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.VOLUME_STATUS;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeOption;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.dao.DAO;

/**
 * Interface for DB operations on Gluster Volumes
 */
public interface GlusterVolumeDAO extends DAO {

    public void save(GlusterVolumeEntity volume);

    public GlusterVolumeEntity getById(Guid id);

    public GlusterVolumeEntity getByName(Guid clusterId, String volName);

    public void remove(Guid id);

    public void update(GlusterVolumeEntity volume);

    public void updateVolumeStatus(Guid vdsGroupId, String volumeName, VOLUME_STATUS status);

    public void getVolumesByClusterGuid(Guid clusterId);

    public void addBrickToVolume(Guid volumeId, GlusterBrickEntity brick);

    public void addBricksToVolume(Guid clusterId, Guid volumeId, List<GlusterBrickEntity> bricks);

    public void removeBricksFromVolume(Guid clusterId, Guid volumeId, List<GlusterBrickEntity> bricks);

    public void updateBrickToVolume(Guid clusterId, Guid volumeId, GlusterBrickEntity sourceBrick, GlusterBrickEntity targetBrick);

    public void setVolumeOption(Guid volumeId, GlusterVolumeOption option);

    public List<GlusterVolumeEntity> getAllWithQuery(String query);

    public void deleteGlusterVolumeByName(Guid vdsGroupId, String volumeName);

}
