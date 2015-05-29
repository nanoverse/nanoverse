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

package io.deserialize.continuum;

import control.identifiers.Extrema;
import structural.utilities.FileConventions;

import java.io.*;
import java.util.*;
import java.util.stream.*;

/**
 * Created by dbborens on 5/27/2015.
 */
public class ContinuumStateReader implements Iterator<ContinuumLayerViewer> {

    private final HashMap<String, ContinuumValueIterator> iteratorMap;
    private final HashMap<String, Extrema> extremaMap;
    private final int numSites;
    private boolean hasNext;

    public ContinuumStateReader(String filePath, int numSites) {
        this.numSites = numSites;
        extremaMap = new HashMap<>();
        iteratorMap = new HashMap<>();

        Stream<String> ids = getIds(filePath);

        ids.forEach(id -> {
            Extrema extrema = readExtrema(filePath, id);
            extremaMap.put(id, extrema);

            ContinuumValueIterator iterator = createIterator(filePath, id);
            iteratorMap.put(id, iterator);
        });

        updateNext();
    }

    /**
     * Verify that each layer reports the same value for hasNext()
     * and use it to set hasNext
     */
    private void updateNext() {
        int numTrue = (int) iteratorMap
                .values()
                .stream()
                .filter(ContinuumValueIterator::hasNext)
                .count();

        if (numTrue > 0 && numTrue != iteratorMap.size()) {
            throw new IllegalStateException("Consistency error: not all continuum " +
                    "state files have same number of observations.");
        }

        hasNext = numTrue > 0;
    }

    // Create a ContinuumValueIterator for each ID
    private ContinuumValueIterator createIterator(String filePath, String id) {
        try {
            String fileName = filePath + "/" + FileConventions.makeContinuumStateFilename(id);
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            ContinuumValueIterator iterator = new ContinuumValueIterator(dis, numSites);
            return iterator;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    // Create an Extrema object for each ID
    private Extrema readExtrema(String filePath, String id) {
        try {
            String fileName = filePath + "/" + FileConventions.makeContinuumMetadataFilename(id);
            File file = new File(fileName);
            return ExtremaInstanceReader.get(file);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Read overview into stream of IDs
     */
    private Stream<String> getIds(String filePath) {
        String fileName = filePath + "/" + FileConventions.CONTINUUM_OVERVIEW_FILENAME;
        File file = new File(fileName);
        Stream<String> ids = OverviewInstanceReader.getIdStream(file);
        return ids;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public ContinuumLayerViewer next() {
        // Capture next viewer for each layer
        Map<String, List<Double>> valueMap = new HashMap<>(iteratorMap.size());
        iteratorMap
                .entrySet()
                .stream()
                .map(entry ->
                        new AbstractMap.SimpleEntry<>(
                                entry.getKey(), entry
                                .getValue()
                                .next()
                                .collect(Collectors.toList())
                        ))
                // There's got to be a way to do this with a collector.
                // Have a look at the Java 8 lambdas book.
                .forEach(entry ->
                        valueMap.put(entry.getKey(), entry.getValue()));

        updateNext();

        ContinuumLayerViewer clv = new ContinuumLayerViewer(valueMap);
        return clv;
    }
}
