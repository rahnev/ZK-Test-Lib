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

import org.tsc.emulation.exceptions.EmulationException;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class Checkbox extends Component<org.zkoss.zul.Checkbox> {

    public Checkbox(org.zkoss.zk.ui.Component parent, String id) {
        super(parent, id);
    }

    public Checkbox(org.zkoss.zul.Checkbox instance) {
        super(instance);
    }

    public boolean isChecked() {
        return INSTANCE.isChecked();
    }

    public void check() throws EmulationException {
        if (INSTANCE.isDisabled() || !INSTANCE.isVisible()) {
            fail("can not check an disabled or invisible checkbox");
        }
        if (INSTANCE.isChecked()) {
            return;
        }
        doCheck(true);
    }

    public void uncheck() throws EmulationException {
        if (INSTANCE.isDisabled() || !INSTANCE.isVisible()) {
            fail("can not uncheck an disabled or invisible checkbox");
        }
        if (!INSTANCE.isChecked()) {
            return;
        }
        doCheck(false);
    }

    public Integer getValue() {
        return INSTANCE.isChecked() ? 1 : 0;
    }

    public void setValue(Integer value) throws EmulationException {
        setValue(value);
        if (value == null || value == 0) {
            uncheck();
        } else {
            check();
        }
    }
}
