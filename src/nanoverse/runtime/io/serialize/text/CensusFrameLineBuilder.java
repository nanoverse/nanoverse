package nanoverse.runtime.io.serialize.text;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dbborens on 10/23/2015.
 */
public class CensusFrameLineBuilder {
    private final HashMap<Integer, HashMap<String, Integer>> histo;

    public CensusFrameLineBuilder(HashMap<Integer, HashMap<String, Integer>> histo) {
        this.histo = histo;
    }

    public String frameLine(Integer frame, List<String> names) {

        String trimmed = names.stream()
            .map(name -> countInstancesInFrame(name, frame))
            .map(count -> count.toString())
            .collect(Collectors.joining("\t"));

        String line = frame + "\t" + trimmed + "\n";
        return line;
    }

    private Integer countInstancesInFrame(String name, Integer frame) {
        HashMap<String, Integer> observations = histo.get(frame);

        if (observations.containsKey(name)) {
            return observations.get(name);
        }

        return 0;
    }
}
