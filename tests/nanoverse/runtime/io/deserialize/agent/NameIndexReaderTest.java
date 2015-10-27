package nanoverse.runtime.io.deserialize.agent;

import nanoverse.runtime.io.deserialize.TextInputHandle;
import org.junit.Before;
import org.junit.Test;
import test.TestBase;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/26/2015.
 */
public class NameIndexReaderTest extends TestBase {

    private TextInputHandle indexFile;
    private NameIndexReader query;

    @Before
    public void before() throws Exception {
        indexFile = mock(TextInputHandle.class);
        query = new NameIndexReader();
    }

    @Test
    public void testReadReverseIndex() throws Exception {
        String toSplit = "123\tabc\n";
        Stream<String> stream = Stream.of(toSplit);
        when(indexFile.lines()).thenReturn(stream);
        Map<Integer, String> expected = makeExpected();
        Map<Integer, String> actual = query.readReverseIndex(indexFile);
        assertEquals(expected, actual);
    }

    private Map<Integer, String> makeExpected() {
        Map<Integer, String> ret = new HashMap<>(1);
        ret.put(123, "abc");
        return ret;
    }
}