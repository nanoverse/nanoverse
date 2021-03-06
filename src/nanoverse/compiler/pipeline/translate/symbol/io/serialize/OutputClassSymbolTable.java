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

package nanoverse.compiler.pipeline.translate.symbol.io.serialize;

import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.runtime.io.serialize.Serializer;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 7/21/2015.
 */
public class OutputClassSymbolTable extends ClassSymbolTable<Serializer> {
    @Override
    public String getDescription() {
        return "Output services for Nanoverse, including console output, " +
            "statistical reports and visualizations.";
    }

    @Override
    public HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>();
        haltTimeWriter(ret);
        progressReporter(ret);
        censusWriter(ret);
        agentNameWriter(ret);
        continuumHistogramWriter(ret);
        surfaceCensusWriter(ret);
        individualHaltWriter(ret);
        interfaceCensusWriter(ret);
        randomSeedWriter(ret);
        runningTimeWriter(ret);
        coordinateIndexer(ret);
        continuumStateWriter(ret);
        timeWriter(ret);
        highlightWriter(ret);
        visualizationSerializer(ret);
        correlationWriter(ret);
        return ret;
    }

    private void agentNameWriter(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = AgentNameWriterInstSymbolTable::new;
        ret.put("AgentNameWriter", supplier);

    }

    public void haltTimeWriter(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = HaltTimeWriterInstSymbolTable::new;
        ret.put("HaltTimeWriter", supplier);
    }

    public void progressReporter(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = ProgressReporterInstSymbolTable::new;
        ret.put("ProgressReporter", supplier);
    }

    public void censusWriter(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = CensusWriterInstSymbolTable::new;
        ret.put("CensusWriter", supplier);
    }

    public void continuumHistogramWriter(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = ContinuumHistoWriterInstSymbolTable::new;
        ret.put("ContinuumHistogramWriter", supplier);
    }

    public void surfaceCensusWriter(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = SurfaceCensusWriterInstSymbolTable::new;
        ret.put("SurfaceCensusWriter", supplier);
    }

    public void individualHaltWriter(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = IndividualHaltWriterInstSymbolTable::new;
        ret.put("IndividualHaltWriter", supplier);
    }

    public void interfaceCensusWriter(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = InterfaceCensusWriterInstSymbolTable::new;
        ret.put("InterfaceCensusWriter", supplier);
    }

    public void randomSeedWriter(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = RandomSeedWriterInstSymbolTable::new;
        ret.put("RandomSeedWriter", supplier);
    }

    public void runningTimeWriter(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = RunningTimeWriterInstSymbolTable::new;
        ret.put("RunningTimeWriter", supplier);
    }

    public void coordinateIndexer(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = CoordinateIndexerInstSymbolTable::new;
        ret.put("CoordinateIndexer", supplier);
    }

    public void continuumStateWriter(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = ContinuumStateWriterInstSymbolTable::new;
        ret.put("ContinuumStateWriter", supplier);
    }

    public void timeWriter(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = TimeWriterInstSymbolTable::new;
        ret.put("TimeWriter", supplier);
    }

    public void highlightWriter(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = HighlightWriterInstSymbolTable::new;
        ret.put("HighlightWriter", supplier);
    }

    public void visualizationSerializer(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = VisualizationSerializerInstSymbolTable::new;
        ret.put("VisualizationSerializer", supplier);
    }

    public void correlationWriter(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = CorrelationWriterInstSymbolTable::new;
        ret.put("CorrelationWriter", supplier);
    }


}
