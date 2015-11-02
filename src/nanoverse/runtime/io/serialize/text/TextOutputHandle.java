package nanoverse.runtime.io.serialize.text;

import java.io.*;

/**
 * Created by dbborens on 10/22/2015.
 */
public class TextOutputHandle {

    private final BufferedWriter bw;

    public TextOutputHandle(BufferedWriter bw) {
        this.bw = bw;
    }

    public void newLine() {
        try {
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void flush() {
        try {
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(String str) {
        try {
            bw.write(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}