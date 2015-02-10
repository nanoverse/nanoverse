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

package io.deserialize;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;

/**
 * Created by dbborens on 3/28/14.
 */
public class ContinuumStateReaderManager {


    private HashMap<String, ContinuumStateReader> readers;

    public ContinuumStateReaderManager(String root, String[] ids) {
        throw new NotImplementedException();
//        readers = new HashMap<>(ids.length);
//        for (String id : ids) {
//            String filename = FileConventions.makeContinuumStateFilename(id);
//            String absoluteFilename = root + filename;
//            System.out.println(absoluteFilename);
//            File file = new File(absoluteFilename);
//            ContinuumStateReader reader = new ContinuumStateReader(file);
//            readers.put(id, reader);
//        }
    }

//    public Map<String, double[]> next() {
//        Map<String, double[]> ret = new HashMap<>(readers.size());
//
//        for (String id : readers.keySet()) {
//            ContinuumStateReader reader = readers.get(id);
//            double[] state = reader.next();
//            ret.put(id, state);
//        }
//
//        return ret;
//    }

//    /**
//     * Load next frame into lightweight system state object.
//     */
//    public void populate(LightweightSystemState systemState) {
//        Map<String, double[]> continuumStateMap = next();
//
//        for (String id : continuumStateMap.keySet()) {
//            double[] soluteVector = continuumStateMap.get(id);
//            systemState.initSoluteLayer(id, soluteVector);
//        }
//    }
}
