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

package nanoverse.runtime.io;

import nanoverse.runtime.io.serialize.binary.BinaryOutputHandle;
import nanoverse.runtime.io.serialize.text.TextOutputHandle;
import org.junit.*;
import test.TestBase;

public class DiskOutputManagerTest extends TestBase {

    // These classes create file handling objects and therefore are most
    // sensibly implemented as integration tests. The alternative is
    // to mock every single line of DiskOutputManager.

    private static final String FIXTURE_PATH = "./fixtures/";
    private static final String OUTPUT_PATH = "./output/";

    private DiskOutputManager query;

    @Before
    public void before() throws Exception {
        query = new DiskOutputManager();
    }

    @Test
    public void binaryHandleIntegrationTest() throws Exception {
        String base = "DiskManagerTest.bin";
        String actualFn = OUTPUT_PATH + base;
        createBinaryOutputFile(actualFn);

        String expectedFn = FIXTURE_PATH + base;
        assertFilesEqual(actualFn, expectedFn);
    }

    private void createBinaryOutputFile(String filename) {
        BinaryOutputHandle bn = query.getBinaryHandle(filename);
        bn.writeInt(42);
        bn.close();
    }

    @Test
    public void textHandleIntegrationTest() throws Exception {
        String base = "DiskManagerTest.txt";
        String actualFn = OUTPUT_PATH + base;
        createTextOutputFile(actualFn);

        String expectedFn = FIXTURE_PATH + base;
        assertFilesEqual(actualFn, expectedFn);
    }

    private void createTextOutputFile(String filename) {
        TextOutputHandle bn = query.getTextHandle(filename);
        bn.write("test");
        bn.close();
    }
}