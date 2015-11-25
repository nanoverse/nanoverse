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

package nanoverse.compiler.pipeline.instantiate.loader.io.visual.color.palette;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.color.ColorLoader;
import nanoverse.compiler.pipeline.instantiate.loader.primitive.strings.StringArgumentLoader;
import nanoverse.compiler.pipeline.translate.nodes.DictionaryObjectNode;
import nanoverse.compiler.pipeline.translate.nodes.ObjectNode;
import nanoverse.runtime.control.GeneralParameters;

import java.awt.*;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by dbborens on 11/25/2015.
 */
public class ColorMapLoader extends Loader<Map<String, Color>> {

    private final ColorLoader colorLoader;

    public ColorMapLoader() {
        colorLoader = new ColorLoader();
    }

    public ColorMapLoader(ColorLoader colorLoader) {
        this.colorLoader = colorLoader;
    }

    public Map<String, Color> instantiate(DictionaryObjectNode cNode, GeneralParameters p) {
        Map<String, Color> mappings = cNode.getMemberIdentifiers()
                .collect(Collectors.toMap(
                        Function.identity(),
                        name -> resolveColor(name, cNode, p)));

        return mappings;
    }

    private Color resolveColor(String name, DictionaryObjectNode cNode, GeneralParameters p) {
        ObjectNode colorNode = cNode.getMember(name);
        StringArgumentLoader loader = (StringArgumentLoader) colorNode.getSymbolTable().getLoader();
        String colorName = loader.instantiateToFirst(colorNode);
        Color color = colorLoader.instantiate(colorName, p);
        return color;
    }
}
