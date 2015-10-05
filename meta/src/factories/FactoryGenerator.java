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
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by dbborens on 8/6/15.
 */
public class FactoryGenerator {

    private final STGroup group;
    private final FactoryLoadHelper helper;

    public FactoryGenerator() {
        helper = new FactoryLoadHelper();
        group = new STGroupFile("meta/templates/factory/factory.stg", '$', '$');
    }

    public String generate(Constructor c) {
        ST template = group.getInstanceOf("file");
        helper.loadClazz(template, c);
        helper.loadParams(template, c);
        helper.loadImports(template, c);
        return template.render();
    }


}
