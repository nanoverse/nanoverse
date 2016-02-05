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

import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by dbborens on 12/11/13.
 */
public abstract class Serializer {
    protected LayerManager lm;
    protected GeneralParameters p;
    protected boolean closed = true;

    public Serializer(GeneralParameters p, LayerManager lm) {
        this.p = p;
        this.lm = lm;
    }

    public void init() {
        if (!closed) {
            throw new IllegalStateException("Attempting to initialize active writer!");
        }
    }


    /**
     * Signal that the current instance is concluded. Conclude analysis, finish
     * writing to files, and close handles for instance.
     */
    public abstract void dispatchHalt(HaltCondition ex);

    protected void makeFiles() {
        // Create the directory for state files, if needed
        mkDir(p.getPath(), true);

        mkDir(p.getInstancePath(), true);

        System.out.println(p.getInstancePath());
    }

    protected void mkDir(String pathStr, boolean recursive) {
        File path = new File(pathStr);
        if (!path.exists()) {
            try {
                if (recursive)
                    path.mkdirs();
                else
                    path.mkdir();
            } catch (Exception ex) {
                System.out.println("Could not create directory tree " + pathStr);
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Conclude analysis, finish writing to files, and close for entire project.
     */
    public abstract void close();

    /**
     * Record the state of the simulation.
     *
     * @param stepState
     */
    public abstract void flush(StepState stepState);

    protected BufferedWriter makeBufferedWriter(String filename) {
        BufferedWriter bw;
        try {
            File file = new File(filename);
            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return bw;
    }

    /**
     * Convenience method for appending the contents of a string builder
     * to a buffered writer, converting any IO exceptions to runtime
     * exceptions.
     *
     * @param bw
     * @param line
     */
    protected void hAppend(BufferedWriter bw, StringBuilder line) {
        try {
            bw.append(line.toString());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected void hClose(BufferedWriter bw) {
        try {
            bw.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setOutputImage(BufferedImage image) {
    }

    public void setShowUserInterface(boolean showUserInterface) {
    }
}
