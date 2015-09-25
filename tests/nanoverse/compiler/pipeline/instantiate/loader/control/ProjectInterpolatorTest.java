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

package nanoverse.compiler.pipeline.instantiate.loader.control;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.instantiate.loader.geometry.GeometryDescriptorLoader;
import nanoverse.compiler.pipeline.instantiate.loader.io.serialize.OutputManagerLoader;
import nanoverse.compiler.pipeline.instantiate.loader.layers.LayerManagerLoader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.ProcessManager;
import nanoverse.runtime.control.arguments.GeometryDescriptor;
import nanoverse.runtime.io.serialize.SerializationManager;
import nanoverse.runtime.layers.LayerManager;
import org.junit.*;
import nanoverse.runtime.structural.Version;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ProjectInterpolatorTest {

    private ProjectInterpolator query;
    private LoadHelper loadHelper;
    private ProjectDefaults defaults;
    private MapObjectNode node;
    private GeneralParameters p;
    private GeometryDescriptor geom;
    private LayerManager lm;

    @Before
    public void before() throws Exception {
        node = mock(MapObjectNode.class);
        loadHelper = mock(LoadHelper.class);
        defaults = mock(ProjectDefaults.class);
        p = mock(GeneralParameters.class);
        geom = mock(GeometryDescriptor.class);
        lm = mock(LayerManager.class);

        query = new ProjectInterpolator(loadHelper, defaults);
        configureVersion(Version.VERSION);
    }

    private void configureVersion(String version) {
        when(loadHelper.aString(eq(node), eq("version"), any())).thenReturn(version);
    }

    @Test
    public void correctVersionDoesNothing() throws Exception {
        query.version(node);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongVersionThrows() throws Exception {
        configureVersion("This will never be a valid version number");
        query.version(node);
    }

    @Test
    public void generalParameters() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("parameters")).thenReturn(cNode);

        ParametersLoader loader = mock(ParametersLoader.class);
        when(loadHelper.getLoader(eq(node), eq("parameters"), anyBoolean())).thenReturn(loader);

        GeneralParameters expected = mock(GeneralParameters.class);
        when(loader.instantiate(cNode)).thenReturn(expected);

        GeneralParameters actual = query.generalParameters(node);
        assertSame(expected, actual);
    }

    @Test
    public void generalParametersOmitted() throws Exception {
        GeneralParameters expected = mock(GeneralParameters.class);
        when(defaults.generalParameters()).thenReturn(expected);
        GeneralParameters actual = query.generalParameters(node);

        assertSame(expected, actual);
    }

    @Test
    public void geometry() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("geometry")).thenReturn(cNode);

        GeometryDescriptorLoader loader = mock(GeometryDescriptorLoader.class);
        when(loadHelper.getLoader(eq(node), eq("geometry"), anyBoolean())).thenReturn(loader);

        GeometryDescriptor expected = mock(GeometryDescriptor.class);
        when(loader.instantiate(cNode, p)).thenReturn(expected);

        GeometryDescriptor actual = query.geometry(node, p);
        assertSame(expected, actual);
    }

    @Test
    public void geometryOmitted() throws Exception {
        GeometryDescriptor expected = mock(GeometryDescriptor.class);
        when(defaults.geometry(p)).thenReturn(expected);
        GeometryDescriptor actual = query.geometry(node, p);

        assertSame(expected, actual);
    }

    @Test
    public void layers() throws Exception {
        ListObjectNode cNode = mock(ListObjectNode.class);
        when(node.getMember("layers")).thenReturn(cNode);

        LayerManagerLoader loader = mock(LayerManagerLoader.class);
        when(loadHelper.getLoader(eq(node), eq("layers"), anyBoolean())).thenReturn(loader);

        LayerManager expected = mock(LayerManager.class);
        when(loader.instantiate(cNode, geom, p)).thenReturn(expected);

        LayerManager actual = query.layers(node, geom, p);
        assertSame(expected, actual);
    }

    @Test
    public void layersOmitted() throws Exception {
        LayerManager expected = mock(LayerManager.class);
        when(defaults.layers(geom, p)).thenReturn(expected);
        LayerManager actual = query.layers(node, geom, p);

        assertSame(expected, actual);
    }

    @Test
    public void output() throws Exception {
        ListObjectNode cNode = mock(ListObjectNode.class);
        when(node.getMember("output")).thenReturn(cNode);

        OutputManagerLoader loader = mock(OutputManagerLoader.class);
        when(loadHelper.getLoader(eq(node), eq("output"), anyBoolean())).thenReturn(loader);

        SerializationManager expected = mock(SerializationManager.class);
        when(loader.instantiate(cNode, p, lm)).thenReturn(expected);

        SerializationManager actual = query.output(node, p, lm);
        assertSame(expected, actual);
    }

    @Test
    public void outputOmitted() throws Exception {
        SerializationManager expected = mock(SerializationManager.class);
        when(defaults.output(p, lm)).thenReturn(expected);
        SerializationManager actual = query.output(node, p, lm);

        assertSame(expected, actual);
    }

    @Test
    public void processes() throws Exception {
        ListObjectNode cNode = mock(ListObjectNode.class);
        when(node.getMember("processes")).thenReturn(cNode);

        ProcessManagerLoader loader = mock(ProcessManagerLoader.class);
        when(loadHelper.getLoader(eq(node), eq("processes"), anyBoolean())).thenReturn(loader);

        ProcessManager expected = mock(ProcessManager.class);
        when(loader.instantiate(cNode, lm, p)).thenReturn(expected);

        ProcessManager actual = query.processes(node, p, lm);
        assertSame(expected, actual);
    }
}