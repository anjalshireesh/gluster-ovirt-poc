package org.ovirt.engine.ui.uicommonweb.models.configure.roles_ui;

import java.util.ArrayList;
import java.util.Collections;
import org.ovirt.engine.core.compat.*;
import org.ovirt.engine.ui.uicompat.*;
import org.ovirt.engine.core.common.businessentities.*;
import org.ovirt.engine.core.common.vdscommands.*;
import org.ovirt.engine.core.common.queries.*;
import org.ovirt.engine.core.common.action.*;
import org.ovirt.engine.ui.frontend.*;
import org.ovirt.engine.ui.uicommonweb.*;
import org.ovirt.engine.ui.uicommonweb.models.*;
import org.ovirt.engine.core.common.*;

import org.ovirt.engine.ui.uicommonweb.models.common.*;
import org.ovirt.engine.ui.uicommonweb.*;
import org.ovirt.engine.ui.uicompat.*;
import org.ovirt.engine.core.common.businessentities.*;
import org.ovirt.engine.ui.uicommonweb.models.*;
import org.ovirt.engine.ui.uicommonweb.models.configure.*;

@SuppressWarnings("unused")
public class RoleTreeView {
	public static java.util.ArrayList<SelectionTreeNodeModel> GetRoleTreeView(
			boolean isReadOnly, boolean isAdmin) {
		RoleNode tree = initTreeView();
		java.util.ArrayList<ActionGroup> userActionGroups = null;
		if (isAdmin == false) {
			userActionGroups = GetUserActionGroups();
		}

		java.util.ArrayList<SelectionTreeNodeModel> roleTreeView = new java.util.ArrayList<SelectionTreeNodeModel>();

		SelectionTreeNodeModel firstNode = null, secondNode = null, thirdNode = null;
		for (RoleNode first : tree.getLeafRoles()) {
			firstNode = new SelectionTreeNodeModel();
			firstNode.setTitle(first.getName());
			firstNode.setDescription(first.getName());
			firstNode.setIsChangable(!isReadOnly);

			for (RoleNode second : first.getLeafRoles()) {
				secondNode = new SelectionTreeNodeModel();
				secondNode.setTitle(second.getName());
				secondNode.setDescription(second.getName());
				secondNode.setIsChangable(!isReadOnly);
				secondNode.setTooltip(second.getTooltip());
				for (RoleNode third : second.getLeafRoles()) {
					thirdNode = new SelectionTreeNodeModel();
					thirdNode.setTitle(third.getName());
					thirdNode.setDescription(third.getDesc());
					thirdNode.setIsSelectedNotificationPrevent(true);
					// thirdNode.IsSelected =
					// attachedActions.Contains((VdcActionType)
					// Enum.Parse(typeof (VdcActionType), name)); //TODO:
					// suppose to be action group
					thirdNode.setIsChangable(!isReadOnly);
					thirdNode.setIsSelectedNullable(false);
					thirdNode.setTooltip(third.getTooltip());
					if (!isAdmin) {
						if (userActionGroups.contains(ActionGroup
								.valueOf(thirdNode.getTitle()))) {
							secondNode.getChildren().add(thirdNode);
						}
					} else {
						secondNode.getChildren().add(thirdNode);
					}

				}
				if (secondNode.getChildren().size() > 0) {
					firstNode.getChildren().add(secondNode);
				}
			}
			if (firstNode.getChildren().size() > 0) {
				roleTreeView.add(firstNode);
			}
		}

		return roleTreeView;
	}

