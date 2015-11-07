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

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dbborens on 10/31/2015.
 */
public class VisualizationImageEncoder {

    private final PngEncoder pngEncoder;

    public VisualizationImageEncoder() {
        pngEncoder = new PngEncoder();
    }

    public VisualizationImageEncoder(PngEncoder pngEncoder) {
        this.pngEncoder = pngEncoder;
    }

    public void encodeImage(Image image, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            pngEncoder.setImage(image);
            fos.write(pngEncoder.pngEncode());
            fos.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
