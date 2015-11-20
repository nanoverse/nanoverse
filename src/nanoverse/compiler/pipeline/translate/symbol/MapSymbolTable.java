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

package nanoverse.compiler.pipeline.translate.symbol;

import com.google.common.reflect.TypeToken;
import nanoverse.compiler.error.UnrecognizedIdentifierError;
import org.slf4j.*;

import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Represents a structured mapping of keys to values. Used to define the
 * properties of an object.
 * <p>
 * Created by dbborens on 3/3/15.
 */
public abstract class MapSymbolTable<T> implements InstantiableSymbolTable {
    protected final HashMap<String, MemberSymbol> requiredMembers;
    protected final Logger logger;
    private final TypeToken<T> type = new TypeToken<T>(getClass()) {
    };

    public MapSymbolTable() {
        requiredMembers = resolveMembers();
        logger = LoggerFactory.getLogger(MapSymbolTable.class);
    }

    protected HashMap<String, MemberSymbol> resolveMembers() {
        return new HashMap<>();
    }

    public Stream<String> getMemberNames() {
        return requiredMembers.keySet().stream();
    }

    public ResolvingSymbolTable getSymbolTable(String identifier, int lineNumber) {
        logger.debug("Resolving {}::{}",
            getInstanceClass().getSimpleName(), identifier);

        if (requiredMembers.containsKey(identifier)) {
            return requiredMembers.get(identifier).getSymbolTable();
        }

        throw new UnrecognizedIdentifierError(identifier,
            getInstanceClass(), lineNumber);
    }

    @Override
    public Class getInstanceClass() {
        return type.getRawType();
    }
}
