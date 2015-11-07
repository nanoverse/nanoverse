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

package nanoverse.compiler.pipeline.instantiate.loader.io.serialize;

import nanoverse.compiler.pipeline.instantiate.factory.io.serialize.OutputManagerFactory;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.serialize.*;
import nanoverse.runtime.layers.LayerManager;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dbborens on 8/1/2015.
 */
public class OutputManagerLoader extends Loader<SerializationManager> {
    private final OutputManagerFactory factory;
    private final OutputManagerChildLoader interpolator;

    public OutputManagerLoader() {
        factory = new OutputManagerFactory();
        interpolator = new OutputManagerChildLoader();
    }

    public OutputManagerLoader(OutputManagerFactory factory, OutputManagerChildLoader interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    public SerializationManager instantiate(ListObjectNode node, GeneralParameters p, LayerManager layerManager) {
        List<Serializer> writers = node.getMemberStream()
            .map(o -> (MapObjectNode) o)
            .map(cNode -> interpolator.output(cNode, p, layerManager))
            .collect(Collectors.toList());

        factory.setLayerManager(layerManager);
        factory.setP(p);
        factory.setWriters(writers);

        return factory.build();
    }

    public SerializationManager instantiate(GeneralParameters p, LayerManager lm) {
        factory.setLayerManager(lm);
        factory.setP(p);

        List<Serializer> writers = new ArrayList<>();
        factory.setWriters(writers);

        return factory.build();
    }

}
