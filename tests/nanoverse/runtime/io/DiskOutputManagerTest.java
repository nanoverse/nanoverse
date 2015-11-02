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