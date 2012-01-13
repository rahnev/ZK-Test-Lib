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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class ListboxUtil {

    public static String[] getHeader(Listbox instance) {
        ArrayList<String> ret = new ArrayList<String>();
        for (Object item : instance.getChildren()) {
            if (item instanceof Listhead) {
                for (Object head : ((Listhead) item).getChildren()) {
                    ret.add(((Listheader) head).getLabel());
                }
                break;
            }
        }
        return ret.toArray(new String[ret.size()]);
    }

    public static String[] listItemStrings(Listitem instance) {
        ArrayList<String> ret = new ArrayList<String>();
        for (Object cell : instance.getChildren()) {
            ret.add(((Listcell) cell).getLabel());
        }
        return ret.toArray(new String[ret.size()]);
    }

    public static List<Listitem> getItems(Listbox instance) {
        List<Listitem> ret = new ArrayList<Listitem>();
        for (Object item : (List) instance.getChildren()) {
            if (item instanceof org.zkoss.zul.Listitem) {
                ret.add((org.zkoss.zul.Listitem) item);
            }
        }
        return ret;
    }

    public static String toString(Listbox instance, boolean withHeader) {
        StringBuilder sb = new StringBuilder();

        if (withHeader) {
            String[] headers = getHeader(instance);
            if (headers.length > 0) {
                sb.append(TextUtil.join(headers, ", ")).append("\n");
            }
        }
        for (Listitem item : getItems(instance)) {
            if (item.isSelected()) {
                sb.append("*");
            }
            String[] itemStrings = listItemStrings(item);
            sb.append(TextUtil.join(itemStrings, ", ")).append("\n");
        }
        return sb.toString();
    }
}
