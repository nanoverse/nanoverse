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
package nanoverse.compiler.pipeline.instantiate.factory.processes;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.processes.*;

public class MockProcessFactory implements Factory<MockProcess> {

    private final MockProcessFactoryHelper helper;

    private BaseProcessArguments arguments;
    private String identifier;
    private double weight;
    private int count;

    public MockProcessFactory() {
        helper = new MockProcessFactoryHelper();
    }

    public MockProcessFactory(MockProcessFactoryHelper helper) {
        this.helper = helper;
    }

    public void setArguments(BaseProcessArguments arguments) {
        this.arguments = arguments;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public MockProcess build() {
        return helper.build(arguments, identifier, weight, count);
    }
}