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

package io.visual.legacy;

import control.GeneralParameters;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Deprecated
public class LegacyColorManager {

    private Map<Integer, Color> colorMap = new HashMap<Integer, Color>();

    private Random random;

    public LegacyColorManager(GeneralParameters p) {
        random = p.getRandom();
    }

    public Color basicColor(int state) {

        if (!colorMap.containsKey(state)) {
            makeColor(state);
        }

        return colorMap.get(state);

    }

    private void makeColor(int state) {
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();

        Color c = new Color(r, g, b);

        colorMap.put(state, c);
    }

    public Color highlightColor(int state) {
        Color basic = basicColor(state);

        int r0 = basic.getRed();
        int g0 = basic.getGreen();
        int b0 = basic.getBlue();

        int r1 = ((255 - r0) * 3 / 4) + r0;
        int g1 = ((255 - g0) * 3 / 4) + g0;
        int b1 = ((255 - b0) * 3 / 4) + b0;

        Color highlight = new Color(r1, g1, b1);

        return highlight;
    }
}
