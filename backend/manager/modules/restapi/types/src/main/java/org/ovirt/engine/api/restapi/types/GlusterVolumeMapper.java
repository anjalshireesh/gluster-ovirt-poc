package org.ovirt.engine.api.restapi.types;

import java.math.BigInteger;
import java.util.Set;

import org.ovirt.engine.api.model.GlusterVolume;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.ACCESS_PROTOCOL;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.TRANSPORT_TYPE;
import org.ovirt.engine.core.common.utils.StringUtil;

public class GlusterVolumeMapper {
    @Mapping(from = GlusterVolume.class, to = GlusterVolumeEntity.class)
    public static GlusterVolumeEntity map(GlusterVolume fromVolume, GlusterVolumeEntity toVolume) {
        GlusterVolumeEntity volume = toVolume != null ? toVolume : new GlusterVolumeEntity();
        volume.setId(fromVolume.getId());
        volume.setName(fromVolume.getVolumeName());
        volume.setVolumeType(fromVolume.getVolumeType());

        String transportType = fromVolume.getTransportType();
        if(transportType == null || transportType.trim().isEmpty()) {
            volume.setTransportType(TRANSPORT_TYPE.ETHERNET);
        } else {
            volume.setTransportType(transportType);
        }

        volume.setAccessProtocols(fromVolume.getAccessProtocols());
        volume.setAccessControlList(fromVolume.getAccessControlList());
        volume.setBricks(fromVolume.getBricks());

        BigInteger count = fromVolume.getReplicaCount();
        volume.setReplicaCount(count == null ? 0 : count.intValue());

        count = fromVolume.getStripeCount();
        volume.setStripeCount(count == null ? 0 : count.intValue());

        volume.setCifsUsers(fromVolume.getCifsUsers());
        volume.setOptions(fromVolume.getOptions());

        return volume;
    }

    @Mapping(from = GlusterVolumeEntity.class, to = GlusterVolume.class)
    public static GlusterVolume map(GlusterVolumeEntity fromVolume, GlusterVolume toVolume) {
        GlusterVolume volume = toVolume != null ? toVolume : new GlusterVolume();
        volume.setId(fromVolume.getId());
        volume.setVolumeName(fromVolume.getName());
        volume.setVolumeType(fromVolume.getVolumeType().toString());
        volume.setTransportType(fromVolume.getTransportType().toString());

        Set<ACCESS_PROTOCOL> accessProtocols = fromVolume.getAccessProtocols();
        volume.setAccessProtocols(StringUtil.collectionToString(accessProtocols, ","));

        volume.setAccessControlList(fromVolume.getAccessControlList());
        volume.setBricks(StringUtil.collectionToString(fromVolume.getBricks(), ","));
        volume.setReplicaCount(BigInteger.valueOf(fromVolume.getReplicaCount()));
        volume.setStripeCount(BigInteger.valueOf(fromVolume.getStripeCount()));
        volume.setCifsUsers(StringUtil.collectionToString(fromVolume.getCifsUsers(), ","));
        volume.setOptions(StringUtil.collectionToString(fromVolume.getOptions().getOptions(), ","));

        return volume;
    }
}
