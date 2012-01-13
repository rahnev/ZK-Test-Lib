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
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.tsc.emulation.exceptions.EmulationException;
import org.tsc.emulation.servlet.TestLayoutServlet;
import org.tsc.emulation.servlet.TestServletConfig;
import org.tsc.emulation.servlet.TestServletContext;
import org.tsc.emulation.servlet.TestUpdateServlet;
import org.zkoss.zk.ui.Desktop;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class GuiEnvironmentImpl {

    private final HashMap<String, Client> CLIENT_MAPPING = new HashMap<String, Client>();
    private Client MAIN_CLIENT = null;
    private ServletContext SERVLET_CONTEXT = null;

    protected GuiEnvironmentImpl() {
    }

    protected void register(Client client) {
        CLIENT_MAPPING.put(client.getDesktop().getId(), client);
    }

    protected void unregister(Client client) {
        CLIENT_MAPPING.remove(client.getDesktop().getId());
    }

    public Client getClient(String desktopId) {
        return CLIENT_MAPPING.get(desktopId);
    }

    public Client getClient(Desktop desktop) {
        return CLIENT_MAPPING.get(desktop.getId());
    }

    public ServletContext getServletContext() {
        return SERVLET_CONTEXT;
    }

    protected ServletConfig createUpdateServletConfig() {
        return new TestServletConfig(getServletContext());
    }

    protected ServletConfig createLayoutServletConfig() {
        return new TestServletConfig(getServletContext());
    }

    protected void initServlets() throws ServletException {
        TestLayoutServlet.create(createLayoutServletConfig());
        TestUpdateServlet.create(createUpdateServletConfig());
    }

    public void create(String webPath) throws ServletException {
        SERVLET_CONTEXT = new TestServletContext(webPath);
        initServlets();
    }

    public void destroy() throws EmulationException {
        List<Client> clientsTodestory = new ArrayList<Client>(CLIENT_MAPPING.values());
        for (Client client : clientsTodestory) {
            client.exit();
        }
        if (!CLIENT_MAPPING.isEmpty()) {
            throw new EmulationException("Can not destoy all created clients");
        }
        MAIN_CLIENT = null;
        TestUpdateServlet.destroyServlet();
        TestLayoutServlet.destroyServlet();
    }

    public void restore() throws EmulationException {
        List<Client> clientsToDestory = new ArrayList<Client>(CLIENT_MAPPING.values());
        for (Client client : clientsToDestory) {
            if (client != MAIN_CLIENT) {
                client.exit();
            }
        }
        if (MAIN_CLIENT != null) {
            MAIN_CLIENT.restore();
        }
    }

    public Client getDefaultClient() {
        return MAIN_CLIENT;
    }

    public void setDefaultClient(Client client) {
        MAIN_CLIENT = client;
    }
}
