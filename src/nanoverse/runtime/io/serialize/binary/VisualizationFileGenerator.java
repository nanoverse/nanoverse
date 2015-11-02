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
