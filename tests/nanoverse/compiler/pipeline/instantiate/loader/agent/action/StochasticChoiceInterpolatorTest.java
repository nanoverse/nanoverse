package nanoverse.compiler.pipeline.instantiate.loader.agent.action;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.compiler.pipeline.instantiate.loader.agent.action.stochastic.DynamicActionRangeMapLoader;
import nanoverse.compiler.pipeline.translate.nodes.ListObjectNode;
import nanoverse.runtime.agent.action.stochastic.DynamicActionRangeMapDescriptor;
import nanoverse.runtime.agent.action.stochastic.NormalizedDynamicActionRangeMapDescriptor;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Supplier;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class StochasticChoiceInterpolatorTest extends InterpolatorTest {

    private StochasticChoiceInterpolator query;
    private StochasticChoiceDefaults defaults;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(StochasticChoiceDefaults.class);
        query = new StochasticChoiceInterpolator(load, defaults);
    }

    @Test
    public void optionsWeighted() throws Exception {
        ListObjectNode cNode = mock(ListObjectNode.class);
        when(node.getMember("options")).thenReturn(cNode);

        DynamicActionRangeMapLoader loader = mock(DynamicActionRangeMapLoader.class);
        when(load.getLoader(eq(node), eq("options"), anyBoolean())).thenReturn(loader);

        DynamicActionRangeMapDescriptor expected = mock(NormalizedDynamicActionRangeMapDescriptor.class);
        when(loader.instantiate(cNode, lm, p)).thenReturn(expected);

        DynamicActionRangeMapDescriptor actual = query.options(node, lm, p, false);
        assertSame(expected, actual);
    }

    @Test
    public void normalized() throws Exception {
        Supplier<Boolean> trigger = () -> query.normalized(node, random);
        verifyBoolean("normalized", trigger);
    }

    @Test
    public void normalizedDefault() throws Exception {
        when(defaults.normalized()).thenReturn(true);
        Runnable trigger = () -> query.normalized(node, random);
        verifyBooleanDefault("normalized", true, trigger);
    }
}