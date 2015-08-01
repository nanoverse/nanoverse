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

package io.serialize.interactive;

import control.GeneralParameters;
import control.halt.HaltCondition;
import io.serialize.Serializer;
import layers.LayerManager;
import processes.StepState;
import structural.annotations.FactoryTarget;

/**
 * Outputs some basic information about simulation progress.
 * Without this (or similar), eSLIME does not write to stdout.
 * Note that flush(...) reports time since last flush(...), not
 * the time since the last cycle!
 *
 * @author David Bruce Borenstein
 * @untested
 */
public class ProgressReporter extends Serializer {

    private long projectStart;
    private long instanceStart;
    private long cycleStart;

    @FactoryTarget
    public ProgressReporter(GeneralParameters p, LayerManager lm) {
        super(p, lm);
        projectStart = System.currentTimeMillis();
    }

    public void init() {
        instanceStart = System.currentTimeMillis();
        cycleStart = System.currentTimeMillis();
        System.out.println("Instance " + p.getInstance() + ": " + p.getInstancePath());
        System.out.println("Random key: " + p.getRandomSeed());
    }

    @Override
    public void flush(StepState stepState) {
        long cycleTime = System.currentTimeMillis() - cycleStart;
        System.out.println("   Frame " + stepState.getFrame() + " (" + cycleTime + "ms).");
        cycleStart = System.currentTimeMillis();
    }

    @Override
    public void dispatchHalt(HaltCondition ex) {
        System.out.println("Simulation ended. Cause: " + ex.getClass().getSimpleName());
        long instanceTime = (System.currentTimeMillis() - instanceStart) / 1000;
        System.out.println("Instance running time: " + instanceTime + " seconds.");
    }

    @Override
    public void close() {
        System.out.println(" " + p.getBasePath());
        System.out.println("Project concluded.");

        long totalTime = (System.currentTimeMillis() - projectStart) / 1000;

        System.out.println("=================================================");
        System.out.println("Total running time: " + totalTime + " seconds.");
        System.out.println("=================================================");
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ProgressReporter);
    }
}
