package nanoverse.runtime.io.serialize.binary;

import nanoverse.runtime.io.FileSystemManager;
import nanoverse.runtime.io.serialize.text.TextOutputHandle;

/**
 * Created by dbborens on 10/23/2015.
 */
public class AgentNameIndexWriter {

    public static final String INDEX_FILENAME = "nameIndex.txt";

    private final FileSystemManager fsManager;
    private final AgentNameIndexManager indexManager;

    public AgentNameIndexWriter(FileSystemManager fsManager, AgentNameIndexManager indexManager) {
        this.fsManager = fsManager;
        this.indexManager = indexManager;
    }

    public void writeNameIndex() {
        TextOutputHandle handle = fsManager.makeInstanceTextFile(INDEX_FILENAME);
        indexManager.getNameStream()
            .filter(name -> name != null)
            .forEach(name -> writeLine(name, handle));
        handle.close();
    }

    private void writeLine(String name, TextOutputHandle handle) {
        int index = indexManager.getIndex(name);
        String line = createLine(name, index);
        handle.write(line);
    }

    private String createLine(String name, int index) {
        StringBuilder sb = new StringBuilder();
        sb.append(index);
        sb.append('\t');
        sb.append(name);
        sb.append('\n');
        return sb.toString();
    }
}
