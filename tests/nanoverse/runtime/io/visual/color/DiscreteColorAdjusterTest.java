package nanoverse.runtime.io.visual.color;

import nanoverse.runtime.io.visual.HSLColor;
import org.junit.Test;
import test.TestBase;

import java.awt.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/27/2015.
 */
public class DiscreteColorAdjusterTest extends TestBase {

    // This HSB library stinks.
    private static final float TOLERANCE = 0.1F;

    @Test
    public void adjustColor() throws Exception {
        DiscreteColorAdjuster query = new DiscreteColorAdjuster(0.1F, 0.5F);
        Color original = HSLColor.toRGB(0.5F, 0.8F, 0.2F);
        Color expected = query.adjustColor(original);
        checkHSB(expected, 0.5F, 0.08F, 0.04F);
    }

    @Test
    public void excessValuesCapped() throws Exception {
        DiscreteColorAdjuster query = new DiscreteColorAdjuster(1000.0F, 2000.0F);
        Color original = HSLColor.toRGB(0.5F, 0.8F, 0.2F);
        Color expected = query.adjustColor(original);
        checkHSB(expected, 0.5F, 1.0F, 1.0F);
    }

    private void checkHSB(Color expected, float h, float s, float b) {
        float[] obs = HSLColor.fromRGB(expected);

        assertEquals(h, obs[0], TOLERANCE);
        assertEquals(s, obs[1], TOLERANCE);
        assertEquals(b, obs[2], TOLERANCE);
    }
}