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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.ovirt.engine.core.common.businessentities.GlusterBrick.BRICK_STATUS;
import org.ovirt.engine.core.common.constants.GlusterConstants;
import org.ovirt.engine.core.common.utils.GlusterCoreUtil;
import org.ovirt.engine.core.common.utils.StringUtil;

public class GlusterVolumeEntity extends GlusterEntity implements BusinessEntity<String> {
    public enum VOLUME_STATUS {
        ONLINE,
        OFFLINE
    };

    public enum VOLUME_TYPE {
        DISTRIBUTE,
        REPLICATE,
        DISTRIBUTED_REPLICATE,
        STRIPE,
        DISTRIBUTED_STRIPE
    };

    public enum TRANSPORT_TYPE {
        ETHERNET,
        INFINIBAND
    };

    public enum ACCESS_PROTOCOL {
        GLUSTER,
        NFS,
        CIFS
    };

    public static final int DEFAULT_REPLICA_COUNT = 2;
    public static final int DEFAULT_STRIPE_COUNT = 4;

    public static final String OPTION_AUTH_ALLOW = "auth.allow";
    public static final String OPTION_NFS_DISABLE = "nfs.disable";

    private static final String[] VOLUME_TYPE_STR = new String[] { "Distribute", "Replicate", "Distributed Replicate",
            "Stripe", "Distributed Stripe" };

    private static final String[] TRANSPORT_TYPE_STR = new String[] { "Ethernet", "Infiniband" };
    private static final String[] STATUS_STR = new String[] { "Online", "Offline" };
    private static final String[] ACCESS_PROTOCOL_STR = new String[] { "Gluster", "NFS", "CIFS" };

    private VOLUME_TYPE volumeType;
    private TRANSPORT_TYPE transportType;
    private VOLUME_STATUS status;
    private int replicaCount;
    private int stripeCount;
    private GlusterVolumeOptions options = new GlusterVolumeOptions();
    private List<GlusterBrick> bricks = new ArrayList<GlusterBrick>();
    private List<String> cifsUsers = new ArrayList<String>();

    public GlusterVolumeEntity() {
    }

    // Only GlusterFS is enabled
    private Set<ACCESS_PROTOCOL> accessProtocols = new LinkedHashSet<ACCESS_PROTOCOL>(Arrays.asList(new ACCESS_PROTOCOL[] {
            ACCESS_PROTOCOL.GLUSTER }));

    public String getVolumeTypeStr() {
        return getVolumeTypeStr(getVolumeType());
    }

    public static String getVolumeTypeStr(VOLUME_TYPE volumeType) {
        return VOLUME_TYPE_STR[volumeType.ordinal()];
    }

    public static VOLUME_TYPE getVolumeTypeByStr(String volumeTypeStr) {
        return VOLUME_TYPE.valueOf(volumeTypeStr);
    }

    public static TRANSPORT_TYPE getTransportTypeByStr(String transportTypeStr) {
        return TRANSPORT_TYPE.valueOf(transportTypeStr);
    }

    public String getTransportTypeStr() {
        return TRANSPORT_TYPE_STR[getTransportType().ordinal()];
    }

    public String getStatusStr() {
        return STATUS_STR[getStatus().ordinal()];
    }

    public int getNumOfBricks() {
        return bricks.size();
    }

    public VOLUME_TYPE getVolumeType() {
        return volumeType;
    }

    public void setVolumeType(VOLUME_TYPE volumeType) {
        this.volumeType = volumeType;
        if (volumeType == VOLUME_TYPE.DISTRIBUTED_STRIPE) {
            setReplicaCount(0);
            setStripeCount(DEFAULT_STRIPE_COUNT);
        } else if (volumeType == VOLUME_TYPE.DISTRIBUTED_REPLICATE) {
            setReplicaCount(DEFAULT_REPLICA_COUNT);
            setStripeCount(0);
        } else {
            setReplicaCount(0);
            setStripeCount(0);
        }
    }

    public void setVolumeType(String volumeType) {
        for(VOLUME_TYPE type : VOLUME_TYPE.values()) {
            if(type.toString().equals(volumeType)) {
                setVolumeType(type);
                return;
            }
        }
    }

    public TRANSPORT_TYPE getTransportType() {
        return transportType;
    }

    public void setTransportType(TRANSPORT_TYPE transportType) {
        this.transportType = transportType;
    }

    public void setTransportType(String transportType) {
        for(TRANSPORT_TYPE type : TRANSPORT_TYPE.values()) {
            if(type.toString().equals(transportType)) {
                setTransportType(type);
                return;
            }
        }
    }

