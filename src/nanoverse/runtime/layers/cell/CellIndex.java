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

package nanoverse.runtime.layers.cell;

import nanoverse.runtime.control.identifiers.Coordinate;

import java.util.*;

/**
 * A coordinate set, backed by a hash set, that canonicalizes
 * coordinates before using them.
 *
 * @author dbborens
 */
public class CellIndex implements Set<Coordinate> {

    private Set<Coordinate> contents = new HashSet<>();

    public CellIndex() {
    }

    public CellIndex(Set<Coordinate> contents) {
        for (Coordinate c : contents) {
            add(c);
        }
    }

    @Override
    public boolean add(Coordinate e) {
        Coordinate c = e.canonicalize();
        return contents.add(c);
    }

    @Override
    public boolean addAll(Collection<? extends Coordinate> toAdd) {
        ArrayList<Coordinate> canonical = new ArrayList<>(toAdd.size());

        for (Coordinate c : toAdd) {
            canonical.add(c.canonicalize());
        }

        return contents.addAll(canonical);
    }

    @Override
    public void clear() {
        contents.clear();
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof Coordinate)) {
            return false;
        }

        Coordinate c = (Coordinate) o;

        Coordinate cc = c.canonicalize();

        return contents.contains(cc);
    }

    @Override
    public boolean containsAll(Collection<?> elems) {
        ArrayList<Coordinate> ce = new ArrayList<Coordinate>(elems.size());
        for (Object e : elems) {
            if (!(e instanceof Coordinate)) {
                return false;
            }
            Coordinate c = (Coordinate) e;
            Coordinate cc = c.canonicalize();
            ce.add(cc);
        }

        return contents.containsAll(ce);
    }

    @Override
    public boolean isEmpty() {
        return contents.isEmpty();
    }

    @Override
    public Iterator<Coordinate> iterator() {
        return contents.iterator();
    }

    public CellIndexViewer set() {
        CellIndexViewer copy = new CellIndexViewer(this);
        return copy;
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof Coordinate)) {
            return false;
        }

        Coordinate c = (Coordinate) o;

        Coordinate cc = c.canonicalize();

        return contents.remove(cc);
    }

    @Override
    public boolean removeAll(Collection<?> elems) {
        ArrayList<Coordinate> ce = new ArrayList<Coordinate>(elems.size());
        for (Object e : elems) {
            if (!(e instanceof Coordinate)) {
                return false;
            }
            Coordinate c = (Coordinate) e;
            Coordinate cc = c.canonicalize();
            ce.add(cc);
        }

        return contents.removeAll(ce);
    }

    @Override
    public boolean retainAll(Collection<?> elems) {
        ArrayList<Coordinate> ce = new ArrayList<Coordinate>(elems.size());
        for (Object e : elems) {
            if (!(e instanceof Coordinate)) {
                return false;
            }
            Coordinate c = (Coordinate) e;
            Coordinate cc = c.canonicalize();
            ce.add(cc);
        }

        return contents.retainAll(ce);
    }

    @Override
    public int size() {
        return contents.size();
    }

    @Override
    public Object[] toArray() {
        return contents.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return contents.toArray(a);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellIndex that = (CellIndex) o;

        if (contents != null ? !contents.equals(that.contents) : that.contents != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return contents != null ? contents.hashCode() : 0;
    }
}
