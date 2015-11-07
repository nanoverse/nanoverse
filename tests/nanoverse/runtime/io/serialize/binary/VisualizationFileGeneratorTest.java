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