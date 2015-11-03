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

package test;

import com.google.common.base.Strings;
import org.apache.commons.io.FileUtils;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by dbborens on 7/20/15.
 */
public abstract class FileAssertions {

    protected final static String eslimeRoot = "./";
    protected final static String outputPath = eslimeRoot + "/output/";
    protected final static String fixturePath = eslimeRoot + "/fixtures/";

    public static void assertOutputMatchesFixture(String filename,
                                              boolean plaintext) throws IOException {

        String fixture = fixturePath + filename;
        String output = outputPath + filename;

        doAssertOutputMatchesFixture(fixture, output, plaintext);
    }

    public static void assertOutputMatchesFixture(String fixtureFilename,
                                              String outputFilename,
                                              boolean plaintext) throws IOException {

        String fixture = fixturePath + fixtureFilename;
        String output = outputPath + outputFilename;

        doAssertOutputMatchesFixture(fixture, output, plaintext);
    }

    private static void doAssertOutputMatchesFixture(String fixture,
                                              String output,
                                              boolean plaintext) throws IOException{

        if (plaintext) {
            doPlainTextFileComparison(fixture, output);
        } else {
            doBinaryFileComparison(fixture, output);
        }
    }

    private static void doBinaryFileComparison(String fixture, String output) throws IOException {
        File fixtureFile = new File(fixture);
        File outputFile = new File(output);
        assertTrue(FileUtils.contentEquals(fixtureFile, outputFile));
    }
    private static void doPlainTextFileComparison(String fixture, String output) throws IOException {
        BufferedReader fixtureReader = getReader(fixture);
        BufferedReader outputReader = getReader(fixture);

        while (true) {
            String nextFixtureLine = fixtureReader.readLine();
            String nextOutputLine = outputReader.readLine();
            if (nextFixtureLine == null ^ nextOutputLine == null) {
                fail("Files have unequal length");
            }

            if (nextFixtureLine == null && nextOutputLine == null) {
                return;
            }

            String expected = nextFixtureLine.trim();
            String actual = nextOutputLine.trim();
            assertEquals(expected, actual);
        }
    }

    private static BufferedReader getReader(String filename) throws IOException {
        File file = new File(filename);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        return br;
    }
}
