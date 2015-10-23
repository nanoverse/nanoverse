package nanoverse.runtime.io.serialize.binary;

import nanoverse.runtime.layers.cell.AgentLayerViewer;

import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Created by dbborens on 10/23/2015.
 */
public class AgentNameIndexManager {
    private final HashMap<String, Integer> nameToIndexMap;

    public AgentNameIndexManager() {
        nameToIndexMap = new HashMap<>();
    }

    public AgentNameIndexManager(HashMap<String, Integer> nameToIndexMap) {
        this.nameToIndexMap = nameToIndexMap;
    }

    public void init() {
        nameToIndexMap.clear();
    }

    public Stream<String> getNameStream() {
        return nameToIndexMap.keySet().stream();
    }

    public Integer getIndex(String name) {
        if (name == null) {
            return 0;
        }
        createIndexKeyIfNew(name);
        return nameToIndexMap.get(name);
    }

    private void createIndexKeyIfNew(String name) {
        if (!nameToIndexMap.containsKey(name)) {
            int next = nameToIndexMap.size() + 1;
            nameToIndexMap.put(name, next);
        }
    }

    public Stream<Integer> getIndexStream(AgentLayerViewer viewer) {
        return viewer.getNames()
            .map(this::getIndex);
    }
}
