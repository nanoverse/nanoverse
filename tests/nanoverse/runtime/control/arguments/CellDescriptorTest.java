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

package nanoverse.runtime.control.arguments;

import nanoverse.runtime.agent.action.*;
import nanoverse.runtime.agent.BehaviorCell;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.*;
import nanoverse.runtime.layers.continuum.*;
import org.junit.*;
import test.TestBase;

import java.util.*;
import java.util.stream.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * TODO The sheer number of mocks required tells you that this class needs refactoring
 */
public class CellDescriptorTest extends TestBase {

    private DoubleArgument threshold;
    private DoubleArgument initialHealth;
    private IntegerArgument cellState;
    private Reaction reaction1, reaction2;
    private ActionDescriptor behaviorDescriptor;
    private Action behavior;

    private CellDescriptor query;

    @Before
    public void init() throws Exception {
        initEverythingButReactions();
        initReactions();
    }

    private void initEverythingButReactions() {
        threshold = new ConstantDouble(0.5);
        initialHealth = new ConstantDouble(0.75);
        cellState = new ConstantInteger(1);


        // TODO Instantiation of behaviors from descriptors should be handled by a helper
        behaviorDescriptor = mock(ActionDescriptor.class);
        behavior = mock(Action.class);
        when(behaviorDescriptor.instantiate(any())).thenReturn(behavior);
        Map<String, ActionDescriptor> behaviorDescriptors = new HashMap<>(1);
        behaviorDescriptors.put("behavior", behaviorDescriptor);

        LayerManager layerManager = mock(LayerManager.class);

        // TODO: Refactor CellLayer hierarchy (86866040)
        // There are too many calls -- these things should
        // all be handled in child objects that can be mocked.
        CellLayer cellLayer = mock(CellLayer.class);
        CellLayerViewer viewer = mock(CellLayerViewer.class);
        when(viewer.exists(any())).thenReturn(true);
        when(cellLayer.getViewer()).thenReturn(viewer);
        when(layerManager.getCellLayer()).thenReturn(cellLayer);
        when(layerManager.getCellLayer().getViewer().exists(any())).thenReturn(true);
        CellLookupManager lookup = mock(CellLookupManager.class);
        when(lookup.getCellLocation(any())).thenReturn(mock(Coordinate.class));
        when(cellLayer.getLookupManager()).thenReturn(lookup);
        when(cellLayer.getUpdateManager()).thenReturn(mock(CellUpdateManager.class));
        ContinuumLayer continuumLayer = mock(ContinuumLayer.class);
        ContinuumAgentLinker linker = mock(ContinuumAgentLinker.class);
        when(continuumLayer.getLinker()).thenReturn(linker);
        when(layerManager.getContinuumLayer(any())).thenReturn(continuumLayer);

        query = new CellDescriptor(layerManager);
        query.setCellState(cellState);
        query.setInitialHealth(initialHealth);
        query.setThreshold(threshold);
        query.setBehaviorDescriptors(behaviorDescriptors);
    }

    private void initReactions() {
        // TODO Assignment of reactions should be handled by a helper
        reaction1 = mock(Reaction.class);
        when(reaction1.getId()).thenReturn("1");

        reaction2 = mock(Reaction.class);
        when(reaction2.getId()).thenReturn("2");
        Stream<Reaction> reactions = Arrays.asList(new Reaction[]{reaction1, reaction2}).stream();
        query.setReactions(reactions);
    }

    @Test
    public void cellState() throws Exception {
        BehaviorCell result = query.next();
        assertEquals((int) cellState.next(), result.getState());
    }

    @Test
    public void threshold() throws Exception {
        BehaviorCell result = query.next();
        assertEquals(threshold.next(), result.getThreshold(), epsilon);
    }

    @Test
    public void initialHealth() throws Exception {
        BehaviorCell result = query.next();
        assertEquals(initialHealth.next(), result.getHealth(), epsilon);
    }

    // TODO this should be tested directly
    @Test
    public void reactions() throws Exception {
        BehaviorCell result = query.next();
        List<String> expected = Arrays.asList("1", "2");

        assertEquals(expected, result.getReactionIds().collect(Collectors.toList()));
    }

    // TODO this should be tested directly
    @Test
    public void behaviors() throws Exception {
        BehaviorCell result = query.next();
        List<String> expected = Stream.of("behavior").collect(Collectors.toList());
        verify(behaviorDescriptor).instantiate(any());
        assertEquals(expected, result.getBehaviorNames().collect(Collectors.toList()));
    }

    @Test
    public void nullReactionsAccepted() throws Exception {
        initEverythingButReactions();
        BehaviorCell result = query.next();
        assertEquals(0, result.getReactionIds().count());
    }

}