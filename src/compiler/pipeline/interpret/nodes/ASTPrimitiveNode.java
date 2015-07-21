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

package compiler.pipeline.interpret.nodes;

import java.util.stream.Stream;

/**
 * Created by dbborens on 2/14/15.
 */
public abstract class ASTPrimitiveNode<T> implements ASTNode {

    private final T content;

    public ASTPrimitiveNode(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ASTPrimitiveNode that = (ASTPrimitiveNode) o;

        if (!content.equals(that.content)) return false;

        return true;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public Stream<ASTNode> getChildren() {
        return Stream.of(this);
    }
}
