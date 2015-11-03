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

package nanoverse.compiler.pipeline.instantiate.loader.control;

import nanoverse.compiler.pipeline.instantiate.factory.control.ParametersFactory;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;

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

    /**
     * Instantiate using default values.
     *
     * @return
     */
    public GeneralParameters instantiate() {
        return instantiate(null);
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
}
