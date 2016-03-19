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

package nanoverse.runtime.io.serialize.binary;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.io.deserialize.SystemStateReader;
import nanoverse.runtime.io.visual.Visualization;
import nanoverse.runtime.layers.SystemState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Function;

/**
 * Created by dbborens on 10/28/2015.
 */
public class VisualizationFrameRenderer {
    private final Visualization visualization;
    private final Geometry geometry;
    private final VisualizationFileGenerator generator;
    private final Function<int[], SystemStateReader> readerMaker;
    private BufferedImage image;
    private boolean showUserInterface;

    public VisualizationFrameRenderer(Visualization visualization,
                                      Geometry geometry,
                                      GeneralParameters p,
                                      String prefix) {
        generator = new VisualizationFileGenerator(prefix, p);
        this.readerMaker = channels -> new SystemStateReader(channels, p, geometry);
        this.geometry = geometry;
        this.visualization = visualization;
    }

    public VisualizationFrameRenderer(Visualization visualization,
                                      Geometry geometry,
                                      Function<int[], SystemStateReader> readerMaker,
                                      VisualizationFileGenerator generator) {

        this.visualization = visualization;
        this.geometry = geometry;
        this.generator = generator;
        this.readerMaker = readerMaker;
    }

    public void renderAll(int[] highlightChannels) {
        SystemStateReader reader = readerMaker.apply(highlightChannels);

        // Initialize the visualization to this simulation.
        visualization.init(geometry, reader.getTimes(), reader.getFrames());

        // Scan through the frames...
        for (SystemState systemState : reader) {
            render(systemState);
        }

        visualization.conclude();
    }

    private void render(SystemState systemState) {
        // Render the frame.
        if (showUserInterface) {
            visualization.setShowUserInterface(true);
            visualization.setOutputImage(image);
        }

        Image image = visualization.render(systemState);

        // Image can be null if the visualization only outputs at
        // certain frames (eg kymograph, only returns image at end)
        if (image != null) {
            // Export the frame to the disk.
            double time = systemState.getTime();
            generator.generateFile(time, image);
        }
    }

    public void setShowUserInterface(boolean showUserInterface) {
        this.showUserInterface = showUserInterface;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
