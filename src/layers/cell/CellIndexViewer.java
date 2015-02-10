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

import control.identifiers.Coordinate;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by dbborens on 5/2/14.
 */
public class CellIndexViewer extends CellIndex {
    private CellIndex contents;

    public CellIndexViewer(CellIndex contents) {
        this.contents = contents;
    }

    @Override
    public boolean add(Coordinate e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends Coordinate> toAdd) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> elems) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> elems) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        return contents.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> elems) {
        return contents.containsAll(elems);
    }

    @Override
    public boolean isEmpty() {
        return contents.isEmpty();
    }

    @Override
    public Iterator<Coordinate> iterator() {
        return contents.iterator();
    }

    @Override
    public CellIndexViewer set() {
        return this;
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
        return contents.equals(o);
    }

    @Override
    public int hashCode() {
        return contents.hashCode();
    }
}
