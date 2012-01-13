/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * ZKTest - Free ZeroKode testing library.
 * Copyright (C) 2011 Telesoft Consulting GmbH http://www.telesoft-consulting.at
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; If not, see http://www.gnu.org/licenses/
 * 
 * Telesoft Consulting GmbH
 * Gumpendorferstraße 83-85
 * House 1, 1st Floor, Office No.1
 * 1060 Vienna, Austria
 * http://www.telesoft-consulting.at/
 */
package org.tsc.emulation.components;

import org.tsc.emulation.exceptions.EmulationException;
import org.tsc.emulation.componentutils.TabUtils;
import org.zkoss.zul.Tab;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class Tabbox extends Component<org.zkoss.zul.Tabbox> {

    private static final String mIdent = "$Id: Tabbox.java 2063 2011-05-31 16:53:12Z rahnev $ $HeadURL: https://ontime:8443/svn/iconn/trunk/IConnEmulation/src/iconn/emulation/components/Tabbox.java $"; //NOPMD
    private Tab _selectedTab = null;

    public Tabbox(org.zkoss.zk.ui.Component parent, String id) {
        super(parent, id);
    }

    public Tabbox(org.zkoss.zul.Tabbox instance) {
        super(instance);
    }

    public int tabsCount() {
        return TabUtils.tabsCount(INSTANCE);
    }

    public int panelsCount() {
        return TabUtils.panelsCount(INSTANCE);
    }

    public boolean allTabsAndPanelsAssociated() {
        return tabsCount() == panelsCount();
    }

    public boolean selectTabByLabel(String label) throws EmulationException {
        Tab tab = TabUtils.tabByLabel(INSTANCE, label);
        if (tab == null) {
            return false;
        }
        return new org.tsc.emulation.components.Tab(tab).select();
    }

    public boolean selectTabById(String id) throws EmulationException {
        Tab tab = TabUtils.tabById(INSTANCE, id);
        if (tab == null) {
            return false;
        }
        return new org.tsc.emulation.components.Tab(tab).select();
    }

    public boolean selectTabByIndex(int index) throws EmulationException {
        Tab tab = TabUtils.tabByIndex(INSTANCE, index);
        if (tab == null) {
            return false;
        }
        return new org.tsc.emulation.components.Tab(tab).select();
    }
}