package org.ovirt.engine.ui.uicommonweb.models;
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

import org.ovirt.engine.ui.uicompat.*;
import org.ovirt.engine.core.common.businessentities.*;

import org.ovirt.engine.ui.uicommonweb.dataprovider.*;
import org.ovirt.engine.ui.uicommonweb.*;

@SuppressWarnings("unused")
public enum SystemTreeItemType
{
	System,
	DataCenter,
	Storages,
	Storage,
	Templates,
	Clusters,
	Cluster,
	VMs,
	Hosts,
	Host, 
	Volumes;

	public int getValue()
	{
		return this.ordinal();
	}

	public static SystemTreeItemType forValue(int value)
	{
		return values()[value];
	}
}