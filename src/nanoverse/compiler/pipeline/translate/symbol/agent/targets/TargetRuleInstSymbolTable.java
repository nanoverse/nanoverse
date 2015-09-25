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

package nanoverse.compiler.pipeline.translate.symbol.agent.targets;

import com.google.common.reflect.TypeToken;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.processes.discrete.filter.FilterClassSymbolTable;
import nanoverse.runtime.agent.targets.TargetDescriptor;

import java.util.HashMap;

/**
 * Created by dbborens on 7/23/2015.
 */
public abstract class TargetRuleInstSymbolTable<T extends TargetDescriptor> extends MapSymbolTable<T> {
    private final TypeToken<T> targetRuleClass = new TypeToken<T>(getClass()) {
    };

    public Class getTargetRuleClass() {
        return targetRuleClass.getRawType();
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        maximum(ret);
        filter(ret);
        return ret;
    }

    private void filter(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new FilterClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Filters to apply to the base target rule.");
        ret.put("filters", ms);
    }

    private void maximum(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Maximum number of targets " +
            "to select. If maximum is set to -1, no maximum will be used.");
        ret.put("maximum", ms);

    }
}
