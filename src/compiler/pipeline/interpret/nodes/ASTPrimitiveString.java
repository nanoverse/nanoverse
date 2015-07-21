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

import com.google.common.base.Strings;

/**
 * Created by dbborens on 3/5/15.
 */
public class ASTPrimitiveString extends ASTPrimitiveNode<String> {
    public static final String IDENTIFIER = "AST_PRIMITIVE_STRING";

    public ASTPrimitiveString(String content) {
        super(content);
    }
    @Override
    public void astReport(StringBuilder builder, int indentLevel) {
        builder.append(Strings.repeat(" ", indentLevel));
        builder.append("string: " + getContent() + "\n");
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

}
