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

package nanoverse.runtime.layers.cell;

import com.google.common.collect.Lists;
import nanoverse.runtime.agent.*;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author David Bruce Borenstein
 */
public class AgentLookupManager {

    private AgentLayerContent content;
    private Geometry geom;

    public AgentLookupManager(Geometry geom, AgentLayerContent content) {
        this.content = content;
        this.geom = geom;
    }

    /**
     * Get the state of neighboring nanoverse.runtime.cells. Vacant nanoverse.runtime.cells are returned as zero.
     *
     * @param coord
     * @return
     */
    public String[] getNeighborNames(Coordinate coord, boolean ignoreVacancies) {
        content.sanityCheck(coord);

        // Get set of neighbors
        Coordinate[] neighbors = geom.getNeighbors(coord, Geometry.APPLY_BOUNDARIES);

        List<String> retStr = Arrays.stream(neighbors)
            .filter(c -> c != null)
            .map(content::get)
            .map(agent -> agent.getName())
            .collect(Collectors.toList());

        // TODO lambdify return type
        return retStr.toArray(new String[retStr.size()]);
    }

    /**
     * Get the site or sites with the minimum L1 (Manhattan) distance,
     * up to the specified maximum distance. If maxDistance is -1, the
     * search is unbounded.
     *
     * @param coord
     * @param maxDistance
     * @return
     */
    public Coordinate[] getNearestVacancies(Coordinate coord, int maxDistance) {

        content.sanityCheck(coord);


        // If there are no vacancies, just return now. This should prevent infinite
        // loop even when searching without bound.
        if (!geom.isInfinite() && (content.getOccupiedSites().size() > content.getCanonicalSites().length)) {
            throw new IllegalStateException("Consistency failure.");
        } else if (!geom.isInfinite() && (content.getOccupiedSites().size() == content.getCanonicalSites().length)) {
            return new Coordinate[0];
        }

        // Initialize return object
        ArrayList<Coordinate> res = new ArrayList();

        // Loop through looking for vacancies (starting with target site)
        int r = 0;

        // I included this extra map so I could check for duplicates in best
        // case O(1) time, but if I have to do that, doesn't it seem like I should
        // be returning a set instead of building two data structures?
        HashSet<Coordinate> incl = new HashSet();

        while ((maxDistance == -1) || (r <= maxDistance)) {

            // We want to check every site, so don't use circumnavigation restriction.
            Coordinate[] annulus = geom.getAnnulus(coord, r, Geometry.APPLY_BOUNDARIES);

            for (int i = 0; i < annulus.length; i++) {

                Coordinate query = annulus[i];

                if (query.hasFlag(Flags.UNDEFINED)) {
                    throw new IllegalStateException("Undefined coordinate returned in getAnnulus(...).");
                }
                // Sanity check

                if (!content.getOccupiedSites().contains(query) && !incl.contains(query)) {

                    incl.add(query);
                    res.add(query);
                }

            }

            // If we've managed to populate res, it means that we founds targets
            // in the current annulus, so return.
            if (res.size() > 0) {
                return (res.toArray(new Coordinate[0]));
            }

            r++;
        }

        return (res.toArray(new Coordinate[0]));
    }

    public Coordinate getAgentLocation(AbstractAgent agent) {
        return content.locate(agent);
    }
}
