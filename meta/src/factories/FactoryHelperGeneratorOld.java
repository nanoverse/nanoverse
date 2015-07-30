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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by dbborens on 7/30/2015.
 */
public class FactoryHelperGeneratorOld {

    private final STGroup group;

    public FactoryHelperGeneratorOld() {
        group = new STGroupFile("meta/templates/factory/helper.stg", '$', '$');
    }

    public String generate(Class clazz) {
        ST template = group.getInstanceOf("file");
        names(template, clazz);
        constructorElems(template, clazz);
        String ret = template.render();
        return ret;
    }

    private void constructorElems(ST template, Class clazz) {
        Constructor constructor = getFactoryConstructor(clazz);
        Parameter[] cParams = constructor.getParameters();
        imports(template, cParams, clazz);
        parameters(template, cParams);
        arguments(template, cParams);
    }

    private void imports(ST template, Parameter[] cParams, Class clazz) {
        String line = "import " + clazz.getCanonicalName() + ";";
        template.add("imports", line);

        for (Parameter p : cParams) {
            line = "import " + p.getType().getCanonicalName() + ";";
            template.add("imports", line);
        }
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

    private void names(ST template, Class clazz) {
        String className = clazz.getSimpleName();
        template.add("className", className);
        template.add("displayName", className);
    }

    private void arguments(ST template, Parameter[] cParams) {
        for(Parameter p : cParams) {
            checkNamePresent(p);
            template.add("arguments", p.getName());
        }
    }

    // http://stackoverflow.com/questions/2237803/can-i-obtain-method-parameter-name-using-java-reflection
    private void parameters(ST template, Parameter[] cParams) {
        for(Parameter p : cParams) {
            checkNamePresent(p);
            String pClass = p.getType().getSimpleName();
            String pName = p.getName();
            String signature = pClass + " " + pName;
            template.add("parameters", signature);
        }
    }

    private void checkNamePresent(Parameter p) {
        if (!p.isNamePresent()) {
            throw new IllegalStateException("Parameter names are " +
                    "not present. Try using -parameter argument " +
                    "at Java compile time.");
        }

    }
}