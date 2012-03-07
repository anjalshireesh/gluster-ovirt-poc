

----------------------------------------------------------------
-- [vds_statistics] Table
--





Create or replace FUNCTION InsertVdsStatistics(v_cpu_idle DECIMAL(18,0) ,  
 v_cpu_load DECIMAL(18,0) ,  
 v_cpu_sys DECIMAL(18,0) ,  
 v_cpu_user DECIMAL(18,0) ,  
 v_usage_cpu_percent INTEGER ,  
 v_usage_mem_percent INTEGER ,  
 v_usage_network_percent INTEGER ,  
 v_vds_id UUID,  
 v_mem_available BIGINT ,  
 v_mem_shared BIGINT ,  
    v_swap_free BIGINT ,  
 v_swap_total BIGINT ,   
 v_ksm_cpu_percent INTEGER ,  
 v_ksm_pages BIGINT ,  
 v_ksm_state BOOLEAN)
RETURNS VOID
   AS $procedure$
BEGIN
	
   BEGIN
INSERT INTO vds_statistics(cpu_idle, cpu_load, cpu_sys, cpu_user, usage_cpu_percent, usage_mem_percent, usage_network_percent, vds_id, mem_available, mem_shared,swap_free,swap_total,ksm_cpu_percent,ksm_pages,ksm_state)
	VALUES(v_cpu_idle, v_cpu_load, v_cpu_sys, v_cpu_user, v_usage_cpu_percent, v_usage_mem_percent, v_usage_network_percent, v_vds_id, v_mem_available, v_mem_shared,v_swap_free,v_swap_total,v_ksm_cpu_percent,v_ksm_pages,v_ksm_state);
   END;
    
   RETURN;
END; $procedure$
LANGUAGE plpgsql;    





Create or replace FUNCTION UpdateVdsStatistics(v_cpu_idle DECIMAL(18,0) ,  
 v_cpu_load DECIMAL(18,0) ,  
 v_cpu_sys DECIMAL(18,0) ,  
 v_cpu_user DECIMAL(18,0) ,  
 v_usage_cpu_percent INTEGER ,  
 v_usage_mem_percent INTEGER ,  
 v_usage_network_percent INTEGER ,  
 v_vds_id UUID,  
 v_mem_available BIGINT ,  
 v_mem_shared BIGINT ,  
    v_swap_free BIGINT ,  
 v_swap_total BIGINT ,  
 v_ksm_cpu_percent INTEGER ,  
 v_ksm_pages BIGINT ,  
 v_ksm_state BOOLEAN)
RETURNS VOID

	--The [vds_dynamic] table doesn't have a timestamp column. Optimistic concurrency logic cannot be generated
   AS $procedure$
BEGIN

   BEGIN
      UPDATE vds_statistics
      SET cpu_idle = v_cpu_idle,cpu_load = v_cpu_load,cpu_sys = v_cpu_sys, 
      cpu_user = v_cpu_user,usage_cpu_percent = v_usage_cpu_percent,usage_mem_percent = v_usage_mem_percent, 
      usage_network_percent = v_usage_network_percent, 
      mem_available = v_mem_available,mem_shared = v_mem_shared,
      swap_free = v_swap_free,swap_total = v_swap_total,ksm_cpu_percent = v_ksm_cpu_percent, 
      ksm_pages = v_ksm_pages,ksm_state = v_ksm_state
      WHERE vds_id = v_vds_id;
   END;	

   RETURN;
END; $procedure$
LANGUAGE plpgsql;





Create or replace FUNCTION DeleteVdsStatistics(v_vds_id UUID)
RETURNS VOID
   AS $procedure$
BEGIN
   BEGIN
      DELETE FROM vds_statistics
      WHERE vds_id = v_vds_id;
   END;
    
   RETURN;
END; $procedure$
LANGUAGE plpgsql;





Create or replace FUNCTION GetAllFromVdsStatistics() RETURNS SETOF vds_statistics
   AS $procedure$
BEGIN
BEGIN
      RETURN QUERY SELECT vds_statistics.*
      FROM vds_statistics;
   END;
	

   RETURN;
