package nanoverse.compiler.pipeline.instantiate.loader.geometry.set;

import nanoverse.runtime.control.identifiers.Coordinate;

import java.util.stream.Stream;

/**
 * Created by dbborens on 10/7/2015.
 */
public class CustomCoordinateSetDefaults {


    public Stream<Coordinate> coordinates() {
        return Stream.empty();
    }
}
