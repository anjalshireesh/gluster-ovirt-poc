package org.ovirt.engine.ui.webadmin.section.main.presenter.tab;

import java.util.List;

import org.ovirt.engine.core.common.businessentities.GlusterVolume;
import org.ovirt.engine.core.common.businessentities.VDS;
import org.ovirt.engine.ui.uicommonweb.models.gluster.VolumeListModel;
import org.ovirt.engine.ui.uicommonweb.models.hosts.HostListModel;
import org.ovirt.engine.ui.webadmin.gin.ClientGinjector;
import org.ovirt.engine.ui.webadmin.place.ApplicationPlaces;
import org.ovirt.engine.ui.webadmin.section.main.presenter.AbstractMainTabWithDetailsPresenter;
import org.ovirt.engine.ui.webadmin.section.main.presenter.MainTabPanelPresenter;
import org.ovirt.engine.ui.webadmin.uicommon.model.MainModelProvider;
import org.ovirt.engine.ui.webadmin.widget.tab.ModelBoundTabData;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.mvp.client.TabData;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.TabInfo;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TabContentProxyPlace;

public class MainTabVolumePresenter extends AbstractMainTabWithDetailsPresenter<GlusterVolume, VolumeListModel, MainTabVolumePresenter.ViewDef, MainTabVolumePresenter.ProxyDef> {

    @GenEvent
    public static class VolumeSelectionChange {

        List<GlusterVolume> selectedItems;

    }

    @ProxyCodeSplit
    @NameToken(ApplicationPlaces.volumeMainTabPlace)
    public interface ProxyDef extends TabContentProxyPlace<MainTabVolumePresenter> {
    }

    public interface ViewDef extends AbstractMainTabWithDetailsPresenter.ViewDef<GlusterVolume> {
    }

    @TabInfo(container = MainTabPanelPresenter.class)
    static TabData getTabData(ClientGinjector ginjector) {
        return new ModelBoundTabData(ginjector.getApplicationConstants().volumeMainTabLabel(), 4,
                ginjector.getMainTabVolumeModelProvider());
    }

    @Inject
    public MainTabVolumePresenter(EventBus eventBus, ViewDef view, ProxyDef proxy,
            PlaceManager placeManager, MainModelProvider<GlusterVolume, VolumeListModel> modelProvider) {
        super(eventBus, view, proxy, placeManager, modelProvider);
    }

    @Override
    protected void fireTableSelectionChangeEvent() {
        VolumeSelectionChangeEvent.fire(this, getSelectedItems());
    }

    @Override
    protected PlaceRequest getMainTabRequest() {
        return new PlaceRequest(ApplicationPlaces.volumeMainTabPlace);
    }

    @Override
    protected PlaceRequest getDefaultSubTabRequest() {
        return new PlaceRequest(ApplicationPlaces.volumeGeneralSubTabPlace);
    }
}
