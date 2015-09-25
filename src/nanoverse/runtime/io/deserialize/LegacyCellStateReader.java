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

package nanoverse.runtime.io.deserialize;


import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.LightweightSystemState;

import java.io.*;
import java.util.HashSet;

@Deprecated
public class LegacyCellStateReader {


    private static final String DATA_FILENAME = "data.txt";
    private static final String METADATA_FILENAME = "metadata.txt";

    private BufferedReader br;

    // The CellStateReader always reads one line past the current state, so
    // we hold onto that line for the next call to getNext().
    private String prevLine = null;

    private double gillespie;

    private CoordinateDeindexer deindexer;

    private int frame;

    // The simulation being visualized may be a single instance of a multi-
    // replicate project. The CellStateReader is currently agnostic to that fact,
    // so we just pass in a path.
    private String path;

    public LegacyCellStateReader(String path, Geometry geom) {
        this.path = path;

        deindexer = new CoordinateDeindexer(path);
        initFile(path);
    }

    private void initFile(String path) {
        File dataFile = new File(path + '/' + DATA_FILENAME);

        try {

            // Initialize read-through
            FileReader fr = new FileReader(dataFile);

            br = new BufferedReader(fr);

            String untrimmed = br.readLine();

            if (untrimmed == null) {
                throw new IOException("Empty data file " + dataFile.getAbsolutePath());
            }

            prevLine = untrimmed.trim();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public LegacyCellStateReader(String path, CoordinateDeindexer deindexer) {
        this.deindexer = deindexer;
        this.path = path;

        initFile(path);
    }

    public void close() {
        try {
            br.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void populate(LightweightSystemState state) {
        ConditionViewer viewer = next();
//        double[] healthVector = viewer.getHealthVector();
        int[] stateVector = viewer.getStateVector();
        state.initCellLayer(stateVector);
    }

//    private Extrema getHealthExtremes() {
//        File metadataFile = new File(path + '/' + METADATA_FILENAME);
//        ExtremaReader reader = new ExtremaReader(metadataFile);
//        Extrema ret = reader.get("health");
//        return ret;
//    }

    public ConditionViewer next() {
        try {
            // If prevLine is null, we're at the end of the file
            if (prevLine == null)
                return null;

            if (prevLine.startsWith(">")) {
                String[] tokens = prevLine.split(":");
                frame = Integer.valueOf(tokens[1]);
                gillespie = Double.valueOf(tokens[2]);

                return readConditions();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private ConditionViewer readConditions() throws IOException {
        int fCurrent;

//        VectorViewer f = null;        // Health

//        Extrema ef = getHealthExtremes();
        int[] states = new int[deindexer.getNumSites()];
        HashSet<Coordinate> highlights = new HashSet<Coordinate>();
        while (prevLine != null) {
            // We come into this loop with a header line in prevLine
            String[] tokens = prevLine.split(">")[1].split(":");

            // When we reach the next time step, stop reading
            fCurrent = Integer.valueOf(tokens[1]);

            if (frame != fCurrent)
                break;

            if (tokens[0].equals("health")) {
                readVector();
            } else if (tokens[0].equals("state")) {
                states = readStates();
            } else if (tokens[0].equals("highlight"))
                readHighlights(highlights);
            else {
                throw new IOException("Unrecognized field " + tokens[0]);
            }
        }
        return new ConditionViewer(states, highlights, frame, gillespie, deindexer);

//        return new ConditionViewer(f, states, highlights, frame, gillespie, deindexer);
    }

    private void readHighlights(HashSet<Coordinate> highlights) throws IOException {

        prevLine = br.readLine();

        while (prevLine != null && !(prevLine.startsWith(">"))) {

            String[] valueTokens = prevLine.trim().split("\t");

            for (int j = 0; j < valueTokens.length; j++) {
                int index = Integer.valueOf(valueTokens[j]);
                Coordinate coord = deindexer.getCoordinate(index);
                highlights.add(coord);
            }

            prevLine = br.readLine();
        }
    }

    private void readVector() throws IOException {
//    private VectorViewer readVector() throws IOException {
        prevLine = br.readLine();

        // Line counter. Should reach p.H().
        int i = 0;

        // Iterate until we hit the end of the file or the start of a new data field
        while (prevLine != null && !(prevLine.startsWith(">"))) {
//            String[] tokens = prevLine.trim().split("\t");

//            // TODO: Replace this logic with something that goes until it gets an EOL character.
//            for (int j = 0; j < tokens.length; j++) {
//                double x = Double.valueOf(tokens[j]);
//                v.set(i, x);
//                i++;
//            }
            prevLine = br.readLine();
        }

//        return new VectorViewer(v, ex.min(), ex.max());
    }

    /**
     * Skip ahead to the next field or end of file
     */
    private int[] readStates() throws IOException {
        int[] states = new int[deindexer.getNumSites()];

        prevLine = br.readLine();

        // Line counter. Should reach p.H().
        int i = 0;

        while (prevLine != null && !(prevLine.startsWith(">"))) {

            String[] valueTokens = prevLine.trim().split("\t");

            for (int j = 0; j < valueTokens.length; j++) {
                states[i] = Integer.valueOf(valueTokens[j]);
                i++;
            }

            prevLine = br.readLine();
        }

        return states;
    }
}