END; $procedure$
LANGUAGE plpgsql;





Create or replace FUNCTION GetVdsStatisticsByVdsId(v_vds_id UUID) RETURNS SETOF vds_statistics
   AS $procedure$
BEGIN
BEGIN
      RETURN QUERY SELECT vds_statistics.*
      FROM vds_statistics
      WHERE vds_id = v_vds_id;
   END;

   RETURN;
END; $procedure$
LANGUAGE plpgsql;




----------------------------------------------------------------
-- [vds_dynamic] Table
--


Create or replace FUNCTION InsertVdsDynamic(v_cpu_cores INTEGER ,  
 v_cpu_model VARCHAR(255) ,  
 v_cpu_speed_mh DECIMAL(18,0) ,  
 v_if_total_speed VARCHAR(40) ,  
 v_kvm_enabled BOOLEAN ,  
 v_mem_commited INTEGER ,  
 v_physical_mem_mb INTEGER ,  
 v_status INTEGER,  
 v_vds_id UUID,  
 v_vm_active INTEGER ,  
 v_vm_count INTEGER ,  
 v_vms_cores_count INTEGER ,  
 v_vm_migrating INTEGER ,  
 v_reserved_mem INTEGER ,  
 v_guest_overhead INTEGER ,  
 v_software_version VARCHAR(40) ,  
 v_version_name VARCHAR(40) ,  
 v_build_name VARCHAR(40) ,  
 v_previous_status INTEGER ,  
 v_cpu_flags VARCHAR(4000) ,  
 v_cpu_over_commit_time_stamp TIMESTAMP WITH TIME ZONE ,  
 v_hypervisor_type INTEGER ,  
 v_pending_vcpus_count INTEGER ,  
 v_pending_vmem_size INTEGER ,  
 v_cpu_sockets INTEGER ,  
 v_net_config_dirty BOOLEAN ,  
 v_supported_cluster_levels VARCHAR(40) ,  
 v_supported_engines VARCHAR(40) ,  
 v_host_os VARCHAR(4000) ,  
 v_kvm_version VARCHAR(4000) ,  
 v_spice_version VARCHAR(4000) ,  
 v_kernel_version VARCHAR(4000) ,
 v_iscsi_initiator_name VARCHAR(4000) ,
 v_transparent_hugepages_state INTEGER ,
 v_anonymous_hugepages INTEGER ,
 v_hooks VARCHAR(4000))
RETURNS VOID
   AS $procedure$
BEGIN
	
   BEGIN
INSERT INTO vds_dynamic(cpu_cores, cpu_model, cpu_speed_mh, if_total_speed, kvm_enabled, mem_commited, physical_mem_mb,	status, vds_id, vm_active, vm_count, vm_migrating, reserved_mem, guest_overhead, software_version, version_name, build_name, previous_status, cpu_flags, cpu_over_commit_time_stamp, hypervisor_type, vms_cores_count, pending_vcpus_count, pending_vmem_size, cpu_sockets,net_config_dirty, supported_cluster_levels, supported_engines, host_os, kvm_version, spice_version, kernel_version, iscsi_initiator_name, transparent_hugepages_state, anonymous_hugepages,hooks)
	VALUES(v_cpu_cores,	v_cpu_model,	v_cpu_speed_mh,	v_if_total_speed, v_kvm_enabled, v_mem_commited, v_physical_mem_mb,	v_status, v_vds_id, v_vm_active, v_vm_count, v_vm_migrating,	v_reserved_mem, v_guest_overhead, v_software_version, v_version_name, v_build_name, v_previous_status, v_cpu_flags, v_cpu_over_commit_time_stamp, v_hypervisor_type, v_vms_cores_count,v_pending_vcpus_count, v_pending_vmem_size, v_cpu_sockets, v_net_config_dirty, v_supported_cluster_levels, v_supported_engines, v_host_os, v_kvm_version, v_spice_version, v_kernel_version, v_iscsi_initiator_name, v_transparent_hugepages_state, v_anonymous_hugepages,v_hooks);
   END;
    
   RETURN;
