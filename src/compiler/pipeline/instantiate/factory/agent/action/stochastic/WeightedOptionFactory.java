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
package compiler.pipeline.instantiate.factory.agent.action.stochastic;

import agent.action.ActionDescriptor;
import agent.action.stochastic.WeightedOption;
import compiler.pipeline.instantiate.factory.Factory;

public class WeightedOptionFactory implements Factory<WeightedOption> {

    private final WeightedOptionFactoryHelper helper;

    private double weight;
    private ActionDescriptor action;

    public WeightedOptionFactory() {
        helper = new WeightedOptionFactoryHelper();
    }

    public WeightedOptionFactory(WeightedOptionFactoryHelper helper) {
        this.helper = helper;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setAction(ActionDescriptor action) {
        this.action = action;
    }

    @Override
    public WeightedOption build() {
        return helper.build(weight, action);
    }
}