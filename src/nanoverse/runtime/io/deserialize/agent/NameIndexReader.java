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

package nanoverse.runtime.io.deserialize.agent;

import nanoverse.runtime.io.deserialize.TextInputHandle;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by dbborens on 10/26/2015.
 */
public class NameIndexReader {

    public Map<Integer, String> readReverseIndex(TextInputHandle indexFile) {
        return indexFile.lines()
                .map(String::trim)
                .map(trimmed -> trimmed.split("\t"))
                .collect(Collectors.toMap(this::getKey, this::getValue));
    }

    private Integer getKey(String[] fields) {
        String keyElem = fields[0];
        return Integer.valueOf(keyElem);
    }

    private String getValue(String[] fields) {
        return fields[1];
    }
}
