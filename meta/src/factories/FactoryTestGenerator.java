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

package factories;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.lang.reflect.Constructor;

/**
 * Created by dbborens on 8/7/15.
 */
public class FactoryTestGenerator {

    private final STGroup group;
    private final FactoryLoadHelper helper;

    public FactoryTestGenerator() {
        group = new STGroupFile("meta/templates/nanoverse.runtime.factory/test.stg", '$', '$');
        helper = new FactoryLoadHelper();
    }

    public String generate(Constructor c) {
        StringBuilder sb = new StringBuilder();
        appendPreamble(c, sb);
        appendTests(c, sb);
        appendTerminus(c, sb);
        return sb.toString();
    }

    private void appendPreamble(Constructor c, StringBuilder sb) {
        ST template = group.getInstanceOf("preamble");
        helper.loadClazz(template, c);
        helper.loadParams(template, c);
        helper.loadImports(template, c);
        String str = template.render();
        sb.append(str);
    }

    private void appendTests(Constructor c, StringBuilder sb) {

    }

    private void appendTerminus(Constructor c, StringBuilder sb) {

    }


}
