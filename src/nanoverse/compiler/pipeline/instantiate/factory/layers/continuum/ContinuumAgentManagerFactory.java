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
package nanoverse.compiler.pipeline.instantiate.factory.layers.continuum;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.layers.continuum.*;

public class ContinuumAgentManagerFactory implements Factory<ContinuumAgentManager> {

    private final ContinuumAgentManagerFactoryHelper helper;

    private ReactionLinker loader;
    private ContinuumAgentIndex index;
    private String id;

    public ContinuumAgentManagerFactory() {
        helper = new ContinuumAgentManagerFactoryHelper();
    }

    public ContinuumAgentManagerFactory(ContinuumAgentManagerFactoryHelper helper) {
        this.helper = helper;
    }

    public void setLoader(ReactionLinker loader) {
        this.loader = loader;
    }

    public void setIndex(ContinuumAgentIndex index) {
        this.index = index;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public ContinuumAgentManager build() {
        return helper.build(loader, index, id);
    }
}