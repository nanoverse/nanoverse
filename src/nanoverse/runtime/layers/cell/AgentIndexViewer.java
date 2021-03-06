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

package nanoverse.runtime.layers.cell;

import nanoverse.runtime.control.identifiers.Coordinate;

import java.util.*;

/**
 * Created by dbborens on 5/2/14.
 */
public class AgentIndexViewer extends AgentIndex {
    private AgentIndex contents;

    public AgentIndexViewer(AgentIndex contents) {
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
    public AgentIndexViewer set() {
        return this;
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
