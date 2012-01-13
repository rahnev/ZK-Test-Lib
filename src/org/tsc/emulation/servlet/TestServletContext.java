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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.zkoss.util.CollectionsX.CollectionEnumeration;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class TestServletContext implements ServletContext {

    protected final String MAIN_PATH;
    protected HashMap<String, Object> attributes = new HashMap<String, Object>();
    protected HashMap<String, String> initParameters = new HashMap<String, String>();

    public TestServletContext(String webPath) {
        MAIN_PATH = new File("../" + webPath).getAbsolutePath();
        /*OFF, ERROR, WARNING, INFO, DEBUG and FINER. If not specified, the system default is used.*/
        initParameters.put("log-level", "FINER");
        initParameters.put("compress", "false");
        initParameters.put("update-uri", "/zkau");

    }

    @Override
    public ServletContext getContext(String string) {
        return this;
    }

    @Override
    public String getContextPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getMajorVersion() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getMinorVersion() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getMimeType(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set getResourcePaths(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public URL getResource(String string) throws MalformedURLException {
        if (string.startsWith("/")) {
            return new URL("file:" + MAIN_PATH + string);
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public InputStream getResourceAsStream(String string) {
        try {
            return new FileInputStream(getResource(string).getFile());
        } catch (Exception ex) {
            return null;
        }

    }

    @Override
    public RequestDispatcher getRequestDispatcher(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RequestDispatcher getNamedDispatcher(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Servlet getServlet(String string) throws ServletException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Enumeration getServlets() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Enumeration getServletNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void log(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void log(Exception excptn, String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void log(String string, Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getRealPath(String string) {
        return MAIN_PATH + string;
    }

    @Override
    public String getServerInfo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getAttribute(String string) {
        return attributes.get(string);
    }

    @Override
    public Enumeration getAttributeNames() {
        return new CollectionEnumeration(attributes.keySet());
    }

    @Override
    public void setAttribute(String string, Object o) {
        attributes.put(string, o);
    }

    @Override
    public void removeAttribute(String string) {
        attributes.remove(string);
    }

    @Override
    public String getServletContextName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getInitParameter(String string) {
        return initParameters.get(string);
    }

    @Override
    public Enumeration getInitParameterNames() {
        return new CollectionEnumeration(initParameters.keySet());
    }
}
