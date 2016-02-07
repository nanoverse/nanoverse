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

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class DictionaryCrawler {
    private static final int lineNumber = 1;
    private TreeSet<String> classes = new TreeSet<>((a, b) -> {
        int insensitive = String.CASE_INSENSITIVE_ORDER.compare(a, b);
        return insensitive == 0 ? a.compareTo(b) : insensitive;
    });
    private Map<String, String> instances = new TreeMap<>();

    private void handleClassSymbolTable(ClassSymbolTable cst, String filename, String fileDescription) {
        HashMap<String, MemberSymbol> members = cst.resolveSubclasses();
        BufferedWriter bw = DictionaryUtils.createNewClassFile(filename, fileDescription, members.size());

        TreeSet<String> membersTree = new TreeSet();
        membersTree.addAll(members.keySet());

        membersTree.forEach(memberName -> {
            SymbolTable st = cst.getSymbolTable(memberName, lineNumber);
            String description = st.getDescription();

            DictionaryUtils.writeClassTableRow(bw, st, memberName, description, filename);
            handleMember(st, filename, description);
        });

        DictionaryUtils.endFile(bw);
    }

    private void handleConstantPrimitiveSymbolTable(ConstantPrimitiveSymbolTable cpst, String filename,
                                                    String fileDescription, String superclassFilename) {
        BufferedWriter bw = DictionaryUtils.createNewInstFile(filename, fileDescription, 0, superclassFilename);

        DictionaryUtils.endFile(bw);
    }

    private void handleDictionarySymbolTable(DictionarySymbolTable dst, String filename, String fileDescription) {
        HashMap<String, MemberSymbol> members = dst.resolveMembers();
        BufferedWriter bw = DictionaryUtils.createNewClassFile(filename, fileDescription, members.size());

        TreeSet<String> membersTree = new TreeSet();
        membersTree.addAll(members.keySet());

        membersTree.forEach(memberName -> {
            SymbolTable st = dst.getSymbolTable(memberName, lineNumber);
            String description = st.getDescription();

            DictionaryUtils.writeClassTableRow(bw, st, memberName, description, filename);
            handleMember(st, filename, description);
        });

        DictionaryUtils.endFile(bw);
    }

    private void handleDiscreteProcessSymbolTable(DiscreteProcessInstSymbolTable dpst, String filename,
                                                  String fileDescription, String superclassFilename) {
        HashMap<String, MemberSymbol> members = dpst.resolveMembers();
        BufferedWriter bw = DictionaryUtils.createNewInstFile(filename, fileDescription, members.size(), superclassFilename);

        TreeSet<String> membersTree = new TreeSet();
        membersTree.addAll(members.keySet());

        membersTree.forEach(memberName -> {
            SymbolTable st = dpst.getSymbolTable(memberName, lineNumber);
            String description = members.get(memberName).getDescription();

            DictionaryUtils.writeInstTableRow(bw, st, memberName, description, filename);
            handleMember(st, filename, description);
        });

        DictionaryUtils.endFile(bw);
    }

    private void handleLayerInstSymbolTable(LayerInstSymbolTable lst, String filename,
                                            String fileDescription, String superclassFilename) {
        HashMap<String, MemberSymbol> members = lst.resolveMembers();
        BufferedWriter bw = DictionaryUtils.createNewInstFile(filename, fileDescription, members.size(), superclassFilename);

        TreeSet<String> membersTree = new TreeSet();
        membersTree.addAll(members.keySet());

        membersTree.forEach(memberName -> {
            SymbolTable st = lst.getSymbolTable(memberName, lineNumber);
            String description = members.get(memberName).getDescription();

            DictionaryUtils.writeInstTableRow(bw, st, memberName, description, filename);
            handleMember(st, filename, description);
        });

        DictionaryUtils.endFile(bw);
    }

    private void handleListSymbolTable(ListSymbolTable lst, String filename, String fileDescription) {
        BufferedWriter bw = DictionaryUtils.createNewClassFile(filename, fileDescription, lst.getMemberNames().size());

        TreeSet<String> membersTree = new TreeSet();
        membersTree.addAll(lst.getMemberNames());

        membersTree.forEach(memberName -> {
            SymbolTable st = lst.getSymbolTable(memberName, lineNumber);
            String description = st.getDescription();

            DictionaryUtils.writeClassTableRow(bw, st, memberName, description, filename);
            handleMember(st, filename, description);
        });

        DictionaryUtils.endFile(bw);
    }

    private void handleMapST(MapSymbolTable mst, String filename, String fileDescription, String superclassFilename) {
        HashMap<String, MemberSymbol> members = mst.resolveMembers();
        BufferedWriter bw = DictionaryUtils.createNewInstFile(filename, fileDescription, members.size(), superclassFilename);

        TreeSet<String> membersTree = new TreeSet();
        membersTree.addAll(members.keySet());

        membersTree.forEach(memberName -> {
            SymbolTable st = mst.getSymbolTable(memberName, lineNumber);
            String description = members.get(memberName).getDescription();

            DictionaryUtils.writeInstTableRow(bw, st, memberName, description, filename);
            handleMember(st, filename, description);
        });

        DictionaryUtils.endFile(bw);
    }

    private void crawl() throws IOException{
        ProjectSymbolTable pst = new ProjectSymbolTable();
        String filename = ProjectSymbolTable.class.getSimpleName();

        HashMap<String, MemberSymbol> members = pst.resolveMembers();
        BufferedWriter bw = DictionaryUtils.createNewInstFile(filename, pst.getDescription(), members.size(), "");

        TreeSet<String> membersTree = new TreeSet();
        membersTree.addAll(members.keySet());

        HashMap<String, MemberSymbol> memberSymbolHashMap = pst.resolveMembers();
        membersTree.forEach(memberName -> {
            SymbolTable st = pst.getSymbolTable(memberName, lineNumber);
            String description = memberSymbolHashMap.get(memberName).getDescription();

            DictionaryUtils.writeInstTableRow(bw, st, memberName, description, filename);
            handleMember(st, "", description);
        });

        DictionaryUtils.endFile(bw);

        DictionaryUtils.createClassFrame(classes);
        DictionaryUtils.createInstanceFrame(instances);
    }

    private void handleMember(SymbolTable st, String parentFilename, String description) {
        String filename = st.getClass().getSimpleName();

        if (ListSymbolTable.class.isAssignableFrom(st.getClass())) {
            ListSymbolTable lst = (ListSymbolTable) st;
            filename = lst.getClassSymbolTableClass().getSimpleName();
        }
        else if (DictionarySymbolTable.class.isAssignableFrom(st.getClass())) {
            DictionarySymbolTable dst = (DictionarySymbolTable) st;
            filename = dst.getResolvingSymbolTableClass().getSimpleName();
        }

        if (DictionaryUtils.fileExists(filename))
            return;

        else if (ClassSymbolTable.class.isAssignableFrom(st.getClass())) {
            ClassSymbolTable cst = (ClassSymbolTable) st;
            handleClassSymbolTable(cst, filename, description);
            classes.add(filename);
        }
        else if (ConstantPrimitiveSymbolTable.class.isAssignableFrom(st.getClass())) {
            ConstantPrimitiveSymbolTable cpst = (ConstantPrimitiveSymbolTable) st;
            handleConstantPrimitiveSymbolTable(cpst, filename, description, parentFilename);
            instances.put(filename, parentFilename);
        }
        else if (DictionarySymbolTable.class.isAssignableFrom(st.getClass())) {
            DictionarySymbolTable dst = (DictionarySymbolTable) st;
            handleDictionarySymbolTable(dst, filename, description);
            classes.add(filename);
        }
        else if (DiscreteProcessInstSymbolTable.class.isAssignableFrom(st.getClass())) {
            DiscreteProcessInstSymbolTable dpst = (DiscreteProcessInstSymbolTable) st;
            handleDiscreteProcessSymbolTable(dpst, filename, description, parentFilename);
            instances.put(filename, parentFilename);
        }
        else if (LayerInstSymbolTable.class.isAssignableFrom(st.getClass())) {
            LayerInstSymbolTable list = (LayerInstSymbolTable) st;
            handleLayerInstSymbolTable(list, filename, description, parentFilename);
            instances.put(filename, parentFilename);
        }
        else if (ListSymbolTable.class.isAssignableFrom(st.getClass())) {
            ListSymbolTable lst = (ListSymbolTable) st;
            handleListSymbolTable(lst, filename, description);
            classes.add(filename);
        }
        else if (MapSymbolTable.class.isAssignableFrom(st.getClass())) {
            MapSymbolTable mst = (MapSymbolTable) st;
            handleMapST(mst, filename, description, parentFilename);
            instances.put(filename, parentFilename);
        }
        else {
            System.out.println("Missing handleMember() handler: " + st.getClass().getSimpleName());
        }
    }

    public DictionaryCrawler() {
        File dir = new File("meta/src/dictionary/pages");
        DictionaryUtils.setDirectory(dir);

        dir.mkdirs();

        for(File file: dir.listFiles()) {
            if (!file.getName().equals("style.css")
                    && !file.getName().equals("index.html")
                    && !file.getName().equals("navigation.html"))
                file.delete();
        }
    }

    public static void main(String args[]) throws IOException {
        DictionaryCrawler dictionaryCrawler = new DictionaryCrawler();
        dictionaryCrawler.crawl();
    }
}