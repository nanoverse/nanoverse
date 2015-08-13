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

package io.visual;

import geometry.Geometry;
import layers.SystemState;
import structural.annotations.FactoryTarget;

import java.awt.image.BufferedImage;

/**
 * Created by David B Borenstein on 4/9/14.
 */
public class MockVisualization extends Visualization {
    private boolean isRender;
    private boolean isConclude;
    private boolean isInit;

    @FactoryTarget
    public MockVisualization() {
        isRender = false;
        isConclude = false;
        isInit = false;
    }

    public boolean isRender() {
        return isRender;
    }

    public boolean isConclude() {
        return isConclude;
    }

    public boolean isInit() {
        return isInit;
    }

    @Override
    public void init(Geometry geometry, double[] time, int[] frames) {
        isInit = true;
    }

    @Override
    public BufferedImage render(SystemState systemState) {
        isRender = true;
        return new BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR);
    }

    @Override
    public void conclude() {
        isConclude = true;
    }

    @Override
    public String[] getSoluteIds() {
        return new String[0];
    }

    @Override
    public int[] getHighlightChannels() {
        return new int[0];
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MockVisualization);
    }
}
