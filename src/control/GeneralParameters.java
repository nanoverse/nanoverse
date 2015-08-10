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

package control;

import org.dom4j.Element;
import structural.annotations.FactoryTarget;
import structural.utilities.XmlUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * The parameter object returns general parameters for the simulation.
 *
 * @author dbborens
 */
public class GeneralParameters {

    protected Random random;            // Random number generator
    protected long randomSeed;
    // Dimensions
    private int maxStep;
    private int instances;
    // Path variables
    private String basePath;        // Path as specified
    private String path;            // May contain a time stamp
    // Output flags
    private String instancePath;    // Includes instance number�(if applies)
    // Instantiated members
    private double epsilon;            // Minimum measurable FP delta
    // State members
    private int instance;
    private String projectName;
    private boolean isStamp;

    @FactoryTarget(displayName = "Parameters")
    public GeneralParameters(Random random,
                             long randomSeed,
                             int maxStep,
                             int instances,
                             String basePath,
                             String project,
                             boolean isStamp,
                             double epsilon) {

        this.epsilon = epsilon;
        this.random = random;
        this.randomSeed = randomSeed;
        this.instances = instances;
        this.maxStep = maxStep;
        this.basePath = basePath;
        this.projectName = project;
        this.isStamp = isStamp;

        internalPaths();
        instance = 0;
        updateInstancePath();
    }

    public GeneralParameters(Element root) {
        calcEpsilon();
        load(root);
        instance = 0;
        updateInstancePath();

    }

    // Minimal constructor for mock testing.
    public GeneralParameters() {
        calcEpsilon();
    }

    private void updateInstancePath() {
        if (instances == 1) {
            instancePath = path;
        } else {
            instancePath = path + '/' + instance + '/';
        }

    }

    /**
     * Signals to the parameters object that it should
     * generate a new random number seed and advance the
     * instance counter.
     */
    public void advance() {
        instance++;
        updateInstancePath();
        randomSeed = System.currentTimeMillis();
        random = new Random(randomSeed);
    }

    // Pull in a single-datum element
    private String get(Element g, String key) {
        Element vElem = g.element(key);
        if (vElem == null) {
            throw new IllegalArgumentException("General parameter " +
                    key + " not defined.");
        }

        Object value = vElem.getData();

        return value.toString();

    }

    private void load(Element g) {

        // Load dimensions
        loadDimensions(g);

        // Load base path and (if applicable) time stamped path
        loadPaths(g);

        // Initialize random-number generator
        loadRandom(g);
    }

    private void loadRandom(Element g) {
        String rseed = get(g, "random-seed");
        if (rseed.equals("*")) {
            randomSeed = System.currentTimeMillis();
            random = new Random(randomSeed);
        } else {
            if (instances != 1) {
                throw new IllegalArgumentException("You may only specify a random number seed if you are running a single replicate.");
            }
            randomSeed = Long.valueOf(rseed);
            random = new Random(randomSeed);

        }
    }

    private void internalPaths() {
        if (isStamp) {
            path = basePath + '/' + date() + '/' + projectName + '/' + time() + '/';
        } else {
            path = basePath + projectName;
        }

    }
    private void loadPaths(Element g) {
        basePath = get(g, "path");
        projectName = XmlUtil.getString(g, "project", "");
        isStamp = XmlUtil.getBoolean(g, "date-stamp");
        internalPaths();
    }

    private void loadDimensions(Element g) {
        //width = Integer.valueOf(get(g, "width"));
        //height = Integer.valueOf(get(g, "height"));
        maxStep = Integer.valueOf(get(g, "max-step"));
        instances = Integer.valueOf(get(g, "instances"));
    }


    /**
     * Find the machine epsilon for this computer (i.e., the value at which
     * double-precision floating points can no longer be distinguished.)
     * <p>
     * Adapted from the Wikipedia article "Machine epsilon" (retrieved 3/18/2012)
     */
    private void calcEpsilon() {
        double machEps = 1.0d;

        do {
            machEps /= 2d;
        } while (1d + (machEps / 2d) != 1d);

        epsilon = machEps;
    }

    /**
     * Determines whether two doubles are equal to within machine epsilon.
     *
     * @param p
     * @param q
     */
    public boolean epsilonEquals(double p, double q) {
        if (Math.abs(p - q) < epsilon)
            return true;

        return false;
    }

    public double epsilon() {
        return epsilon;
    }

    private String date() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
    }

    public String time() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH'h'mm'm'ss's'");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * Returns system width. Due to frequent calls, this
     * getter's name has been shortened.
     *
     * @return
     */
    /*public int W() {
        return width;
	}*/

    /**
     * Returns system height. Due to frequent calls, this getter's
     * name has been shortened.
     * @return
     */
    /*public int H() {
        return height;
	}*/

    /**
     * Returns max time step. Due to frequent calls, this getter's
     * name has been shortened.
     *
     * @return
     */
    public int T() {
        return maxStep;
    }

    public int getNumInstances() {
        return instances;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getPath() {
        return path;
    }

    public String getInstancePath() {
        return instancePath;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public Random getRandom() {
        return random;
    }

    public long getRandomSeed() {
        return randomSeed;
    }

    public int getInstance() {
        return instance;
    }

    /**
     * Returns the XML representation of the project, as loaded
     * initially.
     *
     * @return
     */
    public String getProjectXML() {
        throw new UnsupportedOperationException("Re-implement. (Will require extensive effort!)");
    }

    /**
     * Returns an XML project encoding the current instance's
     * path and random number generator. The resulting XML file
     * should deterministically reproduce the specified simulation
     * instance.
     *
     * @return
     */
    public String getInstanceXML() {
        throw new UnsupportedOperationException("Re-implement. (Will require extensive effort!)");
    }
}
