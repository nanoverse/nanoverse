package nanoverse.runtime.io;

import java.io.File;
import java.util.function.Function;

/**
 * Created by dbborens on 10/22/2015.
 */
public class DirectoryMaker {

    private final Function<String, File> pathMaker;

    public DirectoryMaker() {
        pathMaker = fn -> new File(fn);
    }

    public DirectoryMaker(Function<String, File> pathMaker) {
        this.pathMaker = pathMaker;
    }

    public void makeDirectory(String pathStr) {
        File path = pathMaker.apply(pathStr);
        if (!path.exists()) {
            try {
                path.mkdirs();
            } catch (Exception ex) {
                throw new RuntimeException("Could not create directory tree " + pathStr, ex);
            }
        }

    }
}
