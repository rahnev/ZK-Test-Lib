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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.tsc.emulation.Client;
import org.tsc.emulation.exceptions.EmulationException;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class TestLayoutServlet extends DHtmlLayoutServlet {

    protected static TestLayoutServlet SINGLETONE = null;

    protected Desktop doCreateDesktop(Client client, HttpServletRequest request, HttpServletResponse response) throws EmulationException {
        TestUpdateServlet.free();
        try {
            doGet(request, response);
        } catch (Exception ex) {
            throw new EmulationException("Unable to create new desktop", ex);
        }

        Desktop ret = (Desktop) request.getAttribute("javax.zkoss.zk.ui.desktop");
        return ret;
    }

    public static boolean isCreated() {
        return SINGLETONE != null;
    }

    public static void create(ServletConfig config) throws ServletException {
        SINGLETONE = new TestLayoutServlet();
        SINGLETONE.init(config);
    }

    public static Desktop createDesktop(Client client, HttpServletRequest request, HttpServletResponse response) throws EmulationException {
        return SINGLETONE.doCreateDesktop(client, request, response);
    }

    public static void destroyServlet() {
        SINGLETONE.destroy();
        SINGLETONE = null;
    }
}
