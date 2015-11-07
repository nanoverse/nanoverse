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

package nanoverse.runtime.io.serialize.text;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.FileSystemManager;
import nanoverse.runtime.structural.utilities.NanoCollections;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dbborens on 10/23/2015.
 */
public class CensusWriteHelper {
    public static final String FILENAME = "census.txt";

    private final HashSet<String> observedNames;
    private final FileSystemManager fsManager;
    private final CensusLineManager lineBuilder;
    private final NanoCollections collections;

    public CensusWriteHelper(HashSet<String> observedNames,
                             HashMap<Integer, HashMap<String, Integer>> histo,
                             GeneralParameters p) {
        this.observedNames = observedNames;
        fsManager = new FileSystemManager(p);
        this.lineBuilder = new CensusLineManager(histo);
        collections = new NanoCollections();
    }

    public CensusWriteHelper(HashSet<String> observedNames,
                             FileSystemManager fsManager,
                             CensusLineManager lineBuilder,
                             NanoCollections collections) {

        this.observedNames = observedNames;
        this.fsManager = fsManager;
        this.lineBuilder = lineBuilder;
        this.collections = collections;
    }

    public void commit() {
        List<String> names = observedNames.stream().collect(Collectors.toList());
        collections.sort(names);
        TextOutputHandle handle = fsManager.makeInstanceTextFile(FILENAME);
        lineBuilder.writeHeader(handle, names);
        lineBuilder.writeFrames(handle, names);
    }

}
