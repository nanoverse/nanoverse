package nanoverse.runtime.io.serialize.text;

import nanoverse.runtime.layers.cell.*;

import java.util.*;

/**
 * Created by dbborens on 10/23/2015.
 */
public class CensusObservationRecorder {
    private final HashSet<String> observedNames;

    public CensusObservationRecorder(HashSet<String> observedNames) {
        this.observedNames = observedNames;
    }

    public void recordObservations(AgentLayer layer, HashMap<String, Integer> observations) {
        NameMapViewer smv = layer.getViewer().getNameMapViewer();
        for (String name : smv.getNames()) {
            recordName(observations, smv, name);
        }
    }

    private void recordName(HashMap<String, Integer> observations, NameMapViewer smv, String name) {
        Integer count = smv.getCount(name);
        observations.put(name, count);
        observedNames.add(name);
    }
}
