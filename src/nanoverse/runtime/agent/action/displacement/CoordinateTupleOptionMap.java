package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.structural.RangeMap;

/**
 * This horrific class is a poster child for why the entire Coordinate hierarchy,
 * with its named cardinal directions and lack of support for arithmetic operations,
 * needs to be replaced with a single Vector class.
 *
 * Created by dbborens on 11/2/2015.
 */
public class CoordinateTupleOptionMap extends RangeMap<CoordinateTuple> {

    public CoordinateTupleOptionMap(Coordinate currentLocation, Coordinate currentDisplacement) {
        super();
        handleX(currentLocation, currentDisplacement);
        handleY(currentLocation, currentDisplacement);
        handleZ(currentLocation, currentDisplacement);
    }

    // TODO: OMG this coordinate system is SO BAD
    private void handleX(Coordinate currentLocation, Coordinate currentDisplacement) {
        if (currentDisplacement.x() == 0) {
            return;
        }

        double magnitude = Math.abs(currentDisplacement.x());
        int signum = currentDisplacement.x() / Math.abs(currentDisplacement.x());

        CoordinateTuple tuple;
        if (currentDisplacement instanceof Coordinate2D) {
            Coordinate newDisplacement = new Coordinate2D(
                currentDisplacement.x() - signum,
                currentDisplacement.y(),
                currentDisplacement.flags());
            Coordinate newLocation = new Coordinate2D(
                currentLocation.x() + signum,
                currentLocation.y(),
                currentLocation.flags());
            tuple = new CoordinateTuple(newDisplacement, newLocation);
        } else if (currentDisplacement instanceof  Coordinate3D) {
            Coordinate newDisplacement = new Coordinate3D(
                currentDisplacement.x() - signum,
                currentDisplacement.y(),
                currentDisplacement.z(),
                currentDisplacement.flags());
            Coordinate newLocation = new Coordinate3D(
                currentLocation.x() + signum,
                currentLocation.y(),
                currentLocation.z(),
                currentLocation.flags());
            tuple = new CoordinateTuple(newDisplacement, newLocation);
        } else {
            throw new IllegalStateException();
        }

        add(tuple, magnitude);
    }

    // TODO: OMG this coordinate system is SO BAD
    private void handleY(Coordinate currentLocation, Coordinate currentDisplacement) {
        if (currentDisplacement.y() == 0) {
            return;
        }

        double magnitude = Math.abs(currentDisplacement.y());
        int signum = currentDisplacement.y() / Math.abs(currentDisplacement.y());

        CoordinateTuple tuple;
        if (currentDisplacement instanceof Coordinate1D) {
            Coordinate newDisplacement = new Coordinate1D(
                currentDisplacement.y() - signum,
                currentDisplacement.flags());
            Coordinate newLocation = new Coordinate1D(
                currentLocation.y() + signum,
                currentLocation.flags()
            );
            tuple = new CoordinateTuple(newDisplacement, newLocation);
        } else if (currentDisplacement instanceof Coordinate2D) {
            Coordinate newDisplacement = new Coordinate2D(
                currentDisplacement.x(),
                currentDisplacement.y() - signum,
                currentDisplacement.flags());
            Coordinate newLocation = new Coordinate2D(
                currentLocation.x(),
                currentLocation.y() + signum,
                currentLocation.flags());
            tuple = new CoordinateTuple(newDisplacement, newLocation);
        } else if (currentDisplacement instanceof  Coordinate3D) {
            Coordinate newDisplacement = new Coordinate3D(
                currentDisplacement.x(),
                currentDisplacement.y() - signum,
                currentDisplacement.z(),
                currentDisplacement.flags());
            Coordinate newLocation = new Coordinate3D(
                currentLocation.x(),
                currentLocation.y() + signum,
                currentLocation.z(),
                currentLocation.flags());
            tuple = new CoordinateTuple(newDisplacement, newLocation);
        } else {
            throw new IllegalStateException();
        }

        add(tuple, magnitude);
    }

    // TODO: OMG this coordinate system is SO BAD
    private void handleZ(Coordinate currentLocation, Coordinate currentDisplacement) {
        if (currentDisplacement.z() == 0) {
            return;
        }

        double magnitude = Math.abs(currentDisplacement.z());
        int signum = currentDisplacement.z() / Math.abs(currentDisplacement.z());

        CoordinateTuple tuple;
        if (currentDisplacement instanceof  Coordinate3D) {
            Coordinate newDisplacement = new Coordinate3D(
                currentDisplacement.x(),
                currentDisplacement.y(),
                currentDisplacement.z() - signum,
                currentDisplacement.flags());
            Coordinate newLocation = new Coordinate3D(
                currentLocation.x(),
                currentLocation.y(),
                currentLocation.z() + signum,
                currentLocation.flags());
            tuple = new CoordinateTuple(newDisplacement, newLocation);
        } else {
            throw new IllegalStateException();
        }

        add(tuple, magnitude);
    }
}
