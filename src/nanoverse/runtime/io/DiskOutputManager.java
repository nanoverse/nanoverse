package nanoverse.runtime.io;

import nanoverse.runtime.io.serialize.binary.BinaryOutputHandle;
import nanoverse.runtime.io.serialize.text.TextOutputHandle;

import java.io.*;

/**
 * Created by dbborens on 10/22/2015.
 */
public class DiskOutputManager {

    public BinaryOutputHandle getBinaryHandle(String filename) {
        try {
            File file = new File(filename);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            DataOutputStream dataStream = new DataOutputStream(bufferedOutputStream);
            BinaryOutputHandle handle = new BinaryOutputHandle(dataStream);
            return handle;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public TextOutputHandle getTextHandle(String filename) {
        try {
            File file = new File(filename);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            TextOutputHandle handle = new TextOutputHandle(bw);
            return handle;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
