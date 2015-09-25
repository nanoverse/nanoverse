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

package nanoverse.compiler.pipeline.translate.nodes;

import nanoverse.compiler.pipeline.translate.symbol.*;

import java.util.stream.Stream;


/**
 * MapObjectNode represents a Java object whose members
 * have definite names. This is generally everything except
 * a Collection.
 * <p>
 * Created by dbborens on 2/22/15.
 */
public class MapObjectNode implements ObjectNode {

    private final LocalContextMap local;
    private final MapSymbolTable symbolTable;

    public MapObjectNode(MapSymbolTable symbolTable) {
        this(symbolTable, new LocalContextMap());
    }

    public MapObjectNode(MapSymbolTable symbolTable, LocalContextMap local) {
        this.symbolTable = symbolTable;
        this.local = local;
    }

    public void loadMember(String identifier, ObjectNode value) {
        local.loadMember(identifier, value);
    }

    public Stream<String> getMemberIdentifiers() {
        return local.getMemberIdentifiers();
    }

    public ObjectNode getMember(String identifier) {
        return local.getMember(identifier);
    }

    public boolean hasMember(String identifier) {
        return local.hasMember(identifier);
    }

    public ResolvingSymbolTable getSymbolTableFor(String identifier) {
        return symbolTable.getSymbolTable(identifier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapObjectNode that = (MapObjectNode) o;

        if (!local.equals(that.local)) return false;
        if (!symbolTable.equals(that.symbolTable)) return false;

        return true;
    }

    @Override
    public Class getInstantiatingClass() {
        return symbolTable.getInstanceClass();
    }

    public MapSymbolTable getSymbolTable() {
        return symbolTable;
    }

}
