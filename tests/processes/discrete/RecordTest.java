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

import org.junit.Test;
import processes.*;
import test.LegacyLatticeTest;

import static org.junit.Assert.assertTrue;
/**
 * Created by dbborens on 4/24/14.
 */
public class RecordTest extends LegacyLatticeTest {

    @Test
    public void testLifeCycle() throws Exception {
        MockStepState stepState = new MockStepState();
        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, null);
        Record query = new Record(arguments);
        query.target(null);
        query.fire(stepState);
        assertTrue(stepState.isRecorded());
    }
}
