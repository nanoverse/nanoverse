package nanoverse.runtime.io.serialize.binary;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.io.deserialize.SystemStateReader;
import nanoverse.runtime.io.visual.Visualization;
import nanoverse.runtime.layers.SystemState;

import java.awt.*;
import java.util.function.Function;

/**
 * Created by dbborens on 10/28/2015.
 */
public class VisualizationFrameRenderer {
    private final Visualization visualization;
    private final Geometry geometry;
    private final VisualizationFileGenerator generator;
    private final Function<int[], SystemStateReader> readerMaker;

    public VisualizationFrameRenderer(Visualization visualization,
                                      Geometry geometry,
                                      GeneralParameters p,
                                      String prefix) {
        generator = new VisualizationFileGenerator(prefix, p);
        this.readerMaker = channels -> new SystemStateReader(channels, p, geometry);
        this.geometry = geometry;
        this.visualization = visualization;
    }

    public VisualizationFrameRenderer(Visualization visualization,
                                      Geometry geometry,
                                      Function<int[], SystemStateReader> readerMaker,
                                      VisualizationFileGenerator generator) {

        this.visualization = visualization;
        this.geometry = geometry;
        this.generator = generator;
        this.readerMaker = readerMaker;
    }

    public void renderAll(int[] highlightChannels) {
        SystemStateReader reader = readerMaker.apply(highlightChannels);

        // Initialize the visualization to this simulation.
        visualization.init(geometry, reader.getTimes(), reader.getFrames());

        // Scan through the frames...
        for (SystemState systemState : reader) {
            render(systemState);
        }

        visualization.conclude();
    }

    private void render(SystemState systemState) {
        // Render the frame.
        Image image = visualization.render(systemState);

        // Image can be null if the visualization only outputs at
        // certain frames (eg kymograph, only returns image at end)
        if (image != null) {
            // Export the frame to the disk.
            double time = systemState.getTime();
            generator.generateFile(time, image);
        }
    }


}
