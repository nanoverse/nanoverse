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

package nanoverse.runtime.control.run;

import nanoverse.userInterface.UserInterfaceRunnable;
import nanoverse.runtime.control.*;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by dbborens on 11/26/14.
 */
public class RunnerUI extends Runner {

    private GeneralParameters p;
    private Integrator integrator;

    @FactoryTarget(displayName = "Project")
    public RunnerUI(GeneralParameters p, Integrator integrator) {
        super(p, integrator);

        BufferedImage outputImage = null;
        try {
            outputImage = ImageIO.read(new File(getClass().getResource("../../../userInterface/placeholder.png").toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        AtomicBoolean isRunningFlag = new AtomicBoolean(true);

        UserInterfaceRunnable myRunnable = new UserInterfaceRunnable(isRunningFlag, outputImage);
        Thread uiThread = new Thread(myRunnable);
        uiThread.start();

        this.p = p;
        this.integrator = integrator;

        integrator.setShowUserInterface(true);
        integrator.setIsRunningFlag(isRunningFlag);
        integrator.setOutputImage(outputImage);
    }

    public void run() {
        int n = p.getNumInstances();
        for (int i = 0; i < n; i++) {
            integrator.doNext();

            // This instructs the parameter handler to re-initialize the random
            // number generator and to update paths to reflect the next
            // iterate. It is only invoked if there are remaining iterates.
            if (i < p.getNumInstances() - 1) {
                p.advance();
            }
        }
    }
}
