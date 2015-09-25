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

package nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.check;

import nanoverse.compiler.pipeline.instantiate.factory.processes.discrete.check.CheckForExtinctionFactory;
import nanoverse.compiler.pipeline.instantiate.loader.processes.ProcessLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.discrete.CellProcessArguments;
import nanoverse.runtime.processes.discrete.check.CheckForExtinction;

/**
 * Created by dbborens on 8/3/2015.
 */
public class CheckForExtinctionLoader extends ProcessLoader<CheckForExtinction> {
    private final CheckForExtinctionFactory factory;
    private final CheckForExtinctionInterpolator interpolator;

    public CheckForExtinctionLoader() {
        factory = new CheckForExtinctionFactory();
        interpolator = new CheckForExtinctionInterpolator();
    }

    public CheckForExtinctionLoader(CheckForExtinctionFactory factory,
                                    CheckForExtinctionInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public CheckForExtinction instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        BaseProcessArguments arguments = interpolator.arguments(node, lm, p);
        factory.setArguments(arguments);

        CellProcessArguments cpArguments = interpolator.cpArguments(node, lm, p);
        factory.setCpArguments(cpArguments);

        DoubleArgument threshold = interpolator.threshold(node, p.getRandom());
        factory.setThresholdArg(threshold);

        return factory.build();
    }
}
