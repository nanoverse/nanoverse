package nanoverse.runtime.io.deserialize;

import java.io.*;

/**
 * Created by dbborens on 10/24/2015.
 */
public class BinaryInputHandle {
    private final DataInputStream dis;

    public BinaryInputHandle(DataInputStream dis) {
        this.dis = dis;
    }

    public double readDouble() {
        try {
            return dis.readDouble();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public float readFloat() {
        try {
            return dis.readFloat();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int readInt() {
        try {
            return dis.readInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean readBoolean() {
        try {
            return dis.readBoolean();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public short readShort() {
        try {
            return dis.readShort();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            dis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
