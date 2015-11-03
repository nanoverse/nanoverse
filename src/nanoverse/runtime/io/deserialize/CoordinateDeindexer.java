/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.runtime.io.deserialize;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.structural.utilities.FileConventions;

import java.io.*;
import java.util.HashMap;
import java.util.regex.*;

/**
 * Created by dbborens on 12/10/13.
 */
public class CoordinateDeindexer {

    // This file specifies the relationship between vector index and coordinate.

    protected HashMap<Integer, Coordinate> indexToCoord;
    protected HashMap<Coordinate, Integer> coordToIndex;
    private String path;

    /**
     * Default constructor for testing only!
     */
    public CoordinateDeindexer() {
    }

    public CoordinateDeindexer(String path) {

        this.path = path;

        try {
            deindex();
        } catch (FileNotFoundException e) {
            String msg = "Coordinate index file not found in output directory "
                + path + ". Note that any serializers that depend on " +
                "coordinate indices (such as visualizations) need to be " +
                "specified after a <coordinate-indexer> tag in the " +
                "<serializers> section.";

            throw new IllegalArgumentException(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void deindex() throws IOException {
        indexToCoord = new HashMap<>();
        coordToIndex = new HashMap<>();

        String fn = path + '/' + FileConventions.COORDINATE_FILENAME;
        FileReader mfr = new FileReader(fn);
        BufferedReader mbr = new BufferedReader(mfr);
        String next = mbr.readLine();

        while (next != null) {
            // Lines are in the form
            // 		1	(0, 1 | 1)
            // 		2	(0, 2 | 1)
            next = next.trim();
            String[] mapping = next.split("\t");
            Integer key = Integer.valueOf(mapping[0]);

            String token = mapping[1];
            Coordinate c = parseCoordinate(token);

            indexToCoord.put(key, c);
            coordToIndex.put(c, key);

            next = mbr.readLine();

        }

    }

    protected Coordinate parseCoordinate(String token) {
        String pStr = ("\\((\\d+), (\\d+)(, (\\d+))? \\| (\\d+)\\)");

        Coordinate c;
        Pattern pattern = Pattern.compile(pStr);
        Matcher matcher = pattern.matcher(token);
        matcher.find();

        int x = Integer.valueOf(matcher.group(1));
        int y = Integer.valueOf(matcher.group(2));
        int flags = Integer.valueOf(matcher.group(5));

        if (matcher.group(4) != null) {
            // (1, 2, 3 | 4)
            int z = Integer.valueOf(matcher.group(4));
            c = new Coordinate3D(x, y, z, flags);
        } else {
            // (1, 2 | 3)
            c = new Coordinate2D(x, y, flags);
        }

        return c;
    }

    public Coordinate getCoordinate(Integer index) {
        return indexToCoord.get(index);
    }

    public Integer getIndex(Coordinate coord) {
        return coordToIndex.get(coord);
    }

    public int getNumSites() {
        return indexToCoord.size();
    }
}
