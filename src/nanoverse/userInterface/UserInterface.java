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

package nanoverse.userInterface;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by lizzybradley on 1/23/16.
 */
public class UserInterface extends Application {
    private static AtomicBoolean isRunningFlag;
    private static BufferedImage outputImage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Nanoverse User Interface");

        UserInterfacePane root = new UserInterfacePane();
        root.setIsRunningFlag(isRunningFlag);
        root.setOutputImage(outputImage);

        ImageView outputImageView = root.getOutputImageView();
        UserInterfaceTimeline timeline = new UserInterfaceTimeline(outputImageView, outputImage);

        Button toggleShowOutput = root.getToggleShowOutput();
        new UserInterfaceToggle(timeline, toggleShowOutput, outputImageView);

        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }

    public void show() {
        launch();
    }

    public void setIsRunningFlag(AtomicBoolean isRunningFlag) {
        UserInterface.isRunningFlag = isRunningFlag;
    }

    public void setOutputImage(BufferedImage outputImage) {
        UserInterface.outputImage = outputImage;
    }

    public UserInterface() {}
}


