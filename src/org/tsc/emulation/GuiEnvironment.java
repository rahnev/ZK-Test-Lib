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

import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import org.tsc.emulation.exceptions.EmulationException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;

/**
 * 
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class GuiEnvironment {

    protected static GuiEnvironmentImpl SINGLETONE;
    public static String WEB_PATH = null;

    public static void create(String webPath) throws ServletException {
        SINGLETONE = new GuiEnvironmentImpl();
        WEB_PATH = webPath;
        if (!WEB_PATH.startsWith("/")) {
            WEB_PATH = "/" + WEB_PATH;
        }
        SINGLETONE.create(webPath);
    }

    public static HttpServletResponse create(String webPath, String page) throws EmulationException {
        try {
            create(webPath);
        } catch (ServletException ex) {
            throw new EmulationException(ex);
        }
        Client mainClient = new Client();
        HttpServletResponse ret = mainClient.create(page);
        SINGLETONE.setDefaultClient(mainClient);
        return ret;
    }

    protected static void register(Client client) {
        SINGLETONE.register(client);
    }

    protected static void unregister(Client client) {
        SINGLETONE.unregister(client);
    }

    public static Client getClient(Desktop desktop) {
        return SINGLETONE.getClient(desktop);
    }

    public static ServletContext getServletContext() {
        return SINGLETONE.getServletContext();
    }

    public static void destroy() throws EmulationException {
        SINGLETONE.destroy();
        SINGLETONE = null;
        WEB_PATH = null;
    }

    public static void restore() throws EmulationException {
        SINGLETONE.restore();
    }

    public static Client getDefaultClient() {
        return SINGLETONE.getDefaultClient();
    }

    public static List<Component> getCreatedComponents() {
        return getDefaultClient().getCreatedComponents();
    }

    public static Component getMainControl() {
        return getDefaultClient().getMainControl();
    }

    public static Component getComponent(Class cls) {
        return getDefaultClient().getComponent(cls);
    }

    public static Map getAllDownloads() throws EmulationException {
        return getDefaultClient().getAllDownloads();
    }
}
