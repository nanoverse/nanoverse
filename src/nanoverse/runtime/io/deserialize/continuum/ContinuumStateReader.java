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

package nanoverse.runtime.io.deserialize.continuum;

import nanoverse.runtime.control.identifiers.Extrema;
import nanoverse.runtime.io.deserialize.BinaryExtremaReader;
import nanoverse.runtime.structural.utilities.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

/**
 * Created by dbborens on 5/27/2015.
 */
public class ContinuumStateReader implements Iterator<ContinuumLayerViewer> {

    private final HashMap<String, ContinuumFrameIterator> iteratorMap;
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

            ContinuumFrameIterator iterator = createIterator(filePath, id);
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
            .filter(ContinuumFrameIterator::hasNext)
            .count();

        if (numTrue > 0 && numTrue != iteratorMap.size()) {
            throw new IllegalStateException("Consistency error: not all continuum " +
                "state files have same number of observations.");
        }

        hasNext = numTrue > 0;
    }

    // Create a ContinuumValueIterator for each ID
    private ContinuumFrameIterator createIterator(String filePath, String id) {
        try {
            String fileName = filePath + "/" + FileConventions.makeContinuumStateFilename(id);
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            ContinuumFrameIterator iterator = new ContinuumFrameIterator(dis, numSites);
            return iterator;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    // Create an Extrema object for each ID
    private Extrema readExtrema(String filePath, String id) {
        BinaryExtremaReader reader = new BinaryExtremaReader();
        try {
            String fileName = filePath + "/" + FileConventions.makeContinuumMetadataFilename(id);
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            return reader.read(dis);
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
        if (!file.exists()) {
            return Stream.empty();
        }
        Stream<String> ids = OverviewInstanceReader.getIdStream(file);
        return ids;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public ContinuumLayerViewer next() {
        if (iteratorMap.size() == 0) {
            return null;
        }
        // Capture next viewer for each layer
        Map<String, List<Double>> valueMap = new HashMap<>(iteratorMap.size());
        List<Integer> frameNumberList = new ArrayList<>(iteratorMap.size());
        List<Double> timeList = new ArrayList<>(iteratorMap.size());
        iteratorMap.keySet().forEach(id -> {
            ContinuumFrameIterator iterator = iteratorMap.get(id);
            ContinuumFrame frame = iterator.next();
            frameNumberList.add(frame.getFrameNumber());
            timeList.add(frame.getTime());

            List<Double> values = frame
                .getValue()
                .collect(Collectors.toList());
            valueMap.put(id, values);
        });

        double time = verifyTimeIntegrity(timeList);
        int frameNumber = verifyFrameNumberIntegrity(frameNumberList);
        updateNext();

        ContinuumLayerViewer clv = new ContinuumLayerViewer(valueMap, time, frameNumber);
        return clv;
    }

    private double verifyTimeIntegrity(List<Double> timeList) {
        double time = timeList.get(0);
        int numTimesOK = (int) timeList
            .stream()
            .filter(value -> EpsilonUtil.epsilonEquals(time, value))
            .count();
        if (numTimesOK != timeList.size()) {
            throw new IllegalStateException("Consistency error: uneven " +
                "time spacing in continuum state files");
        }
        return time;
    }

    private int verifyFrameNumberIntegrity(List<Integer> frameNumberList) {
        int frameNum = frameNumberList.get(0);
        int numFramesOK = (int) frameNumberList
            .stream()
            .filter(value -> value == frameNum)
            .count();
        if (numFramesOK != frameNumberList.size()) {
            throw new IllegalStateException("Consistency error: uneven " +
                "frame spacing in continuum state files");
        }
        return frameNum;
    }

    public Extrema getExtrema(String id) {
        return extremaMap.get(id);
    }

    public Map<String, Extrema> getExtremaMap() {
        return new HashMap<>(extremaMap);
    }
}
