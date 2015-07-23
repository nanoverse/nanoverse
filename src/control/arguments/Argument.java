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

package control.arguments;

import com.google.common.reflect.TypeToken;
import control.halt.HaltCondition;

/**
 * Beginning in eSLIME 0.5.1, any object constructed by a factory can be
 * implemented to use flexible primitive arguments. These replace traditional
 * primitives and can be used to generate random numbers according to a
 * specified distribution.
 * Created by David B Borenstein on 4/7/14.
 */
public abstract class Argument<T> {

    private final TypeToken<T> type = new TypeToken<T>(getClass()) {};

    @Override
    public abstract boolean equals(Object obj);

    public abstract T next() throws HaltCondition;

    public Class getInstanceClass() {
        return type.getRawType();
    }
}
