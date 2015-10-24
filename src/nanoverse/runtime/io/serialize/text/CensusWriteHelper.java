package nanoverse.runtime.io.serialize.text;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.FileSystemManager;
import nanoverse.runtime.structural.utilities.NanoCollections;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dbborens on 10/23/2015.
 */
public class CensusWriteHelper {
    public static final String FILENAME = "census.txt";

    private final HashSet<String> observedNames;
    private final FileSystemManager fsManager;
    private final CensusLineManager lineBuilder;
    private final NanoCollections collections;

    public CensusWriteHelper(HashSet<String> observedNames,
                             HashMap<Integer, HashMap<String, Integer>> histo,
                             GeneralParameters p) {
        this.observedNames = observedNames;
        fsManager = new FileSystemManager(p);
        this.lineBuilder = new CensusLineManager(histo);
        collections = new NanoCollections();
    }

    public CensusWriteHelper(HashSet<String> observedNames,
                             FileSystemManager fsManager,
                             CensusLineManager lineBuilder,
                             NanoCollections collections) {

        this.observedNames = observedNames;
        this.fsManager = fsManager;
        this.lineBuilder = lineBuilder;
        this.collections = collections;
    }

    public void commit() {
        List<String> names = observedNames.stream().collect(Collectors.toList());
        collections.sort(names);
        TextOutputHandle handle = fsManager.makeInstanceTextFile(FILENAME);
        lineBuilder.writeHeader(handle, names);
        lineBuilder.writeFrames(handle, names);
    }

}
