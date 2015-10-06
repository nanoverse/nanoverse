package nanoverse.compiler.pipeline.instantiate.loader.processes.continuum;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.compiler.pipeline.instantiate.loader.agent.targets.TargetLoader;
import nanoverse.compiler.pipeline.instantiate.loader.geometry.set.CoordinateSetLoader;
import nanoverse.compiler.pipeline.instantiate.loader.processes.BaseProcessArgumentsInterpolator;
import nanoverse.compiler.pipeline.instantiate.loader.processes.BaseProcessArgumentsLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.agent.targets.TargetDescriptor;
import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.set.CoordinateSet;
import nanoverse.runtime.processes.continuum.DirichletBoundaryEnforcer;
import org.junit.Test;

import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/5/2015.
 */
public class DirichletBoundaryEnforcerInterpolatorTest extends InterpolatorTest {

    private DirichletBoundaryEnforcerInterpolator query;
    private BaseProcessArgumentsLoader bpaLoader;

    @Override
    public void before() throws Exception {
        super.before();
        bpaLoader = mock(BaseProcessArgumentsLoader.class);
        query = new DirichletBoundaryEnforcerInterpolator(load, bpaLoader);
    }

    @Test
    public void testActiveSites() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("activeSites")).thenReturn(cNode);

        CoordinateSetLoader loader = mock(CoordinateSetLoader.class);
        when(load.getLoader(eq(node), eq("activeSites"), anyBoolean())).thenReturn(loader);

        CoordinateSet expected = mock(CoordinateSet.class);
        when(loader.instantiate(cNode, lm, p)).thenReturn(expected);

        CoordinateSet actual = query.activeSites(node, lm, p);
        assertSame(expected, actual);
    }

    @Test
    public void testLayer() throws Exception {
        Supplier<String> trigger = () -> query.layer(node);
        verifyString("layer", trigger);
    }

    @Test
    public void testValue() throws Exception {
        Supplier<DoubleArgument> trigger = () -> query.value(node, random);
        verifyDoubleArgument("value", trigger);
    }
}