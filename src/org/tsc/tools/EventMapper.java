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
package org.tsc.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zk.ui.event.SelectEvent;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class EventMapper {

    private static final String mIdent = "$Id: EventMapper.java 1994 2011-05-18 12:37:31Z rahnev $ $HeadURL: https://ontime:8443/svn/iconn/trunk/IConnEmulation/src/iconn/emulation/EventMapper.java $"; //NOPMD

    public static Map mapEvent(Event evt) {
        Map ret = new HashMap();

        if (evt instanceof MouseEvent) {
            ret = mapMouseEvent((MouseEvent) evt);
        } else if (evt instanceof CheckEvent) {
            ret = mapCheckEvent((CheckEvent) evt);
        } else if (evt instanceof DropEvent) {
            ret = mapDropEvent((DropEvent) evt);
        } else if (evt instanceof InputEvent) {
            ret = mapInputEvent((InputEvent) evt);
        } else if (evt instanceof KeyEvent) {
            ret = mapKeyEvent((KeyEvent) evt);
        } else if (evt instanceof OpenEvent) {
            ret = mapOpenEvent((OpenEvent) evt);
        } else if (evt instanceof SelectEvent) {
            ret = mapSelectEvent((SelectEvent) evt);
        }

        if (evt.getData() != null) {
            ret.put("", evt.getData());
        }
        return ret;
    }

    private static Map mapKeys(int keys) {
        Map ret = new HashMap();
        if ((keys & MouseEvent.ALT_KEY) != 0) {
            ret.put("altKey", true);
        }
        if ((keys & MouseEvent.CTRL_KEY) != 0) {
            ret.put("ctrlKey", true);
        }
        if ((keys & MouseEvent.SHIFT_KEY) != 0) {
            ret.put("shiftKey", true);
        }
        if ((keys & MouseEvent.LEFT_CLICK) != 0) {
            ret.put("which", 1);
        } else if ((keys & MouseEvent.MIDDLE_CLICK) != 0) {
            ret.put("which", 2);
        } else if ((keys & MouseEvent.RIGHT_CLICK) != 0) {
            ret.put("which", 3);
        }
        return ret;
    }

    private static Map mapMouseEvent(MouseEvent evt) {
        Map ret = new HashMap();

        if (evt.getArea() != null && !evt.getArea().isEmpty()) {
            ret.put("area", evt.getArea());
        } else {
            ret.put("x", evt.getX());
            ret.put("y", evt.getY());
            ret.put("pageX", evt.getPageX());
            ret.put("pageY", evt.getPageY());
        }
        if (evt.getKeys() != 0) {
            ret.putAll(mapKeys(evt.getKeys()));
        }
        return ret;
    }

    private static Map mapCheckEvent(CheckEvent evt) {
        Map ret = new HashMap();
        ret.put("", evt.isChecked());
        return ret;
    }

    private static Map mapDropEvent(DropEvent evt) {
        Map ret = mapMouseEvent(evt);
        ret.put("dragged", evt.getDragged().getUuid());
        return ret;
    }

    private static Map mapInputEvent(InputEvent evt) {
        Map ret = new HashMap();
        ret.put("value", evt.getValue());
        ret.put("bySelectBack", evt.isChangingBySelectBack());
        ret.put("start", evt.getStart());
        return ret;
    }

    private static Map mapKeyEvent(KeyEvent evt) {
        Map ret = new HashMap();
        ret.put("keyCode", evt.getKeyCode());
        if (evt.getReference() != null) {
            ret.put("reference", evt.getReference().getUuid());
        }
        ret.put("altKey", evt.isAltKey());
        ret.put("ctrlKey", evt.isCtrlKey());
        ret.put("shiftKey", evt.isShiftKey());
        return ret;
    }

    private static Map mapOpenEvent(OpenEvent evt) {
        Map ret = new HashMap();
        ret.put("open", evt.isOpen());
        if (evt.getReference() != null) {
            ret.put("reference", evt.getReference().getUuid());
        }
        ret.put("value", evt.getValue());
        return ret;
    }

    private static Map mapSelectEvent(SelectEvent evt) {
        Map ret = new HashMap();
        if (evt.getReference() != null) {
            ret.put("reference", evt.getReference().getUuid());
        }
        List selectedItemUuids = new ArrayList();
        if (evt.getSelectedItems() != null) {
            for (Object item : evt.getSelectedItems()) {
                selectedItemUuids.add(((Component) item).getUuid());
            }
            ret.put("items", selectedItemUuids);
        }
        ret.putAll(mapKeys(evt.getKeys()));
        return ret;
    }
}
