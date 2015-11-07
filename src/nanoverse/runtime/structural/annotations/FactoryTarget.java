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

package nanoverse.runtime.structural.annotations;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;

import java.lang.annotation.*;

/**
 * FactoryTarget indicates that a particular constructor should be used to
 * build a nanoverse.runtime.factory for the nanoverse.compiler's Instantiator hierarchy.
 * <p>
 * Created by dbborens on 7/30/2015.
 *
 * @see Factory
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.CONSTRUCTOR)
public @interface FactoryTarget {
    /**
     * If displayName is empty, the nanoverse.runtime.factory will use the class name.
     *
     * @return
     */
    String displayName() default "";
}
