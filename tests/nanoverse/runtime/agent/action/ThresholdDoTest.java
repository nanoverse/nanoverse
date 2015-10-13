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

package nanoverse.runtime.agent.action;

import nanoverse.runtime.agent.BehaviorCell;
import nanoverse.runtime.control.arguments.ConstantDouble;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.*;
import nanoverse.runtime.layers.continuum.ContinuumLayer;
import org.junit.*;

import static org.mockito.Mockito.*;

public class ThresholdDoTest {

    private static final String LAYER_NAME = "test";
    private Coordinate c;

    @Before
    public void before() {
        c = mock(Coordinate.class);
        when(c.canonicalize()).thenReturn(c);
    }

    @Test
    public void belowMinimumDoesNothing() throws Exception {
        doTest(0D, 1D, -1D, true, false);
    }

    private void doTest(double minimum, double maximum, double value, boolean exists, boolean expected) throws Exception {
        Action child = mock(Action.class);
        ThresholdDo query = makeQuery(minimum, maximum, value, exists, child);
        query.run(null);
        verifyRunsIfExpected(expected, child);
    }

    private void verifyRunsIfExpected(boolean expected, Action child) throws HaltCondition {
        if (expected) {
            verify(child).run(null);
        } else {
            verify(child, never()).run(any());
        }
    }

    private ThresholdDo makeQuery(double minimum, double maximum, double value, boolean exists, Action child) {
        ConstantDouble minimumArg = new ConstantDouble(minimum);
        ConstantDouble maximumArg = new ConstantDouble(maximum);
        LayerManager lm = makeLayerManager(value, exists);

        BehaviorCell callback = mock(BehaviorCell.class);
        ThresholdDo query = new ThresholdDo(callback, lm, LAYER_NAME, minimumArg, maximumArg, child);
        return query;
    }

    private LayerManager makeLayerManager(double value, boolean exists) {
        LayerManager lm = mock(LayerManager.class);

        makeContinuumLayer(value, lm);
        makeCellLayer(exists, lm);

        return lm;
    }

    private void makeContinuumLayer(double value, LayerManager lm) {
        ContinuumLayer cl = mock(ContinuumLayer.class);
        when(lm.getContinuumLayer(LAYER_NAME)).thenReturn(cl);
        when(cl.getValueAt(c)).thenReturn(value);
    }

    private void makeCellLayer(boolean exists, LayerManager lm) {
        CellLayer cellLayer = mock(CellLayer.class);
        when(lm.getCellLayer()).thenReturn(cellLayer);

        CellLayerViewer clv = mock(CellLayerViewer.class);
        when(clv.exists(any())).thenReturn(exists);
        when(cellLayer.getViewer()).thenReturn(clv);

        CellLookupManager clm = mock(CellLookupManager.class);
        when(clm.getCellLocation(any())).thenReturn(c);
        when(cellLayer.getLookupManager()).thenReturn(clm);
    }

    @Test
    public void aboveMaximumDoesNothing() throws Exception {
        doTest(0D, 1D, 2D, true, false);
    }

    @Test
    public void nonExistentCallbackDoesNothing() throws Exception {
        doTest(0D, 2D, 1D, false, false);
    }

    @Test
    public void insideBoundsTriggersChild() throws Exception {
        doTest(0D, 2D, 1D, true, true);
    }

    @Test
    public void noMaximumYes() throws Exception {
        doTest(0D, Double.POSITIVE_INFINITY, 1D, true, true);
    }

    @Test
    public void noMaximumNo() throws Exception {
        doTest(0D, Double.POSITIVE_INFINITY, -1D, true, false);
    }

    @Test
    public void noMinimumYes() throws Exception {
        doTest(Double.NEGATIVE_INFINITY, 0, -1D, true, true);
    }

    @Test
    public void noMinimumNo() throws Exception {
        doTest(Double.NEGATIVE_INFINITY, 0, 1D, true, false);
    }
}