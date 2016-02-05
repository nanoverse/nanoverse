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

/**
 * Created by lizzybradley on 1/22/16.
 */
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserInterfaceController {
    public Label status;
    public Button toggleExecution;

    public ImageView outputImageView;
    public Button showOutputImage;

    public HBox toggleButtonBox;
    public Button stopExecution;
    private AtomicBoolean isRunningFlag;

    public void handleToggle() {
        boolean isRunning = isRunningFlag.get();

        if (isRunning) {
            isRunningFlag.set(false);
            status.setText("Program Paused");
            toggleExecution.setText("Resume Execution");
        }
        else {
            isRunningFlag.set(true);
            status.setText("Program Running");
            toggleExecution.setText("Pause Execution");
        }
    }

    public void handleShowOutput() {
        // If Visible
        if (outputImageView.visibleProperty().get()) {
            outputImageView.visibleProperty().set(false);
            outputImageView.setManaged(false);
        }
        else {
            outputImageView.visibleProperty().set(true);
            outputImageView.setManaged(true);
        }
    }

    public void handleStop() {
        System.exit(0);
    }

    public void setIsRunningFlag(AtomicBoolean isRunningFlag) {
        this.isRunningFlag = isRunningFlag;
    }

    public void setOutputImage(BufferedImage outputImage) {
        Image image = SwingFXUtils.toFXImage(outputImage, null);
        outputImageView.setImage(image);
    }
}
