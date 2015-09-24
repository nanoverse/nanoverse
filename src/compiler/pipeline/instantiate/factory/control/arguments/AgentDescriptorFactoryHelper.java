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
package compiler.pipeline.instantiate.factory.control.arguments;

import java.util.List;

import agent.action.*;
import control.arguments.Argument;
import control.arguments.IntegerArgument;
import control.arguments.DoubleArgument;
import java.util.Map;
import java.util.stream.Stream;

import layers.LayerManager;
import control.arguments.CellDescriptor;
import layers.continuum.Reaction;


public class AgentDescriptorFactoryHelper {

    public CellDescriptor build(LayerManager layerManager, IntegerArgument cellState, DoubleArgument threshold, DoubleArgument initialHealth, Stream<Reaction> reactions, Map<String, ActionDescriptor> behaviorDescriptors) {
        return new CellDescriptor(layerManager, cellState, threshold, initialHealth, reactions, behaviorDescriptors);
    }
}