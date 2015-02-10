/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein and the
 * Trustees of Princeton University.
 *
 * This file is part of the Nanoverse simulation framework
 * (patent pending).
 *
 * This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General
 * Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package layers.cell;

import cells.Cell;
import control.halt.HaltCondition;
import control.identifiers.Coordinate;

/**
 * @author David Bruce Borenstein
 */
public class CellUpdateManager {
    protected CellLayerContent content;

    public CellUpdateManager(CellLayerContent content) {
        this.content = content;
    }

    /**
     * Instructs the specified cell to calculate its next state. Returns number
     * of calls to consider since last call to apply (including this one). This
     * should not change the apparent state of the cell. Therefore no indices
     * are updated.
     *
     * @param coord
     * @return
     */
    public int consider(Coordinate coord) {
        Cell cell = content.get(coord);
        int res = cell.consider();
        return res;
    }

    /**
     * Instructs the specified cell to update its state. Should blow up if
     * the cell has not called consider since last apply call. You should
     * USE THIS METHOD for updating cells, rather than doing so directly.
     *
     * @param coord
     */
    public void apply(Coordinate coord) throws HaltCondition {
        content.sanityCheck(coord);
        Cell cell = content.get(coord);
        content.remove(coord);

        cell.apply();
        content.put(coord, cell);
    }

    /**
     * Request a daughter cell from the specified cell, and place it at the
     * specified location. divideTo(...) may trigger physical state changes
     * on the part of the parent cell.
     * <p>
     * Note: the following methods return false if unsuccessful for expected
     * reasons (e.g., lattice full). The definiton of an "expected reason"
     * depends on the cell type. Such events typically are used to signal
     * that the simulation is over. However, they are designed to throw
     * exceptions if something erroneous occurs, such as a call to a null
     * cell. Note however that "position occupied" is a handled condition.
     * If this should be considered erroneous, an exception should be thrown
     * by the calling function.
     *
     * @param pCoord
     * @param cCoord
     */
    public void divideTo(Coordinate pCoord, Coordinate cCoord) throws HaltCondition {
        content.sanityCheck(pCoord);
        content.sanityCheck(cCoord);

        // Note: divide(...) updates state index for parent
        Cell child = divide(pCoord);

        // Attempt to place child
        // Note: place(...) updates state index for child
        place(child, cCoord);

    }

    // TODO: The exposure of this method is a bit of cloodge for the shoving
    // method. There's no obvious way around it as things stand, but it does
    // suggest that a refactor may soon be necessary.
    public Cell divide(Coordinate pCoord) throws HaltCondition {
        content.sanityCheck(pCoord);

        // Divide parent
        Cell parent = content.get(pCoord);

        if (parent == null) {
            throw new IllegalStateException("Coordinate " + pCoord + " is null");
        }

        // Remove the parent from the map. After it divides, we will place
        // it again. This will update the indices.
        content.remove(pCoord);

        // Perform the division.
        Cell child = parent.divide();

        // Place the parent, whose state may have changed as a result of the
        // division event.
        content.put(pCoord, parent);

        // Return the child.
        return child;
    }

    /**
     * Place the specified cell at the specified coordinate.
     *
     * @param cell
     * @param coord
     */
    public void place(Cell cell, Coordinate coord) throws HaltCondition {
        content.sanityCheck(coord);

        if (content.has(coord)) {
            throw new IllegalStateException("Attempting to place a cell into " +
                    "an occupied site at " + coord.toString() + ".");
        }

        // Place cell in cell lattice
        content.put(coord, cell);
    }

    /**
     * Remove the specified cell from the lattice.
     *
     * @param coord
     */
    public void banish(Coordinate coord) {
        content.sanityCheck(coord);

        if (!content.has(coord)) {
            throw new IllegalStateException("Tried to banish non-existent cell.");
        }

        content.remove(coord);
    }

    /**
     * Relocate the cell at the first coordinate to the second coordinate.
     * This method blows up if the target site is occupied.
     *
     * @param pCoord
     * @param qCoord
     */
    public void move(Coordinate pCoord, Coordinate qCoord) throws HaltCondition {

        if (content.has(qCoord)) {
            throw new IllegalStateException("Attempted to move cell to an " +
                    "occupied site. Origin: " + pCoord + "; destination: "
                    + qCoord);
        }
        content.sanityCheck(pCoord);
        content.sanityCheck(qCoord);

        Cell cell = content.get(pCoord);

        content.remove(pCoord);
        content.put(qCoord, cell);
    }

    /**
     * Swap the cells at the specified locations.
     *
     * @param pCoord
     * @param qCoord
     */
    public void swap(Coordinate pCoord, Coordinate qCoord) throws HaltCondition {

        content.sanityCheck(pCoord);
        content.sanityCheck(qCoord);

        // Identify cells
        Cell p = content.get(pCoord);
        Cell q = content.get(qCoord);

        // Clear both sites
        content.remove(pCoord);
        content.remove(qCoord);

        // Place each cell in the other's site
        content.put(pCoord, q);
        content.put(qCoord, p);
    }

}
