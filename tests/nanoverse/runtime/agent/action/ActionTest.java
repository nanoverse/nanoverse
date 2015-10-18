package nanoverse.runtime.agent.action;

import nanoverse.runtime.agent.action.helper.*;
import nanoverse.runtime.layers.LayerManager;
import org.junit.Before;
import test.LayerMocks;

import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/18/2015.
 */
public class ActionTest extends LayerMocks {

    protected ActionIdentityManager identity;
    protected CoordAgentMapper mapper;
    protected ActionHighlighter highlighter;

    @Before @Override
    public void before() throws Exception {
        super.before();
        identity = mock(ActionIdentityManager.class);
        mapper = mock(CoordAgentMapper.class);
        highlighter = mock(ActionHighlighter.class);

        when(mapper.getLayerManager()).thenReturn(layerManager);
    }
}
