/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein and the
 * Trustees of Princeton University.
 *
 * This file is part of the Nanoverse simulation framework
 * (patent pending).
 *
 * This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General
 * Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package nanoverse.runtime.io.serialize.binary.csw;

import nanoverse.runtime.structural.utilities.FileConventions;

import java.io.*;
import java.util.stream.Stream;

/**
 * Created by dbborens on 5/27/2015.
 */
public abstract class CSWOverviewWriter {

    public static void writeOverview(String instancePath, Stream<String> ids) {
        BufferedWriter out = makeWriter(instancePath);
        ids.forEach(id -> {
            try {
                out.write(id);
                out.write("\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static BufferedWriter makeWriter(String instancePath) {
        String filename = FileConventions.CONTINUUM_OVERVIEW_FILENAME;
        String filepath = instancePath + '/' + filename;
        try {
            File stateFile = new File(filepath);
            FileWriter fw = new FileWriter(stateFile);
            BufferedWriter bw = new BufferedWriter(fw);
            return bw;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
