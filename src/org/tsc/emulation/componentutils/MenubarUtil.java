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

import java.util.Arrays;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class MenubarUtil {

    public static Menuitem findMenuItemByPath(Component owner, String... path) {
        Menuitem ret = null;
        for (Component cmp : (List<Component>) owner.getChildren()) {
            if (cmp instanceof Menuitem) {
                if (path.length == 1 && ((Menuitem) cmp).getLabel().equals(path[0])) {
                    return (Menuitem) cmp;
                }
            } else if (cmp instanceof Menupopup) {
                ret = findMenuItemByPath(cmp, path);
                if (ret != null) {
                    return ret;
                }
            } else if (cmp instanceof Menu) {
                if (path.length > 1 && ((Menu) cmp).getLabel().equals(path[0])) {
                    ret = findMenuItemByPath(cmp, Arrays.copyOfRange(path, 1, path.length));
                    if (ret != null) {
                        return ret;
                    }
                }
            }
        }
        return null;
    }
}
