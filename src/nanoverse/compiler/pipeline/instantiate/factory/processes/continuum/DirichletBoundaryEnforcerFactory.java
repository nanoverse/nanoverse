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
package nanoverse.compiler.pipeline.instantiate.factory.processes.continuum;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.continuum.DirichletBoundaryEnforcer;
import nanoverse.runtime.geometry.set.CoordinateSet;
import nanoverse.runtime.control.arguments.Argument;

public class DirichletBoundaryEnforcerFactory implements Factory<DirichletBoundaryEnforcer> {

    private final DirichletBoundaryEnforcerFactoryHelper helper;

    private BaseProcessArguments arguments;
    private DoubleArgument value;
    private String layerId;
    private CoordinateSet activeSites;

    public DirichletBoundaryEnforcerFactory() {
        helper = new DirichletBoundaryEnforcerFactoryHelper();
    }

    public DirichletBoundaryEnforcerFactory(DirichletBoundaryEnforcerFactoryHelper helper) {
        this.helper = helper;
    }

    public void setArguments(BaseProcessArguments arguments) {
        this.arguments = arguments;
    }

    public void setValue(DoubleArgument value) {
        this.value = value;
    }

    public void setLayerId(String layerId) {
        this.layerId = layerId;
    }

    public void setActiveSites(CoordinateSet activeSites) {
        this.activeSites = activeSites;
    }

    @Override
    public DirichletBoundaryEnforcer build() {
        return helper.build(arguments, value, layerId, activeSites);
    }
}