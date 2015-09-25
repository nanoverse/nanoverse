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

import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import org.slf4j.*;

import java.io.File;

/**
 * Created by dbborens on 2/13/15.
 */
public class Interpreter {

    private final AntlrBinding antlr;
    private final Logger logger;

    public Interpreter() {
        this(new AntlrBinding());
    }

    public Interpreter(AntlrBinding antlr) {
        logger = LoggerFactory.getLogger(Interpreter.class);
        this.antlr = antlr;
    }

    public ASTNode interpret(File file) {
        verify(file);
        logger.info("Interpreting {}", file.getAbsoluteFile());
        return antlr.interpret(file);
    }

    private void verify(File file) {
        if (file == null) {
            throw new IllegalArgumentException("No project file specified.");
        }

        if (!file.exists()) {
            throw new IllegalArgumentException("Project file not found: " + file.getAbsolutePath());
        }
    }
}
