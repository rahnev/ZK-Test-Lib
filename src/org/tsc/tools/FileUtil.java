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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
public class FileUtil {

    public static byte[] getFileContentAsByteArray(String fileName) throws FileNotFoundException, IOException {
        RandomAccessFile raf = new RandomAccessFile(fileName, "r");
        Long lengthFile;
        byte[] b;
        raf.seek(0);
        lengthFile = new Long(raf.length());
        b = new byte[lengthFile.intValue()];
        raf.readFully(b);
        raf.close();
        raf = null;
        lengthFile = null;
        return b;
    }

    public static String getFileContent(File f) throws FileNotFoundException, IOException {
        return getFileContent(f.getCanonicalPath());
    }

    public static String getFileContent(String fileName) throws FileNotFoundException, IOException {
        String ret = new String(getFileContentAsByteArray(fileName));
        ret = ret.replace("\r\n", "\n");
        return ret;
    }
}
