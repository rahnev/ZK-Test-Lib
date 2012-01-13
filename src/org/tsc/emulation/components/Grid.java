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

import org.tsc.emulation.componentutils.GridUtil;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class Grid extends Component<org.zkoss.zul.Grid> {

    private static final String mIdent = "$Id: Grid.java 2293 2011-08-08 17:37:41Z rahnev $ $HeadURL: https://ontime:8443/svn/iconn/trunk/IConnEmulation/src/iconn/emulation/components/Grid.java $";

    public Grid(org.zkoss.zk.ui.Component parent, String id) {
        super(parent, id);
    }

    public Grid(org.zkoss.zul.Grid instance) {
        super(instance);
    }

    public int size() {
        return INSTANCE.getRows().getVisibleItemCount();
    }

    public String[] header() {
        return GridUtil.getHeader(INSTANCE);
    }

    public Column getColumn(int index) {
        org.zkoss.zul.Column column = GridUtil.getColumnByIndex(INSTANCE, index);
        if (column == null) {
            return null;
        }
        return new Column(column);
    }

    public Column getColumnByLabel(String label) {
        org.zkoss.zul.Column column = GridUtil.getColumnByLabel(INSTANCE, label);
        if (column == null) {
            return null;
        }
        return new Column(column);

    }

    public Row getRow(int index) {
        return new Row((org.zkoss.zul.Row) INSTANCE.getRows().getChildren().get(index));
    }

    public String printString() {
        return GridUtil.printString(INSTANCE, true);
    }
}
