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

import java.io.IOException;
import org.tsc.emulation.GuiEnvironment;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.tsc.emulation.Client;
import org.tsc.emulation.exceptions.EmulationException;
import org.tsc.emulation.servlet.TestUpdateServlet;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
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
public class EventExecution {

    public static HttpServletResponse executeEvent(Event evt) throws EmulationException {
        Client client = GuiEnvironment.getClient(evt.getPage().getDesktop());
        return client.executeCommand(evt);
    }

    public static HttpServletResponse executeSelect(Component cmp, Component[] selectedItems, Component ref) throws EmulationException {
        HashSet set = new HashSet();
        set.addAll(Arrays.asList(selectedItems));
        return executeEvent(new SelectEvent(Events.ON_SELECT, cmp, set, ref));
    }

    public static HttpServletResponse executeCreate(Component cmp, Map args) throws EmulationException {
        return executeEvent(new CreateEvent(Events.ON_CREATE, cmp, args));
    }

    public static HttpServletResponse executeClick(Component cmp) throws EmulationException {
        return executeEvent(new MouseEvent(Events.ON_CLICK, cmp));
    }

    public static HttpServletResponse executeDoubleClick(Component cmp) throws EmulationException {
        return executeEvent(new MouseEvent(Events.ON_DOUBLE_CLICK, cmp));
    }

    public static HttpServletResponse executeRightClick(Component cmp) throws EmulationException {
        return executeEvent(new MouseEvent(Events.ON_RIGHT_CLICK, cmp));
    }

    public static HttpServletResponse executeFocus(Component cmp) throws EmulationException {
        return executeEvent(new Event(Events.ON_FOCUS, cmp));
    }

    public static HttpServletResponse executeBlur(Component cmp) throws EmulationException {
        return executeEvent(new Event(Events.ON_BLUR, cmp));
    }

    public static HttpServletResponse executeSort(Component cmp) throws EmulationException {
        return executeEvent(new Event(Events.ON_SORT, cmp));
    }

    public static HttpServletResponse executeCheck(Component cmp, boolean checked, Component target) throws EmulationException {
        return executeEvent(new CheckEvent(Events.ON_CHECK, target, checked));
    }

    public static HttpServletResponse executeCheck(Component cmp, boolean checked) throws EmulationException {
        return executeEvent(new CheckEvent(Events.ON_CHECK, cmp, checked));
    }

    public static HttpServletResponse executeClose(Component cmp) throws EmulationException {
        return executeEvent(new Event(Events.ON_CLOSE, cmp));
    }

    public static HttpServletResponse executeClose(Component cmp, Object data) throws EmulationException {
        return executeEvent(new Event(Events.ON_CLOSE, cmp, data));
    }

    public static HttpServletResponse executeOpen(Component cmp, boolean open) throws EmulationException {
        return executeEvent(new OpenEvent(Events.ON_OPEN, cmp, open));
    }

    public static HttpServletResponse executeChange(Component cmp, String value) throws EmulationException {
        return executeEvent(new InputEvent(Events.ON_CHANGE, cmp, value));
    }

    public static HttpServletResponse executeChanging(Component cmp, String value) throws EmulationException {
        return executeEvent(new InputEvent(Events.ON_CHANGING, cmp, value));
    }

    public static HttpServletResponse executeOK(Component cmp) throws EmulationException {
        return executeEvent(new KeyEvent(Events.ON_OK, cmp, 0, false, false, false));
    }

    public static void executeUpload(Component cmp, org.zkoss.util.media.Media media) throws EmulationException {
        Client client = GuiEnvironment.getClient(cmp.getDesktop());
        try {
            TestUpdateServlet.upload(client, cmp, media);
        } catch (IOException ex) {
            throw new EmulationException("upload failed", ex);
        }
    }
}
