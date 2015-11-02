package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.processes.*;
import org.junit.*;
import test.LayerMocks;

import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class TriggerProcessTest extends LayerMocks {

    private BaseProcessArguments arguments;
    private AgentProcessArguments cpArguments;
    private String behaviorName;
    private TriggerProcessTargetResolver targetResolver;
    private Agent target;
    private TriggerProcess query;
    private StepState state;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        arguments = mock(BaseProcessArguments.class);
        cpArguments = mock(AgentProcessArguments.class);
        behaviorName = "test";
        targetResolver = mock(TriggerProcessTargetResolver.class);

        when(arguments.getLayerManager()).thenReturn(layerManager);
        target = mock(Agent.class);
        Stream<Agent> stream = Stream.of(target);
        when(targetResolver.resolveTargets()).thenReturn(stream);

        state = mock(StepState.class);

        query = new TriggerProcess(arguments, cpArguments, behaviorName, targetResolver);
    }

    @Test
    public void testLifeCycle() throws Exception {
        when(viewer.exists(target)).thenReturn(true);

        query.target(null);
        query.fire(state);
        verify(target).trigger(behaviorName, null);
    }

    @Test
    public void removedAgentExcluded() throws Exception {
        when(viewer.exists(target)).thenReturn(false);

        query.target(null);
        query.fire(state);
        verify(target, never()).trigger(behaviorName, null);
    }
}