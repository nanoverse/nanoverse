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
