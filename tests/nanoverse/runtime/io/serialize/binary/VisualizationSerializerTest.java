package nanoverse.runtime.io.serialize.binary;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.visual.Visualization;
import org.junit.Before;
import org.junit.Test;
import test.LayerMocks;

import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/28/2015.
 */
public class VisualizationSerializerTest extends LayerMocks {

    private GeneralParameters p;
    private VisualizationFrameRenderer renderer;
    private VisualizationSerializer query;
    private Visualization visualization;

    @Override @Before
    public void before() throws Exception {
        super.before();
        p = mock(GeneralParameters.class);
        renderer = mock(VisualizationFrameRenderer.class);
        visualization = mock(Visualization.class);
        query = new VisualizationSerializer(p, layerManager, visualization, renderer);
    }

    @Override
    public void verifyNothingHappened() throws Exception {
        super.verifyNothingHappened();
        verifyNoMoreInteractions(p, renderer, visualization);
    }

    @Test
    public void init() throws Exception {
        verifyNothingHappened();
    }

    @Test
    public void dispatchHalt() throws Exception {
        int[] channels = new int[0];
        when(visualization.getHighlightChannels()).thenReturn(channels);
        query.dispatchHalt(null);
        verify(renderer).renderAll(channels);
    }

    @Test
    public void close() throws Exception {
        verifyNothingHappened();
    }

    @Test
    public void flush() throws Exception {
        verifyNothingHappened();
    }

}