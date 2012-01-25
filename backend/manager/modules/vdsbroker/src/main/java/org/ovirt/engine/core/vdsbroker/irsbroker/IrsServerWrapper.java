package org.ovirt.engine.core.vdsbroker.irsbroker;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.ovirt.engine.core.vdsbroker.glusterbroker.GlusterVolumeListReturnForXmlRpc;
import org.ovirt.engine.core.vdsbroker.vdsbroker.StatusOnlyReturnForXmlRpc;
import org.ovirt.engine.core.vdsbroker.vdsbroker.StorageDomainListReturnForXmlRpc;
import org.ovirt.engine.core.vdsbroker.xmlrpc.XmlRpcStruct;

public class IrsServerWrapper implements IIrsServer {

    private final IrsServerConnector irsServer;
    private final HttpClient httpClient;

    public IrsServerWrapper(IrsServerConnector innerImplementor, HttpClient httpClient) {
        this.irsServer = innerImplementor;
        this.httpClient = httpClient;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    @Override
    public OneUuidReturnForXmlRpc createVolume(String sdUUID, String spUUID, String imgGUID, String size,
            int volFormat, int volType, int diskType, String volUUID, String descr, String srcImgGUID, String srcVolUUID) {
        Map<String, Object> xmlRpcReturnValue = irsServer.createVolume(sdUUID, spUUID, imgGUID, size, volFormat,
                volType, diskType, volUUID, descr, srcImgGUID, srcVolUUID);
        OneUuidReturnForXmlRpc wrapper = new OneUuidReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public OneUuidReturnForXmlRpc copyImage(String sdUUID, String spUUID, String vmGUID, String srcImgGUID,
            String srcVolUUID, String dstImgGUID, String dstVolUUID, String descr, String dstSdUUID, int volType,
            int volFormat, int preallocate, String postZero, String force) {
        Map<String, Object> xmlRpcReturnValue = irsServer.copyImage(sdUUID, spUUID, vmGUID, srcImgGUID, srcVolUUID,
                dstImgGUID, dstVolUUID, descr, dstSdUUID, volType, volFormat, preallocate, postZero, force);
        OneUuidReturnForXmlRpc wrapper = new OneUuidReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc setVolumeDescription(String sdUUID, String spUUID, String imgGUID, String volUUID,
            String description) {
        Map<String, Object> xmlRpcReturnValue = irsServer.setVolumeDescription(sdUUID, spUUID, imgGUID, volUUID,
                description);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc setVolumeLegality(String sdUUID, String spUUID, String imgGUID, String volUUID,
            String legality) {
        Map<String, Object> xmlRpcReturnValue = irsServer.setVolumeLegality(sdUUID, spUUID, imgGUID, volUUID, legality);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public OneUuidReturnForXmlRpc mergeSnapshots(String sdUUID, String spUUID, String vmGUID, String imgGUID,
            String ancestorUUID, String successorUUID, String postZero) {
        Map<String, Object> xmlRpcReturnValue = irsServer.mergeSnapshots(sdUUID, spUUID, vmGUID, imgGUID, ancestorUUID,
                successorUUID, postZero);
        OneUuidReturnForXmlRpc wrapper = new OneUuidReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public OneUuidReturnForXmlRpc deleteVolume(String sdUUID, String spUUID, String imgGUID, String[] volUUID,
            String postZero, String force) {
        Map<String, Object> xmlRpcReturnValue = irsServer.deleteVolume(sdUUID, spUUID, imgGUID, volUUID, postZero,
                force);
        OneUuidReturnForXmlRpc wrapper = new OneUuidReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public UuidListReturnForXmlRpc getVolumesList(String sdUUID, String spUUID, String imgGUID) {
        Map<String, Object> xmlRpcReturnValue = irsServer.getVolumesList(sdUUID, spUUID, imgGUID);
        UuidListReturnForXmlRpc wrapper = new UuidListReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public OneImageInfoReturnForXmlRpc getVolumeInfo(String sdUUID, String spUUID, String imgGUID, String volUUID) {
        Map<String, Object> xmlRpcReturnValue = irsServer.getVolumeInfo(sdUUID, spUUID, imgGUID, volUUID);
        OneImageInfoReturnForXmlRpc wrapper = new OneImageInfoReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public IrsStatsAndStatusXmlRpc getIrsStats() {
        Map<String, Object> xmlRpcReturnValue = irsServer.getStats();
        IrsStatsAndStatusXmlRpc wrapper = new IrsStatsAndStatusXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public OneUuidReturnForXmlRpc exportCandidate(String sdUUID, String vmGUID, String[] volumesList, String vmMeta,
            String templateGUID, String templateVolGUID, String templateMeta, String expPath, String collapse,
            String force) {
        Map<String, Object> xmlRpcReturnValue = irsServer.exportCandidate(sdUUID, vmGUID, volumesList, vmMeta,
                templateGUID, templateVolGUID, templateMeta, expPath, collapse, force);
        OneUuidReturnForXmlRpc wrapper = new OneUuidReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public IrsVMListReturnForXmlRpc getImportCandidates(String path, String type, String vmType) {
        Map<String, Object> xmlRpcReturnValue = irsServer.getImportCandidates(path, type, vmType);
        IrsVMListReturnForXmlRpc wrapper = new IrsVMListReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public ImportCandidatesInfoReturnForXmlRpc getImportCandidatesInfo(String path, String type, String vmType) {
        Map<String, Object> xmlRpcReturnValue = irsServer.getImportCandidatesInfo(path, type, vmType);
        ImportCandidatesInfoReturnForXmlRpc wrapper = new ImportCandidatesInfoReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public ImportCandidateInfoReturnForXmlRpc getCandidateInfo(String candidateGUID, String path, String type) {
        Map<String, Object> xmlRpcReturnValue = irsServer.getCandidateInfo(candidateGUID, path, type);
        ImportCandidateInfoReturnForXmlRpc wrapper = new ImportCandidateInfoReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public OneUuidReturnForXmlRpc importCandidate(String sdUUID, String vmGUID, String templateGUID,
            String templateVolGUID, String path, String type, String force) {
        Map<String, Object> xmlRpcReturnValue = irsServer.importCandidate(sdUUID, vmGUID, templateGUID,
                templateVolGUID, path, type, force);
        OneUuidReturnForXmlRpc wrapper = new OneUuidReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public IsoListReturnForXmlRpc getIsoList(String spUUID) {
        Map<String, Object> xmlRpcReturnValue = irsServer.getIsoList(spUUID);
        IsoListReturnForXmlRpc wrapper = new IsoListReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public IsoListReturnForXmlRpc getFloppyList(String spUUID) {
        Map<String, Object> xmlRpcReturnValue = irsServer.getFloppyList(spUUID);
        IsoListReturnForXmlRpc wrapper = new IsoListReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc extendVolume(String sdUUID, String spUUID, String imgGUID, String volUUID,
            int newSize) {
        Map<String, Object> xmlRpcReturnValue = irsServer.extendVolume(sdUUID, spUUID, imgGUID, volUUID, newSize);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StorageStatusReturnForXmlRpc activateStorageDomain(String sdUUID, String spUUID) {
        Map<String, Object> xmlRpcReturnValue = irsServer.activateStorageDomain(sdUUID, spUUID);
        StorageStatusReturnForXmlRpc wrapper = new StorageStatusReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc deactivateStorageDomain(String sdUUID, String spUUID, String msdUUID,
            int masterVersion) {
        Map<String, Object> xmlRpcReturnValue = irsServer.deactivateStorageDomain(sdUUID, spUUID, msdUUID,
                masterVersion);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc detachStorageDomain(String sdUUID, String spUUID, String msdUUID, int masterVersion) {
        Map<String, Object> xmlRpcReturnValue = irsServer.detachStorageDomain(sdUUID, spUUID, msdUUID, masterVersion);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc forcedDetachStorageDomain(String sdUUID, String spUUID) {
        Map<String, Object> xmlRpcReturnValue = irsServer.forcedDetachStorageDomain(sdUUID, spUUID);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc attachStorageDomain(String sdUUID, String spUUID) {
        Map<String, Object> xmlRpcReturnValue = irsServer.attachStorageDomain(sdUUID, spUUID);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc setStorageDomainDescription(String sdUUID, String description) {
        Map<String, Object> xmlRpcReturnValue = irsServer.setStorageDomainDescription(sdUUID, description);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StorageDomainListReturnForXmlRpc reconstructMaster(String spUUID, String msdUUID, String masterVersion) {
        Map<String, Object> xmlRpcReturnValue = irsServer.reconstructMaster(spUUID, msdUUID, masterVersion);
        StorageDomainListReturnForXmlRpc wrapper = new StorageDomainListReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc extendStorageDomain(String sdUUID, String spUUID, String[] devlist) {
        Map<String, Object> xmlRpcReturnValue = irsServer.extendStorageDomain(sdUUID, spUUID, devlist);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc setStoragePoolDescription(String spUUID, String description) {
        Map<String, Object> xmlRpcReturnValue = irsServer.setStoragePoolDescription(spUUID, description);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StoragePoolInfoReturnForXmlRpc getStoragePoolInfo(String spUUID) {
        Map<String, Object> xmlRpcReturnValue = irsServer.getStoragePoolInfo(spUUID);
        StoragePoolInfoReturnForXmlRpc wrapper = new StoragePoolInfoReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc destroyStoragePool(String spUUID, int hostSpmId, String SCSIKey) {
        Map<String, Object> xmlRpcReturnValue = irsServer.destroyStoragePool(spUUID, hostSpmId, SCSIKey);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public OneUuidReturnForXmlRpc deleteImage(String sdUUID, String spUUID, String imgGUID, String postZero,
            String force) {
        Map<String, Object> xmlRpcReturnValue = irsServer.deleteImage(sdUUID, spUUID, imgGUID, postZero, force);
        OneUuidReturnForXmlRpc wrapper = new OneUuidReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public OneUuidReturnForXmlRpc moveImage(String spUUID, String srcDomUUID, String dstDomUUID, String imgGUID,
            String vmGUID, int op, String postZero, String force) {
        Map<String, Object> xmlRpcReturnValue = irsServer.moveImage(spUUID, srcDomUUID, dstDomUUID, imgGUID, vmGUID,
                op, postZero, force);
        OneUuidReturnForXmlRpc wrapper = new OneUuidReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public OneUuidReturnForXmlRpc moveMultipleImages(String spUUID, String srcDomUUID, String dstDomUUID,
            XmlRpcStruct imgDict, String vmGUID) {
        Map<String, Object> xmlRpcReturnValue = irsServer.moveMultipleImages(spUUID, srcDomUUID, dstDomUUID,
                imgDict.getInnerMap(), vmGUID);
        OneUuidReturnForXmlRpc wrapper = new OneUuidReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StorageDomainGuidListReturnForXmlRpc getImageDomainsList(String spUUID, String imgUUID) {
        Map<String, Object> xmlRpcReturnValue = irsServer.getImageDomainsList(spUUID, imgUUID);
        StorageDomainGuidListReturnForXmlRpc wrapper = new StorageDomainGuidListReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc setMaxHosts(int maxHosts) {
        Map<String, Object> xmlRpcReturnValue = irsServer.setMaxHosts(maxHosts);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc updateVM(String spUUID, Map[] vms) {
        Map<String, Object> xmlRpcReturnValue = irsServer.updateVM(spUUID, vms);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc removeVM(String spUUID, String vmGUID) {
        Map<String, Object> xmlRpcReturnValue = irsServer.removeVM(spUUID, vmGUID);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc updateVMInImportExport(String spUUID, Map[] vms, String StorageDomainId) {
        Map<String, Object> xmlRpcReturnValue = irsServer.updateVM(spUUID, vms, StorageDomainId);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc removeVM(String spUUID, String vmGUID, String storageDomainId) {
        Map<String, Object> xmlRpcReturnValue = irsServer.removeVM(spUUID, vmGUID, storageDomainId);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public GetVmsInfoReturnForXmlRpc getVmsInfo(String storagePoolId, String storageDomainId, String[] VMIDList) {
        Map<String, Object> xmlRpcReturnValue = irsServer.getVmsInfo(storagePoolId, storageDomainId, VMIDList);
        GetVmsInfoReturnForXmlRpc wrapper = new GetVmsInfoReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public GetVmsListReturnForXmlRpc getVmsList(String storagePoolId, String storageDomainId) {
        Map<String, Object> xmlRpcReturnValue = irsServer.getVmsList(storagePoolId, storageDomainId);
        GetVmsListReturnForXmlRpc wrapper = new GetVmsListReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc glusterVolumeCreate(
            Map<String, Object> volumeData) {
        Map<String, Object> xmlRpcReturnValue = irsServer.glusterVolumeCreate(volumeData);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public GlusterVolumeListReturnForXmlRpc glusterVolumesList() {
        Map<String, Object> xmlRpcReturnValue = irsServer.glusterVolumesList();
        GlusterVolumeListReturnForXmlRpc wrapper = new GlusterVolumeListReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc glusterVolumeAddBrick(String volumeName, String[] brickList) {
        Map<String, Object> xmlRpcReturnValue = irsServer.glusterVolumeAddBrick(volumeName, brickList);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc glusterVolumeRebalanceStart(String volumeName, String mode) {
        Map<String, Object> xmlRpcReturnValue = irsServer.glusterVolumeRebalanceStart(volumeName, mode);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc glusterVolumeRebalanceStop(String volumeName) {
        Map<String, Object> xmlRpcReturnValue = irsServer.glusterVolumeRebalanceStop(volumeName);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc glusterVolumeRebalanceStatus(String volumeName) {
        Map<String, Object> xmlRpcReturnValue = irsServer.glusterVolumeRebalanceStatus(volumeName);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc glusterVolumeStart(String volumeName) {
        Map<String, Object> xmlRpcReturnValue = irsServer.glusterVolumeStart(volumeName);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc glusterVolumeStop(String volumeName) {
        Map<String, Object> xmlRpcReturnValue = irsServer.glusterVolumeStop(volumeName);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc glusterVolumeDelete(String volumeName) {
        Map<String, Object> xmlRpcReturnValue = irsServer.glusterVolumeDelete(volumeName);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc glusterVolumeSet(String volumeName, String key, String value) {
        Map<String, Object> xmlRpcReturnValue = irsServer.glusterVolumeSet(volumeName, key, value);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }

    @Override
    public StatusOnlyReturnForXmlRpc glusterHostAdd(String hostName) {
        Map<String, Object> xmlRpcReturnValue = irsServer.glusterHostAdd(hostName);
        StatusOnlyReturnForXmlRpc wrapper = new StatusOnlyReturnForXmlRpc(xmlRpcReturnValue);
        return wrapper;
    }
}
