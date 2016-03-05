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

package nanoverse;

import nanoverse.compiler.Compiler;
import nanoverse.compiler.error.ConsoleError;
import nanoverse.runtime.control.run.Runner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by dbborens on 9/17/2015.
 */
public class Nanoverse {

    private final static String usage =
        "\nExpected exactly one argument. Usage:\n" +
            "\tNanoverse <filename>";
    private final nanoverse.compiler.Compiler compiler;
    private boolean showUserInterface;

    public Nanoverse(String[] args) {
        if (args.length == 0 || args.length > 1) {
            throw new ConsoleError(usage);
        }

        String filename = args[0];
        compiler = new nanoverse.compiler.Compiler(filename);
    }

    public Nanoverse(Compiler compiler) {
        this.compiler = compiler;
    }

    public static void main(String[] args) {
        Nanoverse instance = new Nanoverse(args);
        instance.setShowUserInterface(true);
        instance.go();
    }

    public void go() {
        Runner runner = compiler.compile();

        if (showUserInterface) {
            AtomicBoolean isRunningFlag = new AtomicBoolean(true);
            BufferedImage outputImage = null;

            try {
                outputImage = ImageIO.read(new File(getClass().getResource("placeholder.png").toURI()));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }

            UserInterfaceRunnable myRunnable = new UserInterfaceRunnable(isRunningFlag, outputImage);
            Thread uiThread = new Thread(myRunnable);
            uiThread.start();

            runner.setShowUserInterface(showUserInterface);
            runner.setIsRunningFlag(isRunningFlag);
            runner.setOutputImage(outputImage);
        }

        runner.run();
    }

    public void setShowUserInterface(boolean showUserInterface) {
        this.showUserInterface = showUserInterface;
    }
}
