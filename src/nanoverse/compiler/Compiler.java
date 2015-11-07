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

package nanoverse.compiler;

import nanoverse.compiler.pipeline.instantiate.loader.control.ProjectLoader;
import nanoverse.compiler.pipeline.interpret.Interpreter;
import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.compiler.pipeline.translate.symbol.control.run.ProjectSymbolTable;
import nanoverse.compiler.pipeline.translate.visitors.MasterTranslationVisitor;
import nanoverse.runtime.control.run.Runner;
import org.slf4j.*;

import java.io.File;

/**
 * Created by dbborens on 9/17/2015.
 */
public class Compiler {

    private final String targetFilename;
    private final Interpreter interpreter;
    private final ProjectSymbolTable rootIST;
    private final MasterTranslationVisitor translator;
    private final ProjectLoader instantiator;

    private final Logger logger = LoggerFactory.getLogger(Compiler.class);

    /**
     * Standard Nanoverse nanoverse.compiler.
     *
     * @param targetFilename
     */
    public Compiler(String targetFilename) {
        this.targetFilename = targetFilename;

        interpreter = new Interpreter();
        rootIST = new ProjectSymbolTable();
        translator = new MasterTranslationVisitor();
        instantiator = new ProjectLoader();
    }

    /**
     * Dependency-injected constructor for testing purposes.
     *
     * @param targetFilename
     * @param interpreter
     * @param rootIST
     * @param translator
     * @param instantiator
     */
    public Compiler(String targetFilename,
                    Interpreter interpreter,
                    ProjectSymbolTable rootIST,
                    MasterTranslationVisitor translator,
                    ProjectLoader instantiator) {

        this.targetFilename = targetFilename;
        this.interpreter = interpreter;
        this.rootIST = rootIST;
        this.translator = translator;
        this.instantiator = instantiator;
    }

    public Runner compile() {
        File target = new File(targetFilename);

        logger.info("Interpreting source code");
        ASTNode rootASTNode = interpreter.interpret(target);

        logger.info("Translating abstract syntax tree");
        MapObjectNode rootObjNode = (MapObjectNode) translator
            .translate(rootASTNode, rootIST);

        logger.info("Instantiating Java objects");
        Runner runner = instantiator.instantiate(rootObjNode);

        logger.info("Compilation complete.");
        return runner;
    }
}
