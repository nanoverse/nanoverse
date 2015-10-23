package nanoverse.runtime.io.serialize.binary;

import java.io.*;

/**
 * Created by dbborens on 10/22/2015.
 */
public class BinaryOutputHandle {

    private final DataOutputStream stream;

    public BinaryOutputHandle(DataOutputStream stream) {
        this.stream = stream;
    }

    public void writeDouble(double toWrite) {
        try {
            stream.writeDouble(toWrite);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeFloat(float toWrite) {
        try {
            stream.writeFloat(toWrite);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeInt(int toWrite) {
        try {
            stream.writeInt(toWrite);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeBoolean(boolean toWrite) {
        try {
            stream.writeBoolean(toWrite);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeShort(short toWrite) {
        try {
            stream.writeShort(toWrite);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void flush() {
        try {
            stream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
