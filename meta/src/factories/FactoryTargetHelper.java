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

import nanoverse.runtime.structural.annotations.FactoryTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 * Created by dbborens on 7/31/15.
 */
public class FactoryTargetHelper {

    private static final Logger logger = LoggerFactory.getLogger(FactoryTargetHelper.class);

    /**
     * If the class has a constructor that is a nanoverse.runtime.factory target, returns that
     * constructor. If it does not, returns null. If there is more than one
     * nanoverse.runtime.factory target, throws an exception.
     */
    public Constructor getFactoryTarget(Class clazz) {
//        if (clazz == null) {
//            return null;
//        }
//
        try {
            return Arrays.stream(clazz.getConstructors())
                    .filter(c -> annotated(c))
                    .findFirst()
                    .get();
        } catch (NoSuchElementException ex) {
            return null;
        }
//        } catch (Throwable th) {
//            logger.warn("Skipping " + clazz.getCanonicalName() + ". Cause: "
//                    + th.getClass().getSimpleName());
//            return null;
//        }

    }
    /**
     * If the supplied constructor has a user-specified display name, returns
     * that. Otherwise, returns the simple name of the declaring class. If the
     * constructor is not a FactoryTarget, throws an exception.
     */
    public String getDisplayName(Constructor c) {
        FactoryTarget ft;
        try {
            ft = (FactoryTarget) Arrays.stream(c.getDeclaredAnnotations())
                    .filter(a -> a instanceof FactoryTarget)
                    .findFirst()
                    .get();
        } catch (NoSuchElementException ex) {
            throw new IllegalArgumentException("Attempting to get display " +
                    "name of a Constructor that is not a @FactoryTarget.", ex);
        }

        // If the user did not specify a display name, use the original name
        if (ft.displayName().equals("")) {
            return c.getDeclaringClass().getSimpleName();
        }

        return ft.displayName();
    }
//
//    public String getDisplayName(Class clazz) {
//        Constructor constructor = getFactoryTarget(clazz);
//        return getDisplayName(constructor);
//    }
    private boolean annotated(Constructor c) {
        return Arrays.stream(c.getDeclaredAnnotations())
                .anyMatch(annotation ->
                        annotation instanceof FactoryTarget);
    }

}
