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

package nanoverse.runtime.processes.continuum;

import nanoverse.runtime.layers.continuum.ContinuumLayerScheduler;
import nanoverse.runtime.processes.*;
import org.junit.*;

import static org.mockito.Mockito.*;

public class ScheduleHoldTest {

    public ScheduleHold query;
    public StepState state;
    public ContinuumLayerScheduler scheduler;
    public BaseProcessArguments arguments;

    @Before
    public void init() throws Exception {
        state = mock(StepState.class);
        scheduler = mock(ContinuumLayerScheduler.class);
        arguments = mock(BaseProcessArguments.class);
        query = new ScheduleHold(arguments, scheduler);
    }

    @Test
    public void fireCallsSchedulerHold() throws Exception {
        query.fire(state);
        verify(scheduler).hold();
    }

    @Test
    public void initDoesNothing() throws Exception {
        query.init();
        verifyNoMoreInteractions(state, scheduler, arguments);
    }
}