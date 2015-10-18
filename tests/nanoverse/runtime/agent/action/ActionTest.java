package nanoverse.runtime.agent.action;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.action.helper.*;
import nanoverse.runtime.agent.targets.TargetRule;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.Before;
import test.LayerMocks;

import static org.mockito.Mockito.*;

/**
 * Standard mocks used to test actions.
 *
 * Created by dbborens on 10/18/2015.
 */
public class ActionTest extends LayerMocks {

    protected ActionIdentityManager identity;
    protected CoordAgentMapper mapper;
    protected ActionHighlighter highlighter;
    protected TargetRule targetRule;

    protected Agent callerAgent;
    protected Coordinate caller;
    protected Coordinate ownLocation;

    @Before @Override
    public void before() throws Exception {
        super.before();
        identity = mock(ActionIdentityManager.class);
        mapper = mock(CoordAgentMapper.class);
        highlighter = mock(ActionHighlighter.class);
        targetRule = mock(TargetRule.class);

        when(mapper.getLayerManager()).thenReturn(layerManager);

        caller = mock(Coordinate.class);
        callerAgent = mock(Agent.class);
        when(mapper.resolveCaller(caller)).thenReturn(callerAgent);

        ownLocation = mock(Coordinate.class);
        when(identity.getOwnLocation()).thenReturn(ownLocation);
    }
}
