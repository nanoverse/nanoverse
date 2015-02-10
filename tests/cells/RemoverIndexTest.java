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

package cells;


import org.junit.Test;

import java.util.HashSet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RemoverIndexTest {

    @Test
    public void add() throws Exception {
        HashSet<Runnable> removers = (HashSet<Runnable>) mock(HashSet.class);
        RemoverIndex query = new RemoverIndex(removers);
        Runnable runnable = mock(Runnable.class);
        query.add(runnable);
        verify(removers).add(runnable);
    }

    @Test
    public void removeFromAllRunsRunners() throws Exception {
        HashSet<Runnable> removers = new HashSet<>();
        Runnable runnable = mock(Runnable.class);
        removers.add(runnable);
        RemoverIndex query = new RemoverIndex(removers);
        query.removeFromAll();
        verify(runnable).run();
    }
}