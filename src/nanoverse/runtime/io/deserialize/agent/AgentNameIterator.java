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

package nanoverse.runtime.io.deserialize.agent;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.FileSystemManager;
import nanoverse.runtime.io.deserialize.BinaryInputHandle;
import nanoverse.runtime.io.serialize.binary.AgentNameIOHelper;
import nanoverse.runtime.io.serialize.binary.AgentNameIndexWriter;
import nanoverse.runtime.io.serialize.binary.AgentNameWriter;
import nanoverse.runtime.structural.utilities.ParityIO;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by dbborens on 10/25/2015.
 */
public class AgentNameIterator implements Iterator<AgentNameViewer> {
    private final BinaryInputHandle vectorHandle;
    private final ParityIO parityIO;
    private final AgentNameDeindexer deindexer;
    private final int numSites;

    private boolean hasNext;

    public AgentNameIterator(GeneralParameters p, int numSites) {
        this(new FileSystemManager(p), new AgentNameDeindexer(p), new ParityIO(), numSites);
    }

    public AgentNameIterator(FileSystemManager fsManager, AgentNameDeindexer deindexer, ParityIO parityIO, int numSites) {
        this.numSites = numSites;
        this.parityIO = parityIO;
        this.deindexer = deindexer;
        vectorHandle = makeVectorHandle(fsManager);
        init();
    }

    private void init() {
        hasNext = parityIO.readStartOrEOF(vectorHandle);
    }

    private BinaryInputHandle makeVectorHandle(FileSystemManager fsManager) {
        String filename = AgentNameIOHelper.VECTOR_FILENAME;
        return fsManager.readInstanceBinaryFile(filename);
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public AgentNameViewer next() {
        throwIfPastEnd();

        double time = vectorHandle.readDouble();
        int frame = vectorHandle.readInt();
        throwIfSiteCountMismatch();

        List<String> names = IntStream.range(0, numSites)
                .map(i -> vectorHandle.readInt())
                .mapToObj(deindexer::deindex)
                .collect(Collectors.toList());

        parityIO.readEnd(vectorHandle);
        hasNext = parityIO.readStartOrEOF(vectorHandle);

        return new AgentNameViewer(names, time, frame);
    }

    private void throwIfSiteCountMismatch() {
        int reportedNumSites = vectorHandle.readInt();
        if (reportedNumSites != numSites) {
            throw new IllegalStateException("Consistency error: " +
                    "unexpected coordinate number of sites in agent " +
                    "name file");
        }
    }

    private void throwIfPastEnd() {
        if (!hasNext) {
            throw new IllegalStateException("Attempted to read past end of file in name state iterator");
        }
    }
}
