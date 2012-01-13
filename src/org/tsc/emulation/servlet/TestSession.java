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

import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import org.tsc.emulation.GuiEnvironment;
import org.zkoss.util.CollectionsX.CollectionEnumeration;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class TestSession implements HttpSession {

    protected HashMap<String, Object> _attributes = new HashMap<String, Object>();
    protected final ServletContext _servletContext;
    protected static int sessionCounter = 0;
    protected final String _sessionName;

    public TestSession() {
        _servletContext = GuiEnvironment.getServletContext();
        _sessionName = "session-" + sessionCounter++;
    }

    @Override
    public long getCreationTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getId() {
        return _sessionName;

    }

    @Override
    public long getLastAccessedTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ServletContext getServletContext() {
        return _servletContext;
    }

    @Override
    public HttpSessionContext getSessionContext() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setMaxInactiveInterval(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getMaxInactiveInterval() {
        return 100;
    }

    @Override
    public Object getAttribute(String string) {
        return _attributes.get(string);
    }

    @Override
    public Enumeration getAttributeNames() {
        return new CollectionEnumeration(_attributes.keySet());
    }

    @Override
    public void setAttribute(String string, Object o) {
        _attributes.put(string, o);
    }

    @Override
    public void removeAttribute(String string) {
        _attributes.remove(string);
    }

    @Override
    public String[] getValueNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getValue(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void putValue(String string, Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeValue(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void invalidate() {
    }

    @Override
    public boolean isNew() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
