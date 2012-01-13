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

import java.util.HashMap;
import java.util.Map;
import org.tsc.emulation.components.Component;
import org.zkoss.util.logging.Log;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class ComponentWrapperUtil {

    private static final Log _log = Log.lookup(ComponentWrapperUtil.class);
    private static final Map<Class<? extends org.zkoss.zk.ui.Component>, Class<? extends Component>> _customWrapper = new HashMap<Class<? extends org.zkoss.zk.ui.Component>, Class<? extends Component>>();

    public static void registerCustomWrapper(Class<? extends org.zkoss.zk.ui.Component> componentClass, Class<? extends Component> emulatorClass) {
        _customWrapper.put(componentClass, emulatorClass);
    }

    public static void unregisterCustomWrapper(Class<? extends org.zkoss.zk.ui.Component> componentClass) {
        _customWrapper.remove(componentClass);
    }

    private static Component createWrapperInstance(org.zkoss.zk.ui.Component component, Class<? extends Component> emulatorClass) throws Exception {
        return emulatorClass.getConstructor(component.getClass()).newInstance(component);
    }

    private static Class<? extends Component> getWrapperClass(org.zkoss.zk.ui.Component componentToCast) {
        // search for custom inplementation
        if (_customWrapper.containsKey(componentToCast.getClass())) {
            return _customWrapper.get(componentToCast.getClass());
        }

        // get the emulator class if any
        String className = Component.class.getName();
        className = className.substring(0, className.lastIndexOf('.'));
        className += "." + componentToCast.getClass().getSimpleName();

        // try to create the emulator class
        Class<? extends Component> emulatorClass = null;
        try {
            emulatorClass = (Class<? extends Component>) Class.forName(className);
        } catch (ClassNotFoundException ex) {
            _log.debug("no emulator for " + componentToCast.getClass() + ". Will be wrapped as Component");
        }
        return emulatorClass;
    }

    public static Component wrapComponent(org.zkoss.zk.ui.Component component) {
        Class<? extends Component> emulatorClass = getWrapperClass(component);
        if (emulatorClass != null) {
            try {
                return createWrapperInstance(component, emulatorClass);
            } catch (Exception ex) {
                throw new AssertionError(TextUtil.exceptionToString(ex));
            }
        }
        // if no emulator class found wrap the component as default component
        return new Component(component);
    }
}
