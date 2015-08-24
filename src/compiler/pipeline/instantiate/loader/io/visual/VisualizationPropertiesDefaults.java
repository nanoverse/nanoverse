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

package compiler.pipeline.instantiate.loader.io.visual;

import compiler.pipeline.instantiate.loader.io.visual.color.IndexedColorModelLoader;
import compiler.pipeline.instantiate.loader.io.visual.highlight.*;
import control.GeneralParameters;
import io.visual.color.*;
import io.visual.highlight.HighlightManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by dbborens on 8/21/2015.
 */
public class VisualizationPropertiesDefaults {

    public Integer edge() {
        return 10;
    }

    public Integer outline() {
        return 1;
    }

    public ColorManager color(GeneralParameters p) {
        IndexedColorModelLoader loader = new IndexedColorModelLoader();
        return loader.instantiate(p);
    }

    public HighlightManager highlights(GeneralParameters p) {
        HighlightManagerLoader loader = new HighlightManagerLoader();
        return loader.instantiate(p);
    }
}
