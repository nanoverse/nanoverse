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

package nanoverse.runtime.processes.continuum;

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.processes.*;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;

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
