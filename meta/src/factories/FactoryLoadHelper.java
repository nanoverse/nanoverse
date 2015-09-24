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

package factories;

import org.stringtemplate.v4.ST;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * Created by dbborens on 8/7/15.
 */
public class FactoryLoadHelper {
    private final FactoryTargetHelper helper;

    public FactoryLoadHelper() {
        helper = new FactoryTargetHelper();
    }

    public void loadClazz(ST template, Constructor c) {
        String dUpper = helper.getDisplayName(c);
        String dLower = lowerize(dUpper);
        Class clazz = c.getDeclaringClass();
        String pkg = clazz.getPackage().getName();
        String canonical = clazz.getCanonicalName();
        String simple = clazz.getSimpleName();

        ClazzStruct cs = new ClazzStruct(canonical, dLower, dUpper, pkg, simple);
        template.add("clazz", cs);
    }

    private String upperize(String camel) {
        String firstUpper = camel.substring(0, 1).toUpperCase();
        String upper = firstUpper + camel.substring(1);
        return upper;
    }

    private String lowerize(String camel) {
        String firstLower = camel.substring(0, 1).toLowerCase();
        String lower = firstLower + camel.substring(1);
        return lower;
    }

    private ParameterStruct pStruct(Parameter p) {
        String lower = p.getName();
        String upper = upperize(lower);
        Class clazz = p.getType();
        return new ParameterStruct(lower, upper, clazz);
    }

    public void loadImports(ST template, Constructor c) {
        ImportHelper.getImports(c)
                .forEach(imp -> template.add("imports", imp));

        template.add("imports", "compiler.pipeline.instantiate.factory.Factory");
    }

    public void loadParams(ST template, Constructor c) {
        Parameter[] cParams = c.getParameters();

        Arrays.asList(cParams)
                .stream()
                .map(this::pStruct)
                .forEach(ps -> template.add("params", ps));
    }

}
