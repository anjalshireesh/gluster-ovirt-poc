/*******************************************************************************
 * Copyright (c) 2006-2011 Gluster, Inc. <http://www.gluster.com>
 * This file is part of Gluster Management Console.
 *
 * Gluster Management Console is free software; you can redistribute
 * it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Gluster Management Console is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.ovirt.engine.core.common.businessentities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.ovirt.engine.core.common.utils.StringUtil;

public class GlusterEntity extends IVdcQueryable implements Filterable, Serializable {

    protected String name;
    protected List<GlusterEntity> children = new ArrayList<GlusterEntity>();
    private GlusterEntity parent;

    public GlusterEntity() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public GlusterEntity getParent() {
        return parent;
    }

    public void setParent(GlusterEntity parent) {
        this.parent = parent;
    }

    public List<? extends GlusterEntity> getChildren() {
        return children;
    }

    public void setChildren(List<GlusterEntity> children) {
        this.children = children;
    }

    public GlusterEntity(String name, GlusterEntity parent) {
        this.name = name;
        this.parent = parent;
    }

    public GlusterEntity(String name, GlusterEntity parent, List<GlusterEntity> children) {
        this.name = name;
        this.parent = parent;
        this.children = children;
    }

    @Override
    public boolean filter(String filterString, boolean caseSensitive) {
        return StringUtil.filterString(getName(), filterString, caseSensitive);
    }

    @Override
    public String toString() {
        return name;
    }
}
