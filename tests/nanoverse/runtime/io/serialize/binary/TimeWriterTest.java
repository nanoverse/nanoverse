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

package nanoverse.runtime.io.serialize.binary;

import nanoverse.runtime.processes.MockStepState;
import nanoverse.runtime.structural.MockGeneralParameters;
import nanoverse.runtime.structural.utilities.FileConventions;
import org.junit.Test;
import test.*;

/**
 * Created by dbborens on 3/28/14.
 */
public class TimeWriterTest extends LegacyTest {

    @Test
    public void testLifeCycle() throws Exception {
        // Run through a life cycle, creating output in the process
        generateFile();

        // There should now be an output file to match the fixture named after
        // the time file naming convention
        String filename = FileConventions.TIME_FILENAME;

        // Assert the files are equal
        FileAssertions.assertOutputMatchesFixture(filename, false);
//        assertBinaryFilesEqual(filename);
    }

    private void generateFile() {
        MockGeneralParameters p = makeMockGeneralParameters();
        TimeWriter query = new TimeWriter(p, null);
        query.init();
//        query.step(null, 0.5, 2);
//        query.step(null, 1.3, 4);
        query.flush(new MockStepState(0.5, 2));
        query.flush(new MockStepState(1.3, 4));
        query.dispatchHalt(null);
    }
}
