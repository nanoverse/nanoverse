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

package io.serialize.binary;

import control.GeneralParameters;
import control.halt.HaltCondition;
import control.identifiers.Coordinate;
import geometry.Geometry;
import io.serialize.Serializer;
import layers.LayerManager;
import processes.StepState;
import structural.annotations.FactoryTarget;
import structural.utilities.FileConventions;
import structural.utilities.PrimitiveSerializer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.*;

/**
 * Created by dbborens on 3/28/14.
 */
public class HighlightWriter extends Serializer {
    //    private Geometry geometry;
    private Map<Integer, DataOutputStream> streamMap;

    private List<Integer> channelList;

    @FactoryTarget
    public HighlightWriter(GeneralParameters p, Stream<Integer> channels, LayerManager lm) {
        super(p, lm);
        makeFiles();
        channelList = channels.collect(Collectors.toList());
    }

    @Override
    public void dispatchHalt(HaltCondition ex) {
        closeDataStreams();
    }

    @Override
    public void close() {
    }

    @Override
    public void flush(StepState stepState) {
        Geometry geometry = stepState.getRecordedCellLayer().getGeometry();
        for (int channel : channelList) {
            DataOutputStream stream = streamMap.get(channel);
            List<Coordinate> vector = stepState
                    .getHighlights(channel)
                    .collect(Collectors.toList());

            PrimitiveSerializer.writeCoercedCoordinateVector(stream, vector, geometry);
        }
    }

    @Override
    public void init() {
        super.init();

        createDataStreams();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof HighlightWriter)) {
            return false;
        }

        HighlightWriter other = (HighlightWriter) obj;

        if (other.channelList.size() != this.channelList.size()) {
            return false;
        }

        for (int i = 0; i < channelList.size(); i++) {
            if (other.channelList.get(i) != this.channelList.get(i)) {
                return false;
            }
        }

        return true;
    }

    private void createDataStreams() {
        streamMap = new HashMap<>(channelList.size());

        for (Integer channel : channelList) {
            String baseFilename = FileConventions.makeHighlightFilename(channel);
            String absoluteName = p.getInstancePath() + baseFilename;
            DataOutputStream stream = FileConventions.makeDataOutputStream(absoluteName);
            streamMap.put(channel, stream);
        }
    }

    private void closeDataStreams() {
        try {
            for (DataOutputStream stream : streamMap.values()) {
                stream.close();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
