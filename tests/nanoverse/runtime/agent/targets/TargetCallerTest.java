package nanoverse.runtime.agent.targets;

/**
 * Created by dbborens on 10/18/2015.
 */
public class TargetCallerTest extends TargetRuleTestBase {
    @Override
    protected TargetRule resolveQuery() throws Exception {
        return new TargetCaller(self, layerManager, filter, random);
    }

    @Override
    protected boolean acceptsSelf() {
        return false;
    }

    @Override
    protected boolean acceptsCaller() {
        return true;
    }

    @Override
    protected boolean acceptsOccupiedNeighbors() {
        return false;
    }

    @Override
    protected boolean acceptsVacantNeighbors() {
        return false;
    }
}