	private static ArrayList<ActionGroup> GetUserActionGroups() {
		ArrayList<ActionGroup> array = new ArrayList<ActionGroup>();
		array.add(ActionGroup.CREATE_VM);
		array.add(ActionGroup.DELETE_VM);
		array.add(ActionGroup.EDIT_VM_PROPERTIES);
		array.add(ActionGroup.VM_BASIC_OPERATIONS);
		array.add(ActionGroup.CHANGE_VM_CD);
		array.add(ActionGroup.MIGRATE_VM);
		array.add(ActionGroup.CONNECT_TO_VM);
		array.add(ActionGroup.CONFIGURE_VM_NETWORK);
		array.add(ActionGroup.CONFIGURE_VM_STORAGE);
		array.add(ActionGroup.MOVE_VM);
		array.add(ActionGroup.MANIPULATE_VM_SNAPSHOTS);
		array.add(ActionGroup.CREATE_TEMPLATE);
		array.add(ActionGroup.EDIT_TEMPLATE_PROPERTIES);
		array.add(ActionGroup.DELETE_TEMPLATE);
		array.add(ActionGroup.COPY_TEMPLATE);
		array.add(ActionGroup.CONFIGURE_TEMPLATE_NETWORK);
		array.add(ActionGroup.CREATE_VM_POOL);
		array.add(ActionGroup.EDIT_VM_POOL_CONFIGURATION);
		array.add(ActionGroup.DELETE_VM_POOL);
		array.add(ActionGroup.VM_POOL_BASIC_OPERATIONS);
		array.add(ActionGroup.MANIPULATE_PERMISSIONS);
		return array;

	}

	private static RoleNode initTreeView() {
		RoleNode tree = new RoleNode(
				"root",
				new RoleNode[] {
						new RoleNode(
								"System",
								new RoleNode(
										"Configure System",
										new RoleNode[] {
												new RoleNode(
														ActionGroup.MANIPULATE_USERS,
														"Allow to Add/Remove Users from the System"),
												new RoleNode(
														ActionGroup.MANIPULATE_PERMISSIONS,
														"Allow to add/remove permissions for Users on objects in the system"),
												new RoleNode(
														ActionGroup.MANIPULATE_ROLES,
														"Allow to define/configure roles in the System"),
												new RoleNode(
														ActionGroup.CONFIGURE_ENGINE,
														"Allow to get or set System Configuration") })),
						new RoleNode(
								"Gluster",
								new RoleNode(
										"Configure Volumes",
										new RoleNode[] {
												new RoleNode(
														ActionGroup.GLUSTER_CREATE_VOLUME,
														"Allow to create Gluster Volumes"),
												new RoleNode(
														ActionGroup.GLUSTER_VOLUME_OPERATIONS,
														"Allow to manipulate Gluster Volumes") })),
						new RoleNode(
								"Data Center",
								new RoleNode(
										"Configure Data Center",
										new RoleNode[] {
												new RoleNode(
														ActionGroup.CREATE_STORAGE_POOL,
														"Allow to create Data Center"),
												new RoleNode(
														ActionGroup.DELETE_STORAGE_POOL,
														"Allow to remove Data Center"),
												new RoleNode(
														ActionGroup.EDIT_STORAGE_POOL_CONFIGURATION,
														"Allow to modify Data Center properties"),
												new RoleNode(
														ActionGroup.CONFIGURE_STORAGE_POOL_NETWORK,
														"Allow to configure Logical Network per Data Center") })),
						new RoleNode(
								"Cluster",
								new RoleNode(
										"Configure Cluster",
										new RoleNode[] {
												new RoleNode(
														ActionGroup.CREATE_CLUSTER,
														"Allow to create new Cluster"),
												new RoleNode(
														ActionGroup.DELETE_CLUSTER,
														"Allow to remove Cluster"),
												new RoleNode(
														ActionGroup.EDIT_CLUSTER_CONFIGURATION,
														"Allow to Edit Cluster properties"),
												new RoleNode(
														ActionGroup.CONFIGURE_CLUSTER_NETWORK,
														"Allow to add/remove Logical Networks for the Cluster (from the list of Networks defined by the Data Center)") })),
						new RoleNode(
								"Host",
								new RoleNode(
										"Configure Host",
										new RoleNode[] {
												new RoleNode(
														ActionGroup.CREATE_HOST,
														"Allow to add new Host to the Cluster"),
												new RoleNode(
														ActionGroup.DELETE_HOST,
														"Allow  to remove existing Host from the Cluster"),
												new RoleNode(
														ActionGroup.EDIT_HOST_CONFIGURATION,
														"Allow to Edit Host properties; upgrade/install"),
												new RoleNode(
														ActionGroup.MANIPUTLATE_HOST,
														"Allow to change Host status: activate/maintenance"),
												new RoleNode(
														ActionGroup.CONFIGURE_HOST_NETWORK,
														"Allow to configure Host's Network physical interfaces (Nics)") })) });
		return tree;
	}
}
