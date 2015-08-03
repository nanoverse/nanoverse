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

package compiler.pipeline.instantiate;

import com.google.common.reflect.TypeToken;

/**
 * Created by dbborens on 7/29/2015.
 */
public abstract class Loader<T> {

    private final TypeToken<T> type = new TypeToken<T>(getClass()) {};
    protected final TypeCheckHelper helper;

    public Loader() {
        helper = new TypeCheckHelper(type.getRawType());
    }

    public Class getInstanceClass() {
        return type.getRawType();
    }

//    protected final ObjectNode node;
//    protected final Factory<T> factory;
//
//    protected abstract Factory<T> resolveFactory();
//
//    public Loader(ObjectNode node) {
//        this.node = node;
//        this.factory = resolveFactory();
//    }
//
//    public Loader(ObjectNode node, Factory<T> factory) {
//        this.node = node;
//        this.factory = factory;
//    }
//
//    protected void checkReturnClass() {
//        Class actual = node.getInstantiatingClass();
//        Class expected = type.getRawType();
//        if (!actual.isAssignableFrom(expected)) {
//            throw new IllegalStateException("Unexpected object node " + actual.getSimpleName() + " in loader for " + expected.getSimpleName());
//        }
//        if (!expected.isAssignableFrom(actual)) {
//            throw new IllegalStateException("Unexpected object node " + actual.getSimpleName() + " in loader for " + expected.getSimpleName());
//        }
//    }
//
//    protected ObjectNode getIntegerWithDefault(MapObjectNode node, String property, int defaultValue) {
//        PrimitiveSymbolTable stDefault = new PrimitiveIntegerSymbolTable();
//        PrimitiveObjectNode mDefault = new PrimitiveIntegerNode(stDefault, defaultValue);
//        return getObjectWithDefault(node, property, mDefault);
//    }
//
//    protected ObjectNode getDoubleWithDefault(MapObjectNode node, String property, double defaultValue) {
//        PrimitiveSymbolTable stDefault = new PrimitiveDoubleSymbolTable();
//        PrimitiveObjectNode mDefault = new PrimitiveDoubleNode(stDefault, defaultValue);
//        return getObjectWithDefault(node, property, mDefault);
//    }
//
//    protected ObjectNode getBooleanWithDefault(MapObjectNode node, String property, boolean defaultValue) {
//        PrimitiveSymbolTable stDefault = new PrimitiveDoubleSymbolTable();
//        PrimitiveObjectNode mDefault = new PrimitiveBooleanNode(stDefault, defaultValue);
//        return getObjectWithDefault(node, property, mDefault);
//    }
//
//    protected ObjectNode getStringWithDefault(MapObjectNode node, String property, String defaultValue) {
//        PrimitiveSymbolTable stDefault = new StringInstSymbolTable();
//        PrimitiveObjectNode mDefault = new PrimitiveStringNode(stDefault, defaultValue);
//        return getObjectWithDefault(node, property, mDefault);
//    }
//
//    protected ObjectNode getObjectWithDefault(MapObjectNode node, String property, ObjectNode defaultObject) {
//        if (node.hasMember(property)) {
//            return node.getMember(property);
//        } else {
//            return defaultObject;
//        }
//    }
//
}
