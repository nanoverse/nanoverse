package nanoverse.runtime.io.serialize.text;

import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.*;

/**
 * Created by dbborens on 10/23/2015.
 */
public class CensusFlushHelper {
    private final HashSet<String> observedNames;
    private final ArrayList<Integer> frames;
    private final HashMap<Integer, HashMap<String, Integer>> histo;
    private final CensusObservationRecorder recorder;

    public CensusFlushHelper(HashSet<String> observedNames, ArrayList<Integer> frames, HashMap<Integer, HashMap<String, Integer>> histo) {
        this.observedNames = observedNames;
        this.frames = frames;
        this.histo = histo;
        recorder = new CensusObservationRecorder(observedNames);
    }

    public CensusFlushHelper(HashSet<String> observedNames,
                             ArrayList<Integer> frames,
                             HashMap<Integer, HashMap<String, Integer>> histo,
                             CensusObservationRecorder recorder) {

        this.observedNames = observedNames;
        this.frames = frames;
        this.histo = histo;
        this.recorder = recorder;
    }

    public void doFlush(AgentLayer layer, int t) {
        frames.add(t);

        // Create a bucket for this frame.
        HashMap<String, Integer> observations = new HashMap<>();
        histo.put(t, observations);

        // Iterate over all observed states for this frame.
        recorder.recordObservations(layer, observations);
    }

    public void init() {
        histo.clear();
        frames.clear();
        observedNames.clear();
    }

}
