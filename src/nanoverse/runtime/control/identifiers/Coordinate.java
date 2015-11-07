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

package nanoverse.runtime.control.identifiers;


public abstract class Coordinate implements Comparable<Coordinate> {
    protected final int x, y, z;
    protected final int flags;

    public Coordinate(int x, int y, int flags) {
        this.x = x;
        this.y = y;

        z = 0;

        this.flags = flags | Flags.PLANAR;
    }

    public Coordinate(int x, int y, int z, int flags) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.flags = flags;
    }

    /**
     * Copy constructor.
     *
     * @param c
     */
    public Coordinate(Coordinate c) {
        this.x = c.x;
        this.y = c.y;
        this.z = c.z;
        this.flags = c.flags;
    }

    public Coordinate(int[] vec, int flags) {
        x = vec[0];
        y = vec[1];
        this.flags = flags;
        if (!hasFlag(Flags.PLANAR)) {
            z = vec[2];
        } else z = Integer.MIN_VALUE;
    }

    public boolean hasFlag(int flag) {
        return ((flags & flag) != 0);
    }

    public int norm() {
        int total = 0;
        total += Math.abs(x);
        total += Math.abs(y);

        if (!hasFlag(Flags.PLANAR)) {
            total += Math.abs(z);
        }

        return total;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int z() {
        return z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        result = 31 * result + flags;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;

        Coordinate that = (Coordinate) o;

        if (flags != that.flags) return false;
        if (x != that.x) return false;
        if (y != that.y) return false;
        if (z != that.z) return false;

        return true;
    }

    @Override
    public Coordinate clone() {
        if (hasFlag(Flags.PLANAR)) {
            return new Coordinate2D(x, y, flags);
        } else {
            return new Coordinate3D(x, y, z, flags);
        }
    }

    @Override
    public String toString() {
        return stringForm();
    }

    public String stringForm() {
        if (hasFlag(Flags.VECTOR)) {
            return canonical("<", ">", false);
        } else {
            return canonical("(", ")", true);
        }
    }

    protected String canonical(String open, String close, boolean useFlags) {
        StringBuilder ss = new StringBuilder();

        ss.append(open);
        ss.append(x);
        ss.append(", ");
        ss.append(y);

        if ((flags & Flags.PLANAR) == 0) {
            ss.append(", ");
            ss.append(z);
        }

        if (useFlags) {
            ss.append(" | ");
            ss.append(flags);
        }

        ss.append(close);

        String s = ss.toString();

        return s;
    }

    /**
     * This (arbitrary) comparator is used to make ordered
     * arrays, eg for tests.
     */
    @Override
    public int compareTo(Coordinate o) {
        if (x > o.x) {
            return 1;
        } else if (x < o.x) {
            return -1;
        } else if (y > o.y) {
            return 1;
        } else if (y < o.y) {
            return -1;
        } else if (z > o.z) {
            return 1;
        } else if (z < o.z) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Clone the coordinate with additional flags.
     */
    public Coordinate addFlags(int f) {
        Coordinate ret;
        int rf = flags() | f;

        if (hasFlag(Flags.PLANAR)) {
            ret = new Coordinate2D(x, y, rf);
        } else {
            ret = new Coordinate3D(x, y, z, rf);
        }

        return ret;
    }

    public int flags() {
        return flags;
    }

    /**
     * Return a coordinate with same location as this one, and all
     * flags cleared except for the PLANAR flag.
     *
     * @return
     */
    public Coordinate canonicalize() {
        Coordinate ret;

        if (hasFlag(Flags.PLANAR)) {
            ret = new Coordinate2D(x, y, 0);
        } else {
            ret = new Coordinate3D(x, y, z, 0);
        }

        return ret;
    }

}
