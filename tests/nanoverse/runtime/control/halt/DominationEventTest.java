package nanoverse.runtime.control.halt;

import org.junit.*;

import static org.junit.Assert.*;

public class DominationEventTest {

    private String agentClassName;
    private DominationEvent query;

    @Before
    public void before() throws Exception {
        agentClassName = "Test";
        query = new DominationEvent(agentClassName);
    }

    @Test
    public void getAgentClassName() throws Exception {
        assertSame(agentClassName, query.getAgentClassName());
    }

    @Test
    public void string() throws Exception {
        String expected = "DominationEvent (Test)";
        String actual = query.toString();
        assertEquals(expected, actual);
    }
}