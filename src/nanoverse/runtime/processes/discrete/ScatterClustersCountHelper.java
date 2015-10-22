package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.halt.HaltCondition;

/**
 * Created by dbborens on 10/21/2015.
 */
public class ScatterClustersCountHelper {
    private final IntegerArgument maxTargets;
    private final IntegerArgument neighborCount;

    public ScatterClustersCountHelper(IntegerArgument maxTargets,
                                      IntegerArgument neighborCount) {
        this.maxTargets = maxTargets;
        this.neighborCount = neighborCount;
    }

    public int getCeiling() {
        try {
            int n = maxTargets.next();
            if (n < 0) {
                throw new IllegalArgumentException("Scatter cluster process requires >= 0 max targets.");
            }

            return n;
        } catch (HaltCondition ex) {
            throw new RuntimeException("Unexpected halt condition", ex);
        }
    }

    public int getNeighborCount() {
        int m;

        try {
            m = neighborCount.next();
        } catch (HaltCondition ex) {
            throw new RuntimeException("Unexpected halt condition", ex);
        }

        return m;
    }

}
