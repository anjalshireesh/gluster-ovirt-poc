package org.ovirt.engine.ui.uicommonweb.models.userportal;
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

import org.ovirt.engine.ui.uicommonweb.models.vms.*;
import org.ovirt.engine.ui.uicompat.*;
import org.ovirt.engine.ui.uicommonweb.*;
import org.ovirt.engine.ui.uicommonweb.models.*;

@SuppressWarnings("unused")
public abstract class ItemBehavior
{
	private UserPortalItemModel privateItem;
	protected UserPortalItemModel getItem()
	{
		return privateItem;
	}
	private void setItem(UserPortalItemModel value)
	{
		privateItem = value;
	}

	protected ItemBehavior(UserPortalItemModel item)
	{
		setItem(item);
	}

	public abstract void OnEntityChanged();

	public abstract void EntityPropertyChanged(PropertyChangedEventArgs e);

	public abstract void ExecuteCommand(UICommand command);

	public abstract void eventRaised(Event ev, Object sender, EventArgs args);
}