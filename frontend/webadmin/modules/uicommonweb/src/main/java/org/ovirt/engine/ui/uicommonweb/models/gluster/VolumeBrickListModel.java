package org.ovirt.engine.ui.uicommonweb.models.gluster;

import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.ui.uicommonweb.models.EntityModel;
import org.ovirt.engine.ui.uicommonweb.models.SearchableListModel;

public class VolumeBrickListModel extends SearchableListModel {

	@Override
	protected String getListName() {
		// TODO Auto-generated method stub
		return "VolumeBrickListModel";
	}
	
	public VolumeBrickListModel(){
		setIsTimerDisabled(false);
	}
	
	@Override
	protected void OnEntityChanged() {
		super.OnEntityChanged();
		if(getEntity() == null){
			return;
		}
		GlusterVolumeEntity glusterVolumeEntity = (GlusterVolumeEntity)getEntity();
		setItems(glusterVolumeEntity.getBricks());
	}
	
	@Override
	protected void SyncSearch() {
		OnEntityChanged();
	}

}
