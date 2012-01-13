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
import org.zkoss.zul.impl.InputElement;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class InputComponent<T extends InputElement> extends Component<T> {

    private static final String mIdent = "$Id: InputComponent.java 2276 2011-08-04 08:53:04Z rahnev $ $HeadURL: https://ontime:8443/svn/iconn/trunk/IConnEmulation/src/iconn/emulation/components/InputComponent.java $"; //NOPMD

    public InputComponent(org.zkoss.zk.ui.Component parent, String id) {
        super(parent, id);
    }

    public InputComponent(T instance) {
        super(instance);
    }

    public Object getRawValue() {
        return INSTANCE.getRawValue();
    }

    public String getRawText() {
        return INSTANCE.getRawText();
    }

    protected void doSetValue(Object value) throws EmulationException {
        if (!isEditable()) {
            fail(getClass().getSimpleName() + " value can not be changed. It's readonly or disabled!");
        }
        String stringValue = String.valueOf(value);
        doChanging(stringValue);
        doChange(stringValue);
    }

    public boolean isValid() {
        return INSTANCE.isValid();
    }

    public boolean isReadonly() {
        return INSTANCE.isReadonly();
    }

    public boolean isDisabled() {
        return INSTANCE.isDisabled();
    }

    public boolean isEditable() {
        return !(isDisabled() || isReadonly()) && isVisible();
    }
}
