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
import structural.annotations.FactoryTarget;

import java.lang.reflect.*;
import java.util.*;

/**
 * Created by dbborens on 7/30/2015.
 */
public class FactoryHelperGenerator {
    private final STGroup group;

    public FactoryHelperGenerator() {
        group = new STGroupFile("meta/templates/factory/helper.stg", '$', '$');
    }

    public String generate(Class clazz) {
        ST template = group.getInstanceOf("file");
        template.add("class", clazz);
        constructorArgs(template, clazz);
        return template.render();
    }

    private void constructorArgs(ST template, Class clazz) {
        Constructor constructor = getFactoryConstructor(clazz);
        String displayName = getDisplayName(constructor);
        template.add("displayName", displayName);
        params(template, constructor);

    }

    private void params(ST template, Constructor constructor) {
        Parameter[] cParams = constructor.getParameters();
        pValues(template, cParams);
        pTypes(template, cParams, constructor);
    }

    private void pValues(ST template, Parameter[] cParams) {
        for (Parameter p : cParams) {
            template.add("parameters", p);
        }
    }

    /**
     * Populate the set of distinct classes dealt with in this factory
     * (for import statement)
     */
    private void pTypes(ST template, Parameter[] cParams, Constructor constructor) {
        HashSet<Class> classes = new HashSet<>();
        for (Parameter p : cParams) {
            classes.add(p.getType());
        }

        for (Class clazz : classes) {
            template.add("pTypes", clazz);
        }

        // Now add the class of the object to be built
        template.add("pTypes", constructor.getDeclaringClass());
    }

    private String getDisplayName(Constructor c) {
        FactoryTarget ft = (FactoryTarget) Arrays.stream(c.getDeclaredAnnotations())
                .filter(a -> a instanceof FactoryTarget)
                .findFirst()
                .get();

        // If the user did not specify a display name, use the original name
        if (ft.displayName() == "") {
            return c.getDeclaringClass().getSimpleName();
        }

        return ft.displayName();
    }

    private Constructor getFactoryConstructor(Class clazz) {
        try {
            return Arrays.stream(clazz.getConstructors())
                    .filter(c -> annotated(c))
                    .findFirst()
                    .get();
        } catch (NoSuchElementException ex) {
            throw new IllegalStateException("No @FactoryTarget constructor on class "
                    + clazz.getSimpleName());
        }
    }

    private boolean annotated(Constructor c) {
        return Arrays.stream(c.getDeclaredAnnotations())
                .anyMatch(annotation ->
                        annotation instanceof FactoryTarget);
    }
}
