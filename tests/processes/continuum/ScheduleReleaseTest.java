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

import layers.continuum.ContinuumLayerScheduler;
import org.junit.Before;
import org.junit.Test;
import processes.BaseProcessArguments;
import processes.StepState;

import static org.mockito.Mockito.*;

public class ScheduleReleaseTest {
    public ScheduleRelease query;
    public StepState state;
    public ContinuumLayerScheduler scheduler;
    public BaseProcessArguments arguments;

    @Before
    public void init() throws Exception {
        state = mock(StepState.class);
        scheduler = mock(ContinuumLayerScheduler.class);
        arguments = mock(BaseProcessArguments.class);
        query = new ScheduleRelease(arguments, scheduler);
    }

    @Test
    public void fireCallsSchedulerRelease() throws Exception {
        query.fire(state);
        verify(scheduler).release();
    }

    @Test
    public void initDoesNothing() throws Exception {
        query.init();
        verifyNoMoreInteractions(state, scheduler, arguments);
    }

}