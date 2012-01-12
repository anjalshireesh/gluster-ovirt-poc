package org.ovirt.engine.ui.webadmin.gin.uicommon;

import org.ovirt.engine.core.common.businessentities.AuditLog;
import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeOption;
import org.ovirt.engine.core.common.businessentities.permissions;
import org.ovirt.engine.core.compat.Event;
import org.ovirt.engine.core.compat.EventArgs;
import org.ovirt.engine.core.compat.IEventListener;
import org.ovirt.engine.core.compat.PropertyChangedEventArgs;
import org.ovirt.engine.ui.uicommonweb.UICommand;
import org.ovirt.engine.ui.uicommonweb.models.ConfirmationModel;
import org.ovirt.engine.ui.uicommonweb.models.Model;
import org.ovirt.engine.ui.uicommonweb.models.configure.PermissionListModel;
import org.ovirt.engine.ui.uicommonweb.models.gluster.VolumeBrickListModel;
import org.ovirt.engine.ui.uicommonweb.models.gluster.VolumeEventListModel;
import org.ovirt.engine.ui.uicommonweb.models.gluster.VolumeGeneralModel;
import org.ovirt.engine.ui.uicommonweb.models.gluster.VolumeListModel;
import org.ovirt.engine.ui.uicommonweb.models.gluster.VolumeParameterListModel;
import org.ovirt.engine.ui.webadmin.gin.ClientGinjector;
import org.ovirt.engine.ui.webadmin.section.main.presenter.popup.AbstractModelBoundPopupPresenterWidget;
import org.ovirt.engine.ui.webadmin.section.main.presenter.popup.PermissionsPopupPresenterWidget;
import org.ovirt.engine.ui.webadmin.section.main.presenter.popup.RemoveConfirmationPopupPresenterWidget;
import org.ovirt.engine.ui.webadmin.section.main.presenter.popup.gluster.AddBrickPopupPresenterWidget;
import org.ovirt.engine.ui.webadmin.section.main.presenter.popup.gluster.VolumePopupPresenterWidget;
import org.ovirt.engine.ui.webadmin.uicommon.model.DetailModelProvider;
import org.ovirt.engine.ui.webadmin.uicommon.model.DetailTabModelProvider;
import org.ovirt.engine.ui.webadmin.uicommon.model.MainModelProvider;
import org.ovirt.engine.ui.webadmin.uicommon.model.MainTabModelProvider;
import org.ovirt.engine.ui.webadmin.uicommon.model.SearchableDetailModelProvider;
import org.ovirt.engine.ui.webadmin.uicommon.model.SearchableDetailTabModelProvider;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class VolumeModule extends AbstractGinModule {

    // Main List Model

	@Provides
    @Singleton
    public MainModelProvider<GlusterVolumeEntity, VolumeListModel> getVolumeListProvider(ClientGinjector ginjector,
            final Provider<VolumePopupPresenterWidget> popupProvider,
            final Provider<AddBrickPopupPresenterWidget> popupProvider2,
            final Provider<RemoveConfirmationPopupPresenterWidget> removeConfirmPopupProvider) {
        return new MainTabModelProvider<GlusterVolumeEntity, VolumeListModel>(ginjector, VolumeListModel.class) {
        	private AbstractModelBoundPopupPresenterWidget<?, ?> window2;
        	
        	@Override
            protected AbstractModelBoundPopupPresenterWidget<? extends Model, ?> getModelPopup(UICommand lastExecutedCommand) {
                if (lastExecutedCommand == getModel().getCreateVolumeCommand()) {
                    return popupProvider.get();
                } else {
                    return super.getModelPopup(lastExecutedCommand);
                }
            }

            @Override
            protected AbstractModelBoundPopupPresenterWidget<? extends ConfirmationModel, ?> getConfirmModelPopup(UICommand lastExecutedCommand) {
                if (lastExecutedCommand == getModel().getCancelCommand()) {
                    return removeConfirmPopupProvider.get();
                } else {
                    return super.getConfirmModelPopup(lastExecutedCommand);
                }
            }
            
            @Override
            protected void onCommonModelChange() {
            	super.onCommonModelChange();
            	getModel().getPropertyChangedEvent().addListener(new IEventListener() {
                    @Override
                    public void eventRaised(Event ev, Object sender, EventArgs args) {
                        String propName = ((PropertyChangedEventArgs) args).PropertyName;

                        if ("Window2".equals(propName)) {
                        	Model windowModel = getModel().getWindow2();
                        	if(windowModel != null) {
	                            // Resolve
	                            window2 = popupProvider2.get();
	                            revealPopup(windowModel, (AbstractModelBoundPopupPresenterWidget<Model, ?>)window2);
                        	} else {
                        		window2.hideAndUnbind();
                	            window2 = null;
                	            getModel().ForceRefresh();
                        	}
                        } 
                    }
                });
            }
        };
    }
	

	@Provides
    @Singleton
    public DetailModelProvider<VolumeListModel, VolumeGeneralModel> getVolumeGeneralProvider(ClientGinjector ginjector) {
        return new DetailTabModelProvider<VolumeListModel, VolumeGeneralModel>(ginjector,
        		VolumeListModel.class,
        		VolumeGeneralModel.class) {
            @Override
            protected AbstractModelBoundPopupPresenterWidget<? extends Model, ?> getModelPopup(UICommand lastExecutedCommand) {
            	return super.getModelPopup(lastExecutedCommand);
            }
        };
    }
	
	@Provides
    @Singleton
    public SearchableDetailModelProvider<GlusterBrickEntity, VolumeListModel, VolumeBrickListModel> getVolumeBrickListProvider(ClientGinjector ginjector) {
        return new SearchableDetailTabModelProvider<GlusterBrickEntity, VolumeListModel, VolumeBrickListModel>(ginjector,
        		VolumeListModel.class,
        		VolumeBrickListModel.class){
        	@Override
            protected AbstractModelBoundPopupPresenterWidget<? extends Model, ?> getModelPopup(UICommand lastExecutedCommand) {
            	return super.getModelPopup(lastExecutedCommand);
            }
        };
    }
	
	@Provides
    @Singleton
    public SearchableDetailModelProvider<GlusterVolumeOption, VolumeListModel, VolumeParameterListModel> getVolumeParameterListProvider(ClientGinjector ginjector) {
        return new SearchableDetailTabModelProvider<GlusterVolumeOption, VolumeListModel, VolumeParameterListModel>(ginjector,
        		VolumeListModel.class,
        		VolumeParameterListModel.class) {
        	
        };
    }
	
	
	@Provides
    @Singleton
    public SearchableDetailModelProvider<permissions, VolumeListModel, PermissionListModel> getVolumePermissionListProvider(ClientGinjector ginjector,
            final Provider<PermissionsPopupPresenterWidget> popupProvider,
            final Provider<RemoveConfirmationPopupPresenterWidget> removeConfirmPopupProvider) {
        return new SearchableDetailTabModelProvider<permissions, VolumeListModel, PermissionListModel>(ginjector,
        		VolumeListModel.class,
                PermissionListModel.class) {
            @Override
            protected AbstractModelBoundPopupPresenterWidget<? extends Model, ?> getModelPopup(UICommand lastExecutedCommand) {
                PermissionListModel model = getModel();

                if (lastExecutedCommand == model.getAddCommand()) {
                    return popupProvider.get();
                } else {
                    return super.getModelPopup(lastExecutedCommand);
                }
            }

            @Override
            protected AbstractModelBoundPopupPresenterWidget<? extends ConfirmationModel, ?> getConfirmModelPopup(UICommand lastExecutedCommand) {
                if (lastExecutedCommand == getModel().getRemoveCommand()) {
                    return removeConfirmPopupProvider.get();
                } else {
                    return super.getConfirmModelPopup(lastExecutedCommand);
                }
            }
        };
    }

    @Provides
    @Singleton
    public SearchableDetailModelProvider<AuditLog, VolumeListModel, VolumeEventListModel> getHostEventListProvider(ClientGinjector ginjector) {
        return new SearchableDetailTabModelProvider<AuditLog, VolumeListModel, VolumeEventListModel>(ginjector,
        		VolumeListModel.class,
        		VolumeEventListModel.class);
    }
	
	
	@Override
	protected void configure() {
		
	}
}
