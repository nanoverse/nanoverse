package nanoverse.runtime.io.deserialize.agent;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by dbborens on 10/25/2015.
 */
public class AgentNameViewer {

    private final List<String> names;
    private final double time;
    private final int frameNumber;

    public AgentNameViewer(List<String> names, double time, int frameNumber) {
        this.names = names;
        this.time = time;
        this.frameNumber = frameNumber;
    }

    public Stream<String> getNames() {
        return names.stream();
    }

    public double getTime() {
        return time;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public String getName(int coordIndex) {
        return names.get(coordIndex);
    }
}
