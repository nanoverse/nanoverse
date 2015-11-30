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

package nanoverse.compiler.pipeline.translate.visitors;

import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.compiler.pipeline.translate.symbol.*;
import org.junit.*;

import static org.mockito.Mockito.*;

public class MapChildLoaderTest {

    private MapChildTranslator translator;
    private MapChildLoader query;

    private int lineNumber = 1;

    @Before
    public void before() throws Exception {
        translator = mock(MapChildTranslator.class);
        query = new MapChildLoader(translator);
    }

    @Test
    public void loadChild() throws Exception {
        ASTNode child = mock(ASTNode.class);
        String id = "test";
        when(child.getIdentifier()).thenReturn(id);
        when(child.getLineNumber()).thenReturn(lineNumber);

        MapSymbolTable mst = mock(MapSymbolTable.class);
        ResolvingSymbolTable rst = mock(ResolvingSymbolTable.class);
        when(mst.getSymbolTable(id, child.getLineNumber())).thenReturn(rst);

        ObjectNode oNode = mock(ObjectNode.class);
        when(oNode.getInstantiatingClass()).thenReturn(Object.class);
        when(translator.translate(child, rst)).thenReturn(oNode);

        MapObjectNode node = mock(MapObjectNode.class);
        query.loadChild(child, mst, node);

        verify(node).loadMember(id, oNode);
    }
}