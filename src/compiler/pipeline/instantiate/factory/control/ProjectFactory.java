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

import compiler.pipeline.instantiate.factory.Factory;
import control.*;
import control.run.Runner;

/**
 * Created by dbborens on 7/30/2015.
 */
public class ProjectFactory implements Factory<Runner> {
    
    private GeneralParameters parameters;
    private Integrator integrator;
    private final ProjectFactoryHelper helper;

    public ProjectFactory() {
        this(new ProjectFactoryHelper());
    }

    public ProjectFactory(ProjectFactoryHelper helper) {
        this.helper = helper;
    }

    public void setParameters(GeneralParameters parameters) {
        this.parameters = parameters;
    }

    public void setIntegrator(Integrator integrator) {
        this.integrator = integrator;
    }

    @Override
    public Runner build() {
        return helper.newRunner(parameters, integrator);
    }
}
