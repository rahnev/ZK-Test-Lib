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

import org.tsc.emulation.exceptions.EmulationException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class ReflectionUtil {

    private static final String mIdent = "$Id: ReflectionUtil.java 1808 2011-03-18 15:44:17Z rahnev $ $HeadURL: https://ontime:8443/svn/iconn/trunk/tsutil/src/ts/util/ReflectionUtil.java $"; //NOPMD

    private static Class[] getClasses(Object[] parameters) {
        Class[] classes = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] != null) {
                classes[i] = parameters[i].getClass();
            }
        }
        return classes;
    }

    public static Object createInstance(Class cls, Object[] parameters, Class[] parameterTypes) throws EmulationException {
        try {
            Constructor constructor = cls.getDeclaredConstructor(parameterTypes);
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance(parameters);
        } catch (Throwable ex) {
            throw new EmulationException(ex);
        }
    }

    public static Object createInstance(Class cls, Object[] parameters) throws EmulationException {
        return createInstance(cls, parameters, getClasses(parameters));
    }

    public static Object createInstance(Class cls) throws EmulationException {
        return createInstance(cls, new Object[]{});
    }

    public static Object executeMethod(Object target, Class cls, String method, Class[] parameterTypes, Object[] parameters) throws EmulationException {
        try {
            Method methodCls = cls.getDeclaredMethod(method, parameterTypes);
            if (!methodCls.isAccessible()) {
                methodCls.setAccessible(true);
            }
            return methodCls.invoke(target, parameters);
        } catch (Throwable ex) {
            throw new EmulationException(ex);
        }
    }

    public static Object executeMethod(Object target, Class cls, String method, Object[] parameters) throws EmulationException {
        return executeMethod(target, cls, method, getClasses(parameters), parameters);
    }

    public static Object executeMethod(Object target, Class cls, String method) throws EmulationException {
        return executeMethod(target, cls, method, new Object[0]);
    }

    public static Object executeMethod(Object target, String method, Object[] parameters) throws EmulationException {
        return executeMethod(target, target.getClass(), method, parameters);
    }

    public static Object executeMethod(Object target, String method) throws EmulationException {
        return executeMethod(target, target.getClass(), method);
    }

    public static Object executeStaticMethod(Class cls, String method) throws EmulationException {
        return executeMethod(null, cls, method);
    }

    public static Object executeStaticMethod(Class cls, String method, Object[] parameters) throws EmulationException {
        return executeMethod(null, cls, method, parameters);
    }

    public static Object executeStaticMethod(Class cls, String method, Class[] parameterTypes, Object[] parameters) throws EmulationException {
        return executeMethod(null, cls, method, parameterTypes, parameters);
    }

    public static void setField(Object target, Class cls, String name, Object value) throws EmulationException {
        try {
            Field field = cls.getDeclaredField(name);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            field.set(target, value);
        } catch (Exception ex) {
            throw new EmulationException(ex);
        }
    }

    public static void setField(Object target, String name, Object value) throws EmulationException {
        setField(target, target.getClass(), name, value);
    }

    public static void setStaticField(Class cls, String name, Object value) throws EmulationException {
        setField(null, cls, name, value);
    }

    public static Object getField(Object target, Class cls, String name) throws EmulationException {
        try {
            Field field = cls.getDeclaredField(name);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return field.get(target);
        } catch (Exception ex) {
            throw new EmulationException(ex);
        }
    }

    public static Object getField(Object target, String name) throws EmulationException {
        return getField(target, target.getClass(), name);
    }

    public static Object getStaticField(Class cls, String name) throws EmulationException {
        return getField(null, cls, name);
    }
}
