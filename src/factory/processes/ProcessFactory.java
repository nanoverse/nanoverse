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

package factory.processes;

import control.GeneralParameters;
import control.arguments.Argument;
import control.arguments.CellDescriptor;
import factory.control.arguments.CellDescriptorFactory;
import factory.control.arguments.DoubleArgumentFactory;
import factory.control.arguments.IntegerArgumentFactory;
import factory.geometry.set.CoordinateSetFactory;
import factory.processes.discrete.FillProcessFactory;
import factory.processes.discrete.MockProcessFactory;
import factory.processes.discrete.ScatterProcessFactory;
import factory.processes.discrete.TriggerProcessFactory;
import factory.processes.discrete.filter.FilterFactory;
import geometry.Geometry;
import geometry.set.CoordinateSet;
import layers.LayerManager;
import layers.continuum.ContinuumLayer;
import no.uib.cipr.matrix.DenseMatrix;
import org.dom4j.Element;
import processes.BaseProcessArguments;
import processes.EcoProcess;
import processes.continuum.*;
import processes.discrete.*;
import processes.discrete.check.CheckForDomination;
import processes.discrete.check.CheckForExtinction;
import processes.discrete.check.CheckForFixation;
import processes.discrete.check.CheckForThresholdOccupancy;
import processes.discrete.filter.Filter;
import processes.temporal.ExponentialInverse;
import processes.temporal.Tick;
import structural.utilities.XmlUtil;

import java.util.function.Consumer;

/**
 * Created by dbborens on 11/23/14.
 */
public abstract class ProcessFactory {
    public static EcoProcess instantiate(Element e, LayerManager layerManager, GeneralParameters p, int id) {

        String processClass = e.getName();

        BaseProcessArguments arguments = makeProcessArguments(e, layerManager, p, id);

        if (processClass.equalsIgnoreCase("exponential-inverse")) {
            return new ExponentialInverse(arguments);

        } else if (processClass.equalsIgnoreCase("tick")) {
            Argument<Double> dt = DoubleArgumentFactory.instantiate(e, "dt", 1.0, p.getRandom());
            return new Tick(arguments, dt);

        } else if (processClass.equalsIgnoreCase("divide")) {
            CellProcessArguments cpArguments = makeCellProcessArguments(e, layerManager, p);
            return new Divide(arguments, cpArguments);

        } else if (processClass.equalsIgnoreCase("occupied-neighbor-swap")) {
            CellProcessArguments cpArguments = makeCellProcessArguments(e, layerManager, p);
            return new OccupiedNeighborSwap(arguments, cpArguments);

        } else if (processClass.equalsIgnoreCase("general-neighbor-swap")) {
            CellProcessArguments cpArguments = makeCellProcessArguments(e, layerManager, p);
            return new GeneralNeighborSwap(arguments, cpArguments);

        } else if (processClass.equalsIgnoreCase("scatter")) {
            return ScatterProcessFactory.instantiate(e, layerManager, p, id);

        } else if (processClass.equalsIgnoreCase("fill")) {
            return FillProcessFactory.instantiate(e, layerManager, p, id);

        } else if (processClass.equalsIgnoreCase("mock-process")) {
            return MockProcessFactory.instantiate(e, layerManager, p, id);

        } else if (processClass.equalsIgnoreCase("trigger")) {
            return TriggerProcessFactory.instantiate(e, layerManager, p, id);

        } else if (processClass.equalsIgnoreCase("cull")) {
            double threshold = XmlUtil.getDouble(e, "threshold", 0.0);
            CellProcessArguments cpArguments = makeCellProcessArguments(e, layerManager, p);
            return new Cull(arguments, cpArguments, threshold);

        } else if (processClass.equalsIgnoreCase("diagnostic")) {
            CellProcessArguments cpArguments = makeCellProcessArguments(e, layerManager, p);
            return new DiagnosticProcess(arguments, cpArguments);

        } else if (processClass.equalsIgnoreCase("check-for-fixation")) {
            CellProcessArguments cpArguments = makeCellProcessArguments(e, layerManager, p);
            return new CheckForFixation(arguments, cpArguments);

        } else if (processClass.equalsIgnoreCase("check-threshold-occupancy")) {
            Argument<Double> thresholdOccupancy = DoubleArgumentFactory.instantiate(e, "threshold", 1.0, p.getRandom());
            CellProcessArguments cpArguments = makeCellProcessArguments(e, layerManager, p);
            return new CheckForThresholdOccupancy(arguments, cpArguments, thresholdOccupancy);

        } else if (processClass.equalsIgnoreCase("check-for-domination")) {
            CellProcessArguments cpArguments = makeCellProcessArguments(e, layerManager, p);
            Argument<Double> thresholdFraction = DoubleArgumentFactory.instantiate(e, "threshold", 1.0, p.getRandom());
            Argument<Integer> targetState = IntegerArgumentFactory.instantiate(e, "target", -1, p.getRandom());
            return new CheckForDomination(arguments, cpArguments, targetState, thresholdFraction);

        } else if (processClass.equalsIgnoreCase("check-for-extinction")) {
            Argument<Double> threshold = DoubleArgumentFactory.instantiate(e, "threshold", 0.0, p.getRandom());
            CellProcessArguments cpArguments = makeCellProcessArguments(e, layerManager, p);
            return new CheckForExtinction(arguments, cpArguments, threshold);

        } else if (processClass.equalsIgnoreCase("diffuse")) {
            return diffusionProcess(e, layerManager, arguments);

        } else if (processClass.equalsIgnoreCase("release")) {
            ContinuumLayer layer = resolveLayer(e, layerManager);
            return new ScheduleRelease(arguments, layer.getScheduler());

        } else if (processClass.equalsIgnoreCase("hold")) {
            ContinuumLayer layer = resolveLayer(e, layerManager);
            return new ScheduleHold(arguments, layer.getScheduler());

        } else if (processClass.equalsIgnoreCase("integrate")) {
            ContinuumLayer layer = resolveLayer(e, layerManager);
            return new Integrate(arguments, layer.getScheduler());

        } else if (processClass.equalsIgnoreCase("record")) {
            CellProcessArguments cpArguments = makeCellProcessArguments(e, layerManager, p);
            return new Record(arguments, cpArguments);

        } else {
            String msg = "Unrecognized process '" +
                    processClass + ".'";

            throw new IllegalArgumentException(msg);
        }
    }

