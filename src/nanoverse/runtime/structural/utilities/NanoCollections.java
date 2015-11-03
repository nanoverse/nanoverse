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

package nanoverse.runtime.structural.utilities;

import java.util.*;
import java.util.stream.Stream;

/**
 * A mockable ensemble of commonly used methods for
 * collections, including some from the (static)
 * Collections API.
 * <p>
 * Created by dbborens on 10/22/2015.
 */
public class NanoCollections {

    public <T> List<T> copy(List<T> toCopy) {
        return new ArrayList<>(toCopy);
    }

    public <T> Set<T> copy(Set<T> toCopy) {
        return new HashSet<>(toCopy);
    }

    public void shuffle(List toShuffle) {
        Collections.shuffle(toShuffle);
    }

    public <T> Stream<T> stream(T[] toStream) {
        return Arrays.asList(toStream).stream();
    }

    public void sort(List toSort) {
        Collections.sort(toSort);
    }
}
