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
package org.tsc.tools.tree;

import java.io.FileWriter;
import org.tsc.emulation.componentutils.TreeUtil;
import org.tsc.tools.TextUtil;
import org.tsc.emulation.exceptions.EmulationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import static org.junit.Assert.*;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
@org.junit.Ignore
public class DumpNode {

    String _dump;
    String[] _values = null;
    int _level;
    int _row = 0;
    ArrayList<DumpNode> _children = new ArrayList<DumpNode>();
    DumpNodeReader _reader = null;

    public DumpNode(String dump, int level, int row, DumpNodeReader reader) {
        _dump = dump;
        _level = level;
        _row = row;
        _reader = reader;
        if (dump != null) {
            _values = dump.split(TreeUtil.SPLITTER, -1);
        }
    }

    protected static void load(DumpNode parent, DumpNodeReader reader) {
        while (reader.hasNext()) {
            DumpNode child = reader.next();
            if (parent._level == child._level - 1) {
                parent._children.add(child);
            } else if (parent._level == child._level - 2) {
                reader.goBack();
                load(parent._children.get(parent._children.size() - 1), reader);
            } else {
                reader.goBack();
                return;
            }
        }
    }

    public static DumpNode load(String dumpSource, boolean loadHeader) throws FileNotFoundException, IOException, EmulationException {
        DumpNodeReader reader = new DumpNodeReader();
        reader.load(dumpSource, loadHeader);
        DumpNode ret = new DumpNode(null, 0, 0, reader);
        load(ret, reader);
        return ret;
    }

    public int getLevel() {
        return _level;
    }

    public String getDump() {
        return _dump;
    }

    public DumpNodeReader getReader() {
        return _reader;
    }

    public int getRow() {
        return _row;
    }

    public int size() {
        return _children.size();
    }

    public DumpNode getChild(int position) {
        return _children.get(position);
    }

    protected void compareTreeItem(Treeitem item) throws EmulationException {
        Treerow row = null;
        for (Object obj : item.getChildren()) {
            if (obj instanceof Treerow) {
                row = (Treerow) obj;
                break;
            }
        }
        int counter = 0;
        for (Object obj : row.getChildren()) {
            if (obj instanceof Treecell) {
                if (counter >= _values.length) {
                    fail("Tree contains more items than specified in file. File: " + _reader.getFileName() + " Row: " + _row);
                }
                assertEquals("Tree item doesn't match. File: " + _reader.getFileName() + " Row: " + _row, _values[counter++], TreeUtil.getColumnText((Treecell) obj));
            }
        }
        if (counter < _values.length) {
            fail("Tree contains less items than specified in file. File: " + _reader.getFileName() + " Row: " + _row);
        }
        compareTreeChildren(item);
    }

    protected void compareTreeChildren(Treeitem item) throws EmulationException {
        List<Treeitem> children = new ArrayList<Treeitem>();
        if (!item.isOpen()) {
            new org.tsc.emulation.components.Treeitem(item).open();
        }
        if (item.getTreechildren() != null && item.getTreechildren().getChildren() != null) {
            children = item.getTreechildren().getChildren();
        }
        assertEquals("Wrong children count at row " + _row + ". File " + _reader.getFileName(), _children.size(), children.size());
        for (int i = 0; i < _children.size(); i++) {
            _children.get(i).compareTreeItem(children.get(i));
        }
    }

    public void compareTree(Tree tree) throws EmulationException {
        List<Treeitem> children = new ArrayList<Treeitem>();
        if (tree.getTreechildren() != null && tree.getTreechildren().getChildren() != null) {
            children = tree.getTreechildren().getChildren();
        }

        //assertEquals("Wrong tree items count. Dump file:" + _reader.getFileName(), _reader.rootNodesSize(), tree.getItems().size());
        assertEquals("Wrong tree rows count. Dump file:" + _reader.getFileName(), 0, tree.getRows());
        assertEquals("Wrong root children count. Dump file:" + _reader.getFileName(), _children.size(), children.size());
        for (int i = 0; i < _children.size(); i++) {
            _children.get(i).compareTreeItem(children.get(i));
        }
    }

    public static void compareTree(Tree tree, String treeDumpFile, boolean loadHeader) throws EmulationException, FileNotFoundException, IOException {
        DumpNode node = DumpNode.load(treeDumpFile, loadHeader);
        node.compareTree(tree);
    }

    public static void storeTree(Tree tree, String treeDumpFile, boolean loadHeader) throws EmulationException, IOException {
        FileWriter fw = new FileWriter(treeDumpFile);
        try {
            fw.append(TreeUtil.treeToString(tree, loadHeader));
        } finally {
            fw.close();
        }
    }
    @Override
    public String toString() {
        return TextUtil.padRight("level " + _level + " :", _level + 4, ' ') + _dump;
    }
}
