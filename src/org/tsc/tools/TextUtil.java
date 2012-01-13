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

import java.util.Iterator;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class TextUtil {

    public static final char[] HEXA_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String getStringBytes(String str) {
        return getStringBytes(str, str.length());
    }

    public static String getStringBytes(String str, int length) {
        byte[] bytes = str.getBytes();
        if (length > str.length()) {
            length = str.length();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(HEXA_CHARS[(0xF0 & bytes[i]) >> 4]).append(HEXA_CHARS[0x0F & bytes[i]]).append(" ");
        }
        return sb.toString();
    }

    public static String join(Object[] s, String delimiter) {
        if (s == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(64);
        for (int i = 0; i < s.length; i++) {
            if (s[i] != null) {
                sb.append(s[i].toString());
            }
            if (i < s.length - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }

    public static String join(Iterable<String> s, String delimiter) {
        StringBuilder buffer = new StringBuilder(64);
        Iterator iter = s.iterator();
        if (iter.hasNext()) {
            buffer.append(nvl(iter.next(), ""));
            while (iter.hasNext()) {
                buffer.append(delimiter);
                buffer.append(nvl(iter.next(), ""));
            }
        }
        return buffer.toString();
    }

    public static String exceptionToString(Throwable ex) {
        return exceptionToString(ex, "");
    }

    public static String stackTraceToString(StackTraceElement[] stack, String tabs) {
        String ret = "";
        for (int i = 0; i < stack.length; i++) {
            StackTraceElement elem = stack[i];
            ret += tabs + "    " + elem.toString() + "\n";
        }
        return ret;
    }

    public static String exceptionToString(Throwable ex, String tabs) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(tabs).append("Exception      : ").append(ex.getClass().getSimpleName()).append("\n");
            sb.append(tabs).append("Thread         : ").append(Thread.currentThread().getName()).append("\n");
            sb.append(tabs).append("Message        : ").append(ex.getMessage()).append("\n");

            StackTraceElement[] stack = ex.getStackTrace();
            sb.append(tabs).append("Stacktrace     : \n").append(stackTraceToString(stack, tabs));
            Throwable cause = ex.getCause();

            if (cause != null && !cause.equals(ex)) {
                sb.append(tabs).append("InnerException : \n").append(exceptionToString(cause, tabs + "    "));
            }
        } catch (Throwable exConv) {
        }
        return sb.toString();
    }

    public static String padRight(String src, int length, char filler) {
        return padRight(src, length, filler, false);
    }

    public static String padLeft(String src, int length, char filler) {
        return padLeft(src, length, filler, false);
    }

    public static String padRight(String src, int length, char filler, boolean nullAsEmpty) {
        if (src == null) {
            if (!nullAsEmpty) {
                return null;
            }
            src = "";
        }
        int repeat = length - src.length();
        if (repeat == 0) {
            return src;
        }
        StringBuilder sb = new StringBuilder(length);
        sb.append(src);
        while (repeat-- > 0) {
            sb.append(filler);
        }
        return sb.toString();
    }

    public static String padLeft(String src, int length, char filler, boolean nullAsEmpty) {
        if (src == null) {
            if (!nullAsEmpty) {
                return null;
            }
            src = "";
        }
        int repeat = length - src.length();
        if (repeat == 0) {
            return src;
        }
        StringBuilder sb = new StringBuilder(length);
        while (repeat-- > 0) {
            sb.append(filler);
        }
        sb.append(src);
        return sb.toString();
    }

    public static String nvl(String value) {
        return nvl(value, "".intern());
    }

    public static String nvl(String value, String replacement) {
        if (value == null) {
            return replacement;
        }

        return value;
    }

    public static String nvl(Object value, String replacement) {
        if (value == null) {
            return replacement;
        }

        return value.toString();
    }

    public static <T extends Number> T nvl(T value, T replacement) {
        if (value == null) {
            return replacement;
        }

        return value;
    }
}
