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

package nanoverse.runtime.structural.utilities;

import java.io.*;

/**
 * Created by dbborens on 3/26/14.
 */
public abstract class FileConventions {
    public final static String COORDINATE_FILENAME = "coordinates.txt";

    public final static String CONTINUUM_STATE_SUFFIX = ".name.bin";
    public final static String CONTINUUM_METADATA_SUFFIX = ".extrema.bin";
    public final static String CONTINUUM_OVERVIEW_FILENAME = "solute.overview.txt";

    public final static String HIGHLIGHT_PREFIX = "channel";
    public final static String HIGHLIGHT_SUFFIX = ".highlight.bin";

    public static final String TIME_FILENAME = "clock.bin";

    public static final String INTERFACE_PREFIX = "interface_";
    public static final String INTERFACE_SUFFIX = ".txt";

    public static String makeContinuumStateFilename(String soluteId) {
        StringBuilder sb = new StringBuilder();
        sb.append(soluteId);
        sb.append(CONTINUUM_STATE_SUFFIX);
        return sb.toString();
    }

    public static String makeInterfaceFilename(Integer focalState) {
        StringBuilder sb = new StringBuilder();
        sb.append(INTERFACE_PREFIX);
        sb.append(focalState);
        sb.append(INTERFACE_SUFFIX);
        return sb.toString();
    }

    public static String makeContinuumMetadataFilename(String soluteId) {
        StringBuilder sb = new StringBuilder();
        sb.append(soluteId);
        sb.append(CONTINUUM_METADATA_SUFFIX);
        return sb.toString();
    }

    public static DataInputStream makeDataInputStream(String fullPath) {
        try {
            return doMakeDataInputSteam(fullPath);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static DataInputStream doMakeDataInputSteam(String fullPath)
        throws IOException {

        File file = new File(fullPath);
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);

        return dataInputStream;
    }

    public static String makeHighlightFilename(int channel) {
        StringBuilder sb = new StringBuilder();
        sb.append(HIGHLIGHT_PREFIX);
        sb.append(channel);
        sb.append(HIGHLIGHT_SUFFIX);
        return sb.toString();
    }

    public static DataOutputStream makeDataOutputStream(String fullPath) {
        try {
            return doMakeDataOutputStream(fullPath);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static DataOutputStream doMakeDataOutputStream(String fullPath) throws IOException {
        File file = new File(fullPath);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);

        return dataOutputStream;
    }
}
