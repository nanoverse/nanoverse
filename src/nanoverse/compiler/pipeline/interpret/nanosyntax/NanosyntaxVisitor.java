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

// Generated from D:/Users/dbborens/IdeaProjects/nanoverse/src\Nanosyntax.g4 by ANTLR 4.5.1
package nanoverse.compiler.pipeline.interpret.nanosyntax;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link NanosyntaxParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface NanosyntaxVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link NanosyntaxParser#root}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitRoot(NanosyntaxParser.RootContext ctx);

    /**
     * Visit a parse tree produced by {@link NanosyntaxParser#statement}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStatement(NanosyntaxParser.StatementContext ctx);

    /**
     * Visit a parse tree produced by {@link NanosyntaxParser#assignment}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAssignment(NanosyntaxParser.AssignmentContext ctx);

    /**
     * Visit a parse tree produced by {@link NanosyntaxParser#block}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBlock(NanosyntaxParser.BlockContext ctx);

    /**
     * Visit a parse tree produced by {@link NanosyntaxParser#singleton}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSingleton(NanosyntaxParser.SingletonContext ctx);

    /**
     * Visit a parse tree produced by {@link NanosyntaxParser#id}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitId(NanosyntaxParser.IdContext ctx);

    /**
     * Visit a parse tree produced by {@link NanosyntaxParser#primitive}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPrimitive(NanosyntaxParser.PrimitiveContext ctx);

    /**
     * Visit a parse tree produced by {@link NanosyntaxParser#stringPrimitive}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStringPrimitive(NanosyntaxParser.StringPrimitiveContext ctx);

    /**
     * Visit a parse tree produced by {@link NanosyntaxParser#floatPrimitive}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFloatPrimitive(NanosyntaxParser.FloatPrimitiveContext ctx);

    /**
     * Visit a parse tree produced by {@link NanosyntaxParser#intPrimitive}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIntPrimitive(NanosyntaxParser.IntPrimitiveContext ctx);

    /**
     * Visit a parse tree produced by {@link NanosyntaxParser#boolPrimitive}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBoolPrimitive(NanosyntaxParser.BoolPrimitiveContext ctx);
}