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
public class Longbox extends InputComponent<org.zkoss.zul.Longbox> {

    public Longbox(org.zkoss.zk.ui.Component parent, String id) {
        super(parent, id);
    }

    public Longbox(org.zkoss.zul.Longbox instance) {
        super(instance);
    }

    public Long getValue() {
        return INSTANCE.getValue();
    }

    public void setValue(Long value) throws EmulationException {
        doSetValue(value);
    }
}