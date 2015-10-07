package nanoverse.compiler.pipeline.instantiate.loader.geometry.set;

import nanoverse.compiler.pipeline.instantiate.loader.control.identifiers.CoordinateLoader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.compiler.pipeline.translate.symbol.InstantiableSymbolTable;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;

import java.util.stream.Stream;

/**
 * Created by dbborens on 10/7/2015.
 */
public class CustomCoordinateSetInterpolator {
    private final CustomCoordinateSetDefaults defaults;

    public CustomCoordinateSetInterpolator() {
        defaults = new CustomCoordinateSetDefaults();
    }

    public CustomCoordinateSetInterpolator(CustomCoordinateSetDefaults defaults) {
        this.defaults = defaults;
    }

    public Stream<Coordinate> coordinates(ListObjectNode node, LayerManager lm, GeneralParameters p) {
        if (node == null) {
            return defaults.coordinates();
        }

        return node.getMemberStream()
            .map(cNode -> (MapObjectNode) cNode)
            .map(cNode -> toCoordinate(cNode, lm, p));
    }

    private Coordinate toCoordinate(MapObjectNode cNode, LayerManager lm, GeneralParameters p) {
        InstantiableSymbolTable ist = cNode.getSymbolTable();
        CoordinateLoader loader = (CoordinateLoader) ist.getLoader();
        return loader.instantiate(cNode, lm, p);
    }

}
