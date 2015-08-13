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
package compiler.pipeline.instantiate.factory.io.serialize.text;

import control.arguments.Argument;
import io.serialize.text.CorrelationWriter;
import control.GeneralParameters;
import control.arguments.DoubleArgument;
import layers.LayerManager;
import compiler.pipeline.instantiate.factory.Factory;

public class CorrelationWriterFactory implements Factory<CorrelationWriter> {

    private final CorrelationWriterFactoryHelper helper;

    private GeneralParameters p;
    private String filename;
    private Argument<Double> triggerTimeArg;
    private LayerManager lm;

    public CorrelationWriterFactory() {
        helper = new CorrelationWriterFactoryHelper();
    }

    public CorrelationWriterFactory(CorrelationWriterFactoryHelper helper) {
        this.helper = helper;
    }

    public void setP(GeneralParameters p) {
        this.p = p;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setTriggerTimeArg(Argument<Double> triggerTimeArg) {
        this.triggerTimeArg = triggerTimeArg;
    }

    public void setLm(LayerManager lm) {
        this.lm = lm;
    }

    @Override
    public CorrelationWriter build() {
        return helper.build(p, filename, triggerTimeArg, lm);
    }
}