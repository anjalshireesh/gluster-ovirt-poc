package org.ovirt.engine.ui.uicommonweb.models.gluster;

import java.util.ArrayList;

import org.ovirt.engine.core.common.businessentities.GlusterVolume;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.compat.ObservableCollection;
import org.ovirt.engine.ui.uicommonweb.UICommand;
import org.ovirt.engine.ui.uicommonweb.models.EntityModel;
import org.ovirt.engine.ui.uicommonweb.models.ListWithDetailsModel;
import org.ovirt.engine.ui.uicommonweb.models.configure.PermissionListModel;

public class VolumeListModel extends ListWithDetailsModel {
	static String guid = Guid.NewGuid().toString();
	private UICommand createVolumeCommand;
	public UICommand getCreateVolumeCommand()
	{
		return createVolumeCommand;
	}
	private void setCreateVolumeCommand(UICommand value)
	{
		createVolumeCommand = value;
	}
	private UICommand removeVolumeCommand;
	public UICommand getRemoveVolumeCommand()
	{
		return removeVolumeCommand;
	}
	private void setRemoveVolumeCommand(UICommand value)
	{
		removeVolumeCommand = value;
	}
	
	public VolumeListModel(){
		setTitle("Volumes");
		
		setDefaultSearchString("Volumes:");
		setCreateVolumeCommand(new UICommand("Create Volume", this));
		setRemoveVolumeCommand(new UICommand("Remove", this));
		
		getSearchNextPageCommand().setIsAvailable(true);
		getSearchPreviousPageCommand().setIsAvailable(true);
	}
	
	@Override
	protected void InitDetailModels() {
		super.InitDetailModels();
		ObservableCollection<EntityModel> list = new ObservableCollection<EntityModel>();
		list.add(new VolumeGeneralModel());
		list.add(new VolumeParameterListModel());
		list.add(new VolumeBrickListModel());
		list.add(new PermissionListModel());
		list.add(new VolumeEventListModel());
		setDetailModels(list);
	}

	private void createVolume() {
		if (getWindow() != null) {
			return;
		}
		
		VolumeModel volumeModel = new VolumeModel();
		volumeModel.setTitle("Create Volume");
		setWindow(volumeModel);
		UICommand command = new UICommand("onCreateVolume", this);
		command.setTitle("OK");
		command.setIsDefault(true);
		volumeModel.getCommands().add(command);
		command = new UICommand("Cancel", this);
		command.setTitle("Cancel");
		command.setIsDefault(true);
		volumeModel.getCommands().add(command);
	}

	private void removeVolume() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void SyncSearch() {
		super.SyncSearch();
		ArrayList<GlusterVolumeEntity> list = new ArrayList<GlusterVolumeEntity>();
		GlusterVolumeEntity glusterVolume = new GlusterVolumeEntity();
		glusterVolume.setName("Vol1");
		glusterVolume.setId(guid);
		list.add(glusterVolume);
		setItems(list);
	}
	
	@Override
	protected void OnSelectedItemChanged() {
		super.OnSelectedItemChanged();
		updateActionAvailability();
	}
	
	private void updateActionAvailability() {
		getRemoveVolumeCommand().setIsExecutionAllowed(getSelectedItem() != null);
	}
	@Override
	public void ExecuteCommand(UICommand command) {
		super.ExecuteCommand(command);
		if(command.equals(getCreateVolumeCommand())){
			createVolume();
		}
		else if(command.equals(getRemoveVolumeCommand())){
			removeVolume();
		} 
		else if(command.getName().equals("Cancel")){
			 cancel();
		}
	}
	
	private void cancel() {
		setWindow(null);		
	}
	@Override
	protected String getListName() {
		// TODO Auto-generated method stub
		return "VolumeListModel";
	}

}
