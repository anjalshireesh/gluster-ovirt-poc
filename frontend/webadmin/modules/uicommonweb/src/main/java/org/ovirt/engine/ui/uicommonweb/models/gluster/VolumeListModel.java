package org.ovirt.engine.ui.uicommonweb.models.gluster;

import java.util.ArrayList;

import org.ovirt.engine.core.common.action.VdcActionType;
import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.VOLUME_TYPE;
import org.ovirt.engine.core.common.businessentities.VDSGroup;
import org.ovirt.engine.core.common.businessentities.storage_pool;
import org.ovirt.engine.core.common.glusteractions.CreateGlusterVolumeParameters;
import org.ovirt.engine.core.common.glusteractions.GlusterVolumeParameters;
import org.ovirt.engine.core.common.interfaces.SearchType;
import org.ovirt.engine.core.common.queries.SearchParameters;
import org.ovirt.engine.core.common.queries.VdcQueryType;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.compat.ObservableCollection;
import org.ovirt.engine.core.compat.PropertyChangedEventArgs;
import org.ovirt.engine.ui.frontend.AsyncQuery;
import org.ovirt.engine.ui.frontend.Frontend;
import org.ovirt.engine.ui.frontend.INewAsyncCallback;
import org.ovirt.engine.ui.uicommonweb.Linq;
import org.ovirt.engine.ui.uicommonweb.UICommand;
import org.ovirt.engine.ui.uicommonweb.dataprovider.AsyncDataProvider;
import org.ovirt.engine.ui.uicommonweb.models.EntityModel;
import org.ovirt.engine.ui.uicommonweb.models.ISupportSystemTreeContext;
import org.ovirt.engine.ui.uicommonweb.models.ListWithDetailsModel;
import org.ovirt.engine.ui.uicommonweb.models.Model;
import org.ovirt.engine.ui.uicommonweb.models.SystemTreeItemModel;
import org.ovirt.engine.ui.uicommonweb.models.SystemTreeItemType;
import org.ovirt.engine.ui.uicommonweb.models.configure.PermissionListModel;
import org.ovirt.engine.ui.uicompat.FrontendActionAsyncResult;
import org.ovirt.engine.ui.uicompat.IFrontendActionAsyncCallback;

public class VolumeListModel extends ListWithDetailsModel implements ISupportSystemTreeContext {
    private UICommand startCommand;
    private UICommand stopCommand;
    private UICommand rebalanceCommand;
    private UICommand addBricksCommand;

    private Model window2;
    public Model getWindow2()
    {
        return window2;
    }
    public void setWindow2(Model value)
    {
        if (window2 != value)
        {
            window2 = value;
            OnPropertyChanged(new PropertyChangedEventArgs("Window2"));
        }
    }

    public UICommand getAddBricksCommand() {
        return addBricksCommand;
    }
    public void setAddBricksCommand(UICommand addBricksCommand) {
        this.addBricksCommand = addBricksCommand;
    }

    public UICommand getRebalanceCommand() {
        return rebalanceCommand;
    }
    public void setRebalanceCommand(UICommand rebalanceCommand) {
        this.rebalanceCommand = rebalanceCommand;
    }
    public UICommand getStartCommand() {
        return startCommand;
    }
    public void setStartCommand(UICommand startCommand) {
        this.startCommand = startCommand;
    }
    public UICommand getStopCommand() {
        return stopCommand;
    }
    public void setStopCommand(UICommand stopCommand) {
        this.stopCommand = stopCommand;
    }
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
        setSearchString(getDefaultSearchString());

