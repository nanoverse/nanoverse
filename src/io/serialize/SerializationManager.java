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

package io.serialize;

import control.GeneralParameters;
import control.halt.HaltCondition;
import layers.LayerManager;
import processes.StepState;
import structural.annotations.FactoryTarget;

import java.util.List;

/**
 * @untested
 */
public class SerializationManager extends Serializer {

    private List<Serializer> writers;

    @FactoryTarget
    public SerializationManager(GeneralParameters p, LayerManager layerManager, List<Serializer> writers) {
        super(p, layerManager);
        this.writers = writers;
    }

    /**
     * Opens handles / initializes data structures for a new instance.
     * Blows up if these were left open from the previous instance.
     */
    public void init() {
        for (Serializer tw : writers) {
            tw.init();
        }
    }

    public void flush(StepState stepState) {
        if (stepState.isRecorded()) {
            for (Serializer tw : writers) {
                tw.flush(stepState);
            }
        }
    }

    /**
     * Conclude the entire simulation project.
     */
    public void close() {
        for (Serializer tw : writers) {
            tw.close();
        }
    }

    public void dispatchHalt(HaltCondition ex) {
        for (Serializer tw : writers) {
            tw.dispatchHalt(ex);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SerializationManager that = (SerializationManager) o;

        if (that.writers.size() != this.writers.size()) {
            return false;
        }

        for (int i = 0; i < writers.size(); i++) {
            if (!writers.get(i).equals(that.writers.get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return writers != null ? writers.hashCode() : 0;
    }
}
