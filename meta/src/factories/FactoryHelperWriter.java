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

package factories;

import com.google.common.base.Strings;
import structural.annotations.FactoryTarget;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * Created by dbborens on 7/30/2015.
 */
public class FactoryHelperWriter {

    private final FactoryHelperGenerator gen;
    private final String basePath;

    public FactoryHelperWriter(String basePath) {
        gen = new FactoryHelperGenerator();
        this.basePath = basePath;
    }

    public void write(Class clazz) {
        String code = gen.generate(clazz);
        String path = mkdir(clazz);
        record(clazz, code, path);
    }

    private void record(Class clazz, String code, String path) {
        String displayName = getDisplayName(getFactoryConstructor(clazz));
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

    private String mkdir(Class clazz) {
        String relPath = clazz.getPackage().getName().replace(".", "/");
        String path = basePath + "/" + relPath;
        File file = new File(path);
        file.mkdirs();
        return path;
    }

    private String getDisplayName(Constructor c) {
        FactoryTarget ft = (FactoryTarget) Arrays.stream(c.getDeclaredAnnotations())
                .filter(a -> a instanceof FactoryTarget)
                .findFirst()
                .get();

        // If the user did not specify a display name, use the original name
        if (ft.displayName() == "") {
            return c.getDeclaringClass().getSimpleName();
        }

        return ft.displayName();
    }

    private Constructor getFactoryConstructor(Class clazz) {
        try {
            return Arrays.stream(clazz.getConstructors())
                    .filter(c -> annotated(c))
                    .findFirst()
                    .get();
        } catch (NoSuchElementException ex) {
            throw new IllegalStateException("No @FactoryTarget constructor on class "
                    + clazz.getSimpleName());
        }
    }

    private boolean annotated(Constructor c) {
        return Arrays.stream(c.getDeclaredAnnotations())
                .anyMatch(annotation ->
                        annotation instanceof FactoryTarget);
    }
}
