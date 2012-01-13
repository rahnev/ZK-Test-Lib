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
import java.util.List;
import org.tsc.tools.TextUtil;
import org.zkoss.zul.Column;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Row;
import org.zkoss.zul.impl.LoadStatus;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class GridUtil {

    public static String[] rowToString(Row row) {
        ArrayList<String> ret = new ArrayList<String>();
        List<org.zkoss.zul.Column> cols = row.getGrid().getColumns().getChildren();
        List children = row.getChildren();
        for (int i = 0; i < row.getChildren().size(); i++) {
            if (cols != null && cols.size() > i && !cols.get(i).isVisible()) {
                continue;
            }
            Object obj = children.get(i);
            if (obj instanceof org.zkoss.zul.Label) {
                ret.add(((org.zkoss.zul.Label) obj).getValue());
            } else {
                ret.add("");
            }
        }
        return ret.toArray(new String[]{});
    }

    public static String[] getHeader(Grid instance) {
        if (instance.getColumns() == null) {
            return new String[0];
        }

        ArrayList<String> ret = new ArrayList<String>();
        for (Column column : (List<Column>) instance.getColumns().getChildren()) {
            if (!column.isVisible()) {
                continue;
            }
            String label = column.getLabel();
            if (label == null) {
                label = "";
            }
            ret.add(label.trim());
        }
        return ret.toArray(new String[]{});
    }

    public static String printString(Grid instance, boolean withHeader) {
        StringBuilder sb = new StringBuilder();
        sb.append(TextUtil.join(getHeader(instance), ","));
        for (int i = 0; i < instance.getRows().getChildren().size(); i++) {
            Row row = (Row) instance.getRows().getChildren().get(i);
            if (((LoadStatus) row.getExtraCtrl()).isLoaded()) {
                sb.append("\n").append(TextUtil.join(rowToString(row), ","));
            }
        }
        return sb.toString();
    }

    public static Column getColumnByLabel(Grid instance, String label) {
        if (instance.getColumns() == null || label == null) {
            return null;
        }
        for (Object obj : instance.getColumns().getChildren()) {
            if (obj instanceof Column && label.equals(((Column) obj).getLabel())) {
                return (Column) obj;
            }
        }
        return null;
    }

    public static Column getColumnByIndex(Grid instance, int index) {
        if (instance.getColumns() == null || index < 0 || index >= instance.getColumns().getChildren().size()) {
            return null;
        }
        return (Column) instance.getColumns().getChildren().get(index);
    }
}
