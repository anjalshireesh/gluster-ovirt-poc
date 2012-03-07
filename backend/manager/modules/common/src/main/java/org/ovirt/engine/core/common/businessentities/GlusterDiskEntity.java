/*******************************************************************************
 * Copyright (c) 2006-2011 Gluster, Inc. <http://www.gluster.com>
 * This file is part of Gluster Management Console.
 *
 * Gluster Management Console is free software; you can redistribute
 * it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Gluster Management Console is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.ovirt.engine.core.common.businessentities;

import java.util.ArrayList;
import java.util.Collection;

import org.ovirt.engine.core.common.utils.GlusterCoreUtil;

public class GlusterDiskEntity extends GlusterDeviceEntity {
    private String description;

    // interface = pci, raid0, raid3, etc
    private String diskInterface;

    private Collection<GlusterPartitionEntity> partitions = new ArrayList<GlusterPartitionEntity>();

    // In case of a software raid, the disk will contain an array of other disks
    private Collection<GlusterDiskEntity> raidDisks;

    public GlusterDiskEntity() {
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getDiskInterface() {
        return diskInterface;
    }

    public void setDiskInterface(String diskInterface) {
        this.diskInterface = diskInterface;
    }

    public Collection<GlusterDiskEntity> getRaidDisks() {
        return raidDisks;
    }

    public void setRaidDisks(Collection<GlusterDiskEntity> raidDisks) {
        this.raidDisks = raidDisks;
    }

    public void setPartitions(Collection<GlusterPartitionEntity> partitions) {
        this.partitions = partitions;
    }

    public Collection<GlusterPartitionEntity> getPartitions() {
        return partitions;
    }

    public void addPartition(GlusterPartitionEntity partition) {
        partitions.add(partition);
    }

    public boolean hasPartitions() {
        return (partitions != null && partitions.size() > 0);
    }

    public GlusterDiskEntity(String serverName,
            String name,
            String mountPoint,
            Double space,
            Double spaceInUse,
            DEVICE_STATUS status) {
        super(serverName, name, mountPoint, space, spaceInUse, status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof GlusterDiskEntity)) {
            return false;
        }

        GlusterDiskEntity disk = (GlusterDiskEntity) obj;

        if (!(super.equals(obj) && getDescription().equals(disk.getDescription()) && (getDiskInterface() == disk.getDiskInterface() || getDiskInterface().equals(
                disk.getDiskInterface())))) {
            return false;
        }

        if (raidDisks != null) {
            for (GlusterDiskEntity raidDisk : raidDisks) {
                // check if the disk contains same raid disks
                if (!(raidDisk.equals(GlusterCoreUtil.getEntity(disk.getRaidDisks(), raidDisk.getName(), false)))) {
                    return false;
                }
            }
        }

        // // check if the disk contains same partitions
        // if (partitions != null) {
        // for (GlusterPartitionEntity partition : partitions) {
        // if (!(partition.equals(GlusterCoreUtil.getEntity(disk.getPartitions(), partition.getName(), false)))) {
        // return false;
        // }
        // }
        // }
        return true;
    }

    public void copyFrom(GlusterDiskEntity newDisk) {
        super.copyFrom(newDisk);
        setDescription(newDisk.getDescription());
        setDiskInterface(newDisk.getDiskInterface());
        setPartitions(newDisk.getPartitions());
        setRaidDisks(newDisk.getRaidDisks());
    }

    @Override
    public boolean isReady() {
        if (hasPartitions()) {
            for (GlusterPartitionEntity partition : getPartitions()) {
                if (partition.isReady()) {
                    return true;
                }
            }
            return false;
        } else {
            return super.isReady();
        }
    }

    @Override
    public Double getSpace() {
        Double space = 0d;
        if (hasPartitions()) {
            for (GlusterPartitionEntity partition : getPartitions()) {
                space += partition.getSpace();
            }
            return space;
        } else {
            return super.getSpace();
        }
    }

    @Override
    public Double getSpaceInUse() {
        Double spaceInUse = 0d;
        if (hasPartitions()) {
            for (GlusterPartitionEntity partition : getPartitions()) {
                if (partition.isInitialized()) {
                    spaceInUse += partition.getSpaceInUse();
                }
            }
            return spaceInUse;
        } else {
            return super.getSpaceInUse();
        }
    }
}
