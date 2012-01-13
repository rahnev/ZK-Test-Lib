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

import java.io.File;
import java.io.InputStreamReader;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.tsc.emulation.GuiEnvironment;
import org.tsc.emulation.Client;
import org.tsc.emulation.exceptions.EmulationException;
import org.tsc.emulation.servlet.TestUpdateServlet;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class ExecutionEmulator {

    public static Component createComponentImpl(String resourceName, Component parent, Map map) throws EmulationException {
        Component ret = createComponentFromFile(".." + GuiEnvironment.WEB_PATH + "/" + resourceName, parent, map);
        if (ret != null) {
            return ret;
        }

        return createComponentFromResource(Thread.currentThread().getContextClassLoader(), resourceName, parent, map);
    }

    public static Component createComponentFromResource(ClassLoader loader, String resourceName, Component parent, Map map) throws EmulationException {
        final Component ret;

        try {
            ret = Executions.createComponentsDirectly(new InputStreamReader(loader.getResourceAsStream(resourceName)), null, parent, map);
        } catch (Throwable ex) {
            throw new EmulationException("Unable to create component from " + resourceName, ex);
        }
        return ret;
    }

    public static Component createComponentFromFile(String filePath, Component parent, Map map) throws EmulationException {

        final Component ret;

        try {
            ret = Executions.createComponentsDirectly(FileUtil.getFileContent(new File(filePath)), null, parent, map);
        } catch (Throwable ex) {
            throw new EmulationException("Unable to create component from " + filePath, ex);
        }
        return ret;
    }

    public static Component createComponent(String uri, Component parent, Map arg) throws EmulationException {
        return createComponent(GuiEnvironment.getDefaultClient(), uri, parent, arg);
    }

    public static Component createComponent(Client client, String uri, Component parent, Map arg) throws EmulationException {
        Client activeClient = TestUpdateServlet.getActiveClient();
        TestUpdateServlet.enter(client);
        try {
            return createComponentImpl(uri, parent, arg);
        } finally {
            TestUpdateServlet.leave(client);
            if (activeClient != null) {
                TestUpdateServlet.enter(client);
            }
        }
    }
}
