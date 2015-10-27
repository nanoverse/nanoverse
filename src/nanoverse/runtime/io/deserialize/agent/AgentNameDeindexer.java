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
        this(new FileSystemManager(p), new NameIndexReader());
    }

    public AgentNameDeindexer(FileSystemManager fsManager, NameIndexReader reader) {
        String filename = AgentNameIndexWriter.INDEX_FILENAME;
        TextInputHandle indexFile = fsManager.readInstanceTextFile(filename);
        reverseIndex = reader.readReverseIndex(indexFile);
    }


    public String deindex(Integer index) {
        if (index == 0) {
            return null;
        }

        return reverseIndex.get(index);
    }
}
