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

package nanoverse.runtime.io;

import nanoverse.runtime.control.GeneralParameters;
import org.junit.*;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

public class FileSystemOutputManagerTest {

    private static final String PATH = "path";
    private static final String INST_PATH = "instancePath";
    private static final String FILENAME = "filename";

    private GeneralParameters p;
    private DirectoryMaker directoryMaker;
    private DiskOutputManager diskOutputManager;
    private FileSystemOutputManager query;

    @Before
    public void before() throws Exception {
        p = mock(GeneralParameters.class);
        when(p.getPath()).thenReturn(PATH);
        when(p.getInstancePath()).thenReturn(INST_PATH);

        directoryMaker = mock(DirectoryMaker.class);
        diskOutputManager = mock(DiskOutputManager.class);
        query = new FileSystemOutputManager(p, directoryMaker, diskOutputManager);
    }

    @Test
    public void makeProjectBinaryFile() throws Exception {
        query.makeProjectBinaryFile(FILENAME);
        String filePath = PATH + "/" + FILENAME;
        InOrder inOrder = inOrder(directoryMaker, diskOutputManager);
        inOrder.verify(directoryMaker).makeDirectory(PATH);
        inOrder.verify(diskOutputManager).getBinaryHandle(filePath);
    }

    @Test
    public void makeInstanceBinaryFile() throws Exception {
        query.makeInstanceBinaryFile(FILENAME);
        String filePath = INST_PATH + "/" + FILENAME;
        InOrder inOrder = inOrder(directoryMaker, diskOutputManager);
        inOrder.verify(directoryMaker).makeDirectory(INST_PATH);
        inOrder.verify(diskOutputManager).getBinaryHandle(filePath);
    }

    @Test
    public void makeProjectTextFile() throws Exception {
        query.makeProjectTextFile(FILENAME);
        String filePath = PATH + "/" + FILENAME;
        InOrder inOrder = inOrder(directoryMaker, diskOutputManager);
        inOrder.verify(directoryMaker).makeDirectory(PATH);
        inOrder.verify(diskOutputManager).getTextHandle(filePath);
    }

    @Test
    public void makeInstanceTextFile() throws Exception {
        query.makeInstanceTextFile(FILENAME);
        String filePath = INST_PATH + "/" + FILENAME;
        InOrder inOrder = inOrder(directoryMaker, diskOutputManager);
        inOrder.verify(directoryMaker).makeDirectory(INST_PATH);
        inOrder.verify(diskOutputManager).getTextHandle(filePath);
    }
}