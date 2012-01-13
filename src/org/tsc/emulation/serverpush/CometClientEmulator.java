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
package org.tsc.emulation.serverpush;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.junit.Ignore;
import org.tsc.emulation.Client;
import org.tsc.emulation.servlet.TestRequest;
import org.tsc.emulation.servlet.TestUpdateServlet;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
@Ignore
public class CometClientEmulator extends ServerPushClient {

    private static class CommetRequest extends TestRequest {

        private HttpSession _session = null;

        public CommetRequest(Client client) {
            super(client);
            _session = client.getSession();
            getParameterMap().put("dtid", client.getDesktop().getId());
        }

        @Override
        public HttpSession getSession(boolean bln) {
            return _session;
        }

        @Override
        public HttpSession getSession() {
            return _session;
        }

        @Override
        public String getPathInfo() {
            return "/comet";
        }
    }

    protected CometClientEmulator(Client client) {
        super(client);
    }

    @Override
    public void nextIteration() throws ServletException, IOException {
        TestUpdateServlet.get(new CommetRequest(getCleint()), getCleint().createResponse());
    }
}
