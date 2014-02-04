package org.ovirt.engine.core.vdsbroker.vdsbroker;

import java.util.Map;

import org.ovirt.engine.core.vdsbroker.glusterbroker.GlusterVolumeListReturnForXmlRpc;
import org.ovirt.engine.core.vdsbroker.irsbroker.IsoListReturnForXmlRpc;
import org.ovirt.engine.core.vdsbroker.irsbroker.OneUuidReturnForXmlRpc;
import org.ovirt.engine.core.vdsbroker.xmlrpc.XmlRpcStruct;


public interface IVdsServer {
    OneVmReturnForXmlRpc create(XmlRpcStruct createInfo);

    StatusOnlyReturnForXmlRpc destroy(String vmId);

    StatusOnlyReturnForXmlRpc shutdown(String vmId, String timeout, String message);

    StatusOnlyReturnForXmlRpc shutdownHost(int reboot);

    OneVmReturnForXmlRpc pause(String vmId);

    StatusOnlyReturnForXmlRpc hibernate(String vmId, String hiberVolHandle);

    OneVmReturnForXmlRpc powerDown(String vmId);

    OneVmReturnForXmlRpc reset(String vmId);

    OneVmReturnForXmlRpc resume(String vmId);

    VMListReturnForXmlRpc list();

    VDSInfoReturnForXmlRpc getCapabilities();

    VDSInfoReturnForXmlRpc getVdsStats();

    StatusOnlyReturnForXmlRpc desktopLogin(String vmId, String domain, String user, String password);

    StatusOnlyReturnForXmlRpc desktopLogoff(String vmId, String force);

    StatusOnlyReturnForXmlRpc desktopLock(String vmId);

    VMInfoListReturnForXmlRpc getVmStats(String vmId);

    VMInfoListReturnForXmlRpc getAllVmStats();

    StatusOnlyReturnForXmlRpc migrate(Map<String, String> migrationInfo);

    StatusOnlyReturnForXmlRpc migrateStatus(String vmId);

    StatusOnlyReturnForXmlRpc migrateCancel(String vmId);

    OneVmReturnForXmlRpc changeDisk(String vmId, String imageLocation);

    OneVmReturnForXmlRpc changeFloppy(String vmId, String imageLocation);

    StatusOnlyReturnForXmlRpc heartBeat();

    StatusOnlyReturnForXmlRpc monitorCommand(String vmId, String monitorCommand);

    StatusOnlyReturnForXmlRpc sendHcCmdToDesktop(String vmId, String hcCommand);

    StatusOnlyReturnForXmlRpc setVmTicket(String vmId, String otp64, String sec);

    StatusOnlyReturnForXmlRpc startSpice(String vdsIp, int port, String ticket);

    StatusOnlyReturnForXmlRpc addNetwork(String bridge, String vlan, String bond, String[] nics,
            Map<String, String> options);

    StatusOnlyReturnForXmlRpc delNetwork(String bridge, String vlan, String bond, String[] nics);

    StatusOnlyReturnForXmlRpc editNetwork(String oldBridge, String newBridge, String vlan, String bond, String[] nics,
            Map<String, String> options);

    StatusOnlyReturnForXmlRpc setupNetworks(XmlRpcStruct networks, XmlRpcStruct bonding, XmlRpcStruct options);

    StatusOnlyReturnForXmlRpc setSafeNetworkConfig();

    FenceStatusReturnForXmlRpc fenceNode(String ip, String port, String type, String user, String password,
            String action, String secured, String options);

    ServerConnectionStatusReturnForXmlRpc connectStorageServer(int serverType, String spUUID, Map<String, String>[] args);

    ServerConnectionStatusReturnForXmlRpc validateStorageServerConnection(int serverType, String spUUID,
            Map<String, String>[] args);

    ServerConnectionStatusReturnForXmlRpc disconnectStorageServer(int serverType, String spUUID,
            Map<String, String>[] args);

    ServerConnectionListReturnForXmlRpc getStorageConnectionsList(String spUUID);

    StatusOnlyReturnForXmlRpc validateStorageDomain(String sdUUID);

    StatusOnlyReturnForXmlRpc createStorageDomain(int domainType, String sdUUID, String domainName, String arg,
            int storageType);

    StatusOnlyReturnForXmlRpc createStorageDomain(int domainType, String sdUUID, String domainName, String arg,
            int storageType, String storageFormatType);

    StatusOnlyReturnForXmlRpc formatStorageDomain(String sdUUID);

    StatusOnlyReturnForXmlRpc connectStoragePool(String spUUID, int hostSpmId, String SCSIKey, String masterdomainId,
            int masterVersion);

    StatusOnlyReturnForXmlRpc disconnectStoragePool(String spUUID, int hostSpmId, String SCSIKey);

