package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.agent.action.helper.CoordAgentMapper;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;

/**
 * Created by dbborens on 10/21/2015.
 */
public class DisplacementOptionResolver {

    private final DisplacementManager displacementManager;
    private final CoordAgentMapper mapper;

    public DisplacementOptionResolver(DisplacementManager displacementManager, CoordAgentMapper mapper) {
        this.displacementManager = displacementManager;
        this.mapper = mapper;
    }

    public DisplacementOption getOption(Coordinate start) throws HaltCondition {
        Geometry geom = mapper.getLayerManager().getAgentLayer().getGeometry();
        Coordinate end = displacementManager.chooseVacancy(start);
        int distance = geom.getL1Distance(start, end, Geometry.APPLY_BOUNDARIES);

        DisplacementOption ret = new DisplacementOption(start, end, distance);

        return ret;
    }
}
