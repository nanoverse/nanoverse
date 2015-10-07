package nanoverse.compiler.pipeline.instantiate.loader.processes.continuum;

import nanoverse.compiler.pipeline.instantiate.factory.processes.continuum.DirichletBoundaryEnforcerFactory;
import nanoverse.compiler.pipeline.instantiate.loader.processes.ProcessLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.geometry.set.CoordinateSet;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.continuum.DirichletBoundaryEnforcer;

/**
 * Created by dbborens on 10/5/2015.
 */
public class DirichletBoundaryEnforcerLoader extends ProcessLoader<DirichletBoundaryEnforcer> {

    private final DirichletBoundaryEnforcerFactory factory;
    private final DirichletBoundaryEnforcerInterpolator interpolator;

    public DirichletBoundaryEnforcerLoader() {
        interpolator = new DirichletBoundaryEnforcerInterpolator();
        factory = new DirichletBoundaryEnforcerFactory();
    }

    public DirichletBoundaryEnforcerLoader(DirichletBoundaryEnforcerFactory factory,
                                           DirichletBoundaryEnforcerInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public DirichletBoundaryEnforcer instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        CoordinateSet activeSites = interpolator.activeSites(node, lm, p);
        factory.setActiveSites(activeSites);

        BaseProcessArguments arguments = interpolator.arguments(node, lm, p);
        factory.setArguments(arguments);

        String layer = interpolator.layer(node);
        factory.setLayerId(layer);

        DoubleArgument value = interpolator.value(node, p.getRandom());
        factory.setValue(value);
        return factory.build();
    }

}
