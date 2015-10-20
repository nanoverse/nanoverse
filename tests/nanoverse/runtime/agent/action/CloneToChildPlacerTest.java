package nanoverse.runtime.agent.action;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.action.helper.*;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import test.LayerMocks;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CloneToChildPlacerTest extends LayerMocks {

    private SelfTargetHighlighter highlighter;
    private CoordAgentMapper mapper;
    private boolean noReplace;
    private Coordinate self, target;
    private Agent child;

    private CloneToChildPlacer query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        highlighter = mock(SelfTargetHighlighter.class);

        mapper = mock(CoordAgentMapper.class);
        when(mapper.getLayerManager()).thenReturn(layerManager);

        noReplace = false;
        self = mock(Coordinate.class);
        target = mock(Coordinate.class);
        child = mock(Agent.class);

        query = new CloneToChildPlacer(highlighter, mapper, noReplace);
    }

    @Test
    public void placeHighlights() throws Exception {
        query.place(self, target, child);
        verify(highlighter).highlight(target, self);
    }

    @Test
    public void placeUnoccupied() throws Exception {
        query.place(self, target, child);
        verify(update).place(child, target);
    }

    @Test
    public void placeOccupiedLegal() throws Exception {
        when(viewer.isOccupied(target)).thenReturn(true);
        query.place(self, target, child);
        verify(update).banish(target);
        verify(update).place(child, target);
    }

    @Test(expected = IllegalStateException.class)
    public void placeOccupiedIllegalThrows() throws Exception {
        query = new CloneToChildPlacer(highlighter, mapper, true);
        when(viewer.isOccupied(target)).thenReturn(true);
        query.place(self, target, child);
    }

    @Test
    public void isNoReplace() throws Exception {
        query = new CloneToChildPlacer(highlighter, mapper, true);
        assertTrue(query.isNoReplace());
    }

    @Test
    public void getSelfChannel() throws Exception {
        IntegerArgument expected = mock(IntegerArgument.class);
        when(highlighter.getSelfChannel()).thenReturn(expected);
        assertSame(expected, query.getSelfChannel());
    }

    @Test
    public void getTargetChannel() throws Exception {
        IntegerArgument expected = mock(IntegerArgument.class);
        when(highlighter.getTargetChannel()).thenReturn(expected);
        assertSame(expected, query.getTargetChannel());
    }
}