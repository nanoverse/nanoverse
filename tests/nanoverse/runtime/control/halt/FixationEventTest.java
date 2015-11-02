package nanoverse.runtime.control.halt;

import org.junit.*;

import static org.junit.Assert.*;

public class FixationEventTest {

    private String agentClassName;
    private FixationEvent query;

    @Before
    public void before() throws Exception {
        agentClassName = "Test";
        query = new FixationEvent(agentClassName);
    }

    @Test
    public void getAgentClassName() throws Exception {
        assertSame(agentClassName, query.getFixationClassName());
    }

    @Test
    public void string() throws Exception {
        String expected = "FixationEvent (Test)";
        String actual = query.toString();
        assertEquals(expected, actual);
    }
}