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

package nanoverse.runtime.structural;

import nanoverse.runtime.control.GeneralParameters;

import java.util.Random;

/**
 * Created by dbborens on 12/10/13.
 */
public class MockGeneralParameters extends GeneralParameters {

    private boolean isFrameValue;
    private String instancePath;
    private String path;
    private int T;
    private int numInstances;
    private int instance;

    public MockGeneralParameters() {
        super();
    }

    @Override
    public int T() {
        return T;
    }

    @Override
    public int getNumInstances() {
        return numInstances;
    }

    public String getPath() {
        return path;
    }

    public String getInstancePath() {
        return instancePath;
    }

    public void setInstancePath(String instancePath) {
        this.instancePath = instancePath;
    }

    @Override
    public int getInstance() {
        return instance;
    }

    public void setInstance(int instance) {
        this.instance = instance;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setNumInstances(int numInstances) {
        this.numInstances = numInstances;
    }

    public void setT(int t) {
        T = t;
    }

    public void initializeRandom(long randomSeed) {
        random = new Random(randomSeed);
        this.randomSeed = randomSeed;
    }

    public void setIsFrameValue(boolean isFrameValue) {
        this.isFrameValue = isFrameValue;
    }

    public void setFrameValue(boolean isFrameValue) {
        this.isFrameValue = isFrameValue;
    }

    public void setRandom(MockRandom random) {
        this.random = random;
    }
}
