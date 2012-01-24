package org.ovirt.engine.ui.uicommonweb.models.gluster;

import java.util.ArrayList;

import org.ovirt.engine.core.common.action.VdcActionType;
import org.ovirt.engine.core.common.businessentities.GlusterBrickEntity;
import org.ovirt.engine.core.common.glusteractions.GlusterVolumeParameters;
import org.ovirt.engine.ui.frontend.Frontend;
import org.ovirt.engine.ui.uicommonweb.models.EntityModel;
import org.ovirt.engine.ui.uicommonweb.models.ListModel;
import org.ovirt.engine.ui.uicompat.FrontendActionAsyncResult;
import org.ovirt.engine.ui.uicompat.IFrontendActionAsyncCallback;

public class AddBrickModel extends ListModel{
    private String volumeName;

    public AddBrickModel(){
        setTitle("Add Bricks");
    }

    ArrayList<String> curBrickList;


    public ArrayList<String> getCurBrickList() {
        return curBrickList;
    }

    public void setCurBrickList(ArrayList<String> curBrickList) {
        this.curBrickList = curBrickList;
    }

    public void init(){
        Frontend.RunAction(VdcActionType.ListGlusterBricks, new GlusterVolumeParameters(VolumeListModel.clusterId, getVolumeName()), new IFrontendActionAsyncCallback() {

            @Override
            public void Executed(FrontendActionAsyncResult result) {
                ArrayList<EntityModel> list = new ArrayList<EntityModel>();
                for (GlusterBrickEntity brick : (ArrayList<GlusterBrickEntity>)result.getReturnValue().getActionReturnValue()) {
                    EntityModel eModel = new EntityModel();
                    eModel.setEntity(brick);
                    list.add(eModel);
                }
                setItems(list);

                initSelections();
            }
        });
    }

    public void initSelections(){
        if(getItems() != null){
            for (EntityModel entity : (ArrayList<EntityModel>)getItems()) {
                GlusterBrickEntity brick = (GlusterBrickEntity)entity.getEntity();
                for (String str : getCurBrickList()) {
                    if(brick.getQualifiedName().equals(str)){
                        entity.setIsSelected(true);
                        entity.setIsChangable(false);
                    }
                }
            }
        }
    }

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }

}
