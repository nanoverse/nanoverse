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