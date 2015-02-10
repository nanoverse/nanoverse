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

package structural;

import junit.framework.TestCase;
import structural.utilities.FileConventions;

/**
 * Created by dbborens on 3/26/14.
 */
public class FileConventionsTest extends TestCase {
    public void testMakeContinuumStateFilename() throws Exception {
        String soluteId = "Test";
        String expected = "soluteTest.state.bin";
        String actual = FileConventions.makeContinuumStateFilename(soluteId);
        assertEquals(expected, actual);
    }

    public void testGetMetadataFilename() throws Exception {
        String soluteId = "Test";
        String expected = "soluteTest.metadata.txt";
        String actual = FileConventions.makeContinuumMetadataFilename(soluteId);
        assertEquals(expected, actual);
    }

    public void testMakeHighlightFilename() throws Exception {
        int channel = 0;
        String expected = "channel0.highlight.bin";
        String actual = FileConventions.makeHighlightFilename(channel);
        assertEquals(expected, actual);
    }

    public void testMakeInterfaceFilename() throws Exception {
        int state = 0;
        String expected = "interface_0.txt";
        String actual = FileConventions.makeInterfaceFilename(state);
        assertEquals(expected, actual);
    }
}
