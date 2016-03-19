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

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.awt.image.BufferedImage;

public class UserInterfaceTimeline {
    public static final int PERIOD_MILLIS = 100;

    private final ImageView imageView;
    private final BufferedImage outputImage;

    private final Timeline timeline;

    public UserInterfaceTimeline(ImageView imageView, BufferedImage outputImage) {
        this.imageView = imageView;
        this.outputImage = outputImage;

        timeline = buildTimeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private Timeline buildTimeline() {
        Duration period = Duration.millis(PERIOD_MILLIS);
        return new Timeline(
            new KeyFrame(period, event -> {
                Image image = SwingFXUtils.toFXImage(outputImage, null);
                imageView.setImage(image);
            })
        );
    }

    public void start() {
        timeline.play();
    }

    public void pause() {
        timeline.pause();
    }

}
