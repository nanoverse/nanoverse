package cells;

import control.identifiers.Coordinate;
import layers.continuum.*;

import java.util.function.Supplier;

/**
 * Created by dbborens on 9/25/2015.
 */
public class RelationshipResolver {

    private final Supplier<Coordinate> locator;

    public RelationshipResolver(Supplier<Coordinate> locator) {
        this.locator = locator;
    }

    public Supplier<RelationshipTuple> resolve(Reaction reaction) {
        Supplier<RelationshipTuple> supplier = () -> getRelationshipTuple(reaction);
        return supplier;
    }

    private RelationshipTuple getRelationshipTuple(Reaction reaction) {
        Coordinate c = locator.get();
        return new RelationshipTuple(c, reaction);
    }
}
