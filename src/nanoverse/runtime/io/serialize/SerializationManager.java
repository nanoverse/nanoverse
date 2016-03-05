/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.runtime.io.serialize;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @untested
 */
public class SerializationManager extends Serializer {

    private List<Serializer> writers;
    private AtomicBoolean isRunningFlag;
    private BufferedImage outputImage;
    private boolean showUserInterface;

    @FactoryTarget(displayName = "OutputManager")
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

    public void dispatchHalt(HaltCondition ex) {
        for (Serializer tw : writers) {
            if (showUserInterface) {
                tw.setShowUserInterface(showUserInterface);
                tw.setOutputImage(outputImage);
            }
            tw.dispatchHalt(ex);
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

    public void flush(StepState stepState) {
        if (stepState.isRecorded()) {
            for (Serializer tw : writers) {
                tw.flush(stepState);

                if (showUserInterface) {
                    if (!isRunningFlag.get())
                        System.out.println("Execution Paused");

                    while (!isRunningFlag.get()) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public int hashCode() {
        return writers != null ? writers.hashCode() : 0;
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

    public void setIsRunningFlag(AtomicBoolean isRunningFlag) {
        this.isRunningFlag = isRunningFlag;
    }

    public void setOutputImage(BufferedImage outputImage) {
        this.outputImage = outputImage;
    }

    public void setShowUserInterface(boolean showUserInterface) {
        this.showUserInterface = showUserInterface;
    }
}
