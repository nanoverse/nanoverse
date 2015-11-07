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

import com.keypoint.PngEncoder;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.SystemState;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Supplier;

/**
 * Created by dbborens on 10/28/2015.
 */
public class VisualizationFileGenerator {
    private final String prefix;
    private final GeneralParameters p;
    private final VisualizationImageEncoder encoder;

    public VisualizationFileGenerator(String prefix, GeneralParameters p) {
        this(new VisualizationImageEncoder(), prefix, p);
    }

    public VisualizationFileGenerator(VisualizationImageEncoder encoder,
                                      String prefix,
                                      GeneralParameters p) {
        this.encoder = encoder;
        this.prefix = prefix;
        this.p = p;
    }

    public void generateFile(double time, Image image) {
        String fileName = buildFileName(time);
        File file = new File(fileName);
        encoder.encodeImage(image, file);
    }

    private String buildFileName(double time) {
        StringBuilder builder = new StringBuilder();
        builder.append(p.getInstancePath());
        builder.append("/");
        builder.append(prefix);
        builder.append(time);
        builder.append(".png");
        return builder.toString();
    }
}
