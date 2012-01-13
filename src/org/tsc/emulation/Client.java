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
package org.tsc.emulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.tsc.emulation.exceptions.EmulationException;
import org.tsc.emulation.serverpush.ServerPushClient;
import org.tsc.emulation.servlet.TestLayoutServlet;
import org.tsc.emulation.servlet.TestRequest;
import org.tsc.emulation.servlet.TestResponse;
import org.tsc.emulation.servlet.TestSession;
import org.tsc.emulation.servlet.TestUpdateServlet;
import org.tsc.tools.EventMapper;
import org.tsc.tools.ReflectionUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.impl.DesktopImpl;
import org.zkoss.zk.ui.impl.EventProcessor;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class Client {

    public String URL = null;
    public List<Cookie> COOKIES = null;
    private ServerPushClient _serverPushClient = null;
    private Desktop _desktop = null;
    private final HttpSession _session;

    public Client(HttpSession session) {
        _session = session;
    }

    public Client() {
        this(new TestSession());
    }

    public HttpServletRequest createRequest() {
        return new TestRequest(this);
    }

    public HttpServletRequest createRequest(String desctopId, String componentId, String command, Map values) {
        return new TestRequest(this, desctopId, componentId, command, values);
    }

    public HttpServletResponse createResponse() {
        return new TestResponse(this);
    }

    public HttpSession getSession() {
        return _session;
    }

    public Desktop getDesktop() {
        return _desktop;
    }

    public Page getPage() {
        return getDesktop().getFirstPage();
    }

    public HttpServletResponse create(String url) throws EmulationException {
        Client activeClient = TestUpdateServlet.getActiveClient();
        URL = url;
        COOKIES = new ArrayList<Cookie>();
        //TestUpdateServlet.leave(this);
        HttpServletRequest request = createRequest();
        HttpServletResponse response = createResponse();
        _desktop = TestLayoutServlet.createDesktop(this, request, response);
        TestUpdateServlet.enter(this);
        GuiEnvironment.register(this);
        ping();
        _serverPushClient = ServerPushClient.create(this);
        _serverPushClient.enter();
        ping();
        TestUpdateServlet.free();
        if (activeClient != null) {
            TestUpdateServlet.enter(activeClient);
        }
        return response;
    }

    public void restore() throws EmulationException {
        Client activeClient = TestUpdateServlet.getActiveClient();
        TestUpdateServlet.enter(this);
        clearAll();
        Map downloads = getAllDownloads();
        if (downloads != null) {
            downloads.clear();
        }
        COOKIES.clear();
        //TestUpdateServlet.enter(this);
        TestUpdateServlet.free();
        if (activeClient != null) {
            TestUpdateServlet.enter(activeClient);
        }
    }

    public void exit() throws EmulationException {
        restore();
        _serverPushClient.exit();
        closeDesktop(_desktop);
        Client activeClient = TestUpdateServlet.getActiveClient();
        ThreadLocal _inEvt = (ThreadLocal) ReflectionUtil.getStaticField(EventProcessor.class, "_inEvt");
        if (_inEvt != null) {
            //_inEvt.set(null);
            _inEvt.remove();
        }
        COOKIES = null;
        GuiEnvironment.unregister(this);
        if (activeClient != null && activeClient != this) {
            TestUpdateServlet.enter(activeClient);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Request Handling">
    protected void closeDesktop(Desktop desktop) throws EmulationException {
        if (!desktop.isAlive()) {
            return;
        }
        Client activeClient = TestUpdateServlet.getActiveClient();
        TestUpdateServlet.free();// activate current client (all other are deactivated)
        TestUpdateServlet.leave(this);// deactivate current client

        HttpServletRequest reqest = createRequest(desktop.getId(), null, "rmDesktop", null);
        HttpServletResponse response = createResponse();
        try {
            TestUpdateServlet.get(reqest, response);
        } catch (Exception ex) {
            throw new EmulationException("can not close desktop", ex);
        }
        if (activeClient != null && activeClient != this) {
            TestUpdateServlet.enter(activeClient);
        }
    }

    public void ping() throws EmulationException {
        if (getDesktop().isAlive()) {
            executeCommand(getDesktop().getId(), null, "dummy", null);
        }
    }

    public HttpServletResponse executeCommand(Component component, String command, Map values) throws EmulationException {
        return executeCommand(component.getDesktop().getId(), component.getUuid(), command, values);
    }

    public HttpServletResponse executeCommand(Component component, Event evt) throws EmulationException {
        Map params = EventMapper.mapEvent(evt);

        if (component.getDesktop() == null) {
            return executeCommand(_desktop.getId(), component.getUuid(), evt.getName(), params);
        } else {
            return executeCommand(component.getDesktop().getId(), component.getUuid(), evt.getName(), params);
        }
    }

    public HttpServletResponse executeCommand(Event evt) throws EmulationException {
        return executeCommand(evt.getTarget(), evt);
    }

    public HttpServletResponse executeCommand(String desktopId, String componentId, String command, Map values) throws EmulationException {

        Client activeClient = TestUpdateServlet.getActiveClient();
        TestUpdateServlet.free();// leave the execution for this thread

        HttpServletRequest reqest = createRequest(desktopId, componentId, command, values);
        HttpServletResponse response = createResponse();
        try {
            TestUpdateServlet.get(reqest, response);// send request
        } catch (Exception ex) {
            throw new EmulationException("Send request failed", ex);
        }

        if (activeClient != null) {
            TestUpdateServlet.enter(activeClient);// activate the execution
        }
        return response;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Components">
    public List<Component> getCreatedComponents() {
        return new ArrayList<Component>(getPage().getRoots());
    }

    public Component getMainControl() {
        return getPage().getFirstRoot();
    }

    public Component getComponent(Class cls) {
        for (Component cmp : getCreatedComponents()) {
            if (cls.isAssignableFrom(cmp.getClass())) {
                return cmp;
            }
        }
        for (Object cmp : getDesktop().getComponents()) {
            if (cls.isAssignableFrom(cmp.getClass())) {
                return (Component) cmp;
            }
        }
        return null;
    }

    public void clearAll() {
        String mainControl = getMainControl().getId();
        for (Component cmp : getCreatedComponents()) {
            if (!cmp.getId().equals(mainControl)) {
                cmp.detach();
            }
        }
        for (Component cmp : getCreatedComponents()) {
            if (cmp instanceof org.zkoss.zul.Window) {
                if (!cmp.getId().equals(mainControl)) {
                    cmp.detach();
                }
            }
        }
        List<Component> desktopComponents = new ArrayList(getDesktop().getComponents());
        for (Component cmp : desktopComponents) {
            if (cmp instanceof org.zkoss.zul.Window && ((org.zkoss.zul.Window) cmp).inModal()) {
                cmp.detach();
            }
        }

    }

    public Map getAllDownloads() throws EmulationException {
        Map _meds = (Map) ReflectionUtil.getField(getDesktop(), DesktopImpl.class, "_meds");
        if (_meds == null) {
            return new HashMap();
        }
        return _meds;
    }
    //</editor-fold>
}
