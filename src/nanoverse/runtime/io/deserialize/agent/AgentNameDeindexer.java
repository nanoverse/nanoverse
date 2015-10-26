package nanoverse.runtime.io.deserialize.agent;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.FileSystemManager;
import nanoverse.runtime.io.deserialize.TextInputHandle;
import nanoverse.runtime.io.serialize.binary.AgentNameIndexWriter;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by dbborens on 10/25/2015.
 */
public class AgentNameDeindexer {

    private final Map<Integer, String> reverseIndex;

    public AgentNameDeindexer(GeneralParameters p) {
        this(new FileSystemManager(p));
    }

    public AgentNameDeindexer(FileSystemManager fsManager) {
        String filename = AgentNameIndexWriter.INDEX_FILENAME;
        TextInputHandle indexFile = fsManager.readInstanceTextFile(filename);
        reverseIndex = readReverseIndex(indexFile);
    }

    private Map<Integer, String> readReverseIndex(TextInputHandle indexFile) {
        return indexFile.lines()
                .map(String::trim)
                .map(trimmed -> trimmed.split("\t"))
                .collect(Collectors.toMap(this::getKey, this::getValue));
    }

    private Integer getKey(String[] fields) {
        String keyElem = fields[0];
        return Integer.valueOf(keyElem);
    }

    private String getValue(String[] fields) {
        return fields[1];
    }

    public String deindex(Integer index) {
        if (index == 0) {
            return null;
        }

        return reverseIndex.get(index);
    }
}
