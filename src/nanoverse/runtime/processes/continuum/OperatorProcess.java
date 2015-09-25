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

package nanoverse.runtime.processes.continuum;

import nanoverse.runtime.control.halt.HaltCondition;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.StepState;

import java.util.function.Consumer;

/**
 * Supplies an operator matrix to a consumer (typically a continuum
 * scheduler) when fired.
 * <p>
 * Created by dbborens on 1/22/15.
 */
public class OperatorProcess extends ContinuumProcess {

    private CompDiagMatrix operator;
    private Consumer<CompDiagMatrix> target;

    public OperatorProcess(BaseProcessArguments arguments, CompDiagMatrix operator, Consumer<CompDiagMatrix> target) {
        super(arguments);
        this.target = target;
        this.operator = operator;
    }

    @Override
    public void fire(StepState state) throws HaltCondition {
        target.accept(operator);
    }

    @Override
    public void init() {
    }
}
