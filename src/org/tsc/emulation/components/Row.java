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

import org.tsc.emulation.componentutils.ComponentUtil;
import org.tsc.emulation.componentutils.GridUtil;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class Row extends Component<org.zkoss.zul.Row> {

    private static final String mIdent = "$Id: Row.java 2293 2011-08-08 17:37:41Z rahnev $ $HeadURL: https://ontime:8443/svn/iconn/trunk/IConnEmulation/src/iconn/emulation/components/Row.java $"; //NOPMD

    public Row(org.zkoss.zul.Row instance) {
        super(instance);
    }

    public String[] getTexts() {
        return GridUtil.rowToString(INSTANCE);
    }

    public Button getButton(String label) {
        org.zkoss.zul.Button btn = ComponentUtil.findButtonByLabel(INSTANCE, label);
        if (btn == null) {
            return null;
        }
        return new Button(btn);
    }
}
