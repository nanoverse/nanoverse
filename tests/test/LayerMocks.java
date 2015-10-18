package test;

import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.*;
import org.junit.Before;

import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/18/2015.
 */
public abstract class LayerMocks extends TestBase {

    protected LayerManager layerManager;
    protected AgentLayer agentLayer;
    protected AgentLookupManager lookup;
    protected AgentUpdateManager update;
    protected AgentLayerViewer viewer;
    protected Geometry geometry;

    @Before
    public void before() throws Exception {
        layerManager = mock(LayerManager.class);

        agentLayer = mock(AgentLayer.class);
        when(layerManager.getAgentLayer()).thenReturn(agentLayer);

        lookup = mock(AgentLookupManager.class);
        when(agentLayer.getLookupManager()).thenReturn(lookup);

        update = mock(AgentUpdateManager.class);
        when(agentLayer.getUpdateManager()).thenReturn(update);

        viewer = mock(AgentLayerViewer.class);
        when(agentLayer.getViewer()).thenReturn(viewer);

        geometry = mock(Geometry.class);
        when(agentLayer.getGeometry()).thenReturn(geometry);
    }
}
