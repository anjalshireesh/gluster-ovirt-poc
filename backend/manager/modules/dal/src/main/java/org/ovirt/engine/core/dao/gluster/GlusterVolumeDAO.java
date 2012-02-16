package org.ovirt.engine.core.dao.gluster;

import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeOption;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.dao.DAO;

/**
 * Interface for DB operations on Gluster Volumes
 */
public interface GlusterVolumeDAO extends DAO {

    public void save(GlusterVolumeEntity volume);

    public GlusterVolumeEntity getById(Guid id);

    public void remove(Guid id);

    public void update(GlusterVolumeEntity volume);

    public void getVolumesByClusterGuid(Guid clusterId);

    public void addBrickToVolume(Guid volumeId, GlusterBrickEntity brick);

    public void removeBrickFromVolume(Guid volumeId, GlusterBrickEntity brick);

    public void setVolumeOption(Guid volumeId, GlusterVolumeOption option);
}
