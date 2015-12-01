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

package loaders;

import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import factories.FactoryTargetHelper;
import org.stringtemplate.v4.*;

import java.util.stream.*;

/**
 * Created by dbborens on 8/1/2015.
 */
public class LoaderSkeletonGenerator {
    private final STGroup group;

    public LoaderSkeletonGenerator() {
        group = new STGroupFile("meta/templates/loader/skeleton.stg", '$', '$');
    }

    public String generate(MapSymbolTable st, int lineNumber) {
        ST template = group.getInstanceOf("file");
        System.out.println(st);
        template.add("class", st.getInstanceClass());
        template.add("loaderClass", resolveLoaderClass(st));
        members(st, template);
        mTypes(st, template, lineNumber);
        displayName(st, template);
        return template.render();
    }

    private void displayName(MapSymbolTable st, ST template) {
        String displayName = st.getClass().getSimpleName().replace("SymbolTable", "");
        template.add("displayName", displayName);
    }

    private void mTypes(MapSymbolTable st, ST template, int lineNumber) {
        // Bug in IntelliJ IDEA -- got cast error unless I explicitly defined
        // this stream as a Stream<String> and then used it
        Stream<String> stream = st.getMemberNames();
        stream.map(name -> st.getSymbolTable(name, lineNumber))
                .map(rst -> rst.getBroadClass())
                .map(clazz -> clazz.getCanonicalName())
                .collect(Collectors.toSet())
                .stream()
                .forEach(clazz -> template.add("mTypes", clazz));
    }

    private void members(MapSymbolTable st, ST template) {
        st.getMemberNames()
                .forEach(name -> template.add("members", name));
    }

    private String resolveLoaderClass(MapSymbolTable st) {
        Class superclass = st.getClass().getSuperclass();
        String className = st.getClass().getSimpleName();
        if (superclass.equals(Object.class)) {
            return "Loader<" + className +">";
        } else {
            return superclass.getSimpleName() + "Loader<" + className + ">";
        }
    }

//    public String generate(Constructor c) {
//        ST template = group.getInstanceOf("file");
//        template.add("class", c.getDeclaringClass());
//        constructorArgs(template, c);
//        return template.render();
//    }
//
//    private void constructorArgs(ST template, Constructor c) {
//        String displayName = helper.getDisplayName(c);
//        template.add("displayName", displayName);
//        params(template, c);
//
//    }
//
//    private void params(ST template, Constructor constructor) {
//        Parameter[] cParams = constructor.getParameters();
//        pValues(template, cParams);
//        pTypes(template, cParams, constructor);
//    }
//
//    private void pValues(ST template, Parameter[] cParams) {
//        for (Parameter p : cParams) {
//            template.add("parameters", p);
//        }
//    }
//
//    /**
//     * Populate the set of distinct classes dealt with in this nanoverse.runtime.factory
//     * (for import statement)
//     */
//    private void pTypes(ST template, Parameter[] cParams, Constructor constructor) {
//        HashSet<Class> classes = new HashSet<>();
//        for (Parameter p : cParams) {
//            classes.add(p.getType());
//        }
//
//        for (Class clazz : classes) {
//            template.add("pTypes", clazz);
//        }
//
//        // Now add the class of the object to be built
//        template.add("pTypes", constructor.getDeclaringClass());
//    }
}
