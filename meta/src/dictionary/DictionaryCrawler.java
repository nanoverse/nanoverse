package dictionary;

import nanoverse.compiler.pipeline.translate.symbol.ClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.DictionarySymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.ListSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.control.run.ProjectSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.layers.LayerInstSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.ConstantPrimitiveSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.processes.discrete.DiscreteProcessInstSymbolTable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DictionaryCrawler {

    private void handleMapST(MapSymbolTable st, File dir, String filename, String fileDescription) throws IOException {
        if (fileExists(dir, filename))
            return;

        BufferedWriter bw = null;
        try { bw = createNewFile(dir, filename, fileDescription); } catch (IOException ignored) {}
        final BufferedWriter finalBw = bw;
        
        // Table Header
        if (st.getMemberNames().count() > 0)
            bw.write("<tr><th>Member</th><th>Type</th><th>Description</th></tr>");

        // Table Rows
        st.getMemberNames().forEach(mn -> {
            String mn_name = (String) mn;
            String type = truncateType(st.getSymbolTable(mn_name).getClass().getSimpleName());
            String description = st.getMemberDescription(mn_name);

            try { writeTableRow(finalBw, mn_name, type, description); }
            catch (IOException ignored) {}

            if (ClassSymbolTable.class.isAssignableFrom(st.getSymbolTable(mn_name).getClass())) {
                ClassSymbolTable cst = (ClassSymbolTable) st.getSymbolTable(mn_name);

                try {handleClassST(cst, dir, mn_name, description); } catch (IOException ignored) {}
            }
            else if (ListSymbolTable.class.isAssignableFrom(st.getSymbolTable(mn_name).getClass())) {
                ListSymbolTable lst = (ListSymbolTable) st.getSymbolTable(mn_name);
                try { handleListST(lst, dir, mn_name, description); } catch (IOException ignored) {}
            }
            else if (ConstantPrimitiveSymbolTable.class.isAssignableFrom(st.getSymbolTable(mn_name).getClass())) {
                // DO NOTHING
            }
            else if (DictionarySymbolTable.class.isAssignableFrom(st.getSymbolTable(mn_name).getClass())) {
                // DO NOTHING
            }
            else {
                System.out.println("Missing handleMapST() handler: " + st.getSymbolTable(mn_name).getClass().getSimpleName());
            }
        });

        bw.write("</table>");
        bw.write("</body></html>");
        bw.close();
    }

    private void handleClassST(ClassSymbolTable st, File dir, String filename, String fileDescription) throws IOException {
        if (fileExists(dir, filename))
            return;

        BufferedWriter bw = null;
        try { bw = createNewFile(dir, filename, fileDescription); } catch (IOException ignored) {}
        final BufferedWriter finalBw = bw;

        // Table Header
        if (st.getMemberNames().count() > 0)
            bw.write("<tr><th>Member</th><th>Type</th><th>Description</th></tr>");

        // Table Rows
        st.getMemberNames().forEach(mn -> {
            String mn_name = (String) mn;
            String type = truncateType(st.getSymbolTable(mn_name).getClass().getSimpleName());
            String description = st.getSymbolTable(mn_name).getDescription();

            try { writeTableRow(finalBw, mn_name, type, description); }
            catch (IOException ignored) {}

            if (ListSymbolTable.class.isAssignableFrom(st.getSymbolTable(mn_name).getClass())) {
                ListSymbolTable lst = (ListSymbolTable) st.getSymbolTable(mn_name);
                try { handleListST(lst, dir, mn_name, description); } catch (IOException ignored) {}
            }
            else if (ClassSymbolTable.class.isAssignableFrom(st.getSymbolTable(mn_name).getClass())) {
                ClassSymbolTable cst = (ClassSymbolTable) st.getSymbolTable(mn_name);
                try { handleClassST(cst, dir, mn_name, description); } catch (IOException ignored) {}
            }
            else if (MapSymbolTable.class.isAssignableFrom(st.getSymbolTable(mn_name).getClass())) {
                MapSymbolTable mst = (MapSymbolTable) st.getSymbolTable(mn_name);
                try { handleMapST(mst, dir, mn_name, description); } catch (IOException ignored) {}
            }
            else if (ConstantPrimitiveSymbolTable.class.isAssignableFrom(st.getSymbolTable(mn_name).getClass())) {
                // DO NOTHING
            }
            else {
                System.out.println("Missing handleClassST() handler: " + st.getSymbolTable(mn_name).getClass().getSimpleName());
            }
        });

        bw.write("</table>");
        bw.write("</body></html>");
        bw.close();
    }

    private void handleDiscreteProcessST(DiscreteProcessInstSymbolTable st,
                                         File dir, String filename, String fileDescription) throws IOException {
        if (fileExists(dir, filename))
            return;

        BufferedWriter bw = null;
        try { bw = createNewFile(dir, filename, fileDescription); } catch (IOException ignored) {}
        final BufferedWriter finalBw = bw;

        // Table Header
        if (st.getMemberNames().count() > 0)
            bw.write("<tr><th>Member</th><th>Type</th><th>Description</th></tr>");

        // Table Rows
        st.getMemberNames().forEach(mn -> {
            String mn_name = (String) mn;
            String type = truncateType(st.getSymbolTable(mn_name).getClass().getSimpleName());
            String description = st.getMemberDescription(mn_name);

            try { writeTableRow(finalBw, mn_name, type, description); } catch (IOException ignored) {}

            if (ClassSymbolTable.class.isAssignableFrom(st.getSymbolTable(mn_name).getClass())) {
                ClassSymbolTable cst = (ClassSymbolTable) st.getSymbolTable(mn_name);
                try { handleClassST(cst, dir, mn_name, description); } catch (IOException ignored) {}
            }
            else {
                System.out.println("Missing handleDiscreteProcessST() handler: " + st.getSymbolTable(mn_name).getClass().getSimpleName());
            }
        });

        bw.write("</table>");
        bw.write("</body></html>");
        bw.close();
    }

    private void handleLayerInstST(LayerInstSymbolTable st, File dir, String filename, String fileDescription) throws IOException {
        if (fileExists(dir, filename))
            return;

        BufferedWriter bw = null;
        try { bw = createNewFile(dir, filename, fileDescription); } catch (IOException ignored) {}
        final BufferedWriter finalBw = bw;

        // Table Header
        if (st.getMemberNames().count() > 0)
            bw.write("<tr><th>Member</th><th>Type</th><th>Description</th></tr>");

        // Table Rows
        st.getMemberNames().forEach(mn -> {
            String mn_name = (String) mn;
            String type = truncateType(st.getSymbolTable(mn_name).getClass().getSimpleName());
            String description = st.getMemberDescription(mn_name);

            try { writeTableRow(finalBw, mn_name, type, description); }
            catch (IOException ignored) {}

            if (ClassSymbolTable.class.isAssignableFrom(st.getSymbolTable(mn_name).getClass())) {
                ClassSymbolTable cst = (ClassSymbolTable) st.getSymbolTable(mn_name);
                try { handleClassST(cst, dir, mn_name, description); } catch (IOException ignored) {}
            }
            else {
                System.out.println("Missing handleLayerInstST() handler: " + st.getSymbolTable(mn_name).getClass().getSimpleName());
            }
        });

        bw.write("</table>");
        bw.write("</body></html>");
        bw.close();
    }

    private void handleListST(ListSymbolTable st, File dir, String filename, String fileDescription) throws IOException{

        if (fileExists(dir, filename))
            return;

        BufferedWriter bw = null;
        try { bw = createNewFile(dir, filename, fileDescription); } catch (IOException ignored) {}
        final BufferedWriter finalBw = bw;

        // Table Header
        if (st.getMemberNames().count() > 0)
            bw.write("<tr><th>Member</th><th>Type</th><th>Description</th></tr>");

        // Table Rows
        st.getMemberNames().forEach(mn -> {
            String mn_name = (String) mn;
            String type = truncateType(st.getSymbolTable(mn_name).getClass().getSimpleName());
            String description = st.getSymbolTable(mn_name).getDescription();

            try { writeTableRow(finalBw, mn_name, type, description); }
            catch (IOException ignored) {}

            if (DiscreteProcessInstSymbolTable.class.isAssignableFrom(st.getSymbolTable(mn_name).getClass())) {
                DiscreteProcessInstSymbolTable dpst = (DiscreteProcessInstSymbolTable) st.getSymbolTable(mn_name);
                try { handleDiscreteProcessST(dpst, dir, mn_name, description); } catch (IOException ignored) {}
            } 
            else if (ListSymbolTable.class.isAssignableFrom(st.getSymbolTable(mn_name).getClass())) {
                ListSymbolTable lst = (ListSymbolTable) st.getSymbolTable(mn_name);
                try { handleListST(lst, dir, mn_name, description); } catch (IOException ignored) {}
            } 
            else if (LayerInstSymbolTable.class.isAssignableFrom(st.getSymbolTable(mn_name).getClass())) {
                LayerInstSymbolTable list = (LayerInstSymbolTable) st.getSymbolTable(mn_name);
                try { handleLayerInstST(list, dir, mn_name, description); } catch (IOException ignored) {}
            } 
            else if (MapSymbolTable.class.isAssignableFrom(st.getSymbolTable(mn_name).getClass())) {
                MapSymbolTable mst = (MapSymbolTable) st.getSymbolTable(mn_name);
                try { handleMapST(mst, dir, mn_name, description); } catch (IOException ignored) {}
            } 
            else if (ConstantPrimitiveSymbolTable.class.isAssignableFrom(st.getSymbolTable(mn_name).getClass())) {
                // DO NOTHING
            } 
            else {
                System.out.println("Missing handleListST() handler: " + st.getSymbolTable(mn_name).getClass().getSimpleName());
            }
        });

        bw.write("</table>");
        bw.write("</body></html>");
        bw.close();
    }

    private void beginCrawl(File dir, BufferedWriter bw) throws IOException{
        ProjectSymbolTable st = new ProjectSymbolTable();

        bw.write("<html><head><title></title>" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">" +
                "</head><body>");
        bw.write("<h1>Main</h1>");
        bw.write("<table><tr><th>Member</th><th>Type</th><th>Description</th></tr>");

        st.getMemberNames().forEach(mn -> {
            String type = truncateType(st.getSymbolTable(mn).getClass().getSimpleName());
            String description = st.getMemberDescription(mn);
            
            try { writeTableRow(bw, mn, type, description); } catch (IOException ignored) {}

            if (ListSymbolTable.class.isAssignableFrom(st.getSymbolTable(mn).getClass())) {
                ListSymbolTable lst = (ListSymbolTable) st.getSymbolTable(mn);
                try { handleListST(lst, dir, mn, description); } catch (IOException ignored) {}
            } 
            else if (ClassSymbolTable.class.isAssignableFrom(st.getSymbolTable(mn).getClass())) {
                ClassSymbolTable cst = (ClassSymbolTable) st.getSymbolTable(mn);
                try { handleClassST(cst, dir, mn, description); } catch (IOException ignored) {}
            } 
            else {
                System.out.println("Missing getST() handler: " + st.getSymbolTable(mn).getClass().getSimpleName());
            }
        });

        bw.write("</table>");
        bw.write("</body></html>");
        bw.close();
    }

    private boolean fileExists(File dir, String filename) {
        if (Character.isUpperCase(filename.charAt(0))) {
            filename = filename.charAt(0) + filename;
        }
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

    private void writeTableRow(BufferedWriter bw, String mn, String type, String description) throws IOException {
        String href = mn;
        if (Character.isUpperCase(mn.charAt(0))) {
            href = mn.charAt(0) + mn;
        }
        bw.write("<tr>");
        bw.write("<td><a href='" + href + ".html'>" + mn + "</a></td>");
        bw.write("<td>"+ type + "</td>");
        bw.write("<td>" + description + "</td>");
        bw.write("</tr>");
    }

    private BufferedWriter createNewFile(File dir, String mn, String description) throws IOException {
        String filename = mn;
        if (Character.isUpperCase(mn.charAt(0))) {
            filename = mn.charAt(0) + mn;
        }
        File f = new File(dir, filename + ".html");
        BufferedWriter w = new BufferedWriter(new FileWriter(f));
        w.write("<html><head><title></title>" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">" +
                "</head><body>");
        w.write("<h1>" + mn + "</h1>");
        w.write("<p>" + description + "</p>");
        w.write("<table>");

        return w;
    }

    public static void main(String args[]) throws IOException {
        DictionaryCrawler mstc = new DictionaryCrawler();
        File dir = new File("meta/src/dictionary/pages");
        dir.mkdirs();

        for(File file: dir.listFiles()) {
            if (!file.getName().equals("style.css"))
                file.delete();
        }

        File f = new File(dir, "main.html");
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));

        mstc.beginCrawl(dir, bw);
    }
}
