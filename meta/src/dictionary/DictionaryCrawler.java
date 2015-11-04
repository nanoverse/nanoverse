package dictionary;

import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.control.run.ProjectSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.layers.LayerInstSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.ConstantPrimitiveSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.processes.discrete.DiscreteProcessInstSymbolTable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DictionaryCrawler {

    File dir;

    private void handleMapST(MapSymbolTable st, String filename, String fileDescription) {
        BufferedWriter bw = createNewFile(filename, fileDescription, st.getMemberNames().count());

        st.getMemberNames().forEach(mn -> {
            String mn_name = (String) mn;
            String type = truncateType(st.getSymbolTable(mn_name).getClass().getSimpleName());
            String description = st.getMemberDescription(mn_name);

            writeTableRow(bw, mn_name, type, description);

            handleMember(st.getSymbolTable(mn_name), mn_name, description);

        });

        endFile(bw);
    }

    private void handleClassST(ClassSymbolTable st, String filename, String fileDescription) {
        BufferedWriter bw = createNewFile(filename, fileDescription, st.getMemberNames().count());

        st.getMemberNames().forEach(mn -> {
            String mn_name = (String) mn;
            String type = truncateType(st.getSymbolTable(mn_name).getClass().getSimpleName());
            String description = st.getSymbolTable(mn_name).getDescription();

            writeTableRow(bw, mn_name, type, description);

            handleMember(st.getSymbolTable(mn_name), mn_name, description);
        });

        endFile(bw);
    }

    private void handleDiscreteProcessST(DiscreteProcessInstSymbolTable st, String filename,
                                         String fileDescription) {
        BufferedWriter bw = createNewFile(filename, fileDescription, st.getMemberNames().count());

        st.getMemberNames().forEach(mn -> {
            String mn_name = (String) mn;
            String type = truncateType(st.getSymbolTable(mn_name).getClass().getSimpleName());
            String description = st.getMemberDescription(mn_name);

            writeTableRow(bw, mn_name, type, description);

            handleMember(st.getSymbolTable(mn_name), mn_name, description);
        });

        endFile(bw);
    }

    private void handleLayerInstST(LayerInstSymbolTable st, String filename, String fileDescription) {
        BufferedWriter bw = createNewFile(filename, fileDescription, st.getMemberNames().count());

        st.getMemberNames().forEach(mn -> {
            String mn_name = (String) mn;
            String type = truncateType(st.getSymbolTable(mn_name).getClass().getSimpleName());
            String description = st.getMemberDescription(mn_name);

            writeTableRow(bw, mn_name, type, description);

            handleMember(st.getSymbolTable(mn_name), mn_name, description);
        });

        endFile(bw);
    }

    private void handleListST(ListSymbolTable st, String filename, String fileDescription) {
        BufferedWriter bw = createNewFile(filename, fileDescription, st.getMemberNames().count());

        st.getMemberNames().forEach(mn -> {
            String mn_name = (String) mn;
            String type = truncateType(st.getSymbolTable(mn_name).getClass().getSimpleName());
            String description = st.getSymbolTable(mn_name).getDescription();

            writeTableRow(bw, mn_name, type, description);

            handleMember(st.getSymbolTable(mn_name), mn_name, description);
        });

        endFile(bw);
    }

    private void beginCrawl() throws IOException{
        ProjectSymbolTable st = new ProjectSymbolTable();
        BufferedWriter bw = createNewFile("Main", "", st.getMemberNames().count());

        st.getMemberNames().forEach(mn -> {
            String type = truncateType(st.getSymbolTable(mn).getClass().getSimpleName());
            String description = st.getMemberDescription(mn);

            writeTableRow(bw, mn, type, description);

            handleMember(st.getSymbolTable(mn), mn, description);
        });

        endFile(bw);
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
        else if (ConstantPrimitiveSymbolTable.class.isAssignableFrom(st.getClass())) {
            // DO NOTHING
        }
        else if (DictionarySymbolTable.class.isAssignableFrom(st.getClass())) {
            // DO NOTHING
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
        else
            type = type.substring(0, type.indexOf("Symbol"));

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
