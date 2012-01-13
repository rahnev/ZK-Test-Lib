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
import org.zkoss.zul.Treerow;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class Treeitem extends Component<org.zkoss.zul.Treeitem> {

    private static final String mIdent = "$Id: Treeitem.java 2293 2011-08-08 17:37:41Z rahnev $ $HeadURL: https://ontime:8443/svn/iconn/trunk/IConnEmulation/src/iconn/emulation/components/Treeitem.java $"; //NOPMD

    public Treeitem(org.zkoss.zk.ui.Component parent, String id) {
        super(parent, id);
    }

    public Treeitem(org.zkoss.zul.Treeitem instance) {
        super(instance);
    }

    public void select() throws EmulationException {
        doSelect(INSTANCE.getTree(), new org.zkoss.zk.ui.Component[]{INSTANCE}, INSTANCE);
        //Thread.sleep(1500);
    }

    public Treecell getTreecell(int index) {
        for (Object obj : INSTANCE.getChildren()) {
            if (obj instanceof Treerow) {
                return new Treecell((org.zkoss.zul.Treecell) ((Treerow) obj).getChildren().get(index));
            }
        }
        return null;
    }

    public void open() throws EmulationException {
        doOpen(true);
    }

    public void close() throws EmulationException {
        doOpen(false);
    }

    public boolean isChecked() {
        return INSTANCE.isSelected();
    }

    public boolean isSelected() {
        return INSTANCE.isSelected();
    }

    private ArrayList<org.zkoss.zk.ui.Component> selectedItems() {
        return new ArrayList<org.zkoss.zk.ui.Component>(INSTANCE.getTree().getSelectedItems());
    }

    public List<Treeitem> getChildItems() throws EmulationException {
        List<Treeitem> children = new ArrayList<Treeitem>();
        if (!INSTANCE.isOpen()) {
            open();
        }
        if (INSTANCE.getTreechildren() != null && INSTANCE.getTreechildren().getChildren() != null) {
            for (Object item : INSTANCE.getTreechildren().getChildren()) {
                children.add(new Treeitem((org.zkoss.zul.Treeitem) item));
            }
        }
        return children;
    }

    public void check() throws EmulationException {
        if (!INSTANCE.isCheckable()) {
            return;
        }
        ArrayList<org.zkoss.zk.ui.Component> items = selectedItems();
        if (!items.contains(INSTANCE)) {
            items.add(INSTANCE);
        }
        doSelect(INSTANCE.getTree(), items.toArray(new org.zkoss.zk.ui.Component[0]), INSTANCE);
    }

    public void uncheck() throws EmulationException {
        if (!INSTANCE.isCheckable()) {
            return;
        }
        ArrayList<org.zkoss.zk.ui.Component> items = selectedItems();
        if (items.contains(INSTANCE)) {
            items.remove(INSTANCE);
        }
        doSelect(INSTANCE.getTree(), items.toArray(new org.zkoss.zk.ui.Component[0]), INSTANCE);
    }

    public String getText() {
        return TreeUtil.treeitemTexts(INSTANCE);
    }
}
