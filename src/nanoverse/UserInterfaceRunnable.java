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

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by lizzybradley on 1/23/16.
 */
public class UserInterfaceRunnable implements Runnable {
    private AtomicBoolean isRunningFlag;
    private BufferedImage outputImage;

    @Override
    public void run() {
        UserInterface ui = new UserInterface();
        ui.setIsRunningFlag(isRunningFlag);
        ui.setOutputImage(outputImage);

        ui.show();
    }

    public UserInterfaceRunnable(AtomicBoolean isRunningFlag, BufferedImage outputImage) {
        this.isRunningFlag = isRunningFlag;
        this.outputImage = outputImage;
    }
}
