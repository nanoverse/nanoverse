package nanoverse.runtime.io.serialize.text;

import nanoverse.runtime.io.FileSystemManager;
import nanoverse.runtime.structural.utilities.NanoCollections;
import org.junit.*;

import java.util.*;
import java.util.stream.*;

import static org.mockito.Mockito.*;

public class CensusWriteHelperTest {

    private HashSet<String> observedNames;
    private FileSystemManager fsManager;
    private CensusLineManager lineBuilder;
    private NanoCollections collections;
    private CensusWriteHelper query;

    @Before
    public void before() throws Exception {
        observedNames = mock(HashSet.class);
        fsManager = mock(FileSystemManager.class);
        lineBuilder = mock(CensusLineManager.class);
        collections = mock(NanoCollections.class);
        query = new CensusWriteHelper(observedNames, fsManager, lineBuilder, collections);
    }

    @Test
    public void commit() throws Exception {
        List<String> names = Stream.of("a", "b", "c").collect(Collectors.toList());
        when(observedNames.stream()).thenReturn(names.stream());

        String filename = CensusWriteHelper.FILENAME;
        TextOutputHandle handle = mock(TextOutputHandle.class);
        when(fsManager.makeInstanceTextFile(filename)).thenReturn(handle);

        query.commit();
        verify(lineBuilder).writeHeader(handle, names);
        verify(lineBuilder).writeFrames(handle, names);
    }
}