END; $procedure$
LANGUAGE plpgsql;    





Create or replace FUNCTION UpdateVdsDynamic(v_cpu_cores INTEGER ,  
 v_cpu_model VARCHAR(255) ,  
 v_cpu_speed_mh DECIMAL(18,0) ,  
 v_if_total_speed VARCHAR(40) ,  
 v_kvm_enabled BOOLEAN ,  
 v_mem_commited INTEGER ,  
 v_physical_mem_mb INTEGER ,  
 v_status INTEGER,  
 v_vds_id UUID,  
 v_vm_active INTEGER ,  
 v_vm_count INTEGER ,  
 v_vms_cores_count INTEGER ,  
 v_vm_migrating INTEGER ,  
 v_reserved_mem INTEGER ,  
 v_guest_overhead INTEGER ,  
 v_software_version VARCHAR(40) ,  
 v_version_name VARCHAR(40) ,  
 v_build_name VARCHAR(40) ,  
 v_previous_status INTEGER ,  
 v_cpu_flags VARCHAR(4000) ,  
 v_cpu_over_commit_time_stamp TIMESTAMP WITH TIME ZONE ,  
 v_hypervisor_type INTEGER ,  
 v_pending_vcpus_count INTEGER ,  
 v_pending_vmem_size INTEGER ,  
 v_cpu_sockets INTEGER ,  
 v_net_config_dirty BOOLEAN ,  
 v_supported_cluster_levels VARCHAR(40) ,  
 v_supported_engines VARCHAR(40) ,  
 v_host_os VARCHAR(4000) ,  
 v_kvm_version VARCHAR(4000) ,  
 v_spice_version VARCHAR(4000) ,  
 v_kernel_version VARCHAR(4000) ,
 v_iscsi_initiator_name VARCHAR(4000) ,
 v_transparent_hugepages_state INTEGER ,
 v_anonymous_hugepages INTEGER ,
 v_hooks VARCHAR(4000),
 v_non_operational_reason INTEGER)
RETURNS VOID

	--The [vds_dynamic] table doesn't have a timestamp column. Optimistic concurrency logic cannot be generated
   AS $procedure$
BEGIN

   BEGIN
      UPDATE vds_dynamic
      SET cpu_cores = v_cpu_cores,cpu_model = v_cpu_model,cpu_speed_mh = v_cpu_speed_mh, 
      if_total_speed = v_if_total_speed,kvm_enabled = v_kvm_enabled, 
      mem_commited = v_mem_commited,physical_mem_mb = v_physical_mem_mb, 
      status = v_status,vm_active = v_vm_active,vm_count = v_vm_count, 
      vm_migrating = v_vm_migrating,reserved_mem = v_reserved_mem, 
      guest_overhead = v_guest_overhead,software_version = v_software_version, 
      version_name = v_version_name,build_name = v_build_name,previous_status = v_previous_status, 
      cpu_flags = v_cpu_flags,cpu_over_commit_time_stamp = v_cpu_over_commit_time_stamp, 
      hypervisor_type = v_hypervisor_type, 
      vms_cores_count = v_vms_cores_count,pending_vcpus_count = v_pending_vcpus_count, 
      pending_vmem_size = v_pending_vmem_size, 
      cpu_sockets = v_cpu_sockets,net_config_dirty = v_net_config_dirty, 
      supported_cluster_levels = v_supported_cluster_levels, 
      supported_engines = v_supported_engines,host_os = v_host_os, 
      kvm_version = v_kvm_version,spice_version = v_spice_version, 
      kernel_version = v_kernel_version,iscsi_initiator_name = v_iscsi_initiator_name, 
      transparent_hugepages_state = v_transparent_hugepages_state, 
      anonymous_hugepages = v_anonymous_hugepages,hooks = v_hooks,
      _update_date = LOCALTIMESTAMP,non_operational_reason = v_non_operational_reason
      WHERE vds_id = v_vds_id;
   END;	

   RETURN;
