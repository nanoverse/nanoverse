package nanoverse.runtime.structural.utilities;

import java.util.*;
import java.util.stream.Stream;

/**
 * A mockable ensemble of commonly used methods for
 * collections, including some from the (static)
 * Collections API.
 * <p>
 * Created by dbborens on 10/22/2015.
 */
public class NanoCollections {

    public <T> List<T> copy(List<T> toCopy) {
        return new ArrayList<>(toCopy);
    }

    public <T> Set<T> copy(Set<T> toCopy) {
        return new HashSet<>(toCopy);
    }

    public void shuffle(List toShuffle) {
        Collections.shuffle(toShuffle);
    }

    public <T> Stream<T> stream(T[] toStream) {
        return Arrays.asList(toStream).stream();
    }
}
