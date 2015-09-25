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

package nanoverse.runtime.layers.continuum;

import org.junit.*;
import test.LinearMocks;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ContinuumLayerTest extends LinearMocks {

    private ContinuumLayerScheduler scheduler;
    private ContinuumLayerContent content;
    private ContinuumLayer query;

    @Before
    public void init() throws Exception {
        scheduler = mock(ContinuumLayerScheduler.class);
        content = mock(ContinuumLayerContent.class);
        query = new ContinuumLayer(scheduler, content, geom);
    }

    @Test
    public void getIdAsksScheduler() throws Exception {
        when(scheduler.getId()).thenReturn("test");
        assertEquals("test", query.getId());
    }

    @Test
    public void resetCallsScheduler() throws Exception {
        query.reset();
        verify(scheduler).reset();
    }

    @Test
    public void resetCallsContent() throws Exception {
        query.reset();
        verify(content).reset();
    }

    // TODO I can't figure out how to capture a lambda with Mockito.
    @Test
    public void linkerCanQueryContent() throws Exception {
        // This is not really what I'm trying to ask--the real question is commented out here.
        query.getLinker();
        verify(scheduler).getLinker(any());
    }
}