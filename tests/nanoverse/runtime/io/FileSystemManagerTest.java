package nanoverse.runtime.io;

import nanoverse.runtime.control.GeneralParameters;
import org.junit.*;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

public class FileSystemManagerTest {

    private static final String PATH = "path";
    private static final String INST_PATH = "instancePath";
    private static final String FILENAME = "filename";

    private GeneralParameters p;
    private DirectoryMaker directoryMaker;
    private DiskOutputManager diskOutputManager;
    private FileSystemManager query;

    @Before
    public void before() throws Exception {
        p = mock(GeneralParameters.class);
        when(p.getPath()).thenReturn(PATH);
        when(p.getInstancePath()).thenReturn(INST_PATH);

        directoryMaker = mock(DirectoryMaker.class);
        diskOutputManager = mock(DiskOutputManager.class);
        query = new FileSystemManager(p, directoryMaker, diskOutputManager);
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