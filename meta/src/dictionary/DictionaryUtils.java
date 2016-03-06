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

import nanoverse.compiler.pipeline.translate.symbol.DictionarySymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.ListSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.SymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.control.run.ProjectSymbolTable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by lizzybradley on 2/6/16.
 */
public class DictionaryUtils {
    static File dir;
    private static String leftAngleBracket = "&lt;";
    private static String rightAngleBracket = "&gt;";

    public static void setDirectory(File directory) {
        dir = directory;
    }

    public static boolean fileExists(String filename) {
        File f = new File(dir, filename + ".html");
        return f.exists();
    }

    public static String truncateType(String type) {
        if (type.contains("Class"))
            type = type.substring(0, type.indexOf("Class"));
        else if (type.contains("Inst"))
            type = type.substring(0, type.indexOf("Inst"));
        else if (type.contains("Symbol")){
            type = type.substring(0, type.indexOf("Symbol"));
        }

        return type;
    }

    public static String handleCollectionTypes(String rawType, SymbolTable st, String parentFilename, boolean isInst) {
        String type = truncateType(rawType);
        String parentType = truncateType(parentFilename);

        if ("List".equals(type)) {
            ListSymbolTable lst = (ListSymbolTable) st;
            String superclass = lst.getClassSymbolTableClass().getSimpleName();
            type += createListHTML(superclass);
        }
        else if ("Dictionary".equals(type)) {
            DictionarySymbolTable dst = (DictionarySymbolTable) st;
            String superclass = dst.getResolvingSymbolTableClass().getSimpleName();
            type += createDictHTML(superclass);
        }
        else {
            if (!type.equals(parentType) && type.contains(parentType))
                type = type.replace(parentType, "");

            if (!isInst && !parentType.equals("Project"))
                type += " (" + parentType + ")";

            type = "<a href=" + rawType + ".html>" + type;
        }

        return type;
    }

    public static String createListHTML(String type) {
        String superclass = truncateType(type);
        String filename = type + ".html";
        return leftAngleBracket + "<a href=" + filename + ">" + superclass + "</a>" + rightAngleBracket;
    }

    public static String createDictHTML(String type) {
        String superclass = truncateType(type);
        String filename = type + ".html";
        return leftAngleBracket + "String, <a href=" + filename + ">" + superclass + "</a>" + rightAngleBracket;
    }

    public static String prepareTitle(String rawType, String superclassFilename) {
        String title;

        if (rawType.equals(ProjectSymbolTable.class.getSimpleName()))
            title = "Nanoverse Project";
        else {
            String type = truncateType(rawType);
            String superclassType = truncateType(superclassFilename);

            if (!type.equals(superclassType) && type.contains(superclassType))
                type = type.replace(superclassType, "");

            title = type + " (" + superclassType + ")";
        }

        return title;
    }

    public static BufferedWriter createNewClassFile(String filename, String description, int numMembers) {
        File f = new File(dir, filename + ".html");
        BufferedWriter w = null;

        try {
            w = new BufferedWriter(new FileWriter(f));
            w.write("<html><head><title></title>" +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">" +
                    "</head><body>");
            w.write("<h1>Class " + truncateType(filename) + "</h1>");
            w.write("<p>" + description + "</p>");
            w.write("<table>");

            if (numMembers > 0) {
                String firstColumn = "Instance";
                String tableHeaders = "<th>" + firstColumn + "</th><th>Type</th><th>Description</th>";
                w.write("<tr>" + tableHeaders + "</tr>");
            }
        }
        catch (IOException ignored) {}

        return w;
    }

    public static BufferedWriter createNewInstFile(String filename, String description, int numMembers, String superclassFilename) {
        File f = new File(dir, filename + ".html");
        BufferedWriter w = null;

        try {
            w = new BufferedWriter(new FileWriter(f));
            w.write("<html><head><title></title>" +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">" +
                    "</head><body>");
            w.write("<h1>" + prepareTitle(filename, superclassFilename) + "</h1>");
            w.write("<p>" + description + "</p>");
            w.write("<table>");

            if (numMembers > 0) {
                String firstColumn = "Member";
                String tableHeaders = "<th>" + firstColumn + "</th><th>Type</th><th>Description</th>";
                w.write("<tr>" + tableHeaders + "</tr>");
            }
        }
        catch (IOException ignored) {}

        return w;
    }

    public static void writeClassTableRow(BufferedWriter bw, SymbolTable st, String membername, String description, String parentFilename) {
        String href = st.getClass().getSimpleName();
        String typeToPrint = handleCollectionTypes(href, st, parentFilename, false);

        try {
            bw.write("<tr>");
            bw.write("<td>" + membername + "</a></td>");
            bw.write("<td>" + typeToPrint + "</td>");
            bw.write("<td>" + description + "</td>");
            bw.write("</tr>");
        }
        catch (IOException ignored) {}
    }

    public static void writeInstTableRow(BufferedWriter bw, SymbolTable st, String membername, String description, String parentFilename) {
        String href = st.getClass().getSimpleName();
        String typeToPrint = handleCollectionTypes(href, st, parentFilename, true);

        try {
            bw.write("<tr>");
            bw.write("<td>" + membername + "</a></td>");
            bw.write("<td>" + typeToPrint + "</td>");
            bw.write("<td>" + description + "</td>");
            bw.write("</tr>");
        }
        catch (IOException ignored) {}
    }

    public static void endFile(BufferedWriter bw) {
        try {
            bw.write("</table>");
            bw.write("</body></html>");
            bw.close();
        }
        catch (IOException ignored) {}
    }

    public static void createClassFrame(TreeSet<String> superclasses) {
        File f = new File(dir, "classFrame.html");
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(f));
            bw.write("<html><head><title></title>" +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">" +
                    "</head><body>");
            bw.write("<h1>Classes</h1>");
            bw.write("<table>");
        }
        catch (IOException ignored) {}

        for (String href : superclasses) {
            String name = truncateType(href);

            try {
                bw.write("<tr>");
                bw.write("<td><a href='" + href + ".html' target=view>" + name + "</a></td>");
                bw.write("</tr>");
            }
            catch (IOException ignored) {}
        }

        endFile(bw);
    }

    public static void createInstanceFrame(Map<String, String> instances) {
        File f = new File(dir, "instFrame.html");
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(f));
            bw.write("<html><head><title></title>" +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">" +
                    "</head><body>");
            bw.write("<h1>Instances</h1>");
            bw.write("<table>");
        }
        catch (IOException ignored) {}

        for (String href : instances.keySet()) {
            String instance = truncateType(href);
            String superclass = truncateType(instances.get(href));

            if (!instance.equals(superclass) && instance.contains(superclass))
                instance = instance.replace(superclass, "");

            try {
                bw.write("<tr>");
                bw.write("<td><a href='" + href + ".html' target=view>" + instance + " (" + superclass + ")" + "</a></td>");
                bw.write("</tr>");
            }
            catch (IOException ignored) {}
        }

        endFile(bw);
    }
}
