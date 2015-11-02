package nanoverse.runtime.io.visual.color;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.SystemState;

import java.util.stream.Stream;

/**
 * Created by dbborens on 10/28/2015.
 */
public class InteriorChecker {
    public boolean isInterior(Coordinate c, SystemState systemState) {
        Stream<String> neighborNames = systemState
                .getLayerManager()
                .getAgentLayer()
                .getLookupManager()
                .getNeighborNames(c, false);

        int vacantNeighbors = (int) neighborNames
                .filter(name -> name == null)
                .count();

        return vacantNeighbors == 0;
    }
}
