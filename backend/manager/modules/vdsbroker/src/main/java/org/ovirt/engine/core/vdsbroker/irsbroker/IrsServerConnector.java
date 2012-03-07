package org.ovirt.engine.core.vdsbroker.irsbroker;

import java.util.Map;

public interface IrsServerConnector {
    public Map<String, Object> createVolume(String sdUUID, String spUUID, String imgGUID, int size, int volFormat,
            int volType, int diskType, String volUUID, String descr, String srcImgGUID, String srcVolUUID);

    public Map<String, Object> createVolume(String sdUUID, String spUUID, String imgGUID, String size, int volFormat,
            int volType, int diskType, String volUUID, String descr, String srcImgGUID, String srcVolUUID);

    public Map<String, Object> copyImage(String sdUUID, String spUUID, String vmGUID, String srcImgGUID,
            String srcVolUUID, String dstImgGUID, String dstVolUUID, String descr);

    public Map<String, Object> copyImage(String sdUUID, String spUUID, String vmGUID, String srcImgGUID,
            String srcVolUUID, String dstImgGUID, String dstVolUUID, String descr, String dstSdUUID, int volType,
            int volFormat, int preallocate, String postZero, String force);

    public Map<String, Object> setVolumeDescription(String sdUUID, String spUUID, String imgGUID, String volUUID,
            String description);

    public Map<String, Object> setVolumeLegality(String sdUUID, String spUUID, String imgGUID, String volUUID,
            String legality);

    public Map<String, Object> mergeSnapshots(String sdUUID, String spUUID, String vmGUID, String imgGUID,
            String ancestorUUID, String successorUUID);

    public Map<String, Object> mergeSnapshots(String sdUUID, String spUUID, String vmGUID, String imgGUID,
            String ancestorUUID, String successorUUID, String postZero);

    public Map<String, Object> deleteVolume(String sdUUID, String spUUID, String imgGUID, String[] volUUID,
            String postZero);

    public Map<String, Object> deleteVolume(String sdUUID, String spUUID, String imgGUID, String[] volUUID,
            String postZero, String force);

    public Map<String, Object> getVolumesList(String sdUUID, String spUUID, String imgGUID);

    public Map<String, Object> getVolumeInfo(String sdUUID, String spUUID, String imgGUID, String volUUID);

    public Map<String, Object> getStats();

    public Map<String, Object> exportCandidate(String sdUUID, String vmGUID, String[] volumesList, String vmMeta,
            String templateGUID, String templateVolGUID, String templateMeta, String expPath, String collapse,
            String force);

    public Map<String, Object> getImportCandidates(String path, String type, String vmType);

    public Map<String, Object> getImportCandidatesInfo(String path, String type, String vmType);

    public Map<String, Object> getCandidateInfo(String candidateGUID, String path, String type);

    public Map<String, Object> importCandidate(String sdUUID, String vmGUID, String templateGUID,
            String templateVolGUID, String path, String type, String force);

    public Map<String, Object> getIsoList(String spUUID);

    public Map<String, Object> getFloppyList(String spUUID);

    public Map<String, Object> extendVolume(String sdUUID, String spUUID, String imgGUID, String volUUID, int newSize);

    public Map<String, Object> activateStorageDomain(String sdUUID, String spUUID);

    public Map<String, Object> deactivateStorageDomain(String sdUUID, String spUUID, String msdUUID, int masterVersion);

    public Map<String, Object> detachStorageDomain(String sdUUID, String spUUID, String msdUUID, int masterVersion);

    public Map<String, Object> forcedDetachStorageDomain(String sdUUID, String spUUID);

    public Map<String, Object> attachStorageDomain(String sdUUID, String spUUID);

    public Map<String, Object> setStorageDomainDescription(String sdUUID, String description);

    public Map<String, Object> reconstructMaster(String spUUID, String msdUUID, String masterVersion);

    public Map<String, Object> extendStorageDomain(String sdUUID, String spUUID, String[] devlist);

    public Map<String, Object> setStoragePoolDescription(String spUUID, String description);

    public Map<String, Object> getStoragePoolInfo(String spUUID);

    public Map<String, Object> destroyStoragePool(String spUUID, int hostSpmId, String SCSIKey);

    public Map<String, Object> deleteImage(String sdUUID, String spUUID, String imgGUID, String postZero);

    public Map<String, Object> deleteImage(String sdUUID, String spUUID, String imgGUID, String postZero, String force);

    public Map<String, Object> moveImage(String spUUID, String srcDomUUID, String dstDomUUID, String imgGUID,
            String vmGUID, int op, String postZero, String force);

    public Map<String, Object> moveImage(String spUUID, String srcDomUUID, String dstDomUUID, String imgGUID,
            String vmGUID, int op);

    public Map<String, Object> moveMultipleImages(String spUUID, String srcDomUUID, String dstDomUUID, Map imgDict,
            String vmGUID);

    public Map<String, Object> getImageDomainsList(String spUUID, String imgUUID);

    public Map<String, Object> setMaxHosts(int maxHosts);

    public Map<String, Object> updateVM(String spUUID, Map[] vms);

    public Map<String, Object> removeVM(String spUUID, String vmGUID);

    public Map<String, Object> updateVM(String spUUID, Map[] vms, String StorageDomainId);

    public Map<String, Object> removeVM(String spUUID, String vmGUID, String storageDomainId);

    public Map<String, Object> getVmsInfo(String storagePoolId, String storageDomainId, String[] VMIDList);

    public Map<String, Object> getVmsList(String storagePoolId, String storageDomainId);

}
