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

package nanoverse.runtime.io.serialize.text;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.annotations.FactoryTarget;
import nanoverse.runtime.structural.utilities.EpsilonUtil;

import java.io.BufferedWriter;
import java.util.*;

/**
 * Calculate the pairwise correlation between individuals at every distance.
 * This calculation is used to measure spatial structure. The results are
 * measured at a specified time point, and aggregated over all simulations.
 * <p>
 * Created by dbborens on 4/22/14.
 */
public class CorrelationWriter extends Serializer {

    private double triggerTime;

    //The number of identity observations as a function of L1 distance.
    private HashMap<Integer, Double> identity;

    //The total number of number of observations at each L1 distance.
    private HashMap<Integer, Double> observations;

    // Indicates whether the analysis has occurred yet for this simulation.
    private boolean fired;

    private String filename;

    /**
     * @param p
     * @param triggerTimeArg The minimum time at which the RDF should be run.
     *                       Once it is run, it will not be run a second time
     *                       for the same simulation. Multiple RDF serializers
     *                       can be included for the same model!
     */
    @FactoryTarget
    public CorrelationWriter(GeneralParameters p, String filename, DoubleArgument triggerTimeArg, LayerManager lm) {
        super(p, lm);
        identity = new HashMap<>();
        observations = new HashMap<>();
        try {
            this.triggerTime = triggerTimeArg.next();
        } catch (HaltCondition ex) {
            throw new IllegalStateException("Halt condition thrown while retrieving trigger time for correlation writer.");
        }
        this.filename = filename;
    }

    @Override
    public void init() {
        super.init();
        fired = false;
    }

    @Override
    public void dispatchHalt(HaltCondition ex) {
    }

    @Override
    public void close() {

        TreeMap<Integer, Double> correlations = calcCorrelations();

        StringBuilder sb = new StringBuilder();
        // Write a header row
        for (Integer r : correlations.keySet()) {
            sb.append("\t");
            sb.append(r);
        }
        sb.append("\n");
        sb.append(triggerTime);

        // Write each entry
        for (Integer r : correlations.keySet()) {
            sb.append("\t");
            sb.append(correlations.get(r));
        }

        // Write the output to a file
        String path = p.getPath() + '/' + filename;
        mkDir(p.getPath(), true);
        BufferedWriter writer = makeBufferedWriter(path);
        hAppend(writer, sb);
        hClose(writer);
    }

    private TreeMap<Integer, Double> calcCorrelations() {
        TreeMap<Integer, Double> corMap = new TreeMap<>();

        for (Integer r : observations.keySet()) {
            double x = getIdentities(r);
            double n = observations.get(r);

            double p = x / n;
            double cor = (p * 2.0) - 1.0;
            corMap.put(r, cor);
        }
        return corMap;
    }

    private double getIdentities(Integer r) {
        double x;
        if (!identity.containsKey(r)) {
            x = 0.0;
        } else {
            x = identity.get(r);
        }

        return x;
    }

    @Override
    public void flush(StepState stepState) {
        long start = System.currentTimeMillis();
        // Has the analysis fired yet? If so, return.
        if (fired) {
            return;
        }

        // Is it time to fire yet? If not, return.
        if (stepState.getTime() < triggerTime) {
            return;
        }

        AgentLayer layer = stepState.getRecordedAgentLayer();
        Geometry geom = layer.getGeometry();
        Coordinate[] cc = geom.getCanonicalSites();
        // Iterate over all canonical sites.
        for (Coordinate i : cc) {
            // For each canonical site, iterate over all canonical sites.
            for (Coordinate j : cc) {
                recordObservation(i, j, layer);
            }
        }
        // Mark the analysis event as having fired.
        long total = System.currentTimeMillis() - start;
        fired = true;
    }

    private void recordObservation(Coordinate i, Coordinate j, AgentLayer l) {

        // Calculate L1 distance r.
        int r = l.getGeometry().getL1Distance(i, j, Geometry.IGNORE_BOUNDARIES);

        int iState = l.getViewer().getState(i);
        int jState = l.getViewer().getState(j);

        // If identical, record an identity at distance r.
        if (iState == jState) {
            increment(identity, r);
        }

        // Record that an observation occurred.
        increment(observations, r);
    }

    private void increment(HashMap<Integer, Double> map, Integer key) {
        if (!map.containsKey(key)) {
            map.put(key, 0.0);
        }

        Double current = map.get(key);

        map.put(key, current + 1.0);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CorrelationWriter)) {
            return false;
        }

        CorrelationWriter other = (CorrelationWriter) obj;
        if (!EpsilonUtil.epsilonEquals(triggerTime, other.triggerTime)) {
            return false;
        }

        if (!filename.equals(other.filename)) {
            return false;
        }

        return true;
    }
}