    StatusOnlyReturnForXmlRpc createStoragePool(int poolType, String spUUID, String poolName, String msdUUID,
            String[] domList, int masterVersion, String lockPolicy, int lockRenewalIntervalSec, int leaseTimeSec,
            int ioOpTimeoutSec, int leaseRetries);

    StatusOnlyReturnForXmlRpc reconstructMaster(String spUUID, String poolName, String masterDom,
            Map<String, String> domDict, int masterVersion, String lockPolicy, int lockRenewalIntervalSec,
            int leaseTimeSec, int ioOpTimeoutSec, int leaseRetries);

    OneStorageDomainStatsReturnForXmlRpc getStorageDomainStats(String sdUUID);

    OneStorageDomainInfoReturnForXmlRpc getStorageDomainInfo(String sdUUID);

    StorageDomainListReturnForXmlRpc getStorageDomainsList(String spUUID, int domainType, int poolType, String path);

    IsoListReturnForXmlRpc getIsoList(String spUUID);

    OneUuidReturnForXmlRpc createVG(String sdUUID, String[] deviceList);

    @Deprecated
    StatusOnlyReturnForXmlRpc removeVG(String vgUUID);

    VGListReturnForXmlRpc getVGList();

    OneVGReturnForXmlRpc getVGInfo(String vgUUID);

    LUNListReturnForXmlRpc getDeviceList(int storageType);

    OneLUNReturnForXmlRpc getDeviceInfo(String devGUID);

    DevicesVisibilityMapReturnForXmlRpc getDevicesVisibility(String[] devicesList);

    IQNListReturnForXmlRpc discoverSendTargets(Map<String, String> args);

    SessionsListReturnForXmlRpc getSessionList();

    OneUuidReturnForXmlRpc spmStart(String spUUID,
            int prevID,
            String prevLVER,
            int recoveryMode,
            String SCSIFencing,
            int maxHostId);

    OneUuidReturnForXmlRpc spmStart(String spUUID,
            int prevID,
            String prevLVER,
            int recoveryMode,
            String SCSIFencing,
            int maxHostId,
            String storagePoolFormatType);

    StatusOnlyReturnForXmlRpc spmStop(String spUUID);

    SpmStatusReturnForXmlRpc spmStatus(String spUUID);

    StatusOnlyReturnForXmlRpc fenceSpmStorage(String spUUID, int prevID, String prevLVER);

    StatusOnlyReturnForXmlRpc refreshStoragePool(String spUUID, String msdUUID, int masterVersion);

    TaskStatusReturnForXmlRpc getTaskStatus(String taskUUID);

    TaskStatusListReturnForXmlRpc getAllTasksStatuses();

    TaskInfoReturnForXmlRpc getTaskInfo(String taskUUID);

    TaskInfoListReturnForXmlRpc getAllTasksInfo();

    StatusOnlyReturnForXmlRpc stopTask(String taskUUID);

    StatusOnlyReturnForXmlRpc clearTask(String taskUUID);

    StatusOnlyReturnForXmlRpc revertTask(String taskUUID);

    GlusterDiskListReturnForXmlRpc glusterDisksList();

    OneUuidReturnForXmlRpc glusterVolumeCreate(Map<String, Object> volumeData);
    StatusOnlyReturnForXmlRpc glusterVolumeStart(String volumeName);
    StatusOnlyReturnForXmlRpc glusterVolumeStop(String volumeName);
    GlusterVolumeListReturnForXmlRpc glusterVolumesList();
    StatusOnlyReturnForXmlRpc glusterVolumeDelete(String volumeName);
    StatusOnlyReturnForXmlRpc glusterVolumeAddBrick(String volumeName, String[] brickList);
    StatusOnlyReturnForXmlRpc glusterVolumeRemoveBrick(String volumeName, String[] brickList);
    StatusOnlyReturnForXmlRpc glusterVolumeRebalanceStart(String volumeName, String mode, String force);
    StatusOnlyReturnForXmlRpc glusterVolumeRebalanceStop(String volumeName);
    GlusterTaskStatusReturnForXmlRpc glusterVolumeRebalanceStatus(String volumeName);
    StatusOnlyReturnForXmlRpc glusterVolumeSet(String volumeName, String key, String value);
    StatusOnlyReturnForXmlRpc glusterHostAdd(String hostName);
    StatusOnlyReturnForXmlRpc glusterVolumeReplaceBrickStart(String volumeName, String sourceBrick, String targetBrick);
    StatusOnlyReturnForXmlRpc glusterVolumeReplaceBrickAbort(String volumeName, String sourceBrick, String targetBrick);
    StatusOnlyReturnForXmlRpc glusterVolumeReplaceBrickStatus(String volumeName, String sourceBrick, String targetBrick);
    StatusOnlyReturnForXmlRpc glusterVolumeReplaceBrickPause(String volumeName, String sourceBrick, String targetBrick);
    StatusOnlyReturnForXmlRpc glusterVolumeReplaceBrickCommit(String volumeName, String sourceBrick, String targetBrick);
}
