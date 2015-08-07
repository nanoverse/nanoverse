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

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by dbborens on 8/7/15.
 */
public abstract class ImportHelper {

    public static Stream<String> getImports(Constructor c) {
        Stream<Class> cClazz = Stream.of(c.getDeclaringClass());

        Stream<Class> cStream = Arrays.asList(c.getParameters())
                .stream()
                .map(p -> p.getType());

        Stream<Class> aStream = Stream.concat(cClazz, cStream);

        return getImports(aStream);
    }

    public static Stream<String> getImports(Stream<Class> classStream) {
        return classStream.filter(t -> !t.isPrimitive())
                .filter(t -> !t.isArray())
                .map(c -> c.getCanonicalName())
                .filter(str -> !str.startsWith("java.lang"))
                .collect(Collectors.toSet())
                .stream();
    }

}
