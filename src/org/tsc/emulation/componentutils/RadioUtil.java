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
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class RadioUtil {

    public static Radio radioById(Radiogroup instance, String id) {
        Component cmp = instance.getFellow(id);
        if (cmp instanceof Radio) {
            return (Radio) cmp;
        }
        return null;
    }

    public static List<Radio> radioByLabel(Radiogroup instance, String label) {
        List<Radio> ret = new ArrayList<Radio>();
        for (Object cmp : instance.getChildren()) {
            String radioLabel = ((Radio) cmp).getLabel();
            if (radioLabel == null) {
                if (label == null) {
                    ret.add((Radio) cmp);
                }
            } else if (radioLabel.equals(label)) {
                ret.add((Radio) cmp);
            }
        }
        return ret;
    }

    public static List<Radio> radioByValue(Radiogroup instance, String value) {
        List<Radio> ret = new ArrayList<Radio>();
        for (Object cmp : instance.getChildren()) {
            if (cmp instanceof Radio) {
                String radioValue = ((Radio) cmp).getValue();
                if (radioValue == null) {
                    if (value == null) {
                        ret.add((Radio) cmp);
                    }
                } else if (radioValue.equals(value)) {
                    ret.add((Radio) cmp);
                }
            }
        }
        return ret;
    }
}
