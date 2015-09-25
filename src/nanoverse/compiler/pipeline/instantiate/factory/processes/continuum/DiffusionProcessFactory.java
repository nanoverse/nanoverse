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

import java.util.function.Consumer;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;
import nanoverse.runtime.processes.continuum.DiffusionProcess;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.compiler.pipeline.instantiate.factory.Factory;

public class DiffusionProcessFactory implements Factory<DiffusionProcess> {

    private final DiffusionProcessFactoryHelper helper;

    private BaseProcessArguments arguments;
    private CompDiagMatrix operator;
    private Consumer target;

    public DiffusionProcessFactory() {
        helper = new DiffusionProcessFactoryHelper();
    }

    public DiffusionProcessFactory(DiffusionProcessFactoryHelper helper) {
        this.helper = helper;
    }

    public void setArguments(BaseProcessArguments arguments) {
        this.arguments = arguments;
    }

    public void setOperator(CompDiagMatrix operator) {
        this.operator = operator;
    }

    public void setTarget(Consumer target) {
        this.target = target;
    }

    @Override
    public DiffusionProcess build() {
        return helper.build(arguments, operator, target);
    }
}