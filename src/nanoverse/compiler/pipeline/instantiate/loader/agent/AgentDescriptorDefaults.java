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

package nanoverse.compiler.pipeline.instantiate.loader.agent;

import nanoverse.runtime.agent.action.ActionDescriptor;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.layers.continuum.Reaction;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by dbborens on 8/25/2015.
 */
public class AgentDescriptorDefaults {
    public Map<String, ActionDescriptor> behaviors() {
        return new HashMap<>(0);
    }

    public IntegerArgument clazz() {
        return new ConstantInteger(1);
    }

    public DoubleArgument initialHealth() {
        return new ConstantDouble(1.0);
    }

    public DoubleArgument threshold() {
        return new ConstantDouble(1.0);
    }

    public Stream<Reaction> reactions() {
        return Stream.empty();
    }

}