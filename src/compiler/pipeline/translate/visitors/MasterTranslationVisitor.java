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

package compiler.pipeline.translate.visitors;

import compiler.pipeline.interpret.nodes.*;
import compiler.pipeline.translate.helpers.TranslationCallback;
import compiler.pipeline.translate.nodes.ObjectNode;
import compiler.pipeline.translate.symbol.*;
import compiler.pipeline.translate.symbol.primitive.ConstantPrimitiveSymbolTable;
import org.slf4j.*;

/**
 * Depth-first search of the abstract syntax tree, which translates
 * each node into a roughly one-to-one correspondence with the
 * prototype for the Java objects into which the simulation will be
 * compiled. These translations are not necessarily complete; default
 * values will be inferred, if possible, in the next stage of the pipeline.
 *
 * Process, starting with the root symbol table and node:
 *
 *    (0) Before starting, each symbol in the symbol table corresponding to
 *        a node in the abstract syntax tree will have been resolved to the
 *        narrowest possible symbol table.
 *
 *    (1) For each child node, retrieve its symbol table, and then recursively
 *        translate it into an ObjectNode.
 *
 *    (2) Load each returned ObjectNode as members of the local ObjectNode.
 *
 * Created by dbborens on 2/22/15.
 */
public class MasterTranslationVisitor {

    private final DictionaryContainerVisitor dictVisitor;
    private final MapContainerVisitor mapVisitor;
    private final ListContainerVisitor listVisitor;
    private final PrimitiveVisitor primitiveVisitor;
    private final Logger logger;

    public MasterTranslationVisitor() {
        TranslationCallback walker = (node, st) -> translate(node, st);
        listVisitor = new ListContainerVisitor(walker);
        mapVisitor = new MapContainerVisitor(walker);
        dictVisitor = new DictionaryContainerVisitor(walker);
        primitiveVisitor = new PrimitiveVisitor();
        logger = LoggerFactory.getLogger(MasterTranslationVisitor.class);
        logger.info("Loading semantic information.");
    }

    public MasterTranslationVisitor(MapContainerVisitor mapVisitor,
                                    ListContainerVisitor listVisitor,
                                    DictionaryContainerVisitor dictVisitor,
                                    PrimitiveVisitor primitiveVisitor) {


        this.mapVisitor = mapVisitor;
        this.listVisitor = listVisitor;
        this.dictVisitor = dictVisitor;
        this.primitiveVisitor = primitiveVisitor;
        logger = LoggerFactory.getLogger(MasterTranslationVisitor.class);
        logger.info("Loading semantic information.");
    }

    public ObjectNode translate(ASTNode toTranslate, SymbolTable symbolTable) {
        if (symbolTable instanceof ListSymbolTable) {
            return listVisitor.translate((ASTContainerNode) toTranslate, (ListSymbolTable) symbolTable);
        } else if (symbolTable instanceof MapSymbolTable) {
            return mapVisitor.translate((ASTContainerNode) toTranslate, (MapSymbolTable) symbolTable);
        } else if (symbolTable instanceof DictionarySymbolTable) {
            return dictVisitor.translate((ASTContainerNode) toTranslate, (DictionarySymbolTable) symbolTable);
        } else if (symbolTable instanceof ConstantPrimitiveSymbolTable) {
            return primitiveVisitor.translate((ASTContainerNode) toTranslate, (ConstantPrimitiveSymbolTable) symbolTable);
        } else {
            throw new IllegalArgumentException("Unexpected symbol table class");
        }
    }
}
