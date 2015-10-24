package nanoverse.runtime.io;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.deserialize.*;
import nanoverse.runtime.io.serialize.binary.BinaryOutputHandle;

import java.io.*;

/**
 * Created by dbborens on 10/24/2015.
 */
public class FileSystemInputManager {

    private final GeneralParameters p;
    private final DiskInputManager manager;

    public FileSystemInputManager(GeneralParameters p) {
        this.p = p;
        manager = new DiskInputManager();
    }

    public FileSystemInputManager(GeneralParameters p, DiskInputManager manager) {
        this.p = p;
        this.manager = manager;
    }

    public BinaryInputHandle readProjectBinaryFile(String filename) {
        String path = p.getPath();
        return manager.doMakeBinaryReader(path, filename);
    }

    public BinaryInputHandle readInstanceBinaryFile(String filename) {
        String path = p.getInstancePath();
        return manager.doMakeBinaryReader(path, filename);
    }

    public TextInputHandle readProjectTextFile(String filename) {
        String path = p.getPath();
        return manager.doMakeTextReader(path, filename);
    }

    public TextInputHandle readInstanceTextFile(String filename) {
        String path = p.getInstancePath();
        return manager.doMakeTextReader(path, filename);
    }



}
