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

package compiler.pipeline.interpret.visitors;

import compiler.pipeline.interpret.nanosyntax.*;
import compiler.pipeline.interpret.nodes.ASTNode;
import org.antlr.v4.runtime.misc.NotNull;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Rejects any visit that is not explicitly overridden. This prevents my custom
 * visitor from quietly using default behavior.
 *
 * Created by dbborens on 4/22/15.
 */
public class RejectingVisitor extends NanosyntaxBaseVisitor<ASTNode> {
    @Override
    public ASTNode visitRoot(@NotNull NanosyntaxParser.RootContext ctx) {
        throw new NotImplementedException();
    }

    @Override
    public ASTNode visitStatement(@NotNull NanosyntaxParser.StatementContext ctx) {
        throw new NotImplementedException();
    }

    @Override
    public ASTNode visitAssignment(@NotNull NanosyntaxParser.AssignmentContext ctx) {
        throw new NotImplementedException();
    }

    @Override
    public ASTNode visitBlock(@NotNull NanosyntaxParser.BlockContext ctx) {
        throw new NotImplementedException();
    }

    @Override
    public ASTNode visitSingleton(@NotNull NanosyntaxParser.SingletonContext ctx) {
        throw new NotImplementedException();
    }

    @Override
    public ASTNode visitId(@NotNull NanosyntaxParser.IdContext ctx) {
        throw new NotImplementedException();
    }

    @Override
    public ASTNode visitPrimitive(@NotNull NanosyntaxParser.PrimitiveContext ctx) {
        throw new NotImplementedException();
    }

    @Override
    public ASTNode visitStringPrimitive(@NotNull NanosyntaxParser.StringPrimitiveContext ctx) {
        throw new NotImplementedException();
    }

    @Override
    public ASTNode visitFloatPrimitive(@NotNull NanosyntaxParser.FloatPrimitiveContext ctx) {
        throw new NotImplementedException();
    }

    @Override
    public ASTNode visitIntPrimitive(@NotNull NanosyntaxParser.IntPrimitiveContext ctx) {
        throw new NotImplementedException();
    }
}
