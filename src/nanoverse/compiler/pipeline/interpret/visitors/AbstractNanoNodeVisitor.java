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

package nanoverse.compiler.pipeline.interpret.visitors;

import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Created by dbborens on 2/14/15.
 */
public abstract class AbstractNanoNodeVisitor extends RejectingVisitor {

    protected void verifyPayload(ParseTree child, Class expected) {
        if (child.getPayload() == null) {
            throw new IllegalStateException("Internal error: empty payload");
        }

        Object payload = child.getPayload();

        if (!expected.isInstance(payload)) {
            throw new IllegalStateException("Internal error: expected " +
                "payload " + expected.getSimpleName() + " but got " +
                payload.getClass().getSimpleName() + ".");
        }
    }

    protected void verifyPayload(ParseTree child, Class[] legalChildClasses) {

        if (child.getPayload() == null) {
            throw new IllegalStateException("Internal error: empty payload");
        }

        Object payload = child.getPayload();

        for (Class clazz : legalChildClasses) {
            if (clazz.isInstance(payload)) {
                return;
            }
        }

        throw new IllegalStateException("Unexpected payload class: " + child.getPayload().getClass().getSimpleName());
    }
}
