/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

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
