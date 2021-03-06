/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.runtime.layers.continuum;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.structural.annotations.FactoryTarget;
import no.uib.cipr.matrix.DenseVector;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;

import java.util.function.Function;

/**
 * Created by dbborens on 12/12/14.
 */
public class ContinuumLayerScheduler {

    private ScheduledOperations scheduledOperations;
    private HoldManager holdManager;

    @FactoryTarget
    public ContinuumLayerScheduler(ScheduledOperations scheduledOperations, HoldManager holdManager) {
        this.scheduledOperations = scheduledOperations;
        this.holdManager = holdManager;
    }

    public void apply(CompDiagMatrix matrix) {
        holdManager.resolve(() -> scheduledOperations.apply(matrix));
    }

    public void inject(DenseVector vector) {
        holdManager.resolve(() -> scheduledOperations.inject(vector));
    }

    public void inject(Coordinate target, double delta) {
        holdManager.resolve(() -> scheduledOperations.inject(target, delta));
    }

    public void exp(Coordinate target, double b) {
        holdManager.resolve(() -> scheduledOperations.exp(target, b));
    }

    public void setBoundaryCondition(Coordinate target, double b) {
        if (!holdManager.isHeld()) {
            System.err.println("Warning: Dirichlet boundary condition is " +
                "being enforced without hold on layer");
        }

        scheduledOperations.zeroOperatorRow(target);
        scheduledOperations.setSource(target, b);
    }

    public void reset() {
        holdManager.reset();
        scheduledOperations.reset();
    }

    public ContinuumAgentLinker getLinker(Function<Coordinate, Double> stateLookup) {
        return holdManager.getLinker(stateLookup);
    }

    public String getId() {
        return holdManager.getId();
    }

    public void solve() {
        holdManager.solve();
    }

    public void scheduleApplyRelationships() {
        holdManager.scheduleApplyRelationships();
    }
    public void hold() {
        holdManager.hold();
    }

    public void release() {
        holdManager.release();
    }

    public ScheduledOperations getScheduledOperations() {
        return scheduledOperations;
    }
}
