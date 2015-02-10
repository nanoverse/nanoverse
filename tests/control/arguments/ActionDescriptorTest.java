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

package control.arguments;

import agent.action.Action;
import cells.BehaviorCell;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Function;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ActionDescriptorTest {

    private Function<BehaviorCell, Action> constructor;
    private ActionDescriptor<Action> query;
    private BehaviorCell cell;

    @Before
    public void init() throws Exception {
        constructor = (Function<BehaviorCell, Action>) mock(Function.class);
        cell = mock(BehaviorCell.class);
        query = new ActionDescriptor<>(constructor);
    }

    @Test
    public void instantiateCallsFunction() throws Exception {
        query.instantiate(cell);
        verify(constructor).apply(cell);
    }
}