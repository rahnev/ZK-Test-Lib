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
package org.tsc.emulation.components;

import org.tsc.emulation.GuiEnvironment;
import org.tsc.emulation.Client;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.impl.MessageboxDlg;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class MessageBoxEmulator extends Window<MessageboxDlg> {

    private static final String mIdent = "$Id: MessageBoxEmulator.java 2007 2011-05-19 15:14:54Z gerov $ $HeadURL: https://ontime:8443/svn/iconn/trunk/IConnEmulation/src/iconn/emulation/components/MessageBoxEmulator.java $"; //NOPMD
    Button _btnOK = null;
    Button _btnABORT = null;
    Button _btnCANCEL = null;
    Button _btnIGNORE = null;
    Button _btnRETRY = null;
    Button _btnYES = null;
    Button _btnNO = null;

    public MessageBoxEmulator(MessageboxDlg instance) {
        super(instance);
    }

    public String getMessage() {
        return ((org.zkoss.zul.Label) ((org.zkoss.zk.ui.Component) INSTANCE.getFirstChild().getChildren().get(1)).getFirstChild()).getValue();
    }

    public String getTitle() {
        return INSTANCE.getTitle();
    }

    public Button getOK() {
        if (_btnOK == null) {
            _btnOK = new Button(INSTANCE, "btn" + Messagebox.OK);
        }
        return _btnOK;
    }

    public Button getABORT() {
        if (_btnABORT == null) {
            _btnABORT = new Button(INSTANCE, "btn" + Messagebox.ABORT);
        }
        return _btnABORT;
    }

    public Button getCANCEL() {
        if (_btnCANCEL == null) {
            _btnCANCEL = new Button(INSTANCE, "btn" + Messagebox.CANCEL);
        }
        return _btnCANCEL;
    }

    public Button getIGNORE() {
        if (_btnIGNORE == null) {
            _btnIGNORE = new Button(INSTANCE, "btn" + Messagebox.IGNORE);
        }
        return _btnIGNORE;
    }

    public Button getRETRY() {
        if (_btnRETRY == null) {
            _btnRETRY = new Button(INSTANCE, "btn" + Messagebox.RETRY);
        }
        return _btnRETRY;
    }

    public Button getYES() {
        if (_btnYES == null) {
            _btnYES = new Button(INSTANCE, "btn" + Messagebox.YES);
        }
        return _btnYES;
    }

    public Button getNO() {
        if (_btnNO == null) {
            _btnNO = new Button(INSTANCE, "btn" + Messagebox.NO);
        }
        return _btnNO;
    }

    public static MessageBoxEmulator get() {
        return get(GuiEnvironment.getDefaultClient());
    }

    public static MessageBoxEmulator get(Client client) {
        return new MessageBoxEmulator((MessageboxDlg) client.getComponent(MessageboxDlg.class));
    }
}
