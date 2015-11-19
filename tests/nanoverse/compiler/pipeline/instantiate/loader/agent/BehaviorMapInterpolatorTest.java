package nanoverse.compiler.pipeline.instantiate.loader.agent;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.compiler.pipeline.instantiate.loader.agent.action.FlexibleActionLoader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.agent.action.ActionDescriptor;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BehaviorMapInterpolatorTest extends InterpolatorTest {

    private final static String ID = "test";
    private FlexibleActionLoader loader;
    private BehaviorMapInterpolator query;

    @Override @Before
    public void before() throws Exception {
        super.before();
        loader = mock(FlexibleActionLoader.class);
        query = new BehaviorMapInterpolator(loader);
    }

    @Test
    public void load() throws Exception {
        DictionaryObjectNode parent = mock(DictionaryObjectNode.class);
        ObjectNode child = mock(ObjectNode.class);
        when(parent.getMember(ID)).thenReturn(child);

        ActionDescriptor expected = mock(ActionDescriptor.class);
        when(loader.load(child, lm, p)).thenReturn(expected);

        ActionDescriptor actual = query.load(ID, parent, lm, p);
        assertSame(expected, actual);
    }
}