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

package nanoverse.runtime.factory.io.serialize;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.factory.control.arguments.DoubleArgumentFactory;
import nanoverse.runtime.factory.control.arguments.IntegerArgumentFactory;
import nanoverse.runtime.factory.io.visual.VisualizationFactory;
import nanoverse.runtime.io.serialize.SerializationManager;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.io.serialize.binary.ContinuumStateWriter;
import nanoverse.runtime.io.serialize.binary.HighlightWriter;
import nanoverse.runtime.io.serialize.binary.TimeWriter;
import nanoverse.runtime.io.serialize.binary.VisualizationSerializer;
import nanoverse.runtime.io.serialize.interactive.ProgressReporter;
import nanoverse.runtime.io.serialize.text.*;
import nanoverse.runtime.io.visual.Visualization;
import nanoverse.runtime.layers.LayerManager;
import org.dom4j.Element;
import nanoverse.runtime.structural.utilities.XmlUtil;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by dbborens on 1/17/14.
 */
public abstract class SerializationFactory {
    public static Serializer instantiate(Element e, GeneralParameters p, LayerManager lm) {

        String writerClass = e.getName();

        Serializer ret;
        // Cell writers
        if (writerClass.equalsIgnoreCase("cell-state-writer")) {
            ret = new LegacyCellStateWriter(p, lm);
            return ret;
        } else if (writerClass.equalsIgnoreCase("halt-time-writer")) {
            ret = new HaltTimeWriter(p, lm);
            return ret;
        } else if (writerClass.equalsIgnoreCase("progress-reporter")) {
            ret = new ProgressReporter(p, lm);
            return ret;
        } else if (writerClass.equalsIgnoreCase("census-writer")) {
            ret = new CensusWriter(p, lm);
            return ret;
        } else if (writerClass.equalsIgnoreCase("continuum-histogram-writer")) {
            String layerId = e.element("layer").getTextTrim();
            boolean occupiedOnly = XmlUtil.getBoolean(e, "occupied-only");
            ret = new ContinuumHistoWriter(p, lm, layerId, occupiedOnly);
            return ret;
        } else if (writerClass.equalsIgnoreCase("surface-census-writer")) {
            ret = new SurfaceCensusWriter(p, lm);
            return ret;
        } else if (writerClass.equalsIgnoreCase("individual-halt-writer")) {
            ret = new IndividualHaltWriter(p, lm);
            return ret;
        } else if (writerClass.equalsIgnoreCase("interface-census-writer")) {
            IntegerArgument focalStateArg = IntegerArgumentFactory.instantiate(e, "focal-state", p.getRandom());
            ret = new InterfaceCensusWriter(p, focalStateArg, lm);
            return ret;
        } else if (writerClass.equalsIgnoreCase("interval-writer")) {
            ret = new IntervalWriter(p, lm);
            return ret;
        } else if (writerClass.equalsIgnoreCase("random-seed-writer")) {
            ret = new RandomSeedWriter(p, lm);
            return ret;
        } else if (writerClass.equalsIgnoreCase("running-time-writer")) {
            ret = new RunningTimeWriter(p, lm);
            return ret;
        } else if (writerClass.equalsIgnoreCase("coordinate-indexer")) {
            ret = new CoordinateIndexer(p, lm);
            return ret;
        } else if (writerClass.equalsIgnoreCase("continuum-state-writer")) {
            ret = new ContinuumStateWriter(p, lm);
            return ret;
        } else if (writerClass.equalsIgnoreCase("time-writer")) {
            ret = new TimeWriter(p, lm);
            return ret;
        } else if (writerClass.equalsIgnoreCase("highlight-writer")) {
            Element channelsElem = e.element("channels");
            int[] channels = XmlUtil.getIntegerArray(channelsElem, "channel");

            // So glad this will all go away with nanoverse.compiler
            List<Integer> channelList = new ArrayList<>(channels.length);

            for (int channel : channels) {
                channelList.add(channel);
            }

            Stream<Integer> channelStream = channelList.stream();
            ret = new HighlightWriter(p, channelStream, lm);
            return ret;
        } else if (writerClass.equalsIgnoreCase("visualization-serializer")) {
            ret = visualizationSerializer(e, p, lm);
            return ret;
        } else if (writerClass.equalsIgnoreCase("correlation-writer")) {
            return correlationWriter(e, p, lm);
        } else {
            throw new IllegalArgumentException("Unrecognized serialization '" + writerClass + "'");
        }
    }

    public static SerializationManager makeManager(Element we, LayerManager layerManager, GeneralParameters p) {
        List<Serializer> writers = new ArrayList<>();

        // No writers? No problem -- return an empty manager.
        if (we == null) {
            return new SerializationManager(p, layerManager, writers);
        }

        for (Object o : we.elements()) {
            Element e = (Element) o;
            Serializer w = SerializationFactory.instantiate(e, p, layerManager);
            writers.add(w);
        }

        SerializationManager manager = new SerializationManager(p, layerManager, writers);
        return manager;
    }

    private static CorrelationWriter correlationWriter(Element e, GeneralParameters p, LayerManager lm) {
        DoubleArgument triggerTimeArg = DoubleArgumentFactory.instantiate(e, "trigger-time", 0.0, p.getRandom());
        String filename = XmlUtil.getString(e, "filename", "correlation.txt");
        return new CorrelationWriter(p, filename, triggerTimeArg, lm);
    }

    private static VisualizationSerializer visualizationSerializer(Element e,
                                                                   GeneralParameters p, LayerManager lm) {

        String prefix;
        Element prefixElement = e.element("prefix");
        if (prefixElement == null) {
            prefix = "frame";
        } else {
            prefix = prefixElement.getTextTrim();
        }

        Element visElement = e.element("visualization");
        Visualization visualization = VisualizationFactory.instantiate(visElement, p);

        return new VisualizationSerializer(p, visualization, prefix, lm);
    }
}
