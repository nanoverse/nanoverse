package nanoverse.runtime.io.deserialize;

import java.io.*;
import java.util.stream.Stream;

/**
 * Created by dbborens on 10/24/2015.
 */
public class TextInputHandle {
    private final BufferedReader br;

    public TextInputHandle(BufferedReader br) {
        this.br = br;
    }

    public String readLine() {
        try {
            return br.readLine();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Stream<String> lines() {
        return br.lines();
    }

    public void close() {
        try {
            br.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}