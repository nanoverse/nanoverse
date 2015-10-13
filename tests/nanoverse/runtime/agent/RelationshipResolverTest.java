package nanoverse.runtime.agent;

import nanoverse.runtime.agent.RelationshipResolver;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.continuum.*;
import org.junit.*;
import test.TestBase;

import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RelationshipResolverTest extends TestBase {

    private Supplier<Coordinate> locator;
    private RelationshipResolver query;

    @Before
    public void before() throws Exception {
        locator = mock(Supplier.class);
        query = new RelationshipResolver(locator);
    }

    @Test
    public void resolve() throws Exception {
        Coordinate c = mock(Coordinate.class);
        when(locator.get()).thenReturn(c);

        Reaction r = mock(Reaction.class);
        Supplier<RelationshipTuple> supplier = query.resolve(r);

        RelationshipTuple tuple = supplier.get();
        assertSame(c, tuple.getCoordinate());

        when(r.getExp()).thenReturn(-1.0);
        assertEquals(-1.0, tuple.getExp(), epsilon);

        when(r.getInj()).thenReturn(3.0);
        assertEquals(3.0, tuple.getInj(), epsilon);
    }
}