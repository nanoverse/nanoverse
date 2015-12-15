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

package dictionary;

import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.control.run.ProjectSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.layers.LayerInstSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.ConstantPrimitiveSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.processes.discrete.DiscreteProcessInstSymbolTable;
import nanoverse.runtime.control.arguments.Constant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class DictionaryCrawler {
    File dir;
    private static final int lineNumber = 1;
    private String leftAngleBracket = "&lt;";
    private String rightAngleBracket = "&gt;";

    private void handleConstantPrimitiveSymbolTable(ConstantPrimitiveSymbolTable st, String filename, String fileDescription) {
        BufferedWriter bw = createNewFile(filename, fileDescription, 0);
        endFile(bw);
    }

    private void handleDictST(DictionarySymbolTable st, String filename, String fileDescription) {
        HashMap<String, MemberSymbol> members = st.resolveMembers();
        BufferedWriter bw = createNewFile(filename, fileDescription, members.size());

        members.keySet().forEach(memberName -> {
            String type = truncateType(
                    st.getSymbolTable(memberName, lineNumber).getClass().getSimpleName());
            String typeToPrint = handleCollectionTypes(type, st.getSymbolTable(memberName, lineNumber));
            String description = st.getSymbolTable(memberName, lineNumber).getDescription();

            writeTableRow(bw, memberName, typeToPrint, description);
            handleMember(st.getSymbolTable(memberName, lineNumber), memberName, description);
        });

        endFile(bw);
    }

    private void handleMapST(MapSymbolTable st, String filename, String fileDescription) {
        HashMap<String, MemberSymbol> members = st.resolveMembers();
        BufferedWriter bw = createNewFile(filename, fileDescription, members.size());

        members.keySet().forEach(memberName -> {
            String type = truncateType(
                    st.getSymbolTable(memberName, lineNumber).getClass().getSimpleName());
            String typeToPrint = handleCollectionTypes(type, st.getSymbolTable(memberName, lineNumber));
            String description = members.get(memberName).getDescription();

            writeTableRow(bw, memberName, typeToPrint, description);
            handleMember(st.getSymbolTable(memberName, lineNumber), memberName, description);
        });

        endFile(bw);
    }

    private void handleClassST(ClassSymbolTable st, String filename, String fileDescription) {
        HashMap<String, MemberSymbol> members = st.resolveSubclasses();
        BufferedWriter bw = createNewFile(filename, fileDescription, members.size());

        members.keySet().forEach(memberName -> {
            String type = truncateType(
                    st.getSymbolTable(memberName, lineNumber).getClass().getSimpleName());
            String typeToPrint = handleCollectionTypes(type, st.getSymbolTable(memberName, lineNumber));
            String description = st.getSymbolTable(memberName, lineNumber).getDescription();

            writeTableRow(bw, memberName, typeToPrint, description);
            handleMember(st.getSymbolTable(memberName, lineNumber), memberName, description);
        });

        endFile(bw);
    }

    private void handleDiscreteProcessST(DiscreteProcessInstSymbolTable st, String filename,
                                         String fileDescription) {
        HashMap<String, MemberSymbol> members = st.resolveMembers();
        BufferedWriter bw = createNewFile(filename, fileDescription, members.size());

        members.keySet().forEach(memberName -> {
            String type = truncateType(
                    st.getSymbolTable(memberName, lineNumber).getClass().getSimpleName());
            String typeToPrint = handleCollectionTypes(type, st.getSymbolTable(memberName, lineNumber));
            String description = members.get(memberName).getDescription();

            writeTableRow(bw, memberName, typeToPrint, description);
            handleMember(st.getSymbolTable(memberName, lineNumber), memberName, description);
        });

        endFile(bw);
    }

    private void handleLayerInstST(LayerInstSymbolTable st, String filename, String fileDescription) {
        HashMap<String, MemberSymbol> members = st.resolveMembers();
        BufferedWriter bw = createNewFile(filename, fileDescription, members.size());

        members.keySet().forEach(memberName -> {
            String type = truncateType(
                    st.getSymbolTable(memberName, lineNumber).getClass().getSimpleName());
            String typeToPrint = handleCollectionTypes(type, st.getSymbolTable(memberName, lineNumber));
            String description = members.get(memberName).getDescription();

            writeTableRow(bw, memberName, typeToPrint, description);
            handleMember(st.getSymbolTable(memberName, lineNumber), memberName, description);
        });

        endFile(bw);
    }

    private void handleListST(ListSymbolTable st, String filename, String fileDescription) {
        BufferedWriter bw = createNewFile(filename, fileDescription, st.getMemberNames().size());

        st.getMemberNames().forEach(mn -> {
            String memberName = (String) mn;
            String type = truncateType(
                    st.getSymbolTable(memberName, lineNumber).getClass().getSimpleName());
            String typeToPrint = handleCollectionTypes(type, st.getSymbolTable(memberName, lineNumber));
            String description = st.getSymbolTable(memberName, lineNumber).getDescription();

            writeTableRow(bw, memberName, typeToPrint, description);
            handleMember(st.getSymbolTable(memberName, lineNumber), memberName, description);
        });

        endFile(bw);
    }

    private void beginCrawl() throws IOException{
        ProjectSymbolTable st = new ProjectSymbolTable();
        HashMap<String, MemberSymbol> members = st.resolveMembers();
        BufferedWriter bw = createNewFile("Main", "", members.size());

        members.keySet().forEach(memberName -> {
            String type = truncateType(
                    st.getSymbolTable(memberName, lineNumber).getClass().getSimpleName());
            String typeToPrint = handleCollectionTypes(type, st.getSymbolTable(memberName, lineNumber));
            String description = st.resolveMembers().get(memberName).getDescription();

            writeTableRow(bw, memberName, typeToPrint, description);
            handleMember(st.getSymbolTable(memberName, lineNumber), memberName, description);
        });

        endFile(bw);
    }

    private String handleCollectionTypes(String type, SymbolTable st) {
        if ("List".equals(type)) {
            ListSymbolTable lst = (ListSymbolTable) st;
            String name = truncateType(lst.getClassSymbolTableClass().getSimpleName());
            type += createListHTML(name);
            handleMember(st, name, lst.getClassSymbolTableDescription());
        }

        if ("Dictionary".equals(type)) {
            DictionarySymbolTable dst = (DictionarySymbolTable) st;
            String name = truncateType(dst.getResolvingSymbolTableClass().getSimpleName());
            type += createDictHTML(name);
            handleMember(st, name, dst.getResolvingSymbolTableDescription());
        }

        return type;
    }

    private String createListHTML(String className) {
        String filename = className + ".html";

        if (Character.isUpperCase(filename.charAt(0))) {
            filename = filename.charAt(0) + filename;
        }

        return leftAngleBracket + "<a href=" + filename + ">" + className + "</a>" + rightAngleBracket;
    }

    private String createDictHTML(String className) {
        String filename = className + ".html";

        if (Character.isUpperCase(filename.charAt(0))) {
            filename = filename.charAt(0) + filename;
        }

        return leftAngleBracket + "String, <a href=" + filename + ">" + className + "</a>" + rightAngleBracket;
    }

    private void handleMember(SymbolTable st, String mn_name, String description) {
        if (fileExists(mn_name))
            return;

        if (DiscreteProcessInstSymbolTable.class.isAssignableFrom(st.getClass())) {
            DiscreteProcessInstSymbolTable dpst = (DiscreteProcessInstSymbolTable) st;
            handleDiscreteProcessST(dpst, mn_name, description);
        }
        else if (ListSymbolTable.class.isAssignableFrom(st.getClass())) {
            ListSymbolTable lst = (ListSymbolTable) st;
            handleListST(lst, mn_name, description);
        }
        else if (ClassSymbolTable.class.isAssignableFrom(st.getClass())) {
            ClassSymbolTable cst = (ClassSymbolTable) st;
            handleClassST(cst, mn_name, description);
        }
        else if (LayerInstSymbolTable.class.isAssignableFrom(st.getClass())) {
            LayerInstSymbolTable list = (LayerInstSymbolTable) st;
            handleLayerInstST(list, mn_name, description);
        }
        else if (MapSymbolTable.class.isAssignableFrom(st.getClass())) {
            MapSymbolTable mst = (MapSymbolTable) st;
            handleMapST(mst, mn_name, description);
        }
        else if (DictionarySymbolTable.class.isAssignableFrom(st.getClass())) {
            DictionarySymbolTable dst = (DictionarySymbolTable) st;
            handleDictST(dst, mn_name, description);
        }
        else if (ConstantPrimitiveSymbolTable.class.isAssignableFrom(st.getClass())) {
            ConstantPrimitiveSymbolTable cpst = (ConstantPrimitiveSymbolTable) st;
            handleConstantPrimitiveSymbolTable(cpst, mn_name, description);
        }
        else {
            System.out.println("Missing handleMember() handler: " + st.getClass().getSimpleName());
        }
    }

    private boolean fileExists(String filename) {
        if (Character.isUpperCase(filename.charAt(0)))
            filename = filename.charAt(0) + filename;

        File f = new File(dir, filename + ".html");

        return f.exists();
    }

    private String truncateType(String type) {
        if (type.contains("Class"))
            type = type.substring(0, type.indexOf("Class"));
        else if (type.contains("Inst"))
            type = type.substring(0, type.indexOf("Inst"));
        else if (type.contains("Symbol")){
            type = type.substring(0, type.indexOf("Symbol"));
        }

        return type;
    }

    private BufferedWriter createNewFile(String mn, String description, long numMembers) {
        String filename = mn;
        if (Character.isUpperCase(mn.charAt(0))) {
            filename = mn.charAt(0) + mn;
        }

        File f = new File(dir, filename + ".html");
        BufferedWriter w = null;

        try {
            w = new BufferedWriter(new FileWriter(f));
            w.write("<html><head><title></title>" +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">" +
                    "</head><body>");
            w.write("<h1>" + mn + "</h1>");
            w.write("<p>" + description + "</p>");
            w.write("<table>");

            if (numMembers > 0)
                w.write("<tr><th>Member</th><th>Type</th><th>Description</th></tr>");
        }
        catch (IOException ignored) {}

        return w;
    }

    private void writeTableRow(BufferedWriter bw, String mn, String type, String description) {
        String href = mn;
        if (Character.isUpperCase(mn.charAt(0))) {
            href = mn.charAt(0) + mn;
        }

        try {
            bw.write("<tr>");
            bw.write("<td><a href='" + href + ".html'>" + mn + "</a></td>");
            bw.write("<td>" + type + "</td>");
            bw.write("<td>" + description + "</td>");
            bw.write("</tr>");
        }
        catch (IOException ignored) {}
    }

    private void endFile(BufferedWriter bw) {
        try {
            bw.write("</table>");
            bw.write("</body></html>");
            bw.close();
        }
        catch (IOException ignored) {}
    }

    public DictionaryCrawler() {
        dir = new File("meta/src/dictionary/pages");
        dir.mkdirs();

        for(File file: dir.listFiles()) {
            if (!file.getName().equals("style.css"))
                file.delete();
        }
    }

    public static void main(String args[]) throws IOException {
        DictionaryCrawler dictionaryCrawler = new DictionaryCrawler();
        dictionaryCrawler.beginCrawl();
    }
}
