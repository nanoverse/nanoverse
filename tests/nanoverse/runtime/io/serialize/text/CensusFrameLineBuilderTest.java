package nanoverse.runtime.io.serialize.text;

import org.junit.*;

import java.util.*;
import java.util.stream.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CensusFrameLineBuilderTest {

    private HashMap<Integer, HashMap<String, Integer>> histo;
    private CensusFrameLineBuilder query;
    private HashMap<String, Integer> observations;

    @Before
    public void before() throws Exception {
        histo = mock(HashMap.class);
        query = new CensusFrameLineBuilder(histo);
        observations = makeObservations();
        when(histo.get(1)).thenReturn(observations);
    }

    private HashMap<String, Integer> makeObservations() {
        HashMap<String, Integer> observations = new HashMap<>();
        observations.put("a", 1);
        observations.put("c", 3);
        return observations;
    }

    @Test
    public void frameLine() throws Exception {
        List<String> names = Stream.of("a", "b", "c").collect(Collectors.toList());
        String actual = query.frameLine(1, names);
        String expected = "1\t1\t0\t3\n";

        assertEquals(expected, actual);
    }
}