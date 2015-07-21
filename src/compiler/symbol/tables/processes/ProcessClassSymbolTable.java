/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein and the
 * Trustees of Princeton University.
 *
 * This file is part of the Nanoverse simulation framework
 * (patent pending).
 *
 * This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General
 * Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package compiler.symbol.tables.processes;

import compiler.symbol.symbols.*;
import compiler.symbol.tables.*;
import compiler.symbol.tables.processes.continuum.*;
import compiler.symbol.tables.processes.discrete.*;
import compiler.symbol.tables.processes.discrete.check.*;
import compiler.symbol.tables.processes.temporal.*;
import processes.NanoverseProcess;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 7/21/2015.
 */
public class ProcessClassSymbolTable extends ClassSymbolTable<NanoverseProcess> {

    @Override
    protected HashMap<String, ClassSymbol> resolveSubclasses() {
        HashMap<String, ClassSymbol> ret = new HashMap<>();
        tick(ret);
        divide(ret);
        occupiedNeighborSwap(ret);
        generalNeighborSwap(ret);
        scatter(ret);
        powerScatter(ret);
        scatterClusters(ret);
        fill(ret);
        mockProcess(ret);
        trigger(ret);
        cull(ret);
        checkForFixation(ret);
        checkThresholdOccupancy(ret);
        checkForDomination(ret);
        checkForExtinction(ret);
        diffuse(ret);
        inject(ret);
        release(ret);
        hold(ret);
        integrate(ret);
        record(ret);

        return ret;
    }

    private void record(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new RecordInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "Captures the state of the " +
                "simulation. If not specified, occurs by default at the end " +
                "of each simulation cycle.");
        ret.put("Record", ms);
    }

    private void integrate(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new IntegrateInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "Update the state of the " +
                "specified continuum layer.");
        ret.put("Integrate", ms);
    }

    private void hold(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new ScheduleHoldInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "Begin queueing changes to the " +
                "specified continuum layer, but do not execute them until a " +
                "corresponding \"release\" event takes place.");
        ret.put("Hold", ms);
    }

    private void release(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new ScheduleReleaseInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "Resolve all changes to the " +
                "specified continuum layer since the last \"hold\" event " +
                "occurred.");
        ret.put("Release", ms);
    }

    private void inject(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new InjectionProcessInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "Schedule a fixed-value source " +
                "(injection) at a site or sites of a specified continuum " +
                "layer.");
        ret.put("Inject", ms);
    }

    private void diffuse(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new DiffusionProcessInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "Schedule a homogeneous " +
                "diffusion process across the entirety of a specified " +
                "continuum layer.");
        ret.put("Diffuse", ms);
    }

    private void checkForExtinction(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new CheckForExtinctionInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "Halt simulation if all sites " +
                "are vacant.");
        ret.put("CheckForExtinction", ms);
    }

    private void checkForDomination(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new CheckForDominationInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "Halt the simulation when the " +
                "target cell type has the specified fraction of the overall " +
                "live cell population.");
        ret.put("CheckForDomination", ms);
    }

    private void checkThresholdOccupancy(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new CheckForThresholdOccupancyInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "Halt the simulation of the " +
                "overall population exceeds a certain threshold.");
        ret.put("CheckForThresholdOccupancy", ms);
    }

    private void checkForFixation(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new CheckForFixationInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "Halt the simulation if only one " +
                "type of agent exists.");
        ret.put("CheckForFixation", ms);
    }

    private void cull(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new CullInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "LEGACY: Kill all agents whose health " +
                "is below a specified threshold.");
        ret.put("Cull", ms);
    }

    private void trigger(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new TriggerProcessInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "Trigger agents to perform a specified Action.");
        ret.put("Trigger", ms);
    }

    private void mockProcess(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new MockProcessInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "LEGACY: Mock process, used in legacy tests.");
        ret.put("Mock", ms);
    }

    private void fill(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new FillInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "Fill in a region with agents.");
        ret.put("Fill", ms);
    }

    private void scatterClusters(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new ScatterClustersInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "Scatter agents in clusters of " +
                "a specific size.");
        ret.put("ScatterClusters", ms);
    }

    private void powerScatter(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new PowerScatterInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "Scatter groups of cells in a distribution of cluster sizes that may " +
                "or may not follow a power law. (Check this before using)");
        ret.put("PowerScatter", ms);
    }

    private void scatter(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new ScatterInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "Scatter a specified number of " +
                "new agents to random locations.");
        ret.put("Scatter", ms);
    }

    private void generalNeighborSwap(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new GeneralNeighborSwapInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "Swap the specified agent with any of its neighbors.");
        ret.put("GeneralNeighborSwap", ms);
    }

    private void occupiedNeighborSwap(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new OccupiedNeighborSwapInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "Swap the specified agent with " +
                "one of its occupied neighbors.");
        ret.put("OccupiedNeighborSwap", ms);
    }

    private void divide(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new DivideInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "LEGACY: Divide the agent to a neighboring site.");
        ret.put("Divide", ms);
    }

    private void tick(HashMap<String, ClassSymbol> ret) {
        Supplier<InstantiableSymbolTable> st = () -> new TickInstSymbolTable();
        ClassSymbol ms = new ClassSymbol(st, "Advance the simulation clock by the specified dt.");
        ret.put("Tick", ms);
    }

}
