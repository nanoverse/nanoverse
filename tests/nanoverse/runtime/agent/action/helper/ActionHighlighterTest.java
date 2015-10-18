package nanoverse.runtime.agent.action.helper;

import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.processes.StepState;
import org.junit.*;
import test.LayerMocks;

import static org.mockito.Mockito.*;

public class ActionHighlighterTest extends LayerMocks {

    private StepState stepState;
    private Coordinate toHighlight;
    private Geometry geometry;

    private IntegerArgument channelArg;
    private Integer channel = 1;

    private ActionHighlighter query;

    @Before
    public void before() throws Exception {
        super.before();

        stepState = mock(StepState.class);
        when(layerManager.getStepState()).thenReturn(stepState);


        query = new ActionHighlighter(layerManager);

        channelArg = mock(IntegerArgument.class);
        when(channelArg.next()).thenReturn(channel);

        geometry = mock(Geometry.class);
        when(agentLayer.getGeometry()).thenReturn(geometry);

        toHighlight = mock(Coordinate.class);
        when(geometry.isInBounds(toHighlight)).thenReturn(true);

    }

    @Test
    public void highlightNotifiesStepState() throws Exception {
        query.doHighlight(channelArg, toHighlight);
        verify(stepState).highlight(toHighlight, channel);
    }

    @Test
    public void noChannelSkips() throws Exception {
        query.doHighlight(null, toHighlight);
        verifyNoMoreInteractions(stepState);
    }

    @Test
    public void outOfBoundsSkips() throws Exception {
        when(geometry.isInBounds(toHighlight)).thenReturn(false);
        query.doHighlight(channelArg, toHighlight);
        verifyNoMoreInteractions(stepState);
    }
}