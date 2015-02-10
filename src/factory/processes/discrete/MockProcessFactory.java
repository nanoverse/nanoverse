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

package factory.processes.discrete;

import control.GeneralParameters;
import factory.processes.ProcessFactory;
import layers.LayerManager;
import org.dom4j.Element;
import processes.BaseProcessArguments;
import processes.MockProcess;
import structural.utilities.XmlUtil;

/**
 * Abstract class for parsing the requirements pertaining to process
 * mocks. This class is not itself a mock, so it is not in the
 * mock/ source directory. However, it builds a mock object.
 * <p>
 * Created by dbborens on 11/23/14.
 */
public abstract class MockProcessFactory extends ProcessFactory {

    public static MockProcess instantiate(Element e, LayerManager layerManager, GeneralParameters p, int id) {
        BaseProcessArguments arguments = makeProcessArguments(e, layerManager, p, id);
        String identifier = XmlUtil.getString(e, "identifier", "");
        double weight = XmlUtil.getDouble(e, "weight", 1.0);
        int count = XmlUtil.getInteger(e, "count", 1);
        return new MockProcess(arguments, identifier, weight, count);
    }
}
