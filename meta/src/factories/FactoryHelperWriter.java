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

package factories;

import java.io.*;
import java.lang.reflect.Constructor;

/**
 * Created by dbborens on 7/30/2015.
 */
public class FactoryHelperWriter {

    private final FactoryHelperGenerator gen;
    private final String basePath;
    private final FactoryTargetHelper targetHelper;

    public FactoryHelperWriter(String basePath) {
        gen = new FactoryHelperGenerator();
        this.basePath = basePath;
        targetHelper = new FactoryTargetHelper();
    }

    public void write(Constructor c) {
        String code = gen.generate(c);
        String path = mkdir(c);
        record(c, code, path);
    }

    private void record(Constructor c, String code, String path) {
        String displayName = targetHelper.getDisplayName(c);
        String filename = displayName + "FactoryHelper.java";
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

    private String mkdir(Constructor c) {
        String relPath = c.getDeclaringClass()
                .getPackage()
                .getName()
                .replace(".", "/");

        String path = basePath + "/" + relPath;
        File file = new File(path);
        file.mkdirs();
        return path;
    }
}
