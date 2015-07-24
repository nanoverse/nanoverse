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

package layers.continuum;

/**
 * Lightweight tuple or capturing a continuum reaction.
 * <p>
 * Created by dbborens on 1/8/15.
 */
public class Reaction {

    private double inj;
    private double exp;
    private String id;

    /**
     * @param inj The magnitude of injection (source vector)
     * @param exp The magnituude of exponentiation (matrix diagonal)
     * @param id  The ID of the layer upon which the reaction occurs
     */
    public Reaction(double inj, double exp, String id) {
        this.inj = inj;
        this.exp = exp;
        this.id = id;
    }

    public double getInj() {
        return inj;
    }

    public double getExp() {
        return exp;
    }

    public String getId() {
        return id;
    }
}
