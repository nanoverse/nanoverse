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

package nanoverse.runtime.processes;

import java.util.*;

/**
 * Selects a subset of candidates, when needed, to allow
 * nanoverse.runtime.processes to respect a target maximum.
 * <p>
 * Created by dbborens on 3/7/14.
 */
public abstract class MaxTargetHelper {

    public static Object[] respectMaxTargets(Collection<? extends Object> candidates, int maxTargets, Random random) {
        // This method is a target for optimization.
        Object[] candidateArr = candidates.toArray(new Object[0]);
        return respectMaxTargets(candidateArr, maxTargets, random);
    }

    public static Object[] respectMaxTargets(Object[] candidates, int maxTargets, Random random) {
        // If maxTargets is < 0, it means that there is no maxTargets; return all.
        if (maxTargets < 0) {
            return candidates;
        }
        // If there the number of candidates does not exceed the max, return.
        if (candidates.length <= maxTargets) {
            return candidates;
        }

        // Otherwise, permute and choose the first n, where n = maxTargets.
        permute(candidates, random);

        Object[] reduced = Arrays.copyOfRange(candidates, 0, maxTargets);

        return reduced;
    }

    /**
     * Fischer-Yates shuffling algorithm for permuting the contents of
     * a coordinate array.
     */
    private static void permute(Object[] arr, Random random) {
        for (int i = arr.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            swap(arr, i, j);
        }
    }

    private static void swap(Object[] arr, int i, int j) {
        Object temp = arr[j];
        arr[j] = arr[i];
        arr[i] = temp;
    }
}
