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

package nanoverse.runtime.io.serialize.text;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.NotYetImplementedException;

/**
 * @author dbborens
 */
public class InterfaceCensusWriter extends Serializer {
    private final String name;

    public InterfaceCensusWriter(GeneralParameters p, String name, LayerManager lm) {
        super(p, lm);
        this.name = name;
    }

    @Override
    public void dispatchHalt(HaltCondition ex) {
        throw new NotYetImplementedException("Rewrite me using logic from CensusWriter");
    }

    @Override
    public void close() {
        throw new NotYetImplementedException("Rewrite me using logic from CensusWriter");
    }

    @Override
    public void flush(StepState stepState) {
        throw new NotYetImplementedException("Rewrite me using logic from CensusWriter");
    }

//    private Integer focalState;
//
//    private BufferedWriter bw;
//
//    private HashSet<Integer> observedInterfaceStates;
//
//    private HashMap<Integer, Map<Integer, Double>> frameToHistogramMap;
//
//    @FactoryTarget
//    public InterfaceCensusWriter(GeneralParameters p, IntegerArgument focalStateArg, LayerManager lm) {
//        super(p, lm);
//
//        try {
//            focalState = focalStateArg.next();
//        } catch (HaltCondition ex) {
//            throw new IllegalStateException(ex);
//        }
//    }
//
//    @Override
//    public void init() {
//        super.init();
//        observedInterfaceStates = new HashSet<>();
//        frameToHistogramMap = new HashMap<>();
//
//        String filename = FileConventions.makeInterfaceFilename(focalState);
//        mkDir(p.getInstancePath(), true);
//        bw = makeBufferedWriter(p.getInstancePath() + filename);
//    }
//
//    public void dispatchHalt(HaltCondition ex) {
//        conclude();
//        closed = true;
//    }
//
//    private void conclude() {
//        // Sort the states numerically
//        TreeSet<Integer> sortedStates = new TreeSet<>(observedInterfaceStates);
//        writeHeader(sortedStates);
//
//        TreeSet<Integer> sortedFrames = new TreeSet<>(frameToHistogramMap.keySet());
//
//
//        for (Integer frame : sortedFrames) {
//            Map<Integer, Double> absHisto = frameToHistogramMap.get(frame);
//            Map<Integer, Double> relHisto = calcRelHisto(absHisto);
//
//            StringBuilder line = new StringBuilder();
//            line.append(frame);
//
//            for (Integer state : sortedStates) {
//                line.append("\t");
//                line.append(relHisto.get(state));
//            }
//            line.append("\n");
//            hAppend(bw, line);
//        }
//        hClose(bw);
//    }
//
//    /**
//     * Convert absolute histogram of interface types to relative histogram.
//     *
//     * @param absHisto
//     * @return
//     */
//    private Map<Integer, Double> calcRelHisto(Map<Integer, Double> absHisto) {
//        double ttlObs = 0.0;
//
//        // First, get the total number of observations
//        for (double obs : absHisto.values()) {
//            ttlObs += obs;
//        }
//
//        HashMap<Integer, Double> relHisto = new HashMap<>();
//
//        for (int state : observedInterfaceStates) {
//            if (!absHisto.containsKey(state)) {
//                relHisto.put(state, 0.0);
//            } else {
//                double obs = absHisto.get(state);
//                double relObs = obs / ttlObs;
//                relHisto.put(state, relObs);
//            }
//        }
//
//        return relHisto;
//    }
//
//    private void writeHeader(TreeSet<Integer> sortedStates) {
//        // Write out the header
//        StringBuilder line = new StringBuilder();
//        line.append("frame");
//        for (Integer state : sortedStates) {
//            line.append("\t");
//            line.append(state);
//        }
//
//        line.append("\n");
//
//        hAppend(bw, line);
//    }
//
//    public void close() {
//        // Doesn't do anything.
//    }
//
//    @Override
//    public void flush(StepState stepState) {
//        Map<Integer, Double> histo = new HashMap<>();
//        ArrayList<Coordinate> focalSites = getFocalSites(stepState);
//        processFocalSites(stepState, histo, focalSites);
//
//        int frame = stepState.getFrame();
//        frameToHistogramMap.put(frame, histo);
//    }
//
//    private ArrayList<Coordinate> getFocalSites(StepState stepState) {
//        AgentLayer layer = stepState.getRecordedAgentLayer();
//        ArrayList<Coordinate> focalSites = new ArrayList<>();
//
//        // Find all agents of focal type.
//        HashSet<Coordinate> sites = layer.getViewer().getOccupiedSites();
//
//        for (Coordinate site : sites) {
//            Agent focalAgent = layer.getViewer().getAgent(site);
//            if (focalAgent.getState() == focalState) {
//                focalSites.add(site);
//            }
//        }
//
//        return focalSites;
//    }
//
//    private void processFocalSites(StepState stepState, Map<Integer, Double> histo, ArrayList<Coordinate> focalSites) {
//        for (Coordinate site : focalSites) {
//            processNeighbors(site, stepState, histo);
//        }
//    }
//
//    private void processNeighbors(Coordinate site, StepState stepState,
//                                  Map<Integer, Double> histo) {
//
//        AgentLayer layer = stepState.getRecordedAgentLayer();
//
//        int[] neighborStates = layer.getLookupManager().getNeighborStates(site, false);
//
//        for (int neighborState : neighborStates) {
//            increment(histo, neighborState);
//            note(neighborState);
//        }
//    }
//
//    private void note(int neighborState) {
//        observedInterfaceStates.add(neighborState);
//    }
//
//    private void increment(Map<Integer, Double> histo, int neighborState) {
//        if (!histo.containsKey(neighborState)) {
//            histo.put(neighborState, 0.0);
//        }
//
//        double current = histo.get(neighborState);
//        histo.put(neighborState, current + 1.0);
//    }
}
