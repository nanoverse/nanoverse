package nanoverse.runtime.io.serialize.binary;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.io.FileSystemManager;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.io.serialize.text.TextOutputHandle;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.utilities.ParityIO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dbborens on 10/22/2015.
 */
public class AgentNameWriter extends Serializer {

    private static final String VECTOR_FILENAME = "nameVector.bin";
    private static final String INDEX_FILENAME = "nameIndex.txt";

    private final ParityIO parity;
    private final FileSystemManager fsManager;

    private HashMap<String, Integer> nameToIndexMap;
    private BinaryOutputHandle nameVectorFile;

    public AgentNameWriter(GeneralParameters p, LayerManager lm) {
        super(p, lm);
        nameToIndexMap = new HashMap<>();
        parity = new ParityIO();
        fsManager = new FileSystemManager(p);
    }

    @Override
    public void init() {
        nameVectorFile = fsManager.makeInstanceBinaryFile(VECTOR_FILENAME);
    }

    @Override
    public void dispatchHalt(HaltCondition ex) {
        nameVectorFile.close();
        writeNameIndex();
    }

    private void writeNameIndex() {
        TextOutputHandle handle = fsManager.makeInstanceTextFile(INDEX_FILENAME);
        nameToIndexMap.keySet()
            .forEach(name -> writeLine(name, handle));
    }

    private void writeLine(String name, TextOutputHandle handle) {
        int index = nameToIndexMap.get(name);
        String line = createLine(name, index);
        handle.write(line);
    }

    private String createLine(String name, int index) {
        StringBuilder sb = new StringBuilder();
        sb.append(index);
        sb.append('\t');
        sb.append(name);
        sb.append('\n');
        return sb.toString();
    }

    @Override
    public void close() {
    }

    @Override
    public void flush(StepState stepState) {
        AgentLayer layer = stepState.getRecordedAgentLayer();
        List<Integer> indexedNameVector = layer.getViewer()
            .getNames()
            .map(this::nameToIndex)
            .collect(Collectors.toList());

        commit(indexedNameVector, stepState);
    }

    private void commit(List<Integer> indexedNameVector, StepState state) {
        double time = state.getTime();
        int frame = state.getFrame();

        parity.writeStart(nameVectorFile);
        nameVectorFile.writeDouble(time);
        nameVectorFile.writeInt(frame);

        indexedNameVector.stream()
            .forEach(nameVectorFile::writeInt);

        parity.writeEnd(nameVectorFile);
    }

    private Integer nameToIndex(String name) {
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
}
