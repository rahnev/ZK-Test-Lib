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

import org.tsc.emulation.Client;
import org.tsc.emulation.exceptions.EmulationException;
import org.tsc.emulation.servlet.TestUpdateServlet;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.impl.DesktopImpl;
import org.zkoss.zk.ui.impl.PollingServerPush;
import org.zkoss.zk.ui.sys.ServerPush;
import org.zkoss.zk.ui.util.DesktopCleanup;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public abstract class ServerPushClient extends Thread {

    private static final Log _log = Log.lookup(ServerPushClient.class);
    private final Client _client;
    private boolean _exit = false;

    public abstract void nextIteration() throws Exception;

    protected ServerPushClient(Client cleint) {
        super();
        this.setName(this.getClass().getSimpleName() + " for desktop " + cleint.getDesktop().getId());
        _client = cleint;
        _client.getDesktop().addListener(new DesktopCleanup() {

            @Override
            public void cleanup(Desktop desktop) throws Exception {
                exit();
            }
        });
    }

    public Client getCleint() {
        return _client;
    }

    public void enter() {
        start();
    }

    public void exit() {
        _exit = true;
    }

    @Override
    public void run() {
        while (!_exit) {
            try {
                nextIteration();
            } catch (Exception ex) {
                _log.error(ex);
                exit();
            }
        }
    }

    public static ServerPushClient create(Client client) throws EmulationException {
        Client activeClient = TestUpdateServlet.getActiveClient();

        TestUpdateServlet.enter(client);
        ServerPush serverPush = null;
        if (!client.getDesktop().isServerPushEnabled()) {
            client.getDesktop().enableServerPush(true);
            serverPush = ((DesktopImpl) client.getDesktop()).getServerPush();
            client.getDesktop().enableServerPush(false);
        } else {
            serverPush = ((DesktopImpl) client.getDesktop()).getServerPush();
        }
        if (serverPush == null) {
            return new NoServerPushClientEmulator(client);
        }
        if (serverPush.getClass().getName().equals("org.zkoss.zkmax.ui.comet.CometServerPush")) {
            return new CometClientEmulator(client);
        }
        if (serverPush instanceof PollingServerPush) {
            return new PollingClientEmulator(client, 500);
        }

        if (activeClient != null) {
            TestUpdateServlet.leave(activeClient);
        }
        throw new AssertionError("Unsupported server push model " + serverPush.getClass().getName());
    }
}
