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

import junit.framework.TestCase;
import org.junit.BeforeClass;
import org.junit.Test;
import test.TestBase;

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.mockito.Mockito.*;

/**
 * Created by lizzybradley on 2/18/16.
 */
public class UserInterfaceTest extends TestBase {
    private static UserInterfaceController controller;

    @BeforeClass
    public static void setUp() throws Exception {
        AtomicBoolean isRunningFlag = new AtomicBoolean(true);
        BufferedImage outputImage = mock(BufferedImage.class);

        UserInterfaceRunnable runner = new UserInterfaceRunnable(isRunningFlag, outputImage);
        Thread uiThread = new Thread(runner);
        uiThread.start();

        controller = new UserInterfaceController();
    }

    @Test
    public void toggleIsRunningFlag() {
        boolean beforeToggle = controller.getIsRunningFlag().get();

        controller.handleToggle();

        boolean afterToggle = controller.getIsRunningFlag().get();
        TestCase.assertEquals(afterToggle, !beforeToggle);
    }

    @Test
    public void handleShowOutput() {
        boolean beforeShowOutput = controller.outputImageView.visibleProperty().get();

        controller.handleShowOutput();

        boolean afterShowOutput = controller.outputImageView.visibleProperty().get();
        TestCase.assertEquals(afterShowOutput, !beforeShowOutput);
    }
}
