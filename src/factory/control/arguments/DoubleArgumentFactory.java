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

package factory.control.arguments;

import control.arguments.*;
import control.halt.HaltCondition;
import org.dom4j.Element;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by David B Borenstein on 4/7/14.
 */
public abstract class DoubleArgumentFactory {

    public static DoubleArgument instantiate(Element e, String name, double defaultValue, Random random) {
        // No element --> return default
        Element valueElement = e.element(name);
        if (valueElement == null) {
            return new ConstantDouble(defaultValue);
        }

        return instantiate(e, name, random);
    }

    public static DoubleArgument instantiate(Element e, String name, Random random) {

        // No element --> return default
        Element valueElement = e.element(name);
        if (valueElement == null) {
            throw new IllegalArgumentException("No value specified for " +
                    "expected field '" + name + "' in element " +
                    e.getQualifiedName());
        }

        // No children --> return text
        Iterator children = valueElement.elementIterator();
        if (!children.hasNext()) {
            return getConstant(valueElement);
        }

        // Instantiate child
        Element stochastic = (Element) children.next();
        String className = stochastic.getName();

        if (className.equalsIgnoreCase("constant")) {
            return getConstant(stochastic);
        } else if (className.equalsIgnoreCase("uniform")) {
            return getUniformDouble(stochastic, random);
        } else {
            throw new IllegalArgumentException("Unrecognized argument type '" + className + "'");
        }
    }

    private static DoubleArgument getUniformDouble(Element valueElement, Random random) {
        double min, max;

        try {
            min = instantiate(valueElement, "min", random).next();
            max = instantiate(valueElement, "max", random).next();
        } catch (HaltCondition ex) {
            throw new IllegalStateException(ex);
        }
        DoubleArgument ret = new UniformDouble(min, max, random);
        return ret;
    }

    private static DoubleArgument getConstant(Element e) {
        String valueText = e.getTextTrim();
        double ret = Double.valueOf(valueText);
        return new ConstantDouble(ret);
    }
}
