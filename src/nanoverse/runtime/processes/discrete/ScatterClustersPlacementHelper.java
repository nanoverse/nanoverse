package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.arguments.AgentDescriptor;
import nanoverse.runtime.control.halt.*;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.processes.discrete.cluster.SeparationStrategyManager;

import java.util.Iterator;

/**
 * Created by dbborens on 10/21/2015.
 */
public class ScatterClustersPlacementHelper {

    private final SeparationStrategyManager strategy;
    private final AgentDescriptor descriptor;

    public ScatterClustersPlacementHelper(SeparationStrategyManager strategy, AgentDescriptor descriptor) {
        this.strategy = strategy;
        this.descriptor = descriptor;
    }

    public void doPlacement(int n, int m, Iterator<Coordinate> cIter) throws HaltCondition {
        Agent toPlace = descriptor.next();

        int placed = 0;

        while (placed < n) {
            if (!cIter.hasNext()) {
                throw new LatticeFullEvent();
            }

            // Get next candidate coordinate.
            Coordinate current = cIter.next();

            // Place cell at this site, if it is acceptable
            int curPlaced = strategy.attemptPlacement(current, toPlace, m);
            if (curPlaced > 0) {
                placed += curPlaced;
                toPlace = descriptor.next();
            }
        }
    }
}
