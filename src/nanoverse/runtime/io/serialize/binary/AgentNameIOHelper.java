package nanoverse.runtime.io.serialize.binary;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.FileSystemManager;
import nanoverse.runtime.processes.StepState;

/**
 * Created by dbborens on 10/23/2015.
 */
public class AgentNameIOHelper {

    public static final String VECTOR_FILENAME = "nameVector.bin";

    private final FileSystemManager fsManager;
    private final AgentNameIndexWriter indexWriter;
    private final AgentNameCommitHelper commitHelper;

    private BinaryOutputHandle nameVectorFile;

    public AgentNameIOHelper(GeneralParameters p, AgentNameIndexManager indexManager) {
        commitHelper = new AgentNameCommitHelper(indexManager);
        fsManager = new FileSystemManager(p);
        indexWriter = new AgentNameIndexWriter(fsManager, indexManager);
    }

    public AgentNameIOHelper(AgentNameCommitHelper commitHelper,
                             FileSystemManager fsManager,
                             AgentNameIndexWriter indexWriter) {
        this.commitHelper = commitHelper;
        this.fsManager = fsManager;
        this.indexWriter = indexWriter;
    }

    public void init() {
        nameVectorFile = fsManager.makeInstanceBinaryFile(VECTOR_FILENAME);
    }

    public void commit(StepState state) {
        commitHelper.commit(nameVectorFile, state);
    }

    public void conclude() {
        nameVectorFile.close();
        indexWriter.writeNameIndex();
    }


}
