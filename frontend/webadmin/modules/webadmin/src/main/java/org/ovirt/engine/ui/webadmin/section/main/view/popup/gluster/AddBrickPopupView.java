package org.ovirt.engine.ui.webadmin.section.main.view.popup.gluster;

import org.ovirt.engine.ui.uicommonweb.models.gluster.AddBrickModel;
import org.ovirt.engine.ui.webadmin.ApplicationConstants;
import org.ovirt.engine.ui.webadmin.ApplicationResources;
import org.ovirt.engine.ui.webadmin.idhandler.ElementIdHandler;
import org.ovirt.engine.ui.webadmin.section.main.presenter.popup.gluster.AddBrickPopupPresenterWidget;
import org.ovirt.engine.ui.webadmin.section.main.view.popup.AbstractModelBoundPopupView;
import org.ovirt.engine.ui.webadmin.widget.dialog.SimpleDialogPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.inject.Inject;

public class AddBrickPopupView extends AbstractModelBoundPopupView<AddBrickModel> implements AddBrickPopupPresenterWidget.ViewDef {

    interface Driver extends SimpleBeanEditorDriver<AddBrickModel, AddBrickPopupView> {
        Driver driver = GWT.create(Driver.class);
    }

    interface ViewUiBinder extends UiBinder<SimpleDialogPanel, AddBrickPopupView> {
        ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
    }

    interface ViewIdHandler extends ElementIdHandler<AddBrickPopupView> {
        ViewIdHandler idHandler = GWT.create(ViewIdHandler.class);
    }
    
        
    @Inject
    public AddBrickPopupView(EventBus eventBus, ApplicationResources resources, ApplicationConstants constants) {
        super(eventBus, resources);
        initWidget(ViewUiBinder.uiBinder.createAndBindUi(this));
        ViewIdHandler.idHandler.generateAndSetIds(this);
        localize(constants);
        Driver.driver.initialize(this);
    }


    private void localize(ApplicationConstants constants) {
        
    }

    @Override
    public void focusInput() {
    }

    @Override
    public void edit(AddBrickModel object) {
        Driver.driver.edit(object);
    }

    @Override
    public AddBrickModel flush() {
        return Driver.driver.flush();
    }
}