    public VOLUME_STATUS getStatus() {
        return status;
    }

    public int getReplicaCount() {
        return replicaCount;
    }

    public void setReplicaCount(int replicaCount) {
        this.replicaCount = replicaCount;
    }

    public int getStripeCount() {
        return stripeCount;
    }

    public void setStripeCount(int stripeCount) {
        this.stripeCount = stripeCount;
    }

    public void setStatus(VOLUME_STATUS status) {
        this.status = status;
    }

    public Set<ACCESS_PROTOCOL> getAccessProtocols() {
        return accessProtocols;
    }

    public void setAccessProtocols(Set<ACCESS_PROTOCOL> accessProtocols) {
        this.accessProtocols = accessProtocols;
    }

    public String getAccessProtocolsStr() {
        String protocolsStr = "";
        for (ACCESS_PROTOCOL protocol : accessProtocols) {
            String protocolStr = ACCESS_PROTOCOL_STR[protocol.ordinal()];
            protocolsStr += (protocolsStr.isEmpty() ? protocolStr : ", " + protocolStr);
        }
        return protocolsStr;
    }

    public void setAccessProtocols(String accessProtocols) {
        if(accessProtocols == null || accessProtocols.trim().isEmpty()) {
            this.accessProtocols = null;
            return;
        }

        Set<ACCESS_PROTOCOL> protocols = new HashSet<GlusterVolumeEntity.ACCESS_PROTOCOL>();
        String[] accessProtocolList = accessProtocols.split(",", -1);
        for (String accessProtocol : accessProtocolList) {
            protocols.add(ACCESS_PROTOCOL.valueOf(accessProtocol.trim()));
        }
        setAccessProtocols(protocols);
    }

    public String getAccessControlList() {
        return options.get(OPTION_AUTH_ALLOW);
    }

    public void setAccessControlList(String accessControlList) {
        setOption(OPTION_AUTH_ALLOW, accessControlList);
    }

    public boolean isNfsEnabled() {
        String nfsDisabled = options.get(OPTION_NFS_DISABLE);
        if (nfsDisabled == null || nfsDisabled.equalsIgnoreCase(GlusterConstants.OFF)) {
            return true;
        } else {
            return false;
        }
    }

    public GlusterVolumeOptions getOptions() {
        return options;
    }

    public void setOption(String key, String value) {
        options.put(key, value);
    }

    public void setOptions(GlusterVolumeOptions options) {
        this.options = options;
    }

    public void setOptions(String options) {
        if(options == null || options.trim().isEmpty()) {
            this.options = null;
            return;
        }

        String[] optionArr = options.split(",", -1);
        for(String option : optionArr) {
            String[] optionInfo = option.split("=", -1);
            setOption(optionInfo[0], optionInfo[1]);
        }
    }

    public void setOptions(LinkedHashMap<String, String> options) {
        List<GlusterVolumeOption> volumeOptions = new ArrayList<GlusterVolumeOption>();
        for (Entry<String, String> entry : options.entrySet()) {
            volumeOptions.add(new GlusterVolumeOption(entry.getKey(), entry.getValue()));
        }
        this.options.setOptions(volumeOptions);
    }

    public void addBrick(GlusterBrick GlusterBrick) {
        bricks.add(GlusterBrick);
    }

    public void addBrick(String brickQualifiedName) {
        bricks.add(new GlusterBrick(brickQualifiedName));
    }

    public void addBricks(Collection<GlusterBrick> bricks) {
        this.bricks.addAll(bricks);
    }

    public void setBricks(List<GlusterBrick> bricks) {
        this.bricks = bricks;
    }

    public void setBricks(String bricksStr) {
        if(bricksStr == null || bricksStr.trim().isEmpty()) {
            this.bricks = new ArrayList<GlusterBrick>();
            return;
        }

        String[] brickList = bricksStr.split(",", -1);
        List<GlusterBrick> bricks = new ArrayList<GlusterBrick>();
        for(String brick : brickList) {
            String[] brickInfo = brick.split(":", -1);
            bricks.add(new GlusterBrick(brickInfo[0], BRICK_STATUS.ONLINE, brickInfo[1]));
        }
        setBricks(bricks);
    }

    public void removeBrick(GlusterBrick GlusterBrick) {
        bricks.remove(GlusterBrick);
    }

    public List<GlusterBrick> getBricks() {
        return bricks;
    }

