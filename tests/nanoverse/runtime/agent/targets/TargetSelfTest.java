package nanoverse.runtime.agent.targets;

/**
 * Created by dbborens on 10/18/2015.
 */
public class TargetSelfTest extends TargetRuleTestBase {

    @Override
    protected TargetRule resolveQuery() throws Exception {
        return new TargetSelf(self, layerManager, filter, random);
    }

    @Override
    protected boolean acceptsSelf() {
        return true;
    }

    @Override
    protected boolean acceptsCaller() {
        return false;
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
