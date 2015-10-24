package nanoverse.runtime.io;

import nanoverse.runtime.io.deserialize.*;
import nanoverse.runtime.io.serialize.binary.BinaryOutputHandle;
import nanoverse.runtime.io.serialize.text.TextOutputHandle;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DiskInputManagerTest {

    // These classes create file handling objects and therefore are most
    // sensibly implemented as integration tests. The alternative is
    // to mock most lines of DiskInputManager.

    private static final String FIXTURE_PATH = "./fixtures/";

    private DiskInputManager query;

    @Before
    public void before() throws Exception {
        query = new DiskInputManager();
    }

    @Test
    public void binaryHandleIntegrationTest() throws Exception {
        String filename = "DiskManagerTest.bin";
        BinaryInputHandle bn = query.doMakeBinaryReader(FIXTURE_PATH, filename);
        int actual = bn.readInt();
        assertEquals(42, actual);
        bn.close();
    }

    @Test
    public void textHandleIntegrationTest() throws Exception {
        String filename = "DiskManagerTest.txt";
        TextInputHandle bn = query.doMakeTextReader(FIXTURE_PATH, filename);
        String actual = bn.readLine();
        assertEquals("test", actual);
        bn.close();
    }
}