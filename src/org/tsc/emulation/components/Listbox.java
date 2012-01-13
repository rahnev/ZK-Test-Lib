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

import java.util.ArrayList;
import org.tsc.emulation.exceptions.EmulationException;
import java.util.List;
import org.tsc.emulation.componentutils.ListboxUtil;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class Listbox extends Component<org.zkoss.zul.Listbox> {

    private static final String mIdent = "$Id: Listbox.java 2425 2011-10-05 11:03:41Z gerov $ $HeadURL: https://ontime:8443/svn/iconn/trunk/IConnEmulation/src/iconn/emulation/components/Listbox.java $"; //NOPMD

    public enum EMold {

        Paging,
        None
    }

    public Listbox(org.zkoss.zk.ui.Component parent, String id) {
        super(parent, id);
    }

    public Listbox(org.zkoss.zul.Listbox instance) {
        super(instance);
    }

    public void select(org.zkoss.zul.Listitem item) throws EmulationException {
        if (INSTANCE.isDisabled()) {
            System.out.println("Listbox " + INSTANCE.getName() + " disabled. No select operation performed!");
            return;
        }
        doSelect(new org.zkoss.zk.ui.Component[]{item}, null);
    }

    public String printString() {
        return ListboxUtil.toString(INSTANCE, true);
    }

    public List<Listitem> getItems() {
        List<Listitem> ret = new ArrayList<Listitem>();
        for (Object item : (List) INSTANCE.getChildren()) {
            if (item instanceof org.zkoss.zul.Listitem) {
                ret.add(new Listitem((org.zkoss.zul.Listitem) item));
            }
        }
        return ret;
    }
}