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

package io.serialize.binary;

import control.identifiers.Coordinate;
import processes.MockStepState;
import structural.MockGeneralParameters;
import structural.utilities.FileConventions;
import test.EslimeLatticeTestCase;

/**
 * Created by dbborens on 3/28/14.
 */
public class HighlightWriterTest extends EslimeLatticeTestCase {


    private int[] channels = new int[]{0, 7};

    public void testLifeCycle() throws Exception {
        runLifeCycle();
        checkFiles();
    }

    private void checkFiles() throws Exception {
        for (int channel : channels) {
            String filename = FileConventions.makeHighlightFilename(channel);
            assertBinaryFilesEqual(filename);
        }
    }

    private void runLifeCycle() {
        MockGeneralParameters p = makeMockGeneralParameters();
        HighlightWriter query = new HighlightWriter(p, channels, layerManager);
        query.init();
        MockStepState stepState = new MockStepState(0.1, 2);
        stepState.setHighlights(0, new Coordinate[]{x, y});
        stepState.setHighlights(7, new Coordinate[]{origin});
        stepState.record(cellLayer);
        query.flush(stepState);
        query.dispatchHalt(null);
    }

}
