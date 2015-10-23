package nanoverse.runtime.io.serialize.binary;

import nanoverse.runtime.layers.cell.*;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.utilities.ParityIO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dbborens on 10/23/2015.
 */
public class AgentNameCommitHelper {

    private final ParityIO parity;
    private final AgentNameIndexManager indexManager;

    public AgentNameCommitHelper(AgentNameIndexManager indexManager) {
        this(new ParityIO(), indexManager);
    }

    public AgentNameCommitHelper(ParityIO parity, AgentNameIndexManager indexManager) {
        this.parity = parity;
        this.indexManager = indexManager;
    }

    public void commit(BinaryOutputHandle file, StepState state) {
        AgentLayer layer = state.getRecordedAgentLayer();
        AgentLayerViewer viewer = layer.getViewer();
        List<Integer> indexedNameVector = indexManager
            .getIndexStream(viewer)
            .collect(Collectors.toList());
        double time = state.getTime();
        int frame = state.getFrame();

        parity.writeStart(file);
        file.writeDouble(time);
        file.writeInt(frame);

        indexedNameVector.stream()
            .forEach(file::writeInt);

        parity.writeEnd(file);
    }
}
