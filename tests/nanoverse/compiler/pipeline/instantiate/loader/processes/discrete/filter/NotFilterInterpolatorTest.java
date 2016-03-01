package nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.filter;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.compiler.pipeline.translate.nodes.ListObjectNode;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.processes.discrete.filter.Filter;
import org.junit.*;

import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class NotFilterInterpolatorTest extends InterpolatorTest {

    private NotFilterInterpolator query;

    @Before @Override
    public void before() throws Exception {
        super.before();
        query = new NotFilterInterpolator(load);
    }
    @Test
    public void child() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("child")).thenReturn(cNode);

        FilterLoader loader = mock(FilterLoader.class);
        when(load.getLoader(eq(node), eq("child"), anyBoolean())).thenReturn(loader);

        Filter expected = mock(Filter.class);
        when(loader.instantiate(cNode, lm, p)).thenReturn(expected);

        Filter actual = query.child(node, lm, p);
        assertSame(expected, actual);
    }
}