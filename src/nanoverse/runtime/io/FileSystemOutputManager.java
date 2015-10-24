package nanoverse.runtime.io;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.serialize.binary.BinaryOutputHandle;
import nanoverse.runtime.io.serialize.text.TextOutputHandle;

/**
 * Created by dbborens on 10/24/2015.
 */
public class FileSystemOutputManager {
    private final DirectoryMaker directoryMaker;
    private final DiskOutputManager diskOutputManager;
    private final GeneralParameters p;

    /**
     * Main constructor
     */
    public FileSystemOutputManager(GeneralParameters p) {
        this.p = p;
        directoryMaker = new DirectoryMaker();
        diskOutputManager = new DiskOutputManager();
    }

    /**
     * Testing constructor
     */
    public FileSystemOutputManager(GeneralParameters p, DirectoryMaker directoryMaker, DiskOutputManager diskOutputManager) {
        this.p = p;
        this.directoryMaker = directoryMaker;
        this.diskOutputManager = diskOutputManager;
    }

    /**
     * Create a binary file in the project's base directory,
     * creating subdirectories if needed.
     */
    public BinaryOutputHandle makeProjectBinaryFile(String filename) {
        String path = p.getPath();
        return doMakeBinaryFile(path, filename);
    }

    private BinaryOutputHandle doMakeBinaryFile(String path, String filename) {
        directoryMaker.makeDirectory(path);
        String filePath = path + "/" + filename;
        return diskOutputManager.getBinaryHandle(filePath);
    }

    /**
     * Create a binary file in the current instance's directory,
     * creating subdirectories if needed.
     */
    public BinaryOutputHandle makeInstanceBinaryFile(String filename) {
        String path = p.getInstancePath();
        return doMakeBinaryFile(path, filename);
    }

    /**
     * Create a text file in the project's base directory,
     * creating subdirectories if needed.
     */
    public TextOutputHandle makeProjectTextFile(String filename) {
        String path = p.getPath();
        return doMakeTextFile(path, filename);
    }

    private TextOutputHandle doMakeTextFile(String path, String filename) {
        directoryMaker.makeDirectory(path);
        String filePath = path + "/" + filename;
        return diskOutputManager.getTextHandle(filePath);
    }

    /**
     * Create a text file in the current instance's directory,
     * creating subdirectories if needed.
     */
    public TextOutputHandle makeInstanceTextFile(String filename) {
        String path = p.getInstancePath();
        return doMakeTextFile(path, filename);
    }
}
