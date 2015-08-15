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
package compiler.pipeline.instantiate.factory.control;

import control.GeneralParameters;
import java.util.Random;
import compiler.pipeline.instantiate.factory.Factory;

public class ParametersFactory implements Factory<GeneralParameters> {

    private final ParametersFactoryHelper helper;

    private Random random;
    private long randomSeed;
    private int maxStep;
    private int instances;
    private String basePath;
    private String project;
    private boolean isStamp;
    private double epsilon;

    public ParametersFactory() {
        helper = new ParametersFactoryHelper();
    }

    public ParametersFactory(ParametersFactoryHelper helper) {
        this.helper = helper;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public void setRandomSeed(long randomSeed) {
        this.randomSeed = randomSeed;
    }

    public void setMaxStep(int maxStep) {
        this.maxStep = maxStep;
    }

    public void setInstances(int instances) {
        this.instances = instances;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setIsStamp(boolean isStamp) {
        this.isStamp = isStamp;
    }

    @Override
    public GeneralParameters build() {
        return helper.build(random, randomSeed, maxStep, instances, basePath, project, isStamp, epsilon);
    }
}