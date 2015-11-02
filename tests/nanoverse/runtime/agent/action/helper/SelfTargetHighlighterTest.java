package nanoverse.runtime.agent.action.helper;

import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class SelfTargetHighlighterTest {

    private ActionHighlighter highlighter;
    private IntegerArgument selfChannel, targetChannel;
    private Coordinate self, target;

    private SelfTargetHighlighter query;

    @Before
    public void before() throws Exception {
        highlighter = mock(ActionHighlighter.class);
        selfChannel = mock(IntegerArgument.class);
        targetChannel = mock(IntegerArgument.class);
        self = mock(Coordinate.class);
        target = mock(Coordinate.class);

        query = new SelfTargetHighlighter(highlighter, selfChannel, targetChannel);
    }

    @Test
    public void highlightHighlightsSelf() throws Exception {
        query.highlight(target, self);
        verify(highlighter).doHighlight(selfChannel, self);
    }

    @Test
    public void highlightHighlightsTarget() throws Exception {
        query.highlight(target, self);
        verify(highlighter).doHighlight(targetChannel, target);
    }

    @Test
    public void getTargetChannel() throws Exception {
        assertSame(targetChannel, query.getTargetChannel());
    }

    @Test
    public void getSelfChannel() throws Exception {
        assertSame(selfChannel, query.getSelfChannel());
    }
}