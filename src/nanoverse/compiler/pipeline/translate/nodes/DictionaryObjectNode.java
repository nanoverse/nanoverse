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

package nanoverse.compiler.pipeline.translate.nodes;

import nanoverse.compiler.pipeline.translate.symbol.DictionarySymbolTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

/**
 * Created by dbborens on 2/22/15.
 */
public class DictionaryObjectNode implements ObjectNode {
    private final Logger logger;

    private final DictionarySymbolTable symbolTable;
    private final LocalContextMap local;
    private final int lineNumber;

    public DictionaryObjectNode(DictionarySymbolTable symbolTable, int lineNumber) {
        this(symbolTable, new LocalContextMap(), lineNumber);
    }

    public DictionaryObjectNode(DictionarySymbolTable symbolTable, LocalContextMap local, int lineNumber) {
        logger = LoggerFactory.getLogger(DictionaryObjectNode.class);

        this.symbolTable = symbolTable;
        this.local = local;
        this.lineNumber = lineNumber;

        logger.debug("Dictionary object on line number {}", lineNumber);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DictionaryObjectNode that = (DictionaryObjectNode) o;

        if (!local.equals(that.local)) return false;
        if (!symbolTable.equals(that.symbolTable)) return false;

        return true;
    }

    public int getLineNumber() { return lineNumber; }

    @Override
    public Class getInstantiatingClass() {
        return symbolTable.getBroadClass();
    }

    public DictionarySymbolTable getSymbolTable() {
        return symbolTable;
    }
}
