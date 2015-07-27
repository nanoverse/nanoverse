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

package compiler.pipeline.translate.symbol;

import com.google.common.reflect.TypeToken;
import compiler.error.UnrecognizedIdentifierError;
import org.slf4j.*;

import java.util.HashMap;
import java.util.function.Supplier;

/**
* Created by dbborens on 3/3/15.
*/
public abstract class ClassSymbolTable<T> implements ResolvingSymbolTable {

   private HashMap<String, Supplier<InstantiableSymbolTable>> members;
   private Logger logger;

   private final TypeToken<T> type = new TypeToken<T>(getClass()) {};

   public ClassSymbolTable() {
       members = resolveSubclasses();
       logger = LoggerFactory.getLogger(ClassSymbolTable.class);
   }

   protected abstract HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses();

   @Override
   public InstantiableSymbolTable getSymbolTable(String identifier) {
       logger.debug("Resolving \"{}\" against class {}", identifier,
               getBroadClass().getSimpleName());

       if (!members.containsKey(identifier)) {
           logger.error("Unable to resolve \"{}\" against class {}",
                   identifier, getBroadClass().getSimpleName());

           throw new UnrecognizedIdentifierError(identifier,
                   getBroadClass());
       }

       return members.get(identifier).get();
   }

   @Override
   public Class getBroadClass() {
       return type.getRawType();
   }
}
