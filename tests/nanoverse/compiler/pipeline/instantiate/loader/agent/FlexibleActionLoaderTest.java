package nanoverse.compiler.pipeline.instantiate.loader.agent;

import nanoverse.compiler.pipeline.instantiate.loader.agent.action.*;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.runtime.agent.action.*;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FlexibleActionLoaderTest {


    private CompoundActionLoader loader;
    private FlexibleActionLoader query;
    private LayerManager lm;
    private GeneralParameters p;
    @Before
    public void before() throws Exception {
        loader = mock(CompoundActionLoader.class);
        query = new FlexibleActionLoader(loader);

        lm = mock(LayerManager.class);
        p = mock(GeneralParameters.class);
    }

    @Test
    public void loadSingleton() throws Exception {
        MapObjectNode child = mock(MapObjectNode.class);

        MapSymbolTable ist = mock(MapSymbolTable.class);
        when(child.getSymbolTable()).thenReturn(ist);

        ActionLoader loader = mock(ActionLoader.class);
        when(ist.getLoader()).thenReturn(loader);

        ActionDescriptor expected = mock(ActionDescriptor.class);
        when(loader.instantiate(child, lm, p)).thenReturn(expected);

        ActionDescriptor actual = query.load(child, lm, p);
        assertSame(expected, actual);
    }

    @Test
    public void loadList() throws Exception {
        ListObjectNode child = mock(ListObjectNode.class);

        ListSymbolTable lst = mock(ListSymbolTable.class);
        when(child.getSymbolTable()).thenReturn(lst);

        CompoundActionDescriptor expected = mock(CompoundActionDescriptor.class);
        when(loader.instantiate(child, lm, p)).thenReturn(expected);

        ActionDescriptor actual = query.load(child, lm, p);
        assertSame(expected, actual);
    }
}