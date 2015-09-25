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

package nanoverse.runtime.structural;

import nanoverse.runtime.structural.utilities.FileConventions;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by dbborens on 3/26/14.
 */
public class FileConventionsTest {

    @Test
    public void testMakeContinuumStateFilename() throws Exception {
        String soluteId = "Test";
        String expected = "Test.state.bin";
        String actual = FileConventions.makeContinuumStateFilename(soluteId);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetMetadataFilename() throws Exception {
        String soluteId = "Test";
        String expected = "Test.extrema.bin";
        String actual = FileConventions.makeContinuumMetadataFilename(soluteId);
        assertEquals(expected, actual);
    }

    @Test
    public void testMakeHighlightFilename() throws Exception {
        int channel = 0;
        String expected = "channel0.highlight.bin";
        String actual = FileConventions.makeHighlightFilename(channel);
        assertEquals(expected, actual);
    }

    @Test
    public void testMakeInterfaceFilename() throws Exception {
        int state = 0;
        String expected = "interface_0.txt";
        String actual = FileConventions.makeInterfaceFilename(state);
        assertEquals(expected, actual);
    }
}
