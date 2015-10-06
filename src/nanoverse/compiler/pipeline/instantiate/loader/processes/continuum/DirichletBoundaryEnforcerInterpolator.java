package nanoverse.compiler.pipeline.instantiate.loader.processes.continuum;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.instantiate.loader.geometry.set.CoordinateSetLoader;
import nanoverse.compiler.pipeline.instantiate.loader.processes.BaseProcessArgumentsLoader;
import nanoverse.compiler.pipeline.instantiate.loader.processes.ProcessInterpolator;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.geometry.set.CoordinateSet;
import nanoverse.runtime.layers.LayerManager;

import java.util.Random;

/**
 * Created by dbborens on 10/5/2015.
 */
public class DirichletBoundaryEnforcerInterpolator extends ProcessInterpolator {
    public DirichletBoundaryEnforcerInterpolator() {
        super();
    }

    public DirichletBoundaryEnforcerInterpolator(LoadHelper load, BaseProcessArgumentsLoader bpaLoader) {
        super(load, bpaLoader);
    }

    public CoordinateSet activeSites(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        CoordinateSetLoader loader = (CoordinateSetLoader) load.getLoader(node, "activeSites", true);
        MapObjectNode childNode = (MapObjectNode) node.getMember("activeSites");
        return loader.instantiate(childNode, lm, p);
    }

    public String layer(MapObjectNode node) {
        return load.aString(node, "layer");
    }

    public DoubleArgument value(MapObjectNode node, Random random) {
        return load.aDoubleArgument(node, "value", random);
    }
}
