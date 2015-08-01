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

package io.serialize.text;

import control.GeneralParameters;
import control.halt.HaltCondition;
import control.identifiers.Coordinate;
import geometry.Geometry;
import io.serialize.Serializer;
import layers.LayerManager;
import processes.StepState;
import structural.annotations.FactoryTarget;
import structural.utilities.FileConventions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CoordinateIndexer extends Serializer {

    private Geometry geometry;

    // This file specifies the relationship between vector index and coordinate.

    @FactoryTarget
    public CoordinateIndexer(GeneralParameters p, LayerManager lm) {
        super(p, lm);
    }

    protected void makeCoordinateMap() {
        try {

            String coordMapFileStr = p.getInstancePath() + '/' + FileConventions.COORDINATE_FILENAME;
            File coordMapFile = new File(coordMapFileStr);
            FileWriter fw = new FileWriter(coordMapFile);
            BufferedWriter bwp = new BufferedWriter(fw);

            for (Coordinate c : geometry.getCanonicalSites()) {
                StringBuilder sb = new StringBuilder();
                sb.append(geometry.getIndexer().apply(c));
                sb.append("\t");
                sb.append(c.toString());
                sb.append("\n");
                bwp.append(sb.toString());
            }

            bwp.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void init() {
        super.init();
        geometry = lm.getCellLayer().getGeometry();
    }

    @Override
    public void flush(StepState stepState) {

    }

    @Override
    public void dispatchHalt(HaltCondition ex) {
        // Writes coordinate map of model to disk
        makeCoordinateMap();
    }

    @Override
    public void close() {
        // Doesn't do anything
    }
}
