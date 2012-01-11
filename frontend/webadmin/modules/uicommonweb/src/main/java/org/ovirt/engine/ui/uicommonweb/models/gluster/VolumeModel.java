package org.ovirt.engine.ui.uicommonweb.models.gluster;

import java.util.ArrayList;
import java.util.Arrays;

import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.VOLUME_TYPE;
import org.ovirt.engine.core.common.businessentities.StorageType;
import org.ovirt.engine.core.common.businessentities.VDSGroup;
import org.ovirt.engine.core.common.businessentities.storage_pool;
import org.ovirt.engine.core.compat.Event;
import org.ovirt.engine.core.compat.EventArgs;
import org.ovirt.engine.core.compat.IEventListener;
import org.ovirt.engine.ui.frontend.AsyncQuery;
import org.ovirt.engine.ui.frontend.INewAsyncCallback;
import org.ovirt.engine.ui.uicommonweb.Linq;
import org.ovirt.engine.ui.uicommonweb.UICommand;
import org.ovirt.engine.ui.uicommonweb.dataprovider.AsyncDataProvider;
import org.ovirt.engine.ui.uicommonweb.models.EntityModel;
import org.ovirt.engine.ui.uicommonweb.models.ListModel;
import org.ovirt.engine.ui.uicommonweb.models.Model;
import org.ovirt.engine.ui.uicommonweb.models.hosts.HostModel;

public class VolumeModel extends Model{
	
	ListModel dataCenter;
	ListModel cluster;
	EntityModel name;
	ListModel typeList;
	EntityModel bricks;
	EntityModel gluster_accecssProtocol;
	EntityModel nfs_accecssProtocol;
	EntityModel cifs_accecssProtocol;
	EntityModel users;
	EntityModel allowAccess;
	private UICommand addBrickCommand;
	
	
	public VolumeModel() {
		setDataCenter(new ListModel());
		getDataCenter().getSelectedItemChangedEvent().addListener(new IEventListener() {
			
			@Override
			public void eventRaised(Event ev, Object sender, EventArgs args) {
				dataCenter_SelectedItemChanged();
			}
		});
		setCluster(new ListModel());
		
		setName(new EntityModel());
		
		setTypeList(new ListModel());
		ArrayList<VOLUME_TYPE> list = new ArrayList<VOLUME_TYPE>(Arrays.asList(new VOLUME_TYPE[] {VOLUME_TYPE.DISTRIBUTE, VOLUME_TYPE.REPLICATE ,  VOLUME_TYPE.STRIPE}));
		getTypeList().setItems(list);
		getTypeList().setSelectedItem(VOLUME_TYPE.DISTRIBUTE);
		
		setBricks(new EntityModel());
		
		setGluster_accecssProtocol(new EntityModel());
		getGluster_accecssProtocol().setEntity(true);
		getGluster_accecssProtocol().setIsChangable(false);
		
		setNfs_accecssProtocol(new EntityModel());
		getNfs_accecssProtocol().setEntity(true);

		setCifs_accecssProtocol(new EntityModel());
		getCifs_accecssProtocol().setEntity(false);
		
		setUsers(new EntityModel());
		
		setAllowAccess(new EntityModel());
		getAllowAccess().setEntity("*");
	}
	
	public ListModel getDataCenter() {
		return dataCenter;
	}
	
	public void setDataCenter(ListModel dataCenter) {
		this.dataCenter = dataCenter;
	}
	
	public ListModel getCluster() {
		return cluster;
	}
	
	public void setCluster(ListModel cluster) {
		this.cluster = cluster;
	}
	
	public EntityModel getName() {
		return name;
	}

	public void setName(EntityModel name) {
		this.name = name;
	}

	public ListModel getTypeList() {
		return typeList;
	}

	public void setTypeList(ListModel typeList) {
		this.typeList = typeList;
	}
	
	public EntityModel getBricks() {
		return bricks;
	}

	public void setBricks(EntityModel bricks) {
		this.bricks = bricks;
	}

	public EntityModel getGluster_accecssProtocol() {
		return gluster_accecssProtocol;
	}

	public void setGluster_accecssProtocol(EntityModel gluster_accecssProtocol) {
		this.gluster_accecssProtocol = gluster_accecssProtocol;
	}

	public EntityModel getNfs_accecssProtocol() {
		return nfs_accecssProtocol;
	}

	public void setNfs_accecssProtocol(EntityModel nfs_accecssProtocol) {
		this.nfs_accecssProtocol = nfs_accecssProtocol;
	}

	public EntityModel getCifs_accecssProtocol() {
		return cifs_accecssProtocol;
	}

	public void setCifs_accecssProtocol(EntityModel cifs_accecssProtocol) {
		this.cifs_accecssProtocol = cifs_accecssProtocol;
	}

	public EntityModel getUsers() {
		return users;
	}

	public void setUsers(EntityModel users) {
		this.users = users;
	}

	public EntityModel getAllowAccess() {
		return allowAccess;
	}

	public void setAllowAccess(EntityModel allowAccess) {
		this.allowAccess = allowAccess;
	}
	
	private void dataCenter_SelectedItemChanged()
	{
		storage_pool dataCenter = (storage_pool)getDataCenter().getSelectedItem();
		if (dataCenter != null)
		{
			AsyncQuery _asyncQuery = new AsyncQuery();
			_asyncQuery.setModel(this);
			_asyncQuery.asyncCallback = new INewAsyncCallback() { public void OnSuccess(Object model, Object result)
				{
					VolumeModel volumeModel = (VolumeModel)model;
					java.util.ArrayList<VDSGroup> clusters = (java.util.ArrayList<VDSGroup>)result;
					VDSGroup oldCluster = (VDSGroup)volumeModel.getCluster().getSelectedItem();
					storage_pool selectedDataCenter = (storage_pool) getDataCenter().getSelectedItem();

					// Update selected cluster only if the returned cluster list is indeed the selected datacenter's clusters
					if (clusters.isEmpty() || clusters.size() > 0 && clusters.get(0).getstorage_pool_id().getValue().equals(selectedDataCenter.getId().getValue()))
					{
						volumeModel.getCluster().setItems(clusters);

						if (oldCluster != null)
						{
							VDSGroup newSelectedItem = Linq.FirstOrDefault(clusters, new Linq.ClusterPredicate(oldCluster.getID()));
							if (newSelectedItem != null)
							{
								volumeModel.getCluster().setSelectedItem(newSelectedItem);
							}
						}

						if (volumeModel.getCluster().getSelectedItem() == null)
						{
							volumeModel.getCluster().setSelectedItem(Linq.FirstOrDefault(clusters));
						}
					}
				}};

			AsyncDataProvider.GetClusterList(_asyncQuery, dataCenter.getId());
		}
	}

	public boolean Validate(){
		return true;
	}

	public void setAddBricksCommand(UICommand addBricksCommand) {
		this.addBrickCommand = addBricksCommand;
	}
	
	public UICommand getAddBrickCommand(){
		return addBrickCommand;
	}
}
