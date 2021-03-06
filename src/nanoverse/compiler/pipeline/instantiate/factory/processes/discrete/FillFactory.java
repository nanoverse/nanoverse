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
import nanoverse.runtime.control.arguments.AgentDescriptor;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.discrete.*;

public class FillFactory implements Factory<Fill> {

    private final FillFactoryHelper helper;

    private BaseProcessArguments arguments;
    private AgentProcessArguments cpArguments;
    private boolean skipFilled;
    private AgentDescriptor cellDescriptor;

    public FillFactory() {
        helper = new FillFactoryHelper();
    }

    public FillFactory(FillFactoryHelper helper) {
        this.helper = helper;
    }

    public void setArguments(BaseProcessArguments arguments) {
        this.arguments = arguments;
    }

    public void setCpArguments(AgentProcessArguments cpArguments) {
        this.cpArguments = cpArguments;
    }

    public void setSkipFilled(boolean skipFilled) {
        this.skipFilled = skipFilled;
    }

    public void setAgentDescriptor(AgentDescriptor cellDescriptor) {
        this.cellDescriptor = cellDescriptor;
    }

    @Override
    public Fill build() {
        return helper.build(arguments, cpArguments, skipFilled, cellDescriptor);
    }
}