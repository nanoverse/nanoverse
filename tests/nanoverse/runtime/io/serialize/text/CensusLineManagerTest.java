package nanoverse.runtime.io.serialize.text;

import org.junit.*;

import java.util.*;
import java.util.stream.*;

import static org.mockito.Mockito.*;

public class CensusLineManagerTest {

    private HashMap<Integer, HashMap<String, Integer>> histo;
    private CensusFrameLineBuilder builder;
    private CensusLineManager query;
    private TextOutputHandle handle;

    @Before
    public void before() throws Exception {
        histo = mock(HashMap.class);
        builder = mock(CensusFrameLineBuilder.class);
        query = new CensusLineManager(histo, builder);

        handle = mock(TextOutputHandle.class);
    }

    @Test
    public void writeFrames() throws Exception {
        Set<Integer> keySet = Stream.of(1).collect(Collectors.toSet());
        when(histo.keySet()).thenReturn(keySet);

        List<String> names = mock(List.class);
        String line = "test";
        when(builder.frameLine(1, names)).thenReturn(line);

        query.writeFrames(handle, names);
        verify(handle).write(line);
    }

    @Test
    public void writeHeader() throws Exception {
        List<String> sortedNames = Stream.of("a", "b", "c")
            .collect(Collectors.toList());

        query.writeHeader(handle, sortedNames);
        verify(handle).write("frame\ta\tb\tc\n");
    }
}