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

package nanoverse.runtime.io;

import java.io.File;
import java.util.function.Function;

/**
 * Created by dbborens on 10/22/2015.
 */
public class DirectoryMaker {

    private final Function<String, File> pathMaker;

    public DirectoryMaker() {
        pathMaker = fn -> new File(fn);
    }

    public DirectoryMaker(Function<String, File> pathMaker) {
        this.pathMaker = pathMaker;
    }

    public void makeDirectory(String pathStr) {
        File path = pathMaker.apply(pathStr);
        if (!path.exists()) {
            try {
                path.mkdirs();
            } catch (Exception ex) {
                throw new RuntimeException("Could not create directory tree " + pathStr, ex);
            }
        }

    }
}
