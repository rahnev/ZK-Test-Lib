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

import java.util.ArrayList;
import java.util.List;
import org.tsc.emulation.exceptions.EmulationException;
import org.tsc.tools.EventExecution;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.tsc.tools.ComponentWrapperUtil;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class Component<T extends org.zkoss.zk.ui.Component> {

    private static final String mIdent = "$Id: Component.java 2548 2011-11-15 16:47:11Z rahnev $ $HeadURL: https://ontime:8443/svn/iconn/trunk/IConnEmulation/src/iconn/emulation/components/Component.java $"; //NOPMD
    private static final Log _log = Log.lookup(Component.class);
    public T INSTANCE = null;

    public Component(org.zkoss.zk.ui.Component parent, String id) {
        this((T) parent.getFellow(id));
    }

    public Component(T instance) {
        if (instance == null) {
            fail("can not create wrapper of a null instance");
        }
        INSTANCE = instance;
        if (instance.getPage() == null) {
            instance.setPage(Executions.getCurrent().getDesktop().getFirstPage());
        }
    }

    public T getComponent() {
        return INSTANCE;
    }

    protected void fail(String message) {
        throw new AssertionError(message == null ? "" : message);
    }

    protected HttpServletResponse doSelect(org.zkoss.zk.ui.Component[] selectedItems, org.zkoss.zk.ui.Component ref) throws EmulationException {
        return doSelect(INSTANCE, selectedItems, ref);
    }

    protected HttpServletResponse doSelect(org.zkoss.zk.ui.Component instance, org.zkoss.zk.ui.Component[] selectedItems, org.zkoss.zk.ui.Component ref) throws EmulationException {
        return EventExecution.executeSelect(instance, selectedItems, ref);
    }

    protected HttpServletResponse doCreate(Map args) throws EmulationException {
        return EventExecution.executeCreate(INSTANCE, args);
    }

    protected HttpServletResponse doClick() throws EmulationException {
        return EventExecution.executeClick(INSTANCE);
    }

    protected HttpServletResponse doDoubleClick() throws EmulationException {
        return EventExecution.executeDoubleClick(INSTANCE);
    }

    protected HttpServletResponse doRightClick() throws EmulationException {
        return EventExecution.executeRightClick(INSTANCE);
    }

    protected HttpServletResponse doFocus() throws EmulationException {
        return EventExecution.executeFocus(INSTANCE);
    }

    protected HttpServletResponse doBlur() throws EmulationException {
        return EventExecution.executeBlur(INSTANCE);
    }

    protected HttpServletResponse doSort() throws EmulationException {
        return EventExecution.executeSort(INSTANCE);
    }

    protected HttpServletResponse doCheck(boolean checked, org.zkoss.zk.ui.Component target) throws EmulationException {
        return EventExecution.executeCheck(target, checked, target);
    }

    protected HttpServletResponse doCheck(boolean checked) throws EmulationException {
        return EventExecution.executeCheck(INSTANCE, checked);
    }

    protected HttpServletResponse doClose() throws EmulationException {
        return EventExecution.executeClose(INSTANCE);
    }

    protected HttpServletResponse doClose(Object data) throws EmulationException {
        return EventExecution.executeClose(INSTANCE, data);
    }

    protected HttpServletResponse doOpen(boolean open) throws EmulationException {
        return EventExecution.executeOpen(INSTANCE, open);
    }

    protected HttpServletResponse doChange(String value) throws EmulationException {
        return EventExecution.executeChange(INSTANCE, value);
    }

    protected HttpServletResponse doChanging(String value) throws EmulationException {
        return EventExecution.executeChanging(INSTANCE, value);
    }

    public List<Component> getChildren() {
        List<Component> children = new ArrayList<Component>();
        for (Object child : INSTANCE.getChildren()) {
            if (child instanceof org.zkoss.zk.ui.Component) {
                Component emulator = ComponentWrapperUtil.wrapComponent((org.zkoss.zk.ui.Component) child);
                children.add(emulator);
            }
        }
        return children;
    }

    public Component getFellow(String id) {
        org.zkoss.zk.ui.Component component = INSTANCE.getFellow(id);
        if (component == null) {
            return null;
        }
        return ComponentWrapperUtil.wrapComponent(component);
    }

    public boolean isVisible() {
        return INSTANCE.isVisible();
    }
}
