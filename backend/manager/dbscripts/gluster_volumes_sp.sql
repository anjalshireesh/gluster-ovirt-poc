Create or replace FUNCTION InsertGlusterVolume(v_id UUID, v_cluster_id UUID, v_vol_name VARCHAR(1000), v_vol_type INTEGER, v_transport_type INTEGER, v_status INTEGER, v_replica_count INTEGER, v_stripe_count INTEGER, v_cifs_users VARCHAR(1024))
RETURNS VOID
   AS $procedure$
BEGIN
INSERT INTO gluster_volumes(id, cluster_id, vol_name, vol_type, transport_type, status, replica_count, stripe_count, cifs_users)
   VALUES(v_id,  v_cluster_id, v_vol_name, v_vol_type,  v_transport_type, v_status,  v_replica_count ,  v_stripe_count,  v_cifs_users);
END; $procedure$
LANGUAGE plpgsql;


Create or replace FUNCTION InsertGlusterVolumeBrick(v_volume_id UUID, v_host_id UUID, v_brick_dir VARCHAR(4096), v_status INTEGER)
RETURNS VOID
   AS $procedure$
BEGIN
INSERT INTO gluster_volume_bricks(volume_id, host_id, brick_dir, status)
   VALUES(v_volume_id, v_host_id, v_brick_dir, v_status);

END; $procedure$
LANGUAGE plpgsql;


Create or replace FUNCTION InsertGlusterVolumeOption(v_volume_id UUID, v_option_key VARCHAR(8192), v_option_val VARCHAR(8192))
RETURNS VOID
   AS $procedure$
BEGIN
INSERT INTO gluster_volume_options(volume_id, option_key, option_val)
   VALUES(v_volume_id, v_option_key, v_option_val);

END; $procedure$
LANGUAGE plpgsql;


Create or replace FUNCTION InsertGlusterVolumeAccessProtocol(v_volume_id UUID, v_access_protocol INTEGER)
RETURNS VOID
   AS $procedure$
BEGIN
INSERT INTO gluster_volume_access_protocols(volume_id, access_protocol)
   VALUES(v_volume_id, v_access_protocol);

END; $procedure$
LANGUAGE plpgsql;

-- Returns all the volumes the cluster
Create or replace FUNCTION GetGlusterVolumesByClusterGuid(v_cluster_id UUID)
RETURNS SETOF gluster_volumes
   AS $procedure$
BEGIN
   RETURN QUERY SELECT *
   FROM  gluster_volumes
   WHERE cluster_id = v_cluster_id;
END; $procedure$
LANGUAGE plpgsql;


Create or replace FUNCTION GetGlusterVolumeById(v_volume_id UUID)
RETURNS SETOF gluster_volumes
   AS $procedure$
BEGIN
   RETURN QUERY SELECT *
   FROM gluster_volumes
   WHERE id = v_volume_id;
END; $procedure$
LANGUAGE plpgsql;


Create or replace FUNCTION GetGlusterVolumeByName(v_cluster_id UUID, v_vol_name VARCHAR(1000))
RETURNS SETOF gluster_volumes
   AS $procedure$
BEGIN
   RETURN QUERY SELECT *
   FROM gluster_volumes
   WHERE cluster_id = v_cluster_id and vol_name = v_vol_name;
END; $procedure$
LANGUAGE plpgsql;


Create or replace FUNCTION GetBricksByGlusterVolumeGuid(v_volume_id UUID)
RETURNS SETOF gluster_volume_bricks
   AS $procedure$
BEGIN
   RETURN QUERY SELECT *
   FROM  gluster_volume_bricks
   WHERE volume_id = v_volume_id;
END; $procedure$
LANGUAGE plpgsql;


Create or replace FUNCTION GetOptionsByGlusterVolumeGuid(v_volume_id UUID)
RETURNS SETOF gluster_volume_options
   AS $procedure$
BEGIN
   RETURN QUERY SELECT *
   FROM  gluster_volume_options
   WHERE volume_id = v_volume_id;
END; $procedure$
LANGUAGE plpgsql;


