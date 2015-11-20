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

package nanoverse.compiler.pipeline.translate.symbol.io.visual.color;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.color.ContinuumColorModelLoader;
import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.MemberSymbol;
import nanoverse.compiler.pipeline.translate.symbol.ResolvingSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.booleans.BooleanClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import nanoverse.runtime.io.visual.color.ContinuumColorModel;

import java.util.HashMap;

/**
 * Created by dbborens on 7/27/2015.
 */
public class ContinuumColorModelInstSymbolTable extends MapSymbolTable<ContinuumColorModel> {

    @Override
    public String getDescription() {
        return "The continuum color model adjusts the hue, luminance and " +
            "saturation of each site based on the state of a specified " +
            "continuum. Values are normalized to the observed minimum " +
            "and maximum of the continuum.";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        id(ret);
        base(ret);
        minHue(ret);
        maxHue(ret);
        minSat(ret);
        maxSat(ret);
        minLum(ret);
        maxLum(ret);
        useLuminanceAverage(ret);
        return ret;
    }

    private void id(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The id of the layer with " +
            "which this color model should be associated.");
        ret.put("id", ms);
    }

    public void base(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new ColorModelClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The color model upon which " +
            "this color model should be overlaid, if any.");
        ret.put("base", ms);
    }

    public void minHue(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The minimum hue value (between 0 and 1 inclusive).");
        ret.put("minHue", ms);
    }

    public void maxHue(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The maximum hue value (between 0 and 1 inclusive).");
        ret.put("maxHue", ms);
    }

    public void minSat(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The minimum saturation value (between 0 and 1 inclusive).");
        ret.put("minSat", ms);
    }

    public void maxSat(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The maximum saturation value (between 0 and 1 inclusive).");
        ret.put("maxSat", ms);
    }

    public void minLum(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The minimum luminance value (between 0 and 1 inclusive).");
        ret.put("minLum", ms);
    }

    public void maxLum(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The maximum luminance value (between 0 and 1 inclusive).");
        ret.put("maxLum", ms);
    }

    public void useLuminanceAverage(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new BooleanClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "If true, color models are " +
            "composited by averaging over their luminance; if false, the " +
            "product of the luminance is used.");
        ret.put("useLuminanceAverage", ms);
    }

    @Override
    public Loader getLoader() {
        return new ContinuumColorModelLoader();
    }
}
