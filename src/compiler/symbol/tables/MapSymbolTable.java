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

package compiler.symbol.tables;

import com.google.common.reflect.TypeToken;
import compiler.error.UnrecognizedIdentifierError;
import compiler.pipeline.translate.nodes.*;
import compiler.symbol.symbols.MemberSymbol;
import org.slf4j.*;

import java.util.HashMap;
import java.util.function.Supplier;

/** * Created by dbborens on 3/3/15.
 */
public abstract class MapSymbolTable<T> implements InstantiableSymbolTable {
    protected final HashMap<String, MemberSymbol> requiredMembers;
    protected final Logger logger;
    private final TypeToken<T> type = new TypeToken<T>(getClass()) {};

    public MapSymbolTable() {
        requiredMembers = resolveMembers();
        logger = LoggerFactory.getLogger(MapSymbolTable.class);
    }

    protected abstract HashMap<String, MemberSymbol> resolveMembers();

    public ResolvingSymbolTable getSymbolTable(String identifier) {
        logger.debug("Resolving {}::{}",
                getInstanceClass().getSimpleName(), identifier);

        if (requiredMembers.containsKey(identifier)) {
            return requiredMembers.get(identifier).getSymbolTable();
        }

        throw new UnrecognizedIdentifierError(identifier,
                getInstanceClass());
    }

    public Class getInstanceClass() {
        return type.getRawType();
    }

    protected Supplier<Integer> intProperty(ObjectNode node, String property) {
        ObjectNode child = ((MapObjectNode) node).getMember(property);
        IntegerInstanceSymbolTable symbolTable = (IntegerInstanceSymbolTable) child.getSymbolTable();
        Supplier<Integer> supplier = symbolTable.instantiate(child);
        return supplier;
    }

    protected Supplier<Double> doubleProperty(ObjectNode node, String property) {
        ObjectNode child = ((MapObjectNode) node).getMember(property);
        DoubleInstanceSymbolTable symbolTable = (DoubleInstanceSymbolTable) child.getSymbolTable();
        Supplier<Double> supplier = symbolTable.instantiate(child);
        return supplier;
    }

}
