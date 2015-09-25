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

package nanoverse.runtime.io.deserialize.continuum;

import java.io.*;
import java.nio.file.Files;
import java.util.stream.Stream;

/**
 * Created by dbborens on 5/27/2015.
 */
public abstract class OverviewInstanceReader {

    public static Stream<String> getIdStream(File file) {
        Stream<String> ret;

        try {
            ret = Files.lines(file.toPath()).map(String::trim);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return ret;
    }
}
