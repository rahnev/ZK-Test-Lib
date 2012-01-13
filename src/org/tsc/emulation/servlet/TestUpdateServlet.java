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
package org.tsc.emulation.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.tsc.emulation.Client;
import org.tsc.emulation.exceptions.EmulationException;
import org.zkoss.util.media.Media;
import org.zkoss.web.servlet.xel.RequestContexts;
import org.zkoss.zk.au.AuRequest;
import org.zkoss.zk.au.AuWriter;
import org.zkoss.zk.au.AuWriters;
import org.zkoss.zk.au.http.DHtmlUpdateServlet;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.http.ExecutionImpl;
import org.zkoss.zk.ui.http.WebManager;
import org.zkoss.zk.ui.impl.UiEngineImpl;
import org.zkoss.zk.ui.sys.WebAppCtrl;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class TestUpdateServlet extends DHtmlUpdateServlet {

    protected static TestUpdateServlet SINGLETONE;
    protected static final ThreadLocal<Client> _activeClients = new ThreadLocal<Client>();

    protected void doEnter(Client client) throws EmulationException {
        freeExecutionThread();
        // If any other client active on current thread (even the cleint self) deactivate the execution
        HttpServletRequest request = client.createRequest(client.getDesktop().getId(), null, "dummy", null);
        HttpServletResponse response = client.createResponse();
        Desktop desktop = client.getDesktop();

        Session sess = WebManager.getSession(getServletContext(), request, false);
        WebAppCtrl wappc = ((WebAppCtrl) sess.getWebApp());
        ExecutionImpl exec = new ExecutionImpl(getServletContext(), request, response, desktop, null);
        ((UiEngineImpl) wappc.getUiEngine()).activate(exec);
        _activeClients.set(client);
    }

    protected void doLeave(Client client) throws EmulationException {
        HttpServletRequest request = client.createRequest();
        HttpServletResponse response = client.createResponse();
        Desktop desktop = client.getDesktop();


        Session sess = WebManager.getSession(getServletContext(), request, false);
        WebAppCtrl wappc = ((WebAppCtrl) sess.getWebApp());
        ExecutionImpl exec = new ExecutionImpl(getServletContext(), request, response, desktop, null);
        if (exec == null || exec.getDesktop() == null) {
            System.out.print("leave of client " + client.getDesktop().getId() + " failed");
            return;
        }

        if (RequestContexts.getCurrent() != null) {
            try {
                ((UiEngineImpl) wappc.getUiEngine()).endUpdate(exec);
            } catch (Exception ex) {
                throw new EmulationException("deactivate of the execution falied", ex);
            }
        }
        _activeClients.remove();
    }

    protected void doUpload(Client client, Component target, Media media) throws IOException {
        Desktop desktop = target.getDesktop();
        HttpServletRequest request = client.createRequest();
        HttpServletResponse response = client.createResponse();
        ServletContext context = getServletContext();

        List aureqs = new ArrayList();
        aureqs.add(new AuRequest(target.getDesktop(), "dummy", new HashMap()));

        AuWriter out = AuWriters.newInstance();
        out.open(request, response, 0);

        Session sess = WebManager.getSession(context, request, false);
        WebAppCtrl wappc = ((WebAppCtrl) sess.getWebApp());
        ExecutionImpl exec = new ExecutionImpl(context, request, response, desktop, null);

        exec.postEvent(new UploadEvent(Events.ON_UPLOAD, target, new Media[]{media}));
        ((UiEngineImpl) wappc.getUiEngine()).execUpdate(exec, aureqs, out);
    }

    protected void freeExecutionThread() throws EmulationException {
        Client activeClient = _activeClients.get();
        if (activeClient != null) {
            doLeave(activeClient);
        }
    }

    public static boolean isCreated() {
        return SINGLETONE != null;
    }

    public static void create(ServletConfig config) throws ServletException {
        SINGLETONE = new TestUpdateServlet();
        SINGLETONE.init(config);
    }

    public static void destroyServlet() {
        SINGLETONE.destroy();
        SINGLETONE = null;
    }

    public static void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SINGLETONE.doGet(request, response);
    }

    public static void upload(Client client, Component target, Media media) throws IOException {
        SINGLETONE.doUpload(client, target, media);
    }

    public static void enter(Client client) throws EmulationException {
        SINGLETONE.doEnter(client);
    }

    public static void leave(Client client) throws EmulationException {
        SINGLETONE.doLeave(client);
    }

    public static void free() throws EmulationException {
        SINGLETONE.freeExecutionThread();
    }

    public static Client getActiveClient() {
        return _activeClients.get();
    }
}
