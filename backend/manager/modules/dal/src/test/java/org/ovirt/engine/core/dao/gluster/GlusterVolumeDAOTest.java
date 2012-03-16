package org.ovirt.engine.core.dao.gluster;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity.BRICK_STATUS;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.TRANSPORT_TYPE;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.VOLUME_STATUS;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.VOLUME_TYPE;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeOption;
import org.ovirt.engine.core.common.businessentities.VdsStatic;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.dao.BaseDAOTestCase;

public class GlusterVolumeDAOTest extends BaseDAOTestCase {
    private GlusterVolumeDAO dao;
    private static final Guid HOST_ID = new Guid("afce7a39-8e8c-4819-ba9c-796d316592e6");
    private static final Guid CLUSTER_ID = new Guid("0e57070e-2469-4b38-84a2-f111aaabd49d");
    private VdsStatic host;
    private GlusterVolumeEntity volume;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        dao = dbFacade.getGlusterVolumeDAO();
        host = dbFacade.getVdsStaticDAO().get(HOST_ID);
        volume = insertTestVolume(true);
    }

    public GlusterVolumeDAOTest() throws Exception {
        setUp();
    }

    @Test
    public void testGlusterVolumeInsert() throws Exception {
        GlusterVolumeEntity volumeEntity = dao.getById(volume.getId());
        assertNotNull(volumeEntity);
        assertEquals(volumeEntity, volume);
    }

    @Test
    public void testGlusterVolumeStatusChange() throws Exception {
        volume.setStatus(VOLUME_STATUS.OFFLINE);
        dao.updateVolumeStatus(host.getvds_group_id(), "testVol1", VOLUME_STATUS.OFFLINE);
        GlusterVolumeEntity volumeEntity = dao.getById(volume.getId());
        assertNotNull(volumeEntity);
        assertEquals(volumeEntity, volume);
    }

    @Test
    public void testGlusterVolumeOptions() throws Exception {
        volume.setOption("nfs.disable", "on");
        dao.setVolumeOption(volume.getId(), new GlusterVolumeOption("nfs.disable", "on"));
        GlusterVolumeEntity volumeEntity = dao.getById(volume.getId());
        assertNotNull(volumeEntity);
        assertEquals(volumeEntity, volume);
    }

    @Test
    public void testGlusterVolumeOption2() throws Exception {
        GlusterVolumeEntity volumeEntity = insertTestVolume(false);

        dao.setVolumeOption(volumeEntity.getId(), new GlusterVolumeOption("auth.allow", "*"));
        dao.setVolumeOption(volumeEntity.getId(), new GlusterVolumeOption("nfs.disable", "on"));
        volumeEntity.setOption("auth.allow", "*");
        volumeEntity.setOption("nfs.disable", "on");

        GlusterVolumeEntity newVolumeEntity = dao.getById(volumeEntity.getId());
        assertNotNull(newVolumeEntity);
        assertEquals(newVolumeEntity, volumeEntity);
    }


    @Test
    public void testAddBricksToGlusterVolume() throws Exception {
        GlusterBrickEntity brick = getGlusterVolumeBrick("/export/testVol2", true);
        dao.addBrickToVolume(volume.getId(), brick);

        volume.addBrick(brick);

        GlusterVolumeEntity volumeEntity = dao.getById(volume.getId());
        assertNotNull(volumeEntity);
        assertEquals(volumeEntity, volume);
    }

    @Test
    public void testAddBricksToGlusterVolumeWithoutBrickId() throws Exception {
        GlusterBrickEntity brick = getGlusterVolumeBrick("/export/testVol3", true);
        dao.addBrickToVolume(volume.getId(), brick);

        brick.setServerId(host.getId());
        volume.addBrick(brick);

        GlusterVolumeEntity volumeEntity = dao.getById(volume.getId());
        assertNotNull(volumeEntity);
        for (GlusterBrickEntity brickEntity : volumeEntity.getBricks()) {
            if (brickEntity.getBrickDirectory().equals("/export/testVol3")) {
                assertEquals(brick.getServerId(), brickEntity.getServerId());
            }
        }
    }

    @Test
    public void testRemoveBricksFromGlusterVolume() throws Exception {
        GlusterBrickEntity brick = getGlusterVolumeBrick("/export/testVol2", true);
        dao.addBrickToVolume(volume.getId(), brick);
        List<GlusterBrickEntity> bricks = new ArrayList<GlusterBrickEntity>();
        bricks.add(brick);
        dao.removeBricksFromVolume(volume.getClusterId(), volume.getId(), bricks);

        GlusterVolumeEntity volumeEntity = dao.getById(volume.getId());
        assertNotNull(volumeEntity);
    }

    private GlusterBrickEntity getGlusterVolumeBrick(String brickDirectory, boolean setHostID) {
        GlusterBrickEntity brick = new GlusterBrickEntity(host, brickDirectory);
        if (setHostID) {
            brick.setServerId(host.getId());
        }
        brick.setStatus(BRICK_STATUS.ONLINE);
        return brick;
    }

    private GlusterVolumeEntity insertTestVolume(boolean setOption) {
        Guid volumeId = Guid.NewGuid();

        GlusterVolumeEntity volume = new GlusterVolumeEntity();
        volume.setName("testVol1");
        volume.setClusterId(host.getvds_group_id());
        volume.setId(volumeId);
        volume.setVolumeType(VOLUME_TYPE.DISTRIBUTE);
        volume.setTransportType(TRANSPORT_TYPE.ETHERNET);
        volume.setReplicaCount(0);
        volume.setStripeCount(0);
        volume.setStatus(VOLUME_STATUS.ONLINE);
        volume.setCifsUsers("user1, user2");
        if (setOption) {
            volume.setOption("auth.allow", "*");
        }
        volume.setAccessProtocols("GLUSTER,NFS");

        GlusterBrickEntity brick = getGlusterVolumeBrick("/export/testVol1", true);
        volume.addBrick(brick);

        dao.save(volume);
        return volume;
    }
}
