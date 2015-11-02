package nanoverse.runtime.io.serialize.text;

import org.junit.*;
import org.mockito.*;
import test.LayerMocks;

import java.util.*;

import static org.mockito.Mockito.*;

public class CensusFlushHelperTest extends LayerMocks {

    private HashSet<String> observedNames;
    private ArrayList<Integer> frames;
    private HashMap<Integer, HashMap<String, Integer>> histo;
    private CensusObservationRecorder recorder;
    private CensusFlushHelper query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        observedNames = mock(HashSet.class);
        frames = mock(ArrayList.class);
        histo = mock(HashMap.class);
        recorder = mock(CensusObservationRecorder.class);
        query = new CensusFlushHelper(observedNames, frames, histo, recorder);
    }

    @Test
    public void doFlush() throws Exception {
        // This should be a clue I designed something wrong
        ArgumentCaptor<HashMap> captor = ArgumentCaptor.forClass(HashMap.class);

        query.doFlush(agentLayer, 1);

        InOrder inOrder = inOrder(frames, histo, recorder);
        inOrder.verify(frames).add(1);
        inOrder.verify(histo).put(eq(1), captor.capture());

        HashMap observations = captor.getValue();

        inOrder.verify(recorder).recordObservations(agentLayer, observations);
    }

    public void init() throws Exception {
        query.init();
        verify(histo).clear();
        verify(frames).clear();
        verify(observedNames).clear();
    }
}