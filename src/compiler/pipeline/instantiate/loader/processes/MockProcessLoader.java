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

package compiler.pipeline.instantiate.loader.processes;

import compiler.pipeline.instantiate.factory.processes.MockProcessFactory;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import layers.LayerManager;
import processes.*;

/**
 * Created by dbborens on 8/10/2015.
 */
public class MockProcessLoader extends ProcessLoader<MockProcess> {
    private final MockProcessFactory factory;
    private final MockProcessInterpolator interpolator;

    public MockProcessLoader() {
        factory = new MockProcessFactory();
        interpolator = new MockProcessInterpolator();
    }

    public MockProcessLoader(MockProcessFactory factory,
                             MockProcessInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public MockProcess instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        BaseProcessArguments base = interpolator.arguments(node, lm, p);
        factory.setArguments(base);

        int count = interpolator.count(node, p.getRandom());
        factory.setCount(count);

        String identifier = interpolator.identifier(node);
        factory.setIdentifier(identifier);

        double weight = interpolator.weight(node, p.getRandom());
        factory.setWeight(weight);

        return factory.build();
    }
}
