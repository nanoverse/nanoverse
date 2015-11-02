package nanoverse.runtime.io.serialize.text;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dbborens on 10/23/2015.
 */
public class CensusLineManager {

    private final HashMap<Integer, HashMap<String, Integer>> histo;
    private final CensusFrameLineBuilder builder;

    public CensusLineManager(HashMap<Integer, HashMap<String, Integer>> histo) {
        this.histo = histo;
        builder = new CensusFrameLineBuilder(histo);
    }

    public CensusLineManager(HashMap<Integer, HashMap<String, Integer>> histo,
                             CensusFrameLineBuilder builder) {
        this.histo = histo;
        this.builder = builder;
    }

    public void writeFrames(TextOutputHandle handle, List<String> names) {
        TreeSet<Integer> sortedFrames = new TreeSet<>(histo.keySet());
        sortedFrames.stream()
            .map(frame -> builder.frameLine(frame, names))
            .forEach(handle::write);
    }


    public void writeHeader(TextOutputHandle handle, List<String> sortedNames) {
        String joined = sortedNames
            .stream()
            .collect(Collectors.joining("\t"));

        String line = buildHeaderLine(joined);
        handle.write(line);
    }

    private String buildHeaderLine(String joined) {
        StringBuilder sb = new StringBuilder();
        sb.append("frame");
        sb.append("\t");
        sb.append(joined);
        sb.append("\n");
        return sb.toString();
    }
}
