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

package processes.discrete;

import control.arguments.Argument;
import geometry.set.CoordinateSet;
import layers.cell.CellLayer;
import processes.BaseProcessArguments;
import processes.NanoverseProcess;

public abstract class CellProcess extends NanoverseProcess {
    // These are easily accessed from the layer manager, but there
    // are very many calls to them thanks to some legacy code.
    private final CellProcessArguments cpArguments;
    private final BaseProcessArguments arguments;
    public CellProcess(BaseProcessArguments arguments, CellProcessArguments cpArguments) {
        super(arguments);
        this.cpArguments = cpArguments;
        this.arguments = arguments;
    }

    protected CellLayer getLayer() {
        return arguments.getLayerManager().getCellLayer();
    }

    protected CoordinateSet getActiveSites() {
        return cpArguments.getActiveSites();
    }

    protected Argument<Integer> getMaxTargets() {
        return cpArguments.getMaxTargets();
    }
}
