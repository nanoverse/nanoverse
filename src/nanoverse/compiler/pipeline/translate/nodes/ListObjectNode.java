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

import nanoverse.compiler.pipeline.translate.symbol.ListSymbolTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

/**
 * Created by dbborens on 2/22/15.
 */
public class ListObjectNode implements ObjectNode {
    private final Logger logger;

    private final ListSymbolTable symbolTable;
    private final LocalContextList local;
    private final int lineNumber;

    public ListObjectNode(ListSymbolTable symbolTable, int lineNumber) {
        this(symbolTable, new LocalContextList(), lineNumber);
    }

    public ListObjectNode(ListSymbolTable symbolTable, LocalContextList local, int lineNumber) {
        logger = LoggerFactory.getLogger(ListObjectNode.class);

        this.symbolTable = symbolTable;
        this.local = local;
        this.lineNumber = lineNumber;

        logger.debug("Map object on line number {}", lineNumber);
    }

    public Stream<ObjectNode> getMemberStream() {
        return local.getMembers();
    }

    public ObjectNode getMember(int index) {
        if (index >= size()) {
            throw new IllegalStateException("List context member index out of bounds.");
        }
        return local.get(index);
    }

    public int size() {
        return local.size();
    }

    public void loadMember(ObjectNode value) {
        local.loadMember(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListObjectNode that = (ListObjectNode) o;

        if (!local.equals(that.local)) return false;
        if (!symbolTable.equals(that.symbolTable)) return false;

        return true;
    }

    public int lineNumber() { return lineNumber; }

    @Override
    public Class getInstantiatingClass() {
        return symbolTable.getBroadClass();
    }

    public ListSymbolTable getSymbolTable() {
        return symbolTable;
    }
}
