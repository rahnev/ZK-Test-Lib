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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.tsc.emulation.exceptions.EmulationException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Box;
import org.zkoss.zul.Label;
import org.zkoss.zul.Span;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treecols;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
@org.junit.Ignore
public class TreeUtil {

    public static final String SPLITTER = ";";

    private static String getChildrenText(Component cmp) {
        boolean isFirst = true;
        StringBuilder sb = new StringBuilder();
        for (Object obj : cmp.getChildren()) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(SPLITTER);
            }
            sb.append(getColumnText((Component) obj));
        }
        return sb.toString();
    }

    public static String getColumnText(Component cell) {
        if (cell.getChildren() == null || cell.getChildren().isEmpty()) {
            if (cell instanceof Treecell) {
                return ((Treecell) cell).getLabel();
            }
            return "";
        }
        Component container = (Component) cell.getChildren().get(0);
        if (container instanceof Label) {
            return ((Label) container).getValue();
        }
        if (container instanceof Textbox) {
            return ((Textbox) container).getValue();
        }
        if (container instanceof Span || container instanceof Box) {
            return getChildrenText(cell);
        }
        return "Unsupported text container " + container.getClass().getSimpleName();
    }

    public static String treeitemTexts(Treeitem item) {
        Treerow row = null;

        for (Object obj : item.getChildren()) {
            if (obj instanceof Treerow) {
                row = (Treerow) obj;
                break;
            }
        }

        return getChildrenText(row);
    }

    private static String treeitemToString(Treeitem item, String spaces, boolean markSelected) throws EmulationException {
        StringBuilder sb = new StringBuilder();
        if (!item.isOpen()) {
            new org.tsc.emulation.components.Treeitem(item).open();
        }
        String pading = spaces;
        if (markSelected && item.isSelected()) {
            pading = spaces.length() > 0 ? spaces.substring(0, spaces.length() - 1) + "*" : "*";
        }
        sb.append(pading).append(treeitemTexts(item)).append("\n");

        List<Treeitem> children = new ArrayList<Treeitem>();
        if (item.getTreechildren() != null && item.getTreechildren().getChildren() != null) {
            children.addAll(item.getTreechildren().getChildren());
        }
        for (int i = 0; i < children.size(); i++) {
            sb.append(treeitemToString(children.get(i), spaces + "    ", markSelected));

        }
        return sb.toString();
    }

    private static String treeHeaderToString(Treecols cols) {
        StringBuilder sb = new StringBuilder();
        if (cols != null) {
            for (Object obj : cols.getChildren()) {
                if (!(obj instanceof Treecol)) {
                    continue;
                }
                if (sb.length() != 0) {
                    sb.append(SPLITTER);
                }
                sb.append(((Treecol) obj).getLabel());
            }
        }
        return sb.append("\n").toString();
    }

    public static String treeToString(Tree tree, boolean includeHeader, boolean markSelection) throws EmulationException {
        StringBuilder sb = new StringBuilder();
        if (includeHeader) {
            sb.append(treeHeaderToString(tree.getTreecols()));
        }
        for (Object item : tree.getTreechildren().getChildren()) {
            sb.append(treeitemToString((Treeitem) item, "", markSelection));
        }
        return sb.toString();
    }

    public static String treeToString(Tree tree, boolean includeHeader) throws EmulationException {
        return treeToString(tree, includeHeader, false);
    }

    public static Treeitem findTreeitemAtPath(Treechildren tc, String... path) throws EmulationException {
        String[] data = path[0].split(",");
        for (org.zkoss.zul.Treeitem item : new ArrayList<org.zkoss.zul.Treeitem>((Collection<org.zkoss.zul.Treeitem>) tc.getChildren())) {
            org.tsc.emulation.components.Treeitem itemEmulator = new org.tsc.emulation.components.Treeitem(item);
            if (!itemEmulator.INSTANCE.isOpen()) {
                itemEmulator.open();
            }
            for (Object obj : item.getChildren()) {
                if (obj instanceof Treerow) {
                    Treerow row = (Treerow) obj;
                    int cnt = 0;
                    for (Object cell : row.getChildren()) {
                        if (cnt >= data.length) {
                            break;
                        }
                        if (!TreeUtil.getColumnText((Treecell) cell).equals(data[cnt])) {
                            break;
                        }
                        cnt++;
                    }
                    if (cnt == data.length) {
                        if (path.length == 1) {
                            return item;
                        } else {
                            return findTreeitemAtPath(((org.zkoss.zul.Treeitem) item).getTreechildren(), Arrays.copyOfRange(path, 1, path.length));
                        }
                    }
                }
            }
        }
        return null;
    }

    public static Treeitem findTreeitemAtPath(Tree tree, String... path) throws EmulationException {
        return findTreeitemAtPath(tree.getTreechildren(), path);
    }
}
