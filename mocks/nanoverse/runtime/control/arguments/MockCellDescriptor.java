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

import nanoverse.runtime.agent.BehaviorCell;
import nanoverse.runtime.cells.*;
import nanoverse.runtime.control.halt.HaltCondition;

/**
 * Created by dbborens on 12/1/14.
 */
public class MockCellDescriptor extends CellDescriptor {
    private int state = 1;

    public MockCellDescriptor() {
        super(null);
    }

    public MockCellDescriptor(int state) {
        super(null);
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof MockCellDescriptor);
    }

    @Override
    public BehaviorCell next() throws HaltCondition {
        return new MockAgent(state);
    }

    public void setState(int state) {
        this.state = state;
    }
}
