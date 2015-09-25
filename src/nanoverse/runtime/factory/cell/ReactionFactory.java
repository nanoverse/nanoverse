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

package nanoverse.runtime.factory.cell;

import nanoverse.runtime.layers.continuum.Reaction;
import org.dom4j.Element;
import nanoverse.runtime.structural.utilities.XmlUtil;

/**
 * Created by dbborens on 1/8/15.
 */
public abstract class ReactionFactory {

    public static Reaction instantiate(Element root) {
        double exp = XmlUtil.getDouble(root, "exp", 0.0);
        double inj = XmlUtil.getDouble(root, "inj", 0.0);
        String id = root.element("target").getTextTrim();

        return new Reaction(inj, exp, id);
    }

}