END; $procedure$
LANGUAGE plpgsql;





Create or replace FUNCTION DeleteVdsDynamic(v_vds_id UUID)
RETURNS VOID
   AS $procedure$
BEGIN
   BEGIN
      DELETE FROM vds_dynamic
      WHERE vds_id = v_vds_id;
   END;
    
   RETURN;
END; $procedure$
LANGUAGE plpgsql;





Create or replace FUNCTION GetAllFromVdsDynamic() RETURNS SETOF vds_dynamic
   AS $procedure$
BEGIN
BEGIN
      RETURN QUERY SELECT vds_dynamic.*
      FROM vds_dynamic;
   END;
	
   RETURN;
END; $procedure$
LANGUAGE plpgsql;





Create or replace FUNCTION GetVdsDynamicByVdsId(v_vds_id UUID) RETURNS SETOF vds_dynamic
   AS $procedure$
BEGIN
BEGIN
      RETURN QUERY SELECT vds_dynamic.*
      FROM vds_dynamic
      WHERE vds_id = v_vds_id;
   END;

   RETURN;
END; $procedure$
LANGUAGE plpgsql;



----------------------------------------------------------------
-- [vds_static] Table
--


Create or replace FUNCTION InsertVdsStatic(v_host_name VARCHAR(255),
	v_ip VARCHAR(255) ,
    v_vds_unique_id VARCHAR(128) ,
	v_port INTEGER,
	v_vds_group_id UUID,
	INOUT v_vds_id UUID ,
	v_vds_name VARCHAR(255),
	v_server_SSL_enabled BOOLEAN ,
	v_vds_type INTEGER,
	v_vds_strength INTEGER,
    v_pm_type VARCHAR(20) ,
	v_pm_user VARCHAR(50) ,
    v_pm_password VARCHAR(50) ,
    v_pm_port INTEGER ,
    v_pm_options VARCHAR(4000) ,
    v_pm_enabled BOOLEAN,
    v_vds_spm_priority INTEGER)
   AS $procedure$
BEGIN
   IF v_vds_unique_id IS NULL OR NOT EXISTS(SELECT vds_name FROM vds_static WHERE vds_unique_id = v_vds_unique_id) then
      BEGIN
         v_vds_id := uuid_generate_v1();
         INSERT INTO vds_static(vds_id,host_name, ip, vds_unique_id, port, vds_group_id, vds_name, server_SSL_enabled,vds_type,vds_strength,pm_type,pm_user,pm_password,pm_port,pm_options,pm_enabled, vds_spm_priority)
			VALUES(v_vds_id,v_host_name, v_ip, v_vds_unique_id, v_port, v_vds_group_id, v_vds_name, v_server_SSL_enabled,v_vds_type,v_vds_strength,v_pm_type,v_pm_user,v_pm_password,v_pm_port,v_pm_options,v_pm_enabled, v_vds_spm_priority);
      END;
   end if;
   RETURN;
END; $procedure$
LANGUAGE plpgsql;     





Create or replace FUNCTION UpdateVdsStatic(v_host_name VARCHAR(255),
	v_ip VARCHAR(255) ,
    v_vds_unique_id VARCHAR(128),
	v_port INTEGER,
	v_vds_group_id UUID,
	v_vds_id UUID,
	v_vds_name VARCHAR(255),
	v_server_SSL_enabled BOOLEAN ,
	v_vds_type INTEGER,
	v_vds_strength INTEGER,
    v_pm_type VARCHAR(20) ,
	v_pm_user VARCHAR(50) ,
    v_pm_password VARCHAR(50) ,
    v_pm_port INTEGER ,
    v_pm_options VARCHAR(4000) ,
    v_pm_enabled BOOLEAN,
    v_otp_validity BIGINT,
    v_vds_spm_priority INTEGER)
RETURNS VOID

	--The [vds_static] table doesn't have a timestamp column. Optimistic concurrency logic cannot be generated
   AS $procedure$
