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
package compiler.pipeline.instantiate.factory.io.visual.highlight;

import java.util.Map;
import io.visual.highlight.HighlightManager;
import compiler.pipeline.instantiate.factory.Factory;

public class HighlightManagerFactory implements Factory<HighlightManager> {

    private final HighlightManagerFactoryHelper helper;

    private Map glyphMap;

    public HighlightManagerFactory() {
        helper = new HighlightManagerFactoryHelper();
    }

    public HighlightManagerFactory(HighlightManagerFactoryHelper helper) {
        this.helper = helper;
    }

    public void setGlyphMap(Map glyphMap) {
        this.glyphMap = glyphMap;
    }

    @Override
    public HighlightManager build() {
        return helper.build(glyphMap);
    }
}