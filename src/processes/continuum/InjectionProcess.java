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

package processes.continuum;

import control.arguments.Argument;
import control.halt.HaltCondition;
import no.uib.cipr.matrix.DenseVector;
import processes.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Created by dbborens on 6/4/2015.
 */
public class InjectionProcess extends ContinuumProcess {

    private final Argument<Double> valueArg;
    private final String layerId;

    public InjectionProcess(BaseProcessArguments arguments, String layerId, Argument<Double> valueArg) {
        super(arguments);
        this.valueArg = valueArg;
        this.layerId = layerId;
    }

    @Override
    public void fire(StepState state) throws HaltCondition {
        int n = getLayerManager().getCellLayer().getGeometry().getCanonicalSites().length;
        DenseVector source = new DenseVector(n);

        for (int i = 0; i < n; i++) {
            double value = valueArg.next();
            source.set(i, value);
        }

        getLayerManager().getContinuumLayer(layerId).getScheduler().inject(source);
    }

    @Override
    public void init() {
        // Does nothing
    }
}
