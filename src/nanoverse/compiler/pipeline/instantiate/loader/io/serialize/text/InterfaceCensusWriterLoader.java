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

package nanoverse.compiler.pipeline.instantiate.loader.io.serialize.text;

import nanoverse.compiler.pipeline.instantiate.factory.io.serialize.text.InterfaceCensusWriterFactory;
import nanoverse.compiler.pipeline.instantiate.loader.io.serialize.OutputLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.io.serialize.text.InterfaceCensusWriter;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 8/10/2015.
 */
public class InterfaceCensusWriterLoader extends OutputLoader<InterfaceCensusWriter> {

    private final InterfaceCensusWriterFactory factory;
    private final InterfaceCensusWriterInterpolator interpolator;

    public InterfaceCensusWriterLoader() {
        factory = new InterfaceCensusWriterFactory();
        interpolator = new InterfaceCensusWriterInterpolator();
    }

    public InterfaceCensusWriterLoader(InterfaceCensusWriterFactory factory,
                                       InterfaceCensusWriterInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public Serializer instantiate(MapObjectNode node, GeneralParameters p, LayerManager layerManager) {
        factory.setP(p);
        factory.setLm(layerManager);

        String focus = interpolator.focus(node);
        factory.setFocus(focus);

        return factory.build();
    }
}
