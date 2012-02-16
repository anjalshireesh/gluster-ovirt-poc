drop INDEX if exists IDX_gluster_volume_access_protocols_volume_id;
drop TABLE gluster_volume_access_protocols cascade;

drop INDEX if exists IDX_gluster_volume_options_volume_id;
drop TABLE gluster_volume_options cascade;

drop INDEX if exists IDX_gluster_volume_bricks_volume_id;
drop TABLE gluster_volume_bricks cascade;

drop INDEX if exists IDX_gluster_volumes_id;
drop TABLE gluster_volumes cascade;
