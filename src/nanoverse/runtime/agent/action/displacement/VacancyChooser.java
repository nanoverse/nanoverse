package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.halt.*;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.Random;

/**
 * Created by dbborens on 10/20/2015.
 */
public class VacancyChooser {

    private final AgentLayer layer;
    private final Random random;

    public VacancyChooser(AgentLayer layer, Random random) {
        this.layer = layer;
        this.random = random;
    }

    /**
     * Gets the set of all nearest vacancies to the agent, and chooses randomly
     * between them.
     *
     * @param origin
     * @return
     * @throws HaltCondition
     */
    public Coordinate chooseVacancy(Coordinate origin) throws HaltCondition {
        Coordinate target;
        // Get nearest vacancies to the agent
        Coordinate[] targets = layer.getLookupManager().getNearestVacancies(origin, -1);
        if (targets.length == 0) {
            throw new LatticeFullEvent();
        } else {
            int i = random.nextInt(targets.length);
            target = targets[i];
        }

        return target;
    }
}