        setCreateVolumeCommand(new UICommand("Create Volume", this));
        setRemoveVolumeCommand(new UICommand("Remove", this));
        setStartCommand(new UICommand("Start", this));
        setStopCommand(new UICommand("Stop", this));
        setRebalanceCommand(new UICommand("Rebalance", this));

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
        setAddBricksCommand(new UICommand("addBricks", this));
        volumeModel.setAddBricksCommand(getAddBricksCommand());
        AsyncQuery _asyncQuery = new AsyncQuery();
        _asyncQuery.setModel(this);
        _asyncQuery.asyncCallback = new INewAsyncCallback() { @Override
        public void OnSuccess(Object model, Object result)
            {
                VolumeListModel volumeListModel = (VolumeListModel)model;
                VolumeModel innerVolumeModel = (VolumeModel)volumeListModel.getWindow();
                java.util.ArrayList<storage_pool> dataCenters = (java.util.ArrayList<storage_pool>)result;

                innerVolumeModel.getDataCenter().setItems(dataCenters);
                innerVolumeModel.getDataCenter().setSelectedItem(Linq.FirstOrDefault(dataCenters));

                if (volumeListModel.getSystemTreeSelectedItem() != null)
                {
                    switch (volumeListModel.getSystemTreeSelectedItem().getType())
                    {
                        case Hosts:
                        case Cluster:
                        case Volumes:
                            VDSGroup cluster = (VDSGroup)volumeListModel.getSystemTreeSelectedItem().getEntity();
                            for (storage_pool dc : (java.util.ArrayList<storage_pool>)innerVolumeModel.getDataCenter().getItems())
                            {
                                if (dc.getId().equals(cluster.getstorage_pool_id()))
                                {
                                    innerVolumeModel.getDataCenter().setItems(new java.util.ArrayList<storage_pool>(java.util.Arrays.asList(new storage_pool[] { dc })));
                                    innerVolumeModel.getDataCenter().setSelectedItem(dc);
                                    break;
                                }
                            }
                            innerVolumeModel.getDataCenter().setIsChangable(false);
                            innerVolumeModel.getDataCenter().setInfo("Cannot choose Host's Data Center in tree context");
                            innerVolumeModel.getCluster().setIsChangable(false);
                            innerVolumeModel.getCluster().setInfo("Cannot choose Host's Cluster in tree context");
                            break;
                        case DataCenter:
                            storage_pool selectDataCenter = (storage_pool)volumeListModel.getSystemTreeSelectedItem().getEntity();
                            innerVolumeModel.getDataCenter().setItems(new java.util.ArrayList<storage_pool>(java.util.Arrays.asList(new storage_pool[] { selectDataCenter })));
                            innerVolumeModel.getDataCenter().setSelectedItem(selectDataCenter);
                            innerVolumeModel.getDataCenter().setIsChangable(false);
                            innerVolumeModel.getDataCenter().setInfo("Cannot choose Host's Data Center in tree context");
                            break;
                        default:
                            break;
                    }
                }

                UICommand command = new UICommand("onCreateVolume", volumeListModel);
                command.setTitle("OK");
                command.setIsDefault(true);
                innerVolumeModel.getCommands().add(command);
                command = new UICommand("Cancel", volumeListModel);
                command.setTitle("Cancel");
                command.setIsDefault(true);
                innerVolumeModel.getCommands().add(command);
            }};
        AsyncDataProvider.GetDataCenterList(_asyncQuery);


    }

    private void removeVolume() {

    }

    public static Guid clusterId;

    @Override
    public boolean IsSearchStringMatch(String searchString)
    {
        return searchString.trim().toLowerCase().startsWith("volume");
    }

    @Override
    protected void SyncSearch() {
//        super.SyncSearch();
//        if(getSystemTreeSelectedItem() != null && (getSystemTreeSelectedItem().getType().equals(SystemTreeItemType.Cluster) || getSystemTreeSelectedItem().getType().equals(SystemTreeItemType.Volumes))) {
//            final VDSGroup cluster = (VDSGroup)getSystemTreeSelectedItem().getEntity();
//            clusterId = cluster.getID();
//            Frontend.RunAction(VdcActionType.ListGlusterVolumes, new VdsGroupParametersBase(cluster.getID()), new IFrontendActionAsyncCallback() {
//
//                @Override
//                public void Executed(FrontendActionAsyncResult result) {
//                    if(result.getReturnValue().getActionReturnValue() != null) {
//                        ArrayList<GlusterVolumeEntity> volumes =  new ArrayList<GlusterVolumeEntity>();
//                        for (GlusterVolumeEntity iterable_element : (java.util.ArrayList<GlusterVolumeEntity>)result.getReturnValue().getActionReturnValue()) {
//                            volumes.add(iterable_element);
//                        }
//                        setItems(volumes);
//                    } else {
//                        setItems(new ArrayList<GlusterVolumeEntity>());
//                    }
//                }
//            });
//        }
//        else {
//            setItems(new ArrayList<GlusterVolumeEntity>());
//        }
//        setIsQueryFirstTime(false);

        if (getSystemTreeSelectedItem() != null
                && (getSystemTreeSelectedItem().getType().equals(SystemTreeItemType.Cluster) || getSystemTreeSelectedItem().getType()
                        .equals(SystemTreeItemType.Volumes))) {
            final VDSGroup cluster = (VDSGroup) getSystemTreeSelectedItem().getEntity();
            clusterId = cluster.getID(); // this is used by the "add bricks" screen while fetching brick list
        }

        SearchParameters tempVar = new SearchParameters(getSearchString(), SearchType.GlusterVolume);
        tempVar.setMaxCount(getSearchPageSize());
        tempVar.setRefresh(getIsQueryFirstTime());
        super.SyncSearch(VdcQueryType.Search, tempVar);
    }

    @Override
    protected void OnSelectedItemChanged() {
        super.OnSelectedItemChanged();
        updateActionAvailability();
    }

    private void updateActionAvailability() {
        getRemoveVolumeCommand().setIsExecutionAllowed(getSelectedItem() != null);
        getStartCommand().setIsExecutionAllowed(getSelectedItem() != null);
        getStopCommand().setIsExecutionAllowed(getSelectedItem() != null);
        getRebalanceCommand().setIsExecutionAllowed(getSelectedItem() != null);
    }
    @Override
    public void ExecuteCommand(UICommand command) {
        super.ExecuteCommand(command);
        if(command.equals(getCreateVolumeCommand())){
            createVolume();
        } else if (command.equals(getRemoveVolumeCommand())) {
            removeVolume();
        } else if (command.getName().equals("Cancel")) {
            cancel();
        } else if (command.getName().equals("onCreateVolume")) {
            onCreateVolume();
        } else if (command.equals(getStartCommand())) {
            start();
        } else if (command.equals(getStopCommand())) {
            stop();
        } else if (command.equals(getRebalanceCommand())){
            rebalance();
        } else if(command.equals(getAddBricksCommand())){
            addBricks();
        } else if(command.getName().equals("CancelAddBrick")){
            CancelWindow2();
        } else if(command.getName().equals("onAddBrick")){
            onAddBrick();
        }
    }

    private void CancelWindow2() {
        setWindow2(null);
    }

    private void onAddBrick() {
        final VDSGroup cluster = (VDSGroup)getSystemTreeSelectedItem().getEntity();
        clusterId = cluster.getID(); // this is used by the "add bricks" screen while fetching brick list

        AddBrickModel model = (AddBrickModel)getWindow2();
        if(model == null){
            CancelWindow2();
        }
        VolumeModel volumeModel = (VolumeModel)getWindow();
        for (Object item : model.getItems())
        {
            EntityModel entityModel = (EntityModel) item;
            if (entityModel.getIsSelected())
            {
                String currBricks = (String)volumeModel.getBricks().getEntity();
                if(currBricks == null || currBricks.isEmpty()){
                    volumeModel.getBricks().setEntity(((GlusterBrickEntity)entityModel.getEntity()).getQualifiedName());
                } else {
                    volumeModel.getBricks().setEntity(currBricks + ", " + ((GlusterBrickEntity)entityModel.getEntity()).getQualifiedName());
                }
            }
        }
        CancelWindow2();
    }
    private void addBricks() {

//        if(getSelectedItem() == null) {
//            return;
//        }
//        GlusterVolumeEntity volume = (GlusterVolumeEntity)getSelectedItem();
        if(((VolumeModel)getWindow()).getName() == null || ((String)((VolumeModel)getWindow()).getName().getEntity()).isEmpty()){
            return;
        }
        AddBrickModel model = new AddBrickModel();
        setWindow2(model);
        model.setVolumeName((String)((VolumeModel)getWindow()).getName().getEntity());

        ArrayList<String> list = new ArrayList<String>();
        if(((VolumeModel)getWindow()).getBricks().getEntity() != null && !((String)((VolumeModel)getWindow()).getBricks().getEntity()).isEmpty()) {
            for (String str : ((String)((VolumeModel)getWindow()).getBricks().getEntity()).split(",")) {
                list.add(str.trim());
            }
        }

        model.setCurBrickList(list);
        model.init();
        UICommand command = new UICommand("onAddBrick", this);
        command.setTitle("OK");
        command.setIsDefault(true);
        model.getCommands().add(command);
        command = new UICommand("CancelAddBrick", this);
        command.setTitle("Cancel");
        command.setIsDefault(true);
        model.getCommands().add(command);

    }
    private void rebalance() {
        if(getSelectedItem() == null) {
            return;
        }
        GlusterVolumeEntity volume = (GlusterVolumeEntity)getSelectedItem();
        Frontend.RunAction(VdcActionType.RebalanceGlusterVolumeStart, new GlusterVolumeParameters(clusterId, volume.getName()));

    }
    private void stop() {
        if(getSelectedItem() == null) {
            return;
        }
        GlusterVolumeEntity volume = (GlusterVolumeEntity)getSelectedItem();
        Frontend.RunAction(VdcActionType.StopGlusterVolume, new GlusterVolumeParameters(clusterId, volume.getName()));

    }
    private void start() {
        if(getSelectedItem() == null) {
            return;
        }
        GlusterVolumeEntity volume = (GlusterVolumeEntity)getSelectedItem();
        Frontend.RunAction(VdcActionType.StartGlusterVolume, new GlusterVolumeParameters(clusterId, volume.getName()));
    }

    private void onCreateVolume() {
        VolumeModel model = (VolumeModel) getWindow();
        Guid clusterId = ((VDSGroup)model.getCluster().getSelectedItem()).getID();
        GlusterVolumeEntity volume = new GlusterVolumeEntity();
        volume.setName((String)model.getName().getEntity());
        VOLUME_TYPE type = (VOLUME_TYPE)model.getTypeList().getSelectedItem();

        if(type == VOLUME_TYPE.STRIPE){
            volume.setStripeCount(4);
        } else if(type == VOLUME_TYPE.REPLICATE) {
            volume.setReplicaCount(2);
        }
        volume.setVolumeType(type);
        volume.setBricks((String)model.getBricks().getEntity());
        CreateGlusterVolumeParameters parameter = new CreateGlusterVolumeParameters(clusterId, volume);

        Frontend.RunAction(VdcActionType.CreateGlusterVolume, parameter, new IFrontendActionAsyncCallback() {

            @Override
            public void Executed(FrontendActionAsyncResult result) {
                cancel();
            }
        });
    }
    private void cancel() {
        setWindow(null);
    }
    @Override
    protected String getListName() {
        // TODO Auto-generated method stub
        return "VolumeListModel";
    }
    private SystemTreeItemModel systemTreeSelectedItem;
    @Override
    public SystemTreeItemModel getSystemTreeSelectedItem()
    {
        return systemTreeSelectedItem;
    }
    @Override
    public void setSystemTreeSelectedItem(SystemTreeItemModel value)
    {
        if (systemTreeSelectedItem != value)
        {
            systemTreeSelectedItem = value;
            OnSystemTreeSelectedItemChanged();
        }
    }

    private void OnSystemTreeSelectedItemChanged()
    {
        updateActionAvailability();
    }

}