    private static ContinuumLayer resolveLayer(Element e, LayerManager layerManager) {
        String layerId = XmlUtil.getString(e, "layer");
        ContinuumLayer layer = layerManager.getContinuumLayer(layerId);
        return layer;
    }

    private static OperatorProcess diffusionProcess(Element e, LayerManager layerManager, BaseProcessArguments arguments) {
        double constant = XmlUtil.getDouble(e, "constant");
        ContinuumLayer layer = resolveLayer(e, layerManager);
        Geometry geometry = layer.getGeometry();
        DiffusionConstantHelper helper = new DiffusionConstantHelper(constant,
                geometry.getConnectivity(),
                geometry.getDimensionality());
        DiffusionOperator operator = new DiffusionOperator(helper, geometry);
        Consumer<DenseMatrix> target = matrix -> layer.getScheduler().apply(matrix);
        OperatorProcess process = new OperatorProcess(arguments, operator, target);
        return process;
    }

    protected static BaseProcessArguments makeProcessArguments(Element e,
                                                               LayerManager layerManager,
                                                               GeneralParameters p,
                                                               int id) {

        Argument<Integer> start = IntegerArgumentFactory.instantiate(e, "start", 0, p.getRandom());
        Argument<Integer> period = IntegerArgumentFactory.instantiate(e, "period", 1, p.getRandom());
        return new BaseProcessArguments(layerManager, p, id, start, period);
    }

    protected static CellProcessArguments makeCellProcessArguments(Element e, LayerManager layerManager, GeneralParameters p) {
        Geometry geometry = layerManager.getCellLayer().getGeometry();
        CoordinateSet activeSites = getActiveSites(e, geometry, p);
        Argument<Integer> maxTargets = getMaxTargets(e, p);

        return new CellProcessArguments(activeSites, maxTargets);
    }

    protected static Filter loadFilters(Element root, LayerManager layerManager, GeneralParameters p) {
        Element e = root.element("filters");
        Filter filter = FilterFactory.instantiate(e, layerManager, p);
        return filter;
    }

    private static Argument<Integer> getMaxTargets(Element e, GeneralParameters p) {
        return IntegerArgumentFactory.instantiate(e, "max-targets", -1, p.getRandom());
    }

    private static CoordinateSet getActiveSites(Element e, Geometry geom, GeneralParameters p) {
        Element sitesElem = e.element("active-sites");
        CoordinateSet ret = CoordinateSetFactory.instantiate(sitesElem, geom, p);
        return ret;
    }

    protected static CellDescriptor makeCellDescriptor(Element e, String key, LayerManager layerManager, GeneralParameters p) {
        Element cellDescriptor = e.element(key);
        CellDescriptor cellArgument = CellDescriptorFactory.instantiate(cellDescriptor, layerManager, p);
        return cellArgument;
    }
}
