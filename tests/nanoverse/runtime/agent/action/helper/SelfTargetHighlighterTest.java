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

package nanoverse.runtime.agent.action.helper;

import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class SelfTargetHighlighterTest {

    private ActionHighlighter highlighter;
    private IntegerArgument selfChannel, targetChannel;
    private Coordinate self, target;

    private SelfTargetHighlighter query;

    @Before
    public void before() throws Exception {
        highlighter = mock(ActionHighlighter.class);
        selfChannel = mock(IntegerArgument.class);
        targetChannel = mock(IntegerArgument.class);
        self = mock(Coordinate.class);
        target = mock(Coordinate.class);

        query = new SelfTargetHighlighter(highlighter, selfChannel, targetChannel);
    }

    @Test
    public void highlightHighlightsSelf() throws Exception {
        query.highlight(target, self);
        verify(highlighter).doHighlight(selfChannel, self);
    }

    @Test
    public void highlightHighlightsTarget() throws Exception {
        query.highlight(target, self);
        verify(highlighter).doHighlight(targetChannel, target);
    }

    @Test
    public void getTargetChannel() throws Exception {
        assertSame(targetChannel, query.getTargetChannel());
    }

    @Test
    public void getSelfChannel() throws Exception {
        assertSame(selfChannel, query.getSelfChannel());
    }
}