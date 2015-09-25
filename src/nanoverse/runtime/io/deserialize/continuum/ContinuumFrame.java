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

package nanoverse.runtime.io.deserialize.continuum;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by dbborens on 5/29/2015.
 */
public class ContinuumFrame {

    private final List<Double> value;
    private final int frame;
    private final double time;

    public ContinuumFrame(List<Double> value, int frame, double time) {
        this.value = value;
        this.frame = frame;
        this.time = time;
    }

    public Stream<Double> getValue() {
        return value.stream();
    }

    public int getFrameNumber() {
        return frame;
    }

    public double getTime() {
        return time;
    }
}
