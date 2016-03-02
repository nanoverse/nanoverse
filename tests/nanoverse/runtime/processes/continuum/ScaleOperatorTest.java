package nanoverse.runtime.processes.continuum;

import nanoverse.runtime.geometry.Geometry;
import org.junit.*;
import test.LinearMocks;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ScaleOperatorTest extends LinearMocks{

    @Test
    public void lifeCycle() {
        ScaleOperator operator = new ScaleOperator(0.5, geom);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                checkValue(i, j, operator);
            }
        }
    }

    private void checkValue(int i, int j, ScaleOperator operator) {
        double value = operator.get(i, j);
        if (i == j) {
            assertEquals(0.5, value, epsilon);
        } else {
            assertEquals(0.0, value, epsilon);
        }
    }

}