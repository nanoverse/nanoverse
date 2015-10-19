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

package factories;

import nanoverse.runtime.agent.action.stochastic.ContinuumProbabilitySupplierDescriptor;
import nanoverse.runtime.geometry.set.*;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.*;

import java.lang.reflect.Constructor;
import java.util.stream.Stream;

/**
 * Created by dbborens on 7/31/15.
 */
public class TargetFinder {

    private static final Logger logger = LoggerFactory.getLogger(TargetFinder.class);

    private final FactoryTargetHelper helper;

    public TargetFinder() {
        helper = new FactoryTargetHelper();
    }

    public Stream<Constructor> getTargets() {
        Stream<Class> inferred = Stream.of("nanoverse.runtime")
                .map(pkg -> new Reflections(pkg, new SubTypesScanner(false)))
                .map(r -> r.getAllTypes())
                .flatMap(set -> set.stream())
                .map(className -> {
                    try {
                        return Class.forName(className);
                    } catch (ClassNotFoundException ex) {
                        throw new IllegalStateException(ex);
                    } catch (Throwable ex) {
                        logger.warn("Skipping " + className + ". Cause: " +
                                ex.getClass().getSimpleName());
                        return null;
                    }
                });

        // These classes don't show up using Reflections, probably due to some kind of generics issue
        Stream<Class> cloodge = Stream.of(
                CompleteSet.class,
                CustomSet.class,
                DiscSet.class,
                HorizontalLineSet.class,
                ContinuumProbabilitySupplierDescriptor.class
        );

        Stream<Class> all = Stream.concat(inferred, cloodge).filter(c -> c != null);

        Stream<Constructor> ret = all
                .map(helper::getFactoryTarget)
                .filter(target -> target != null);

        return ret;
    }
}