BEGIN

   BEGIN
      UPDATE vds_static
      SET host_name = v_host_name,ip = v_ip,vds_unique_id = v_vds_unique_id, 
      port = v_port,vds_group_id = v_vds_group_id,vds_name = v_vds_name,server_SSL_enabled = v_server_SSL_enabled, 
      vds_type = v_vds_type, 
      _update_date = LOCALTIMESTAMP,vds_strength = v_vds_strength, 
      pm_type = v_pm_type,pm_user = v_pm_user,pm_password = v_pm_password, 
      pm_port = v_pm_port,pm_options = v_pm_options,pm_enabled = v_pm_enabled, otp_validity = v_otp_validity, vds_spm_priority = v_vds_spm_priority
      WHERE vds_id = v_vds_id;
   END;	

   RETURN;
END; $procedure$
LANGUAGE plpgsql;





Create or replace FUNCTION DeleteVdsStatic(v_vds_id UUID)
RETURNS VOID
   AS $procedure$
BEGIN  
   BEGIN
      UPDATE vm_static
      SET dedicated_vm_for_vds = null,
          migration_support = 0
      WHERE dedicated_vm_for_vds = v_vds_id;
      DELETE FROM tags_vds_map
      WHERE vds_id = v_vds_id;  
   -- Delete all Vds Alerts from the database  
      PERFORM DeleteAuditLogAlertsByVdsID(v_vds_id);
      DELETE FROM vds_static
      WHERE vds_id = v_vds_id;
	-- delete VDS permissions --
      DELETE FROM permissions where object_id = v_vds_id;
   END;  
       
   RETURN;
END; $procedure$
LANGUAGE plpgsql;  




Create or replace FUNCTION GetAllFromVdsStatic() RETURNS SETOF vds_static
   AS $procedure$
BEGIN
RETURN QUERY SELECT vds_static.*
   FROM vds_static;

   RETURN;
END; $procedure$
LANGUAGE plpgsql;





Create or replace FUNCTION GetVdsStaticByVdsId(v_vds_id UUID) RETURNS SETOF vds_static
   AS $procedure$
BEGIN
BEGIN
      RETURN QUERY SELECT vds_static.*
      FROM vds_static
      WHERE vds_id = v_vds_id;
   END;

   RETURN;
END; $procedure$
LANGUAGE plpgsql;



--Vitaly add 


Create or replace FUNCTION GetVdsStaticByVdsName(v_vds_name VARCHAR(255)) RETURNS SETOF vds_static
   AS $procedure$
BEGIN
BEGIN
      RETURN QUERY SELECT vds_static.*
      FROM vds_static
      WHERE vds_name = v_vds_name;
   END;

   RETURN;
END; $procedure$
LANGUAGE plpgsql;




Create or replace FUNCTION GetVdsStaticByHostName(v_host_name VARCHAR(255)) RETURNS SETOF vds_static
   AS $procedure$
BEGIN
RETURN QUERY SELECT vds_static.*
   FROM vds_static
   WHERE host_name = v_host_name;

   RETURN;
END; $procedure$
LANGUAGE plpgsql;





Create or replace FUNCTION GetVdsStaticByIp(v_ip VARCHAR(40)) RETURNS SETOF vds_static
   AS $procedure$
BEGIN
BEGIN
      RETURN QUERY SELECT vds_static.*
      FROM vds_static
      WHERE ip = v_ip;
   END;

   RETURN;
END; $procedure$
LANGUAGE plpgsql;





Create or replace FUNCTION GetVdsByUniqueID(v_vds_unique_id VARCHAR(128)) RETURNS SETOF vds
   AS $procedure$
BEGIN
BEGIN
      RETURN QUERY SELECT DISTINCT vds.*
      FROM vds
      WHERE vds_unique_id = v_vds_unique_id;
   END;

   RETURN;
END; $procedure$
LANGUAGE plpgsql;



--end Vitaly add



Create or replace FUNCTION GetVdsStaticByVdsGroupId(v_vds_group_id UUID) RETURNS SETOF vds_static
   AS $procedure$
