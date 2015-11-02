package nanoverse.runtime.io.visual.color;

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.io.visual.HSLColor;

import java.awt.*;

/**
 * Created by dbborens on 10/27/2015.
 */
public class DiscreteColorAdjuster {
    private final float sScale, lScale;

    public DiscreteColorAdjuster(float sScale, float lScale) {
        this.sScale = sScale;
        this.lScale = lScale;
    }

    public Color adjustColor(Color baseColor) {
        HSLColor baseHSL = new HSLColor(baseColor);

        float saturation = adjust(baseHSL.getSaturation(), sScale);
        float luminance = adjust(baseHSL.getLuminance(), lScale);
        float hue = baseHSL.getHue();
        Color adjusted = HSLColor.toRGB(hue, saturation, luminance);

        return adjusted;
    }

    private float adjust(float original, float scale) {
        float uncapped = original * scale;
        return cap(uncapped);
    }

    private float cap(float value) {
        return value > 1.0F ? 1.0F : value;
    }
}
