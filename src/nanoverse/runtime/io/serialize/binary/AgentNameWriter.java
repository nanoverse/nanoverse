package nanoverse.runtime.io.serialize.binary;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.StepState;

/**
 * Created by dbborens on 10/22/2015.
 */
public class AgentNameWriter extends Serializer {


    public AgentNameWriter(GeneralParameters p, LayerManager lm) {
        super(p, lm);
    }

    @Override
    public void dispatchHalt(HaltCondition ex) {

    }

    @Override
    public void close() {

    }

    @Override
    public void flush(StepState stepState) {

    }
}