Create or replace FUNCTION GetAccessProtocolsByGlusterVolumeGuid(v_volume_id UUID)
RETURNS SETOF gluster_volume_access_protocols
   AS $procedure$
BEGIN
   RETURN QUERY SELECT *
   FROM  gluster_volume_access_protocols
   WHERE volume_id = v_volume_id;
END; $procedure$
LANGUAGE plpgsql;


Create or replace FUNCTION DeleteGlusterVolumeByGuid(v_volume_id UUID)
RETURNS VOID
   AS $procedure$
BEGIN
   DELETE FROM gluster_volumes
   WHERE id = v_volume_id;
END; $procedure$
LANGUAGE plpgsql;


Create or replace FUNCTION DeleteGlusterVolumeByName(v_cluster_id UUID, v_vol_name VARCHAR(1000))
RETURNS VOID
   AS $procedure$
BEGIN
   DELETE FROM gluster_volumes
   WHERE cluster_id = v_cluster_id 
   AND   vol_name = v_vol_name;
END; $procedure$
LANGUAGE plpgsql;

Create or replace FUNCTION DeleteGlusterVolumeBrick(v_volume_id UUID, v_host_id UUID, v_brick_dir VARCHAR(4096))
RETURNS VOID
   AS $procedure$
BEGIN
   DELETE FROM gluster_volume_bricks
   WHERE volume_id = v_volume_id
   AND   host_id = v_host_id
   AND   brick_dir = v_brick_dir;
END; $procedure$
LANGUAGE plpgsql;


Create or replace FUNCTION DeleteGlusterVolumeOption(v_volume_id UUID, v_option_key VARCHAR(8192))
RETURNS VOID
   AS $procedure$
BEGIN
   DELETE FROM gluster_volume_options
   WHERE volume_id = v_volume_id
   AND   option_key = v_option_key;
END; $procedure$
LANGUAGE plpgsql;


Create or replace FUNCTION DeleteGlusterVolumeAccessProtocol(v_volume_id UUID, v_access_protocol INTEGER)
RETURNS VOID
   AS $procedure$
BEGIN
   DELETE FROM gluster_volume_access_protocols
   WHERE volume_id = v_volume_id
   AND   access_protocol = v_access_protocol;
END; $procedure$
LANGUAGE plpgsql;


Create or replace FUNCTION UpdateGlusterVolume(v_id UUID, v_cluster_id UUID, v_vol_name VARCHAR(1000), v_vol_type INTEGER, v_transport_type INTEGER, v_status INTEGER, v_replica_count INTEGER, v_stripe_count INTEGER, v_cifs_users VARCHAR(1024))
RETURNS VOID
   AS $procedure$
BEGIN
UPDATE gluster_volumes
   SET cluster_id = v_cluster_id,
   vol_name = v_vol_name,
   vol_type = v_vol_type,
   transport_type = v_transport_type,
   status = v_status,
   replica_count = v_replica_count,
   stripe_count = v_stripe_count,
   cifs_users = v_cifs_users,
   _update_date = LOCALTIMESTAMP
   WHERE id = v_id;
END; $procedure$
LANGUAGE plpgsql;


Create or replace FUNCTION UpdateGlusterVolumeStatus(v_cluster_id UUID, v_vol_name VARCHAR(1000), v_status INTEGER)
RETURNS VOID
   AS $procedure$
BEGIN
UPDATE gluster_volumes
   SET 
   status = v_status,
   _update_date = LOCALTIMESTAMP
   WHERE cluster_id = v_cluster_id 
   AND   vol_name = v_vol_name;
END; $procedure$
LANGUAGE plpgsql;


Create or replace FUNCTION UpdateGlusterVolumeOption(v_volume_id UUID, v_option_key VARCHAR(8192), v_option_val VARCHAR(8192))
RETURNS VOID
   AS $procedure$
BEGIN
UPDATE gluster_volume_options
   SET option_val = v_option_val
   WHERE volume_id = v_volume_id
   AND   option_key = v_option_key;
END; $procedure$
LANGUAGE plpgsql;

