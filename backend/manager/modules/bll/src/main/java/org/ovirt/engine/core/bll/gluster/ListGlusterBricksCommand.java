/**
 *
 */
package org.ovirt.engine.core.bll.gluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.common.VdcObjectType;
import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.common.businessentities.GlusterDeviceEntity;
import org.ovirt.engine.core.common.businessentities.GlusterDiskEntity;
import org.ovirt.engine.core.common.businessentities.GlusterPartitionEntity;
import org.ovirt.engine.core.common.businessentities.VDS;
import org.ovirt.engine.core.common.glusteractions.GlusterVolumeParameters;
import org.ovirt.engine.core.common.interfaces.SearchType;
import org.ovirt.engine.core.common.queries.SearchParameters;
import org.ovirt.engine.core.common.queries.VdcQueryReturnValue;
import org.ovirt.engine.core.common.queries.VdcQueryType;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VDSReturnValue;
import org.ovirt.engine.core.common.vdscommands.VdsIdVDSCommandParametersBase;
import org.ovirt.engine.core.dal.VdcBllMessages;

/**
 * Fetches set of new bricks that can be added to an existing volume
 */
public class ListGlusterBricksCommand extends GlusterCommandBase<GlusterVolumeParameters> {

    public ListGlusterBricksCommand(GlusterVolumeParameters params) {
        super(params);
    }

    @Override
    protected boolean canDoAction() {
        addCanDoActionMessage(VdcBllMessages.VAR__ACTION__LIST);
        addCanDoActionMessage(VdcBllMessages.VAR__TYPE__DISK);
        return super.canDoAction();
    }

    /*
     * (non-Javadoc)
     * @see org.ovirt.engine.core.bll.CommandBase#executeCommand()
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void executeCommand() {
        List<GlusterBrickEntity> bricks = new ArrayList<GlusterBrickEntity>();

        // Get all hosts of current cluster
        VdcQueryReturnValue returnVal =
                Backend.getInstance().runInternalQuery(VdcQueryType.Search,
                        new SearchParameters("Host: cluster = " + getVdsGroupName(), SearchType.VDS));
        if (!returnVal.getSucceeded()) {
            return;
        }

        List<VDS> hosts = (List<VDS>)returnVal.getReturnValue();

        for (VDS host : hosts) {
            VDSReturnValue returnValue = Backend
                    .getInstance()
                    .getResourceManager()
                    .RunVdsCommand(
                            VDSCommandType.ListGlusterHostDisks,
                            new VdsIdVDSCommandParametersBase(host.getvds_id()));
            if(returnValue.getSucceeded()) {
                GlusterDiskEntity[] disks = (GlusterDiskEntity[])returnValue.getReturnValue();
                for(GlusterDiskEntity disk : disks) {
                    if (disk.hasPartitions()) {
                        for (GlusterPartitionEntity partition : disk.getPartitions()) {
                            if (partition.isReady()) {
                                addBrickToList(bricks, host, partition);
                            }
                        }
                    } else if (disk.isReady()) {
                        addBrickToList(bricks, host, disk);
                    }
                }
            }
        }

        // Get the return value from VDS command and put it into the return value of the BLL command
        getReturnValue().setActionReturnValue(bricks);
        setSucceeded(true);
    }

    private void addBrickToList(List<GlusterBrickEntity> bricks, VDS host, GlusterDeviceEntity device) {
        GlusterBrickEntity brick =
                new GlusterBrickEntity(host.getStaticData(), device.getMountPoint() + "/"
                        + getParameters().getVolumeName());
        brick.setId(host.getvds_id());
        bricks.add(brick);
    }

    /*
     * (non-Javadoc)
     * @see org.ovirt.engine.core.bll.CommandBase#getPermissionCheckSubjects()
     */
    @Override
    public Map getPermissionCheckSubjects() {
        return Collections.singletonMap(getVdsGroupId(), VdcObjectType.VdsGroups);
    }
}
