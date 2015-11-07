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

package loaders;

import nanoverse.compiler.pipeline.translate.symbol.*;
import factories.*;
import org.stringtemplate.v4.ST;

import java.io.*;

/**
 * Created by dbborens on 7/30/2015.
 */
public class LoaderSkeletonWriter {

    private final LoaderSkeletonGenerator gen;
    private final String basePath;

    public LoaderSkeletonWriter(String basePath) {
        gen = new LoaderSkeletonGenerator();
        this.basePath = basePath;
    }

    public void write(MapSymbolTable st) {
        String code = gen.generate(st);
        String path = mkdir(st);
        record(st, code, path);
    }

    private String displayName(MapSymbolTable st) {
        String displayName = st.getClass()
                .getSimpleName()
                .replace("SymbolTable", "");

        return displayName;
    }

    private void record(MapSymbolTable st, String code, String path) {
        String displayName = displayName(st);
        String filename = displayName + "Loader.java";
        String filePath = path + "/" + filename;
        System.out.print("Writing " + filePath + "...");
        File file = new File(filePath);

        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(code);
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Done.");
    }

    private String mkdir(MapSymbolTable st) {
        String relPath = st.getInstanceClass()
                .getPackage()
                .getName()
                .replace(".", "/");

        String path = basePath + "/" + relPath;
        File file = new File(path);
        file.mkdirs();
        return path;
    }
}
