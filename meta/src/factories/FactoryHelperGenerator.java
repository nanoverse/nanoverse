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

import org.stringtemplate.v4.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by dbborens on 7/30/2015.
 */
public class FactoryHelperGenerator {
    private final STGroup group;
    private FactoryTargetHelper helper;

    public FactoryHelperGenerator() {
        helper = new FactoryTargetHelper();
        group = new STGroupFile("meta/templates/factory/helper.stg", '$', '$');
    }

    public String generate(Constructor c) {
        ST template = group.getInstanceOf("file");
        template.add("class", c.getDeclaringClass());
        constructorArgs(template, c);
        return template.render();
    }

    private void constructorArgs(ST template, Constructor c) {
        String displayName = helper.getDisplayName(c);
        template.add("displayName", displayName);
        params(template, c);

    }

    private void params(ST template, Constructor constructor) {
        Parameter[] cParams = constructor.getParameters();
        pValues(template, cParams);
        pTypes(template, constructor);
    }

    private void pValues(ST template, Parameter[] cParams) {
        for (Parameter p : cParams) {
            template.add("parameters", p);
        }
    }

    /**
     * Populate the set of distinct classes dealt with in this nanoverse.runtime.factory
     * (for import statement)
     */
    private void pTypes(ST template, Constructor constructor) {
        ImportHelper.getImports(constructor)
                .forEach(imp -> template.add("pTypes", imp));
    }

}
