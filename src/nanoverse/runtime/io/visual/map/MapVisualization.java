/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.runtime.io.visual.map;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.io.visual.*;
import nanoverse.runtime.io.visual.highlight.HighlightManager;
import nanoverse.runtime.layers.SystemState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by dbborens on 4/1/14.
 */
public class MapVisualization extends Visualization {
    // All state members associated with this visualization.
    protected PixelTranslator translator;
    private BufferedImage outputImage;
    private boolean showUserInterface;

    @FactoryTarget
    public MapVisualization(VisualizationProperties properties) {
        this.properties = properties;
    }

    @Override
    public void init(Geometry geometry, double[] times, int[] frames) {
        Coordinate[] coordinates = geometry.getCanonicalSites();
        properties.setCoordinates(coordinates);
        properties.setFrames(frames);
        properties.setTimes(times);
        translator = PixelTranslatorFactory.instantiate(geometry);
        translator.init(properties);
        HighlightManager highlightManager = properties.getHighlightManager();
        highlightManager.init(translator);
    }

    @Override
    public BufferedImage render(SystemState systemState) {
        System.out.println("Rendering frame " + systemState.getFrame());
        Coordinate pDims = translator.getImageDims();
        BufferedImage img = new BufferedImage(pDims.x(), pDims.y(), BufferedImage.TYPE_INT_RGB);

        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        properties.getHighlightManager().setGraphics(g);

        CoordinateRenderer renderer = new CoordinateRenderer(g, translator, properties);
        for (Coordinate c : properties.getCoordinates()) {
            renderer.render(c, systemState);
        }

        if (showUserInterface) {
            for (int x = 0; x < img.getWidth(); x++) {
                for (int y = 0; y < img.getHeight(); y++) {
                    outputImage.setRGB(x, y, img.getRGB(x, y));
                }
            }
        }

        return img;
    }

    @Override
    public void setShowUserInterface(boolean showUserInterface) {
        this.showUserInterface = showUserInterface;
    }

    @Override
    public void setOutputImage(BufferedImage outputImage) {
        this.outputImage = outputImage;
    }

    @Override
    public void conclude() {
    }

    @Override
    public String[] getSoluteIds() {
        // At the moment, no solute fields are supported.
        return new String[]{};
    }

    @Override
    public int[] getHighlightChannels() {
        return properties.getHighlightManager().getHighlightChannels();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MapVisualization)) {
            return false;
        }

        MapVisualization other = (MapVisualization) obj;

        if (!properties.equals(other.properties)) {
            return false;
        }

        if (!translator.equals(other.translator)) {
            return false;
        }

        return true;
    }

}
