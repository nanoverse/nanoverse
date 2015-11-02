package nanoverse.runtime.io.deserialize;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.identifiers.Extrema;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.io.deserialize.agent.AgentNameIterator;
import nanoverse.runtime.io.deserialize.continuum.ContinuumLayerViewer;
import nanoverse.runtime.io.deserialize.continuum.ContinuumStateReader;
import nanoverse.runtime.layers.LightweightSystemState;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by dbborens on 10/26/2015.
 */
public class SystemStateIterator implements Iterator<LightweightSystemState> {

    // This class is basically crap and should be refactored.

    /* Data handles */
    private final HighlightReader highlightReader;
    private final ContinuumStateReader continuumStateReader;
    private final AgentNameIterator nameIterator;
    private final Supplier<LightweightSystemState> constructor;

    // A list of all time points measured in the data files, as measured
    // in system time.
    private final double[] times;

    // The time points in times correspond to some number of cycles through
    // the simulation (frames).
    private final int[] frames;

    // Coordinate deindexers convert from a numeric index to a coordinate.
    private final CoordinateDeindexer deindexer;


    // Where are we in the time series being loaded?
    private int cursor;

    public double[] getTimes() {
        return times;
    }

    public int[] getFrames() {
        return frames;
    }

    public SystemStateIterator(int[] channelIds,
                             GeneralParameters p, Geometry geometry) {
        cursor = 0;

        String fileRoot = p.getInstancePath();
        // Load coordinate de-indexer.
        deindexer = new CoordinateDeindexer(fileRoot);

        // Load frame-time linkage file.
        TimeReader timeReader = new TimeReader(fileRoot);
        times = timeReader.getTimes();
        frames = timeReader.getFrames();

        // Open handle to data file for each highlght channel.
        highlightReader = new HighlightReader(fileRoot, channelIds, deindexer);

        int numSites = geometry.getCanonicalSites().length;

        // Open handle to data file for cell state vector.
        nameIterator = new AgentNameIterator(p, numSites);

        continuumStateReader = new ContinuumStateReader(fileRoot, numSites);
        constructor = () -> new LightweightSystemState(geometry);
    }

    public SystemStateIterator(HighlightReader highlightReader,
                               ContinuumStateReader continuumStateReader,
                               AgentNameIterator nameIterator,
                               Supplier<LightweightSystemState> constructor,
                               double[] times,
                               int[] frames,
                               CoordinateDeindexer deindexer) {
        this.highlightReader = highlightReader;
        this.continuumStateReader = continuumStateReader;
        this.nameIterator = nameIterator;
        this.times = times;
        this.frames = frames;
        this.deindexer = deindexer;
        this.constructor = constructor;
    }

    public boolean hasNext() {
        return (cursor < frames.length);
    }

    private void setTimeAndFrame(LightweightSystemState state) {
        double time = times[cursor];
        int frame = frames[cursor];
        state.setTime(time);
        state.setFrame(frame);
    }

    @Override
    public LightweightSystemState next() {
        if (!hasNext()) {
            throw new IllegalStateException();
        }

        // Construct display object
        LightweightSystemState state = constructor.get();

        // Populate time and frame
        setTimeAndFrame(state);

        // Populate cell states
        Stream<String> names = nameIterator.next().getNames();
        state.setAgentNames(names);

        // Populate state of continuum fields
        configureContinuumLayer(state);

        // Populate cell change highlights
        highlightReader.populate(state);

        // Advance the cursor
        cursor++;

        // Return display object
        return state;
    }

    private void configureContinuumLayer(LightweightSystemState state) {
        ContinuumLayerViewer continuumViewer = continuumStateReader.next();
        Map<String, Extrema> extremaMap = continuumStateReader.getExtremaMap();
        state.setExtremaMap(extremaMap);
        state.setContinuumLayerViewer(continuumViewer);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
