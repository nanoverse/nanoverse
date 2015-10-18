package nanoverse.runtime.agent.targets;

/**
 * Created by dbborens on 10/18/2015.
 */
public class TargetAllNeighborsTest extends TargetRuleTestBase {

    @Override
    protected TargetRule resolveQuery() throws Exception {
        return new TargetAllNeighbors(self, layerManager, filter, maximum, random);
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
        return true;
    }

    @Override
    protected boolean acceptsCaller() {
        return false;
    }
}
