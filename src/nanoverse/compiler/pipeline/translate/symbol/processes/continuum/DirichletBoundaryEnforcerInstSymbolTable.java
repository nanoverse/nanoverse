package nanoverse.compiler.pipeline.translate.symbol.processes.continuum;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.processes.continuum.DirichletBoundaryEnforcerLoader;
import nanoverse.compiler.pipeline.translate.symbol.InstantiableSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.MemberSymbol;
import nanoverse.compiler.pipeline.translate.symbol.ResolvingSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.geometry.set.CoordinateSetClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import nanoverse.runtime.processes.continuum.DirichletBoundaryEnforcer;

import java.util.HashMap;

/**
 * Created by dbborens on 10/5/2015.
 */
public class DirichletBoundaryEnforcerInstSymbolTable extends ContinuumProcessInstSymbolTable<DirichletBoundaryEnforcer> {

    @Override
    public Loader getLoader() {
        return new DirichletBoundaryEnforcerLoader();
    }

    @Override
    public String getDescription() {
        return "A Dirichlet boundary condition describes region of space " +
                "within which the value of a field remains constant for all " +
                "time. This region is often placed along an edge of the " +
                "system, but it need not be. For example, one might hold " +
                "the value of the continuum constant in a region at the " +
                "center of the simulation space.\n\n" +
                "The DirichletEnforcer process sets the specified " +
                "coordinates to the desired value, regardless of any other " +
                "processes that might have otherwise affected them. It must " +
                "be applied between a Hold process and a Release process.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        activeSites(ret);
        layer(ret);
        value(ret);
        return ret;
    }

    public void value(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Value at which to hold all " +
                "locations along this boundary.");
        ret.put("value", ms);
    }

    public void layer(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Continuum layer with which " +
                "to associate this DBC.");
        ret.put("layer", ms);
    }

    public void activeSites(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new CoordinateSetClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The set of coordinates to be " +
                "held at a constant value.");
        ret.put("activeSites", ms);
    }
}
