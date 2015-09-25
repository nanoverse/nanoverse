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

package nanoverse.compiler.pipeline.interpret;

import nanoverse.compiler.pipeline.interpret.nanosyntax.*;
import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import nanoverse.compiler.pipeline.interpret.visitors.NanoRootVisitor;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.*;

/**
 * An interface to ANTLR4 boilerplate. This class is not
 * tested because the business logic is from ANTLR, which
 * has its own tests.
 * <p>
 * Created by dbborens on 2/13/15.
 */
public class AntlrBinding {

    private final NanosyntaxVisitor<ASTNode> visitor;

    public AntlrBinding() {
        this(new NanoRootVisitor());
    }

    public AntlrBinding(NanosyntaxVisitor<ASTNode> visitor) {
        this.visitor = visitor;
    }

    /**
     * Read a file, lex it and parse it, visit the tree, and return
     * the root in the form returned by the visitor.
     *
     * @param file
     * @return
     */
    public ASTNode interpret(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            ANTLRInputStream input = new ANTLRInputStream(inputStream);
            NanosyntaxLexer lexer = new NanosyntaxLexer(input);
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            NanosyntaxParser parser = new NanosyntaxParser(tokenStream);
            ParseTree tree = parser.root();
            ASTNode root = visitor.visit(tree);
            return root;
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("File not found: " + file.getAbsolutePath());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
