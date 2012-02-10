package org.ovirt.engine.core.vdsbroker.vdsbroker;

import java.util.Map;

import org.ovirt.engine.core.common.businessentities.GlusterDeviceEntity;
import org.ovirt.engine.core.common.businessentities.GlusterDeviceEntity.DEVICE_STATUS;
import org.ovirt.engine.core.common.businessentities.GlusterDeviceEntity.DEVICE_TYPE;
import org.ovirt.engine.core.common.businessentities.GlusterDiskEntity;
import org.ovirt.engine.core.common.businessentities.GlusterPartitionEntity;
import org.ovirt.engine.core.vdsbroker.irsbroker.StatusReturnForXmlRpc;

public class GlusterDiskListReturnForXmlRpc extends StatusReturnForXmlRpc {
    private static final String GLUSTER_DISKS = "disks";
    // We are ignoring missing fields after the status, because on failure it is
    // not sent.
    // [XmlRpcMissingMapping(MappingAction.Ignore), XmlRpcMember("disks")]
    public GlusterDiskEntity[] disks;

    @SuppressWarnings("unchecked")
    public GlusterDiskListReturnForXmlRpc(Map<String, Object> innerMap) {
        super(innerMap);
        Object[] temp = (Object[]) innerMap.get(GLUSTER_DISKS);
        if (temp != null) {
            disks = new GlusterDiskEntity[temp.length];
            for (int i = 0; i < temp.length; i++) {
                disks[i] = prepareDiskEntity((Map<String, Object>)temp[i]);
            }
        }
    }

    private GlusterDiskEntity prepareDiskEntity(Map<String, Object> map) {
        GlusterDiskEntity disk = new GlusterDiskEntity();
        disk.setDescription(map.get("description").toString());
        disk.setDiskInterface(map.get("interface").toString());
        disk.setName(map.get("name").toString());
        populateDevice(disk, map);

        Map<String, Object> temp = (Map<String, Object>)map.get("partitions");
        for(String partitionName : temp.keySet()) {
            GlusterPartitionEntity partition = new GlusterPartitionEntity();
            partition.setName(partitionName);

            Map<String, Object> partitionMap = (Map<String, Object>)temp.get(partitionName);
            populateDevice(partition, partitionMap);
            disk.addPartition(partition);
        }

        return disk;
    }

    private void populateDevice(GlusterDeviceEntity device, Map<String, Object> deviceMap) {
        device.setFsType(deviceMap.get("fsType").toString());
        device.setFsVersion(deviceMap.get("fsVersion").toString());
        device.setId(deviceMap.get("uuid").toString());
        device.setMountPoint(deviceMap.get("mountPoint").toString());

        String diskSize = deviceMap.get("size").toString().trim();
        if(!diskSize.isEmpty()) {
            device.setSpace(Double.valueOf(diskSize));
        }

        String diskSpaceInUse = deviceMap.get("spaceInUse").toString().trim();
        if(!diskSpaceInUse.isEmpty()) {
            device.setSpaceInUse(Double.valueOf(diskSpaceInUse));
        }

        device.setStatus(DEVICE_STATUS.valueOf(deviceMap.get("status").toString()));
        device.setType(DEVICE_TYPE.valueOf(deviceMap.get("type").toString()));
    }

}
