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
package org.tsc.tools.tree;

import org.tsc.emulation.componentutils.TreeUtil;
import org.tsc.emulation.exceptions.EmulationException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Georgi Rahnev (http://github.com/rahnev)
 * @version $Id$
 */
@org.junit.Ignore
public class DumpNodeReader {

    private static final byte[] utf8Marker = new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
    private static final byte[] utfMarkerAsciiReaderMarker = new byte[]{(byte) 0x3F, (byte) 0x3F, (byte) 0x3F};
    private ArrayList<DumpNode> _nodes = new ArrayList<DumpNode>();
    private int _position = 0;
    private String _dumpSource = null;
    private String[] _header = null;

    public void load(String dumpSource, boolean containsHeader) throws FileNotFoundException, IOException, EmulationException {
        _dumpSource = dumpSource;
        FileReader fr = new FileReader(dumpSource);

        BufferedReader br = new BufferedReader(new FileReader(dumpSource));
        String line = br.readLine();
        if (line == null) {
            return; // empty file
        }
        //System.out.println(dumpSource + " encoding : " + fr.getEncoding() + " bytes :" + TextUtil.getStringBytes(line, 10) + " ...");

        byte[] bytes = line.getBytes();


        //System.out.println(getStringBytes(line));
        byte[] fileMarker = Arrays.copyOf(bytes, 3);
        if (Arrays.equals(fileMarker, utf8Marker) || Arrays.equals(fileMarker, utfMarkerAsciiReaderMarker)) {
            line = new String(Arrays.copyOfRange(bytes, 3, bytes.length));
        }

        if (line != null && containsHeader) {
            _header = line.split(TreeUtil.SPLITTER);
            line = br.readLine();
        }

        int lastLevel = 0;
        int row = 0;
        while (line != null) {
            row++;
            int counter = 0;
            int level = 1;
            while (!line.isEmpty() && line.charAt(counter++) == ' ') {
                if (counter == 4) {
                    counter = 0;
                    line = line.substring(4);
                    level++;
                }
                if (line.length() == counter) {
                    line = "";
                    break;
                }
            }

            if (!line.isEmpty()) {
                int diff = level - lastLevel;
                if (diff > 1) {
                    throw new EmulationException("can't assign info at line" + row + " to the previows node. There more than one levels differense (one level is 4 spaces). Source file " + dumpSource + " Row: " + row);
                }
                lastLevel = level;
                _nodes.add(createNode(line, level, row));
            }
            line = br.readLine();
        }
    }

    protected DumpNode createNode(String line, int level, int row) {
        return new DumpNode(line, level, row, this);
    }

    public String getFileName() {
        return _dumpSource;
    }

    public boolean hasNext() {
        return _position < _nodes.size();
    }

    public DumpNode next() {
        return _nodes.get(_position++);
    }

    public void goBack() {
        _position--;
    }

    public int rootNodesSize() {
        return _nodes.size();
    }

    public String[] getHeader() {
        return _header;
    }
}
