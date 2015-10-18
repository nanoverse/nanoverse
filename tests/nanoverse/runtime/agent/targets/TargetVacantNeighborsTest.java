package nanoverse.runtime.agent.targets;

/**
 * Created by dbborens on 10/18/2015.
 */
public class TargetVacantNeighborsTest extends TargetRuleTestBase {
    @Override
    protected TargetRule resolveQuery() throws Exception {
        return new TargetVacantNeighbors(self, layerManager, filter, maximum, random);
    }

    @Override
    protected boolean acceptsSelf() {
        return false;
    }

    @Override
    protected boolean acceptsVacantNeighbors() {
        return true;
    }

    @Override
    protected boolean acceptsOccupiedNeighbors() {
        return false;
    }

    @Override
    protected boolean acceptsCaller() {
        return false;
    }

}
