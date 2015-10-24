package nanoverse.runtime.io;

import nanoverse.runtime.io.deserialize.*;

import java.io.*;

/**
 * Created by dbborens on 10/24/2015.
 */
public class DiskInputManager {

    public BinaryInputHandle doMakeBinaryReader(String path, String filename) {
        String filePath = path + "/" + filename;

        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            return new BinaryInputHandle(dis);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public TextInputHandle doMakeTextReader(String path, String filename) {
        String filePath = path + "/" + filename;

        try {
            File file = new File(filePath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            return new TextInputHandle(br);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
