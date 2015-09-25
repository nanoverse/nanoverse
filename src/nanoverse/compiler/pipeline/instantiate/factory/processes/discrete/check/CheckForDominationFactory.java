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
package nanoverse.compiler.pipeline.instantiate.factory.processes.discrete.check;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.discrete.CellProcessArguments;
import nanoverse.runtime.processes.discrete.check.CheckForDomination;

public class CheckForDominationFactory implements Factory<CheckForDomination> {

    private final CheckForDominationFactoryHelper helper;

    private BaseProcessArguments arguments;
    private CellProcessArguments cpArguments;
    private IntegerArgument targetStateArg;
    private DoubleArgument targetFractionArg;

    public CheckForDominationFactory() {
        helper = new CheckForDominationFactoryHelper();
    }

    public CheckForDominationFactory(CheckForDominationFactoryHelper helper) {
        this.helper = helper;
    }

    public void setArguments(BaseProcessArguments arguments) {
        this.arguments = arguments;
    }

    public void setCpArguments(CellProcessArguments cpArguments) {
        this.cpArguments = cpArguments;
    }

    public void setTargetStateArg(IntegerArgument targetStateArg) {
        this.targetStateArg = targetStateArg;
    }

    public void setTargetFractionArg(DoubleArgument targetFractionArg) {
        this.targetFractionArg = targetFractionArg;
    }

    @Override
    public CheckForDomination build() {
        return helper.build(arguments, cpArguments, targetStateArg, targetFractionArg);
    }
}