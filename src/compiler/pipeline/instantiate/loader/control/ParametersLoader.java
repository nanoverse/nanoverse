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

package compiler.pipeline.instantiate.loader.control;

import compiler.pipeline.instantiate.factory.control.ParametersFactory;
import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.translate.nodes.*;
import control.GeneralParameters;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Random;

/**
 * Created by dbborens on 8/10/2015.
 */
public class ParametersLoader extends Loader<GeneralParameters> {

    private final ParametersFactory factory;
    private final ParametersInterpolator interpolator;

    public ParametersLoader() {
        factory = new ParametersFactory();
        interpolator = new ParametersInterpolator();
    }

    public ParametersLoader(ParametersFactory factory,
                            ParametersInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    public GeneralParameters instantiate(MapObjectNode node) {
        long randomSeed = interpolator.randomSeed(node);
        factory.setRandomSeed(randomSeed);

        Random random = interpolator.random(randomSeed);
        factory.setRandom(random);

        String basePath = interpolator.path(node);
        factory.setBasePath(basePath);

        String project = interpolator.project(node);
        factory.setProject(project);

        int instances = interpolator.instances(node, random);
        factory.setInstances(instances);

        int maxStep = interpolator.maxStep(node, random);
        factory.setMaxStep(maxStep);

        boolean isStamp = interpolator.date(node, random);
        factory.setIsStamp(isStamp);

        return factory.build();
    }

    /**
     * Instantiate using default values.
     *
     * @return
     */
    public GeneralParameters instantiate() {
        return instantiate(null);
    }
}
