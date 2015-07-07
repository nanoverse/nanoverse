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

package control.run;

import factory.control.run.RunnerFactory;

/**
 * The manual runner specifies a hard-coded parameters file to be loaded.
 * It is used for ad-hoc simulations and testing. Batch executions use
 * a command line argument to specify a parameters file.
 *
 * @author dbborens
 */
public class ManualLauncher {

    public static void main(String[] args) {
//        String path = "/Users/dbborens/nanoverse/2015-06-03/non-equilibrium.xml";
//        String path = "e:/nanoverse/2015-06-04/depletion.xml";
//        String path = "e:/Dropbox/T6SS/xml/2015-06-07/depletion/models/max_solute=41.00.xml";
//        String path = "e:/Dropbox/T6SS/xml/2015-06-07/depletion/models/test.xml";
        String path = "e:/Dropbox/T6SS/xml/2015-07-04/blended.xml";
        Runner runner = RunnerFactory.instantiate(path);
        runner.run();
    }

}
