package nanoverse.runtime.io.serialize.binary;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * Created by dbborens on 10/22/2015.
 */
public class AgentNameWriter extends Serializer {

    private final AgentNameIndexManager indexManager;
    private final AgentNameIOHelper ioHelper;


    @FactoryTarget
    public AgentNameWriter(GeneralParameters p, LayerManager lm) {
        super(p, lm);
        indexManager = new AgentNameIndexManager();
        ioHelper = new AgentNameIOHelper(p, indexManager);
    }

    public AgentNameWriter(GeneralParameters p,
                           LayerManager lm,
                           AgentNameIndexManager indexManager,
                           AgentNameIOHelper ioHelper) {
        super(p, lm);
        this.indexManager = indexManager;
        this.ioHelper = ioHelper;
    }

    @Override
    public void init() {
        indexManager.init();
        ioHelper.init();
    }

    @Override
    public void dispatchHalt(HaltCondition ex) {
        ioHelper.conclude();
    }

    @Override
    public void close() {
    }

    @Override
    public void flush(StepState stepState) {
        ioHelper.commit(stepState);
    }

}
