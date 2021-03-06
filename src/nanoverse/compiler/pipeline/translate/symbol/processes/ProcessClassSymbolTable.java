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

package nanoverse.compiler.pipeline.translate.symbol.processes;

import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.processes.continuum.*;
import nanoverse.compiler.pipeline.translate.symbol.processes.discrete.*;
import nanoverse.compiler.pipeline.translate.symbol.processes.discrete.check.*;
import nanoverse.compiler.pipeline.translate.symbol.processes.temporal.TickInstSymbolTable;
import nanoverse.runtime.processes.NanoverseProcess;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 7/21/2015.
 */
public class ProcessClassSymbolTable extends ClassSymbolTable<NanoverseProcess> {

    @Override
    public String getDescription() {
        return "Processes are top-down events that can affect any part of " +
            "any layer, without respect to local neighborhood rules. " +
            "Whenever possible, use Actions instead; these are local and " +
            "are easier for Nanoverse to optimize.";
    }

    @Override
    public HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>();
        tick(ret);
        scatter(ret);
        powerScatter(ret);
        scatterClusters(ret);
        fill(ret);
        mockProcess(ret);
        trigger(ret);
        checkForFixation(ret);
        checkThresholdOccupancy(ret);
        checkForDomination(ret);
        checkForExtinction(ret);
        compositeContinuumProcess(ret);
        diffuse(ret);
        scale(ret);
        inject(ret);
        release(ret);
        hold(ret);
        integrate(ret);
        applyRelationships(ret);
        record(ret);
        manualHalt(ret);
        dirichletEnforcer(ret);

        return ret;
    }

    private void dirichletEnforcer(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = DirichletBoundaryEnforcerInstSymbolTable::new;
        ret.put("DirichletBoundaryEnforcer", st);
    }

    private void record(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = RecordInstSymbolTable::new;
        ret.put("Record", st);
    }

    private void compositeContinuumProcess(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = CompositeContinuumProcessInstSymbolTable::new;
        ret.put("CompositeContinuumProcess", st);
    }

    private void integrate(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = IntegrateInstSymbolTable::new;
        ret.put("Integrate", st);
    }

    private void applyRelationships(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = ApplyRelationshipsInstSymbolTable::new;
        ret.put("ApplyRelationships", st);
    }

    private void hold(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = ScheduleHoldInstSymbolTable::new;
        ret.put("Hold", st);
    }

    private void release(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = ScheduleReleaseInstSymbolTable::new;
        ret.put("Release", st);
    }

    private void inject(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = InjectionProcessInstSymbolTable::new;
        ret.put("Inject", st);
    }

    private void diffuse(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = DiffusionProcessInstSymbolTable::new;
        ret.put("Diffuse", st);
    }

    private void scale(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = ScaleProcessInstSymbolTable::new;
        ret.put("Scale", st);
    }

    private void checkForExtinction(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = CheckForExtinctionInstSymbolTable::new;
        ret.put("CheckForExtinction", st);
    }

    private void checkForDomination(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = CheckForDominationInstSymbolTable::new;
        ret.put("CheckForDomination", st);
    }

    private void checkThresholdOccupancy(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = CheckForThresholdOccupancyInstSymbolTable::new;
        ret.put("CheckForThresholdOccupancy", st);
    }

    private void checkForFixation(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = CheckForFixationInstSymbolTable::new;
        ret.put("CheckForFixation", st);
    }

    private void trigger(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = TriggerProcessInstSymbolTable::new;
        ret.put("Trigger", st);
    }

    private void mockProcess(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = MockProcessInstSymbolTable::new;
        ret.put("Mock", st);
    }

    private void fill(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = FillInstSymbolTable::new;
        ret.put("Fill", st);
    }

    private void scatterClusters(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = ScatterClustersInstSymbolTable::new;
        ret.put("ScatterClusters", st);
    }

    private void powerScatter(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = PowerScatterInstSymbolTable::new;
        ret.put("PowerScatter", st);
    }

    private void scatter(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = ScatterInstSymbolTable::new;
        ret.put("Scatter", st);
    }

    private void tick(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = TickInstSymbolTable::new;
        ret.put("Tick", st);
    }

    private void manualHalt(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = ManualHaltInstSymbolTable::new;
        ret.put("ManualHalt", st);
    }
}
