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

import nanoverse.compiler.error.UserError;
import nanoverse.compiler.pipeline.translate.symbol.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;


/**
 * MapObjectNode represents a Java object whose members
 * have definite names. This is generally everything except
 * a Collection.
 * <p>
 * Created by dbborens on 2/22/15.
 */
public class MapObjectNode implements ObjectNode {
    private final Logger logger;

    private final LocalContextMap local;
    private final MapSymbolTable symbolTable;
    private final int lineNumber;

    public MapObjectNode(MapSymbolTable symbolTable, int lineNumber) {
        this(symbolTable, new LocalContextMap(), lineNumber);
    }

    public MapObjectNode(MapSymbolTable symbolTable, LocalContextMap local, int lineNumber) {
        logger = LoggerFactory.getLogger(MapObjectNode.class);

        this.symbolTable = symbolTable;
        this.local = local;
        this.lineNumber = lineNumber;

        logger.debug("Map object on line number {}", lineNumber);
    }

    public void loadMember(String identifier, ObjectNode value) {
        local.loadMember(identifier, value);
    }

    public Stream<String> getMemberIdentifiers() {
        return local.getMemberIdentifiers();
    }

    public ObjectNode getMember(String identifier) {
        if (!(local.hasMember(identifier))) {
            throw new UserError("Missing argument \"" + identifier + "\" in object " +
                symbolTable.getInstanceClass().getSimpleName());
        }

        return local.getMember(identifier);
    }

    public boolean hasMember(String identifier) {
        return local.hasMember(identifier);
    }

    public ResolvingSymbolTable getSymbolTableFor(String identifier) {
        return symbolTable.getSymbolTable(identifier, lineNumber);
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

    public int getLineNumber() { return lineNumber; }

    @Override
    public Class getInstantiatingClass() {
        return symbolTable.getInstanceClass();
    }

    public MapSymbolTable getSymbolTable() {
        return symbolTable;
    }

}
