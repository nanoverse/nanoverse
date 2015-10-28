package nanoverse.runtime.io.visual.color.palettes;

import java.awt.Color;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by dbborens on 10/27/2015.
 */
public class RainbowColorPalette implements Supplier<Color> {

    public static final List<Color> elements = Stream.of(
            Color.RED,
            Color.PINK,
            Color.ORANGE,
            Color.YELLOW,
            Color.GREEN,
            Color.BLUE,
            Color.MAGENTA
    ).collect(Collectors.toList());

    private int index = 0;

    @Override
    public Color get() {
        Color ret = elements.get(index);
        index = (index + 1) % elements.size();
        return ret;
    }
}