BEGIN
BEGIN
      RETURN QUERY SELECT vds_static.*
      FROM vds_static vds_static
      WHERE vds_group_id = v_vds_group_id;
   END;

   RETURN;
END; $procedure$
LANGUAGE plpgsql;




---------------------------------------------------------------------------------------------------
--    [vds] - view
---------------------------------------------------------------------------------------------------



CREATE OR REPLACE FUNCTION GetUpAndPrioritizedVds(v_storage_pool_id UUID) RETURNS SETOF vds
AS $procedure$

BEGIN
BEGIN
      RETURN QUERY SELECT vds.*
      FROM vds
      WHERE (status = 3) AND (storage_pool_id = v_storage_pool_id) AND (vds_spm_priority IS NULL OR vds_spm_priority > (-1))
      ORDER BY vds_spm_priority DESC, RANDOM();
   END;
   RETURN;
END; $procedure$
  LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION getVdsForVdsGroupWithStatus(v_vds_group_id UUID, v_status integer) RETURNS SETOF vds
AS $procedure$

BEGIN
BEGIN
      RETURN QUERY SELECT vds.*
      FROM vds
      WHERE (status = v_status) AND (vds_group_id = v_vds_group_id);
   END;
   RETURN;
END; $procedure$
  LANGUAGE plpgsql;


Create or replace FUNCTION GetAllFromVds() RETURNS SETOF vds
   AS $procedure$
BEGIN
BEGIN
      RETURN QUERY SELECT DISTINCT vds.*
      FROM vds;
   END;
	

   RETURN;
END; $procedure$
LANGUAGE plpgsql;






Create or replace FUNCTION GetVdsByVdsId(v_vds_id UUID) RETURNS SETOF vds
   AS $procedure$
BEGIN
BEGIN
      RETURN QUERY SELECT DISTINCT vds.*
      FROM vds
      WHERE vds_id = v_vds_id;
   END;

   RETURN;
END; $procedure$
LANGUAGE plpgsql;






Create or replace FUNCTION GetVdsWithoutMigratingVmsByVdsGroupId(v_vds_group_id UUID) RETURNS SETOF vds
   AS $procedure$
BEGIN

	
	-- this sp returns all vds in given cluster that have no pending vms and no vms in migration states
	BEGIN
      RETURN QUERY SELECT DISTINCT vds.*
      FROM vds
      WHERE vds_group_id = v_vds_group_id and
      pending_vcpus_count = 0
      and	vds.status = 3
      and	vds_id not in(select distinct RUN_ON_VDS from vm_dynamic
         where status in(5,6,11,12));
   END;

   RETURN;
END; $procedure$
LANGUAGE plpgsql;







Create or replace FUNCTION InsertVds(v_host_name VARCHAR(255),  
 v_ip VARCHAR(40) ,  
    v_vds_unique_id VARCHAR(128) ,  
 v_port INTEGER,  
 v_vds_group_id UUID,  
 INOUT v_vds_id UUID ,  
 v_vds_name VARCHAR(255),  
 v_server_SSL_enabled BOOLEAN ,  
 v_vds_type INTEGER,  
 v_vds_strength INTEGER,  
    v_pm_type VARCHAR(20) ,  
 v_pm_user VARCHAR(50) ,  
    v_pm_password VARCHAR(50) ,  
 v_pm_port INTEGER ,  
    v_pm_options VARCHAR(4000) ,  
    v_pm_enabled BOOLEAN,
    v_vds_spm_priority INTEGER)
   AS $procedure$
BEGIN
	
   BEGIN
      v_vds_id := uuid_generate_v1();
      INSERT INTO vds_static(vds_id,host_name, ip, vds_unique_id, port, vds_group_id, vds_name, server_SSL_enabled,vds_type,vds_strength,pm_type,pm_user,pm_password, pm_port, pm_options, pm_enabled, vds_spm_priority)
	VALUES(v_vds_id,v_host_name, v_ip, v_vds_unique_id, v_port, v_vds_group_id, v_vds_name, v_server_SSL_enabled,v_vds_type, v_vds_strength,v_pm_type,v_pm_user,v_pm_password,v_pm_port, v_pm_options, v_pm_enabled, v_vds_spm_priority);
    
      INSERT INTO vds_dynamic(vds_id, status) VALUES(v_vds_id, 0);
    
      INSERT INTO vds_statistics(vds_id) VALUES(v_vds_id);
   END;
    
   RETURN;
