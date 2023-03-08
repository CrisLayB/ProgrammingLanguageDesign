package controllers;

// Librerias para leer el contenido del archivo
import java.io.BufferedWriter;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

// Para leer el contenido del Automata
import models.NFA;
import models.Transition;

public class Graphviz {        
    public static String readContentNFA(NFA nfa){
        String contentScript = "";
        contentScript += "digraph \"Resultado Automata\" {\n";
        contentScript += "rankdir=LR size=\"8,5\"\n";
        contentScript += "node [shape=doublecircle]\n";
        contentScript += nfa.getStateFinal().getId() + "\n";
        contentScript += "node [shape=circle]\n";
        contentScript += nfa.getStateInitial().getId() + "\n";
        contentScript += "node [shape=none]\n";
        contentScript += "\"\"\n";
        contentScript += "\"\"-> " + nfa.getStateInitial().getId() + " [label=\"\"]\n";
        contentScript += "node [shape=circle]\n";
        for (Transition transition : nfa.getTransitions()) {
            contentScript += transition.toString();
        }
        contentScript += "}";        
        return contentScript;
    }

    public static boolean writeFileCode(String content, String file){
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            out.write(content);
            out.close();            
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    // dot -Tpng docs/automataAFN.dot -o img/resultsAFN.png && sxiv img/resultsAFN.png
    public static void createImgAutomata(String fileInputPath, String fileOutputPath){
        try {
            String[] cmd = new String[8];
            cmd[0] = "dot";
            cmd[1] = "-Tpng";
            cmd[2] = fileInputPath;
            cmd[3] = "-o";
            cmd[4] = fileOutputPath;
            cmd[5] = "&&";
            cmd[6] = "sxiv";
            cmd[7] = fileOutputPath;

            Runtime rt = Runtime.getRuntime();
            rt.exec( cmd );
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
