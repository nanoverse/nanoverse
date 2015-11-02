package nanoverse.runtime.io.visual.color.palettes;

import org.junit.Test;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/27/2015.
 */
public class RainbowColorPaletteTest {

    @Test
    public void colorCycle() throws Exception {
        RainbowColorPalette query = new RainbowColorPalette();
        Stream.of(
                Color.RED,
                Color.PINK,
                Color.ORANGE,
                Color.YELLOW,
                Color.GREEN,
                Color.BLUE,
                Color.MAGENTA,
                Color.RED
        ).forEach(color -> assertEquals(color, query.get()));
    }
}