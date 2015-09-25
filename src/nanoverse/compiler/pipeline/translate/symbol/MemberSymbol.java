/*
 * Copyright (c) 2015 David Bruce Borenstein and the Trustees
 * of Princeton University. All rights reserved.
 */

package nanoverse.compiler.pipeline.translate.symbol;

/**
 * A member symbol reports back the expected class for
 * a particular symbol in an instance.
 * <p>
 * Created by dbborens on 3/3/15.
 */
public class MemberSymbol {

    protected ResolvingSymbolTable symbolTable;
    private String description;

    public MemberSymbol(ResolvingSymbolTable symbolTable, String description) {
        this.symbolTable = symbolTable;
        this.description = description;
    }

    public ResolvingSymbolTable getSymbolTable() {
        return symbolTable;
    }

    public String getDescription() {
        return description;
    }
}
