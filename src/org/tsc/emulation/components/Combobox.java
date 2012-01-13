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

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class Combobox extends Component<org.zkoss.zul.Combobox> {

    private static final String mIdent = "$Id: Combobox.java 2062 2011-05-31 16:07:13Z rahnev $ $HeadURL: https://ontime:8443/svn/iconn/trunk/IConnEmulation/src/iconn/emulation/components/Combobox.java $"; //NOPMD

    public Combobox(org.zkoss.zk.ui.Component parent, String id) {
        super(parent, id);
    }

    public Combobox(org.zkoss.zul.Combobox instance) {
        super(instance);
    }

    public Object getSelectedValue() {
        return INSTANCE.getSelectedItem().getValue();
    }

    public String getText() {
        return INSTANCE.getRawText();
    }

    public int getSelectedIndex() {
        try {
            return INSTANCE.getSelectedIndex();
        } catch (Exception ex) {
            return -1;
        }
    }

    public boolean isReadonly() {
        return INSTANCE.isReadonly();
    }

    public boolean isDisabled() {
        return INSTANCE.isDisabled();
    }

    public boolean isEditable() {
        return !(isDisabled() || isReadonly());
    }

    public void setValue(String value) throws EmulationException {
        if (!isEditable()) {
            fail("Can't set value when readonly");
        }
        doChange(value);
    }
}
