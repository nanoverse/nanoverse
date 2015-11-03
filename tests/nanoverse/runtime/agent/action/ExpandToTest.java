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

package nanoverse.runtime.agent.action;

import nanoverse.runtime.agent.action.displacement.*;
import nanoverse.runtime.agent.action.helper.SelfTargetHighlighter;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;

import java.util.*;
import java.util.stream.*;

import static org.mockito.Mockito.*;

/**
 * Functional test for the ExpandTo action, which utilizes a path-of-least-
 * resistance preferential division algorithm.
 */
public class ExpandToTest extends ActionTest {

    private DisplacementManager displacementManager;
    private SelfTargetHighlighter stHighlighter;
    private PreferentialExpansionManager expansionManager;
    private Random random;
    private ExpandTo query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        displacementManager = mock(DisplacementManager.class);
        stHighlighter = mock(SelfTargetHighlighter.class);
        expansionManager = mock(PreferentialExpansionManager.class);
        random = mock(Random.class);

        // This is too many arguments...
        query = new ExpandTo(identity,
            mapper,
            highlighter,
            displacementManager,
            stHighlighter,
            random,
            targetRule,
            expansionManager);
    }

    @Test
    public void run() throws Exception {
        Coordinate target = mock(Coordinate.class);
        List<Coordinate> targets = Stream.of(target).collect(Collectors.toList());
        when(targetRule.report(callerAgent)).thenReturn(targets);
        query.run(caller);
        verify(expansionManager).preferentialExpand(target);
    }
}