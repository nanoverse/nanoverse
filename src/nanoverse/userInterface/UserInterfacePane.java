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

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserInterfacePane extends BorderPane {
    private final Label status;
    private final ImageView outputImageView;
    private final Button toggleExecution;
    private final Button toggleShowOutput;

    private AtomicBoolean isRunningFlag;

    public UserInterfacePane() {
        /*
            Top Area
        */
        status = new Label("Program Running");
        status.getStyleClass().add("toggle-text");

        HBox toggleButtonBox = new HBox();
        toggleButtonBox.setStyle("-fx-background-color: #336699");
        toggleButtonBox.setSpacing(10);
        toggleButtonBox.setPadding(new Insets(25, 25, 25, 25));


        toggleButtonBox.getChildren().add(status);


        setTop(toggleButtonBox);

        /*
            Center Area
         */
        StackPane stackpane = new StackPane();

        outputImageView = new ImageView();
        outputImageView.setFitWidth(400);
        outputImageView.setPreserveRatio(true);
        outputImageView.setSmooth(true);
        outputImageView.setVisible(false);
        outputImageView.setManaged(false);
        StackPane.setAlignment(outputImageView, Pos.CENTER_RIGHT);

        toggleShowOutput = new Button();
        toggleShowOutput.setText("Show Output");
        toggleShowOutput.getStyleClass().add("red-button");
        StackPane.setAlignment(toggleShowOutput, Pos.CENTER_LEFT);

        toggleExecution = new Button();
        toggleExecution.setText("Pause Exectuion");
        toggleExecution.setOnAction(event -> handleToggle());
        toggleExecution.getStyleClass().add("red-button");
        StackPane.setAlignment(toggleExecution, Pos.TOP_LEFT);

        Button stopExecution = new Button();
        stopExecution.setText("Stop Execution");
        stopExecution.setOnAction(event -> handleStop());
        stopExecution.getStyleClass().add("red-button");
        StackPane.setAlignment(stopExecution, Pos.BOTTOM_LEFT);

        stackpane.getChildren().addAll(outputImageView, toggleShowOutput, toggleExecution, stopExecution);
        setCenter(stackpane);

        String css = this.getClass().getResource("style.css").toExternalForm();
        getStylesheets().add(css);
    }

    public ImageView getOutputImageView() {
        return outputImageView;
    }
    public void setOutputImage(BufferedImage outputImage) {
        Image image = SwingFXUtils.toFXImage(outputImage, null);
        outputImageView.setImage(image);
    }

    public void setIsRunningFlag(AtomicBoolean value) {
        isRunningFlag = value;
    }

    public Button getToggleShowOutput() {
        return toggleShowOutput;
    }

    public Label getStatus() {
        return status;
    }

    public void setStatus(String text) {
        status.setText(text);
    }

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

    public void handleStop() {
        System.exit(0);
    }
}
