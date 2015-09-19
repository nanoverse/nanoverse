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

package compiler;

import compiler.error.ConsoleError;
import control.run.Runner;

/**
 * Created by dbborens on 9/17/2015.
 */
public class Nanoverse {

    private final Compiler compiler;

    private final static String usage =
        "Compiles and executes a Nanoverse simulation.\n" +
        "Usage:\n\n" +
        "\tNanoverse <filename>";

    public static void main(String[] args) {
        Nanoverse instance = new Nanoverse(args);
        instance.go();
    }

    public Nanoverse(String[] args) {
        if (args.length == 0 || args.length > 1) {
            throw new ConsoleError(usage);
        }

        String filename = args[0];
        compiler = new Compiler(filename);
    }

    public Nanoverse(Compiler compiler) {
        this.compiler = compiler;
    }

    public void go() {
        Runner runner = compiler.compile();
        runner.run();
    }
}