    public void enableNFS() {
        accessProtocols.add(ACCESS_PROTOCOL.NFS);
        setOption(OPTION_NFS_DISABLE, GlusterConstants.OFF);
    }

    public void disableNFS() {
        accessProtocols.remove(ACCESS_PROTOCOL.NFS);
        setOption(OPTION_NFS_DISABLE, GlusterConstants.ON);
    }

    public void enableCifs() {
        if (!accessProtocols.contains(ACCESS_PROTOCOL.CIFS)) {
            accessProtocols.add(ACCESS_PROTOCOL.CIFS);
        }
    }

    public void disableCifs() {
        accessProtocols.remove(ACCESS_PROTOCOL.CIFS);
    }

    public boolean isCifsEnable() {
        return accessProtocols.contains(ACCESS_PROTOCOL.CIFS);
    }

    public void setCifsUsers(List<String> cifsUsers) {
        this.cifsUsers = cifsUsers;
    }

    public void setCifsUsers(String cifsUsers) {
        if(cifsUsers == null || cifsUsers.trim().isEmpty()) {
            this.cifsUsers = new ArrayList<String>();
            return;
        }

        String[] userList = cifsUsers.split(",", -1);
        List<String> users = new ArrayList<String>();
        for(String user : userList) {
            users.add(user.trim());
        }
        setCifsUsers(users);
    }

    public List<String> getCifsUsers() {
        return cifsUsers;
    }

    public GlusterVolumeEntity(String name,
            GlusterEntity parent,
            VOLUME_TYPE volumeType,
            TRANSPORT_TYPE transportType,
            VOLUME_STATUS status) {
        super(name, parent);
        setVolumeType(volumeType);
        setTransportType(transportType);
        setStatus(status);
    }

    /**
     * Filter matches if any of the properties name, GlusterVolume type, transport type, status and number of disks
     * contains the filter string
     */
    @Override
    public boolean filter(String filterString, boolean caseSensitive) {
        return StringUtil.filterString(getName() + getVolumeTypeStr() + getTransportTypeStr() + getStatusStr()
                + getNumOfBricks(), filterString, caseSensitive);
    }

    public List<String> getBrickDirectories() {
        List<String> brickDirectories = new ArrayList<String>();
        for (GlusterBrick brick : getBricks()) {
            brickDirectories.add(brick.getQualifiedName());
        }
        return brickDirectories;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GlusterVolumeEntity)) {
            return false;
        }

        GlusterVolumeEntity volume = (GlusterVolumeEntity) obj;

        if (!(getName().equals(volume.getName()) && getVolumeType() == volume.getVolumeType()
                && getTransportType() == volume.getTransportType() && getStatus() == volume.getStatus()
                && getReplicaCount() == volume.getReplicaCount()
                && getStripeCount() == volume.getStripeCount()
                && getOptions().equals(volume.getOptions()))) {
            return false;
        }

        for (ACCESS_PROTOCOL nasProtocol : getAccessProtocols()) {
            if (!(volume.getAccessProtocols().contains(nasProtocol))) {
                return false;
            }
        }

        List<GlusterBrick> oldBricks = getBricks();
        List<GlusterBrick> newBricks = volume.getBricks();
        if (oldBricks.size() != newBricks.size()) {
            return false;
        }

        if (!GlusterCoreUtil.getAddedEntities(oldBricks, newBricks, false).isEmpty()) {
            return false;
        }

        if (!GlusterCoreUtil.getAddedEntities(newBricks, oldBricks, false).isEmpty()) {
            return false;
        }

        Map<GlusterBrick, GlusterBrick> modifiedBricks = GlusterCoreUtil.getModifiedEntities(oldBricks, newBricks);
        if (modifiedBricks.size() > 0) {
            return false;
        }

        return true;
    }

    /**
     * Note: this method doesn't copy the bricks. Clients should write separate code to identify added/removed/modified
     * bricks and update the GlusterVolume bricks appropriately.
     *
     * @param newVolume
     */
    public void copyFrom(GlusterVolumeEntity newVolume) {
        setName(newVolume.getName());
        setVolumeType(newVolume.getVolumeType());
        setTransportType(newVolume.getTransportType());
        setStatus(newVolume.getStatus());
        setReplicaCount(newVolume.getReplicaCount());
        setStripeCount(newVolume.getStripeCount());
        setAccessProtocols(newVolume.getAccessProtocols());
        setCifsUsers(newVolume.getCifsUsers());
        getOptions().copyFrom(newVolume.getOptions());
    }

    @Override
    public Object getQueryableId() {
        return getId();
    }
}
