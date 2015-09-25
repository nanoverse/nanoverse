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

import nanoverse.compiler.pipeline.instantiate.factory.io.serialize.text.CorrelationWriterFactory;
import nanoverse.compiler.pipeline.instantiate.loader.io.serialize.OutputLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.io.serialize.text.CorrelationWriter;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 8/10/2015.
 */
public class CorrelationWriterLoader extends OutputLoader<CorrelationWriter> {
    private final CorrelationWriterFactory factory;
    private final CorrelationWriterInterpolator interpolator;

    public CorrelationWriterLoader() {
        factory = new CorrelationWriterFactory();
        interpolator = new CorrelationWriterInterpolator();
    }

    public CorrelationWriterLoader(CorrelationWriterFactory factory,
                                   CorrelationWriterInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public Serializer instantiate(MapObjectNode node, GeneralParameters p, LayerManager layerManager) {
        factory.setP(p);
        factory.setLm(layerManager);

        String filename = interpolator.filename(node);
        factory.setFilename(filename);

        DoubleArgument triggerTimeArg = interpolator.time(node, p.getRandom());
        factory.setTriggerTimeArg(triggerTimeArg);

        return factory.build();
    }
}
