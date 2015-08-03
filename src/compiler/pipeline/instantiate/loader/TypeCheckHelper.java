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

package compiler.pipeline.instantiate.loader;

import compiler.pipeline.translate.nodes.ObjectNode;

/**
 * Created by dbborens on 8/1/2015.
 */
public class TypeCheckHelper {

    private final Class expected;

    public TypeCheckHelper(Class expected) {
        this.expected = expected;
    }

    protected void checkReturnClass(ObjectNode node) {
        Class actual = node.getInstantiatingClass();
        if (!actual.isAssignableFrom(expected)) {
            throw new IllegalStateException("Unexpected object node " + actual.getSimpleName() + " in loader for " + expected.getSimpleName());
        }
        if (!expected.isAssignableFrom(actual)) {
            throw new IllegalStateException("Unexpected object node " + actual.getSimpleName() + " in loader for " + expected.getSimpleName());
        }
    }

}
