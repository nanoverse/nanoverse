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
package nanoverse.compiler.pipeline.instantiate.factory.io.serialize.interactive;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.serialize.interactive.ProgressReporter;
import nanoverse.runtime.layers.LayerManager;

public class ProgressReporterFactory implements Factory<ProgressReporter> {

    private final ProgressReporterFactoryHelper helper;

    private GeneralParameters p;
    private LayerManager lm;

    public ProgressReporterFactory() {
        helper = new ProgressReporterFactoryHelper();
    }

    public ProgressReporterFactory(ProgressReporterFactoryHelper helper) {
        this.helper = helper;
    }

    public void setP(GeneralParameters p) {
        this.p = p;
    }

    public void setLm(LayerManager lm) {
        this.lm = lm;
    }

    @Override
    public ProgressReporter build() {
        return helper.build(p, lm);
    }
}