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
package org.tsc.emulation.componentutils;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class TabUtils {

    public static int tabsCount(Tabbox instance) {
        return instance.getTabs().getChildren().size();
    }

    public static int panelsCount(Tabbox instance) {
        return instance.getTabpanels().getChildren().size();
    }

    public static Tab tabById(Tabbox instance, String id) {
        Component cmp = instance.getFellow(id);
        if (cmp instanceof Tab) {
            return (Tab) cmp;
        }
        return null;
    }

    public static Tab tabByIndex(Tabbox instance, int index) {
        for (Object cmp : instance.getTabs().getChildren()) {
            if (((Tab) cmp).getIndex() == index) {
                return (Tab) cmp;
            }
        }
        return null;
    }

    public static Tab tabByLabel(Tabbox instance, String label) {
        if (label == null) {
            return null;
        }
        for (Object cmp : instance.getTabs().getChildren()) {
            if (label.equals(((Tab) cmp).getLabel())) {
                return (Tab) cmp;
            }
        }
        return null;
    }
}
