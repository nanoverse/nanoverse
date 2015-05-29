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

package io.deserialize.continuum;

import control.identifiers.*;

import java.io.*;
import java.util.HashMap;
import java.util.regex.*;

/**
 * Created by dbborens on 5/27/2015.
 */
public abstract class ExtremaInstanceReader {
    public static Extrema get(File metadataFile) throws IOException {
        FileReader mfr = new FileReader(metadataFile);
        BufferedReader mbr = new BufferedReader(mfr);
        String next = mbr.readLine().trim();

        String[] mapping = next.split(">");
        String value = mapping[1];

        Extrema extrema = loadExtrema(value);
        return extrema;
    }

    private static Extrema loadExtrema(String tokenize) {
        String[] minMax = tokenize.split(":");
        String[] minArg = minMax[0].split("@");
        String[] maxArg = minMax[1].split("@");

        Double min = Double.valueOf(minArg[0]);
        Double max = Double.valueOf(maxArg[0]);

        String minToken = minArg[1];
        String maxToken = maxArg[1];

        TemporalCoordinate argMin = parseTemporalCoordinate(minToken);
        TemporalCoordinate argMax = parseTemporalCoordinate(maxToken);

        Extrema ret = new Extrema();
        ret.load(min, argMin, max, argMax);

        return ret;
    }

    private static TemporalCoordinate parseTemporalCoordinate(String token) {
        String pStr = ("\\((\\d+), (\\d+)(, (\\d+))? \\| (\\d+) \\| (\\d+\\.\\d+)\\)");
        TemporalCoordinate c;
        Pattern pattern = Pattern.compile(pStr);
        Matcher matcher = pattern.matcher(token);
        matcher.find();

        int x = Integer.valueOf(matcher.group(1));
        int y = Integer.valueOf(matcher.group(2));
        int flags = Integer.valueOf(matcher.group(5));
        double t = Double.valueOf(matcher.group(6));

        if (matcher.group(4) != null) {
            // (1, 2, 3 | 4 | 5.0)
            int z = Integer.valueOf(matcher.group(4));
            c = new TemporalCoordinate(x, y, z, t, flags);
        } else {
            // (1, 2 | 3 | 4.0)
            c = new TemporalCoordinate(x, y, t, flags);
        }

        return c;
    }

}
