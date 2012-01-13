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

import org.tsc.emulation.componentutils.TreeUtil;
import org.tsc.emulation.exceptions.EmulationException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class Tree extends Component<org.zkoss.zul.Tree> {

    private static final String mIdent = "$Id: Tree.java 2324 2011-08-17 12:44:23Z rahnev $ $HeadURL: https://ontime:8443/svn/iconn/trunk/IConnEmulation/src/iconn/emulation/components/Tree.java $"; //NOPMD

    public Tree(org.zkoss.zk.ui.Component parent, String id) {
        super(parent, id);
    }

    public Tree(org.zkoss.zul.Tree instance) {
        super(instance);
    }

    public Treeitem getItemAtPath(String... arg) throws EmulationException {
        org.zkoss.zul.Treeitem item = TreeUtil.findTreeitemAtPath(INSTANCE, arg);
        if (item == null) {
            return null;
        }
        return new Treeitem(item);
    }

    public List<Treeitem> getSelectedItems() {
        List<Treeitem> ret = new ArrayList<Treeitem>();
        for (Object obj : INSTANCE.getSelectedItems()) {
            ret.add(new Treeitem((org.zkoss.zul.Treeitem) obj));
        }
        return ret;
    }

    public String printString() throws EmulationException {
        return TreeUtil.treeToString(INSTANCE, false, true);
    }
}
