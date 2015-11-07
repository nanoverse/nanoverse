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
package nanoverse.compiler.pipeline.instantiate.factory.processes.discrete;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.discrete.*;

public class ManualHaltFactory implements Factory<ManualHalt> {

    private final ManualHaltFactoryHelper helper;

    private BaseProcessArguments arguments;
    private AgentProcessArguments cpArguments;
    private String message;

    public ManualHaltFactory() {
        helper = new ManualHaltFactoryHelper();
    }

    public ManualHaltFactory(ManualHaltFactoryHelper helper) {
        this.helper = helper;
    }

    public void setArguments(BaseProcessArguments arguments) {
        this.arguments = arguments;
    }

    public void setCpArguments(AgentProcessArguments cpArguments) {
        this.cpArguments = cpArguments;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public ManualHalt build() {
        return helper.build(arguments, cpArguments, message);
    }
}