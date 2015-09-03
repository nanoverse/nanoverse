/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein and the
 * Trustees of Princeton University.
 *
 * This file is part of the Nanoverse simulation framework
 * (patent pending).
 *
 * This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General
 * Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package io.serialize.binary;

import com.keypoint.PngEncoder;
import control.GeneralParameters;
import control.halt.HaltCondition;
import geometry.Geometry;
import io.deserialize.SystemStateReader;
import io.serialize.Serializer;
import io.visual.Visualization;
import layers.LayerManager;
import layers.SystemState;
import processes.StepState;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dbborens on 4/2/14.
 */
public class VisualizationSerializer extends Serializer {

    // The visualization to render
    private Visualization visualization;

    // Leading part of the file name (after the instance path)
    private String prefix;

    private Geometry geometry;

    private PngEncoder pngEncoder;

    public VisualizationSerializer(GeneralParameters p,
                                   Visualization visualization,
                                   String prefix, LayerManager lm) {
        super(p, lm);
        geometry = lm.getCellLayer().getGeometry();
        this.visualization = visualization;
        this.prefix = prefix;
    }

    @Override
    public void init() {
        super.init();

        pngEncoder = new PngEncoder();
    }


    @Override
    public void dispatchHalt(HaltCondition ex) {

        // Get expected fields.
        String[] soluteIds = visualization.getSoluteIds();
        int[] highlightChannels = visualization.getHighlightChannels();

        // Create a SystemStateReader.
        SystemStateReader reader = new SystemStateReader(soluteIds,
                highlightChannels, p.getInstancePath(), geometry);
        // Initialize the visualization to this simulation.
        visualization.init(geometry, reader.getTimes(), reader.getFrames());

        // Scan through the frames...
        for (SystemState systemState : reader) {
            // Render the frame.
            Image image = visualization.render(systemState);

            // Image can be null if the visualization only outputs at
            // certain frames (eg kymograph, only returns image at end)
            if (image != null) {
                // Export the frame to the disk.
                generateFile(systemState, image);
            }
        }

        visualization.conclude();
    }

    private void generateFile(SystemState systemState, Image image) {
        String fileName = buildFileName(systemState.getTime());
        File file = new File(fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            pngEncoder.setImage(image);
            fos.write(pngEncoder.pngEncode());
            fos.close();
//            ImageIO.write(image, mode, file);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String buildFileName(double time) {
        StringBuilder builder = new StringBuilder();
        builder.append(p.getInstancePath());
        builder.append(prefix);
        builder.append(time);
        builder.append(".png");
        return builder.toString();
    }

    @Override
    public void close() {
        // Doesn't do anything
    }

    @Override
    public void flush(StepState stepState) {

    }
}