END; $procedure$
LANGUAGE plpgsql;    





Create or replace FUNCTION DeleteVds(v_vds_id UUID)
RETURNS VOID
   AS $procedure$
BEGIN  
   BEGIN
      UPDATE vm_static
      SET dedicated_vm_for_vds = null
      WHERE dedicated_vm_for_vds = v_vds_id;
      DELETE FROM tags_vds_map
      WHERE vds_id = v_vds_id;  
   -- Delete all Vds Alerts from the database  
      PERFORM DeleteAuditLogAlertsByVdsID(v_vds_id);
      DELETE FROM vds_statistics WHERE vds_id = v_vds_id;
      DELETE FROM vds_dynamic WHERE vds_id = v_vds_id;
      DELETE FROM vds_static WHERE vds_id = v_vds_id;
      DELETE FROM permissions where object_id = v_vds_id;
   END;  
   RETURN;
END; $procedure$
LANGUAGE plpgsql;  




Create or replace FUNCTION GetVdsByType(v_vds_type INTEGER) RETURNS SETOF vds
   AS $procedure$
BEGIN
BEGIN
      RETURN QUERY SELECT DISTINCT vds.*
      FROM vds
      WHERE vds_type = v_vds_type;
   END;

   RETURN;
END; $procedure$
LANGUAGE plpgsql;




Create or replace FUNCTION GetVdsByName(v_vds_name VARCHAR(255)) RETURNS SETOF vds
   AS $procedure$
BEGIN
BEGIN
      RETURN QUERY SELECT DISTINCT vds.*
      FROM vds
      WHERE vds_name = v_vds_name;
   END;

   RETURN;
END; $procedure$
LANGUAGE plpgsql;




Create or replace FUNCTION GetVdsByHostName(v_host_name VARCHAR(255)) RETURNS SETOF vds
   AS $procedure$
BEGIN
BEGIN
      RETURN QUERY SELECT DISTINCT vds.*
      FROM vds
      WHERE host_name = v_host_name;
   END;

   RETURN;
END; $procedure$
LANGUAGE plpgsql;




Create or replace FUNCTION GetVdsByIp(v_ip VARCHAR(40)) RETURNS SETOF vds
   AS $procedure$
BEGIN
BEGIN
      RETURN QUERY SELECT DISTINCT vds.*
      FROM vds
      WHERE ip = v_ip;
   END;

   RETURN;
END; $procedure$
LANGUAGE plpgsql;





Create or replace FUNCTION GetVdsByVdsGroupId(v_vds_group_id UUID) RETURNS SETOF vds
   AS $procedure$
BEGIN

	
	-- this sp returns all vds for a given cluster
	BEGIN
      RETURN QUERY SELECT DISTINCT vds.*

      FROM vds
      WHERE vds_group_id = v_vds_group_id;
   END;

   RETURN;
END; $procedure$
LANGUAGE plpgsql;


Create or replace FUNCTION UpdateVdsDynamicStatus(
        v_vds_guid UUID,
        v_status INTEGER)
RETURNS VOID

   AS $procedure$
BEGIN
      UPDATE vds_dynamic
      SET
      status = v_status
      WHERE vds_id = v_vds_guid;
END; $procedure$
LANGUAGE plpgsql;

Create or replace FUNCTION GetVdsByStoragePoolId(v_storage_pool_id UUID) RETURNS SETOF vds
   AS $procedure$
BEGIN
	-- this sp returns all vds for a given storage pool
	BEGIN
      RETURN QUERY SELECT DISTINCT vds.*

      FROM vds
      WHERE storage_pool_id = v_storage_pool_id;
   END;

   RETURN;
END; $procedure$
LANGUAGE plpgsql;
