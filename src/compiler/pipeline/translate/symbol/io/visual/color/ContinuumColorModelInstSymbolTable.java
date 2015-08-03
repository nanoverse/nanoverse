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

package compiler.pipeline.translate.symbol.io.visual.color;

import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.translate.nodes.ObjectNode;
import compiler.pipeline.translate.symbol.*;
import compiler.pipeline.translate.symbol.primitive.booleans.BooleanClassSymbolTable;
import compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import io.visual.color.NormalizedContinuumColorManager;

import java.util.HashMap;

/**
 * Created by dbborens on 7/27/2015.
 */
public class ContinuumColorModelInstSymbolTable extends MapSymbolTable<NormalizedContinuumColorManager> {

    @Override
    public String getDescription() {
        return "The continuum color model adjusts the hue, luminance and " +
                "saturation of each site based on the state of a specified " +
                "continuum. Values are normalized to the observed minimum " +
                "and maximum of the continuum.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
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
    public Loader getLoader(ObjectNode node) {
        return null;
    }
}
