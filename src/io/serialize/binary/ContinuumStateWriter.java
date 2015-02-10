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
import control.identifiers.Extrema;
import io.serialize.Serializer;
import layers.LayerManager;
import processes.StepState;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by dbborens on 12/11/13.
 * <p>
 * ContinuumStateWriter encodes a binary file containing the
 * state of the model at specified time points.
 */
public class ContinuumStateWriter extends Serializer {

    //    private SoluteLayer layer;
    private Extrema extrema;


    private DataOutputStream dataStream;

    // Canonical sites (for this instance)
    private Coordinate[] sites;


    public ContinuumStateWriter(GeneralParameters p, LayerManager lm) {
        super(p, lm);
        throw new NotImplementedException();
    }

    @Override
    public void init() {
//        super.init();
//
//        if (lm.getSoluteLayers().length == 0) {
//            throw new IllegalArgumentException("Attempted to build a continuum state writer for a model that contains no continuum components.");
//        }
//
//        if (lm.getSoluteLayers().length != 1) {
//            throw new UnsupportedOperationException("Support not yet implemented for multiple solute layer serialization. To do this, all you need to do is turn all of the state variables into maps of ID --> whatever.");
//        }
//
//        // We currently get an array of solute layers, but that array had better only have
//        // one thing in it until multi-layer support is introduced.
//        layer = lm.getSoluteLayers()[0];
//
//        initStructures();
//
//        makeFiles();
//
//        initFiles();
    }

//    private void initFiles() {
//        String filename = FileConventions.makeContinuumStateFilename(layer.getId());
//        String filepath = p.getInstancePath() + '/' + filename;
//
//        try {
//
//            File stateFile = new File(filepath);
//
//            FileOutputStream fileOutputStream = new FileOutputStream(stateFile);
//            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
//            dataStream = new DataOutputStream(bufferedOutputStream);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    public void initStructures() {
//        sites = layer.getGeometry().getCanonicalSites();
//
//        // Initialize extrema
//        extrema = new Extrema();
//    }

    @Override
    public void flush(StepState stepState) {
//        try {
//            // Write opening parity sequence
//            writeStartParitySequence();
//
//            // Write entry header
//            dataStream.writeDouble(stepState.getTime());
//            dataStream.writeInt(stepState.getFrame());
//
//            // Process state vector
//            processData(stepState.getFrame());
//
//            // Write closing parity sequence
//            writeEndParitySequence();
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
    }

    /**
     * Write the state vector to the data stream and update extrema.
     *
     * @param frame
     * @throws IOException
     */
//    private void processData(int frame) throws IOException {
//        DenseVector data = layer.getState().getSolution();
//        updateExtrema(data, frame);
//
//        PrimitiveSerializer.writeDoubleVector(dataStream, data.getData());
//    }
//
//    private void updateExtrema(DenseVector data, int frame) {
//        for (int i = 0; i < data.size(); i++) {
//            double datum = data.get(i);
//            extrema.consider(datum, sites[i], frame);
//        }
//    }
//
//    /**
//     * Encode parity sequence for entry start
//     */
//    private void writeStartParitySequence() throws IOException {
//        for (int i = 0; i < 2; i++) {
//            dataStream.writeBoolean(true);
//        }
//    }

    /**
     * Encode parity sequence for entry end
     */
//    private void writeEndParitySequence() throws IOException {
//        for (int i = 0; i < 2; i++) {
//            dataStream.writeBoolean(false);
//        }
//    }
//
//    @Override
    public void dispatchHalt(HaltCondition ex) {
//        conclude();
//        closed = true;
    }

    //
//    private void conclude() {
//        // Close the state data file.
//        try {
//            dataStream.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        writeMetadata();
//
//    }
//
//    private void writeMetadata() {
//        // Write the extrema file.
//        try {
//            String filename = FileConventions.makeContinuumMetadataFilename(layer.getId());
//            File metadata = new File(filename);
//            String filepath = p.getInstancePath() + '/' + metadata;
//            FileWriter mfw = new FileWriter(filepath);
//            BufferedWriter mbw = new BufferedWriter(mfw);
//
//            mbw.write("extrema>");
//            mbw.write(extrema.toString());
//            mbw.write('\n');
//
//            mbw.close();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
    @Override
    public void close() {
        // Doesn't do anything
    }

}
