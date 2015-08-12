package compiler.pipeline.instantiate.loader.geometry.boundary;

import compiler.pipeline.instantiate.factory.geometry.boundaries.AbsorbingFactory;
import compiler.pipeline.instantiate.factory.geometry.boundaries.PlaneRingReflectingFactory;
import geometry.boundaries.PlaneRingReflecting;

/**
 * Created by dbborens on 8/4/2015.
 */
public class PlaneRingReflectingBoundaryLoader extends BoundaryLoader<PlaneRingReflecting> {
    private final PlaneRingReflectingFactory factory;

    public PlaneRingReflectingBoundaryLoader() {
        factory = new PlaneRingReflectingFactory();
    }

    public PlaneRingReflectingBoundaryLoader(PlaneRingReflectingFactory factory) {
        this.factory = factory;
    }
}
