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
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by dbborens on 3/3/15.
 */
public abstract class ClassSymbolTable<T> implements ResolvingSymbolTable {

    private final TypeToken<T> type = new TypeToken<T>(getClass()) {
    };
    private HashMap<String, Supplier<InstantiableSymbolTable>> members;
    private Logger logger;

    public ClassSymbolTable() {
        members = resolveSubclasses();
        logger = LoggerFactory.getLogger(ClassSymbolTable.class);
    }

    public abstract HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses();

    @Override
    public InstantiableSymbolTable getSymbolTable(String identifier, int lineNumber) {
        logger.debug("Resolving \"{}\" against class {}", identifier,
            getBroadClass().getSimpleName());

        if (!members.containsKey(identifier)) {
            logger.error("Unable to resolve \"{}\" against class {}",
                identifier, getBroadClass().getSimpleName());

            throw new UnrecognizedIdentifierError(identifier,
                getBroadClass(), lineNumber);
        }

        return members.get(identifier).get();
    }

    @Override
    public Class getBroadClass() {
        return type.getRawType();
    }
}
