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

import processes.BaseProcessArguments;
import processes.MockStepState;
import test.EslimeLatticeTestCase;

/**
 * Created by dbborens on 4/24/14.
 */
public class RecordTest extends EslimeLatticeTestCase {

    public void testLifeCycle() throws Exception {
        MockStepState stepState = new MockStepState();
        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, null);
        CellProcessArguments cpArguments = makeCellProcessArguments(geom);
        Record query = new Record(arguments, cpArguments);
        query.target(null);
        query.fire(stepState);
        assertTrue(stepState.isRecorded());
    }
}
