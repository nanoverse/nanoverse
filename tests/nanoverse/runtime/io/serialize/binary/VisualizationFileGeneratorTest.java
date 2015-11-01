package nanoverse.runtime.io.serialize.binary;

import com.keypoint.PngEncoder;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.SystemState;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.awt.*;
import java.io.File;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/28/2015.
 */
public class VisualizationFileGeneratorTest {
    private static final String PREFIX = "test_";
    private static final String PATH = "path";

    private VisualizationImageEncoder encoder;
    private GeneralParameters p;
    private VisualizationFileGenerator query;

    @Before
    public void before() throws Exception {
        encoder = mock(VisualizationImageEncoder.class);
        p = mock(GeneralParameters.class);
        when(p.getInstancePath()).thenReturn(PATH);
        query = new VisualizationFileGenerator(encoder, PREFIX, p);
    }

    @Test
    public void generateFile() throws Exception {
        Image image = mock(Image.class);
        query.generateFile(10.0, image);
        ArgumentCaptor<File> captor = ArgumentCaptor.forClass(File.class);
        verify(encoder).encodeImage(eq(image), captor.capture());
        File actual = captor.getValue();
        File expected = new File("path/test_10.0.png");
        assertEquals(expected, actual);
    }
}