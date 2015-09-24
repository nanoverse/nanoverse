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

package compiler.pipeline.translate.nodes;

import compiler.pipeline.translate.symbol.DictionarySymbolTable;

import java.util.stream.Stream;

/**
 * Created by dbborens on 2/22/15.
 */
public class DictionaryObjectNode implements ObjectNode {

    private final DictionarySymbolTable symbolTable;
    private final LocalContextMap local;

    public DictionaryObjectNode(DictionarySymbolTable symbolTable) {
        this(symbolTable, new LocalContextMap());
    }

    public DictionaryObjectNode(DictionarySymbolTable symbolTable, LocalContextMap local) {
        this.symbolTable = symbolTable;
        this.local = local;
    }

    public Stream<String> getMemberIdentifiers() {
        return local.getMemberIdentifiers();
    }

    public ObjectNode getMember(String identifier) {
        return local.getMember(identifier);
    }

    public void loadMember(String identifier, ObjectNode value) {
        local.loadMember(identifier, value);
    }

    public int size() {
        return local.size();
    }

    public DictionarySymbolTable getSymbolTable() {
        return symbolTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DictionaryObjectNode that = (DictionaryObjectNode) o;

        if (!local.equals(that.local)) return false;
        if (!symbolTable.equals(that.symbolTable)) return false;

        return true;
    }

    @Override
    public Class getInstantiatingClass() {
        return symbolTable.getBroadClass();
    }
}
