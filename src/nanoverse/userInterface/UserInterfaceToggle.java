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

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class UserInterfaceToggle {

    private boolean outputIsVisible;
    private final UserInterfaceTimeline timeline;
    private final Button toggleShowOutput;
    private final ImageView outputImageView;

    public UserInterfaceToggle(UserInterfaceTimeline timeline, Button toggleShowOutput, ImageView imageView) {
        outputIsVisible = imageView.visibleProperty().get();

        this.toggleShowOutput = toggleShowOutput;
        this.toggleShowOutput.setOnAction(event -> toggleShowOutput());

        this.timeline = timeline;
        this.outputImageView = imageView;
    }

    public void toggleShowOutput() {
        if (outputIsVisible)
            hide();
        else
            show();

        outputImageView.visibleProperty().set(outputIsVisible);
        outputImageView.setManaged(outputIsVisible);
    }

    private void show() {
        outputIsVisible = true;
        timeline.start();

        updateButtonText("Hide Output");
    }

    private void hide() {
        outputIsVisible = false;
        timeline.pause();

        updateButtonText("Show Output");
    }

    private void updateButtonText(String text) {
        Platform.runLater(() -> toggleShowOutput.setText(text));
    }
}
