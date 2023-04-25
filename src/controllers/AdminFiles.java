package controllers;

// Para revisar y sobreescribir el contenido del archivo
import java.io.BufferedWriter;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

// Leer archivos
import java.io.BufferedReader;
import java.io.FileReader;

// Para retornar la data
import java.util.ArrayList;

// Para leer el contenido del Automata
import models.NFA;
import models.DFA;
import models.Transition;

public class AdminFiles {        
    public static String readContentNFA(NFA nfa, String r){
        String contentScript = "";
        contentScript += "digraph \"Resultado Automata AFN\" {\n";
        contentScript += "\tlabel = \"" + r + "  [AFN]\"\n";
        contentScript += "\tlabelloc  =  t\n";
        contentScript += "\tfontsize  = 25\n";
        contentScript += "\trankdir=LR size=\"8,5\"\n";
        contentScript += "\tnode [shape=doublecircle]\n";
        contentScript += "\t" + nfa.getStateFinal().getId() + "\n";
        contentScript += "\tnode [shape=circle]\n";
        contentScript += "\t" + nfa.getStateInitial().getId() + "\n";
        contentScript += "\tnode [shape=none]\n";
        contentScript += "\t\"\"\n";
        contentScript += "\t\"\"-> " + nfa.getStateInitial().getId() + " [label=\"\"]\n";
        contentScript += "\tnode [shape=circle]\n";
        for (Transition transition : nfa.getTransitions()) {
            contentScript += "\t" + transition.toString();
        }
        contentScript += "}";        
        return contentScript;
    }

    public static String readContentDFA(DFA dfa, String r){
        String contentScript = "";
        contentScript += "digraph \"Resultado Automata AFD\" {\n";
        contentScript += "\tlabel = \"" + r + "  [AFD]\"\n";
        contentScript += "\tlabelloc  =  t\n";
        contentScript += "\tfontsize  = 25\n";
        contentScript += "\trankdir=LR size=\"8,5\"\n";
        contentScript += "\tnode [shape=doublecircle]\n";
        contentScript += "\tnode [shape=circle]\n";
        contentScript += "\t" + dfa.getStateInitial().getId() + "\n";
        contentScript += "\tnode [shape=none]\n";
        contentScript += "\tnode [shape=circle]\n";
        for (Transition transition : dfa.getTransitions()) {
            contentScript += "\t" + transition.toString();
        }
        contentScript += "}";        
        return contentScript;
    }

    public static String readContentTree(ArrayList<String> information){
        String contentScript = "";
        contentScript += "digraph \"Arbol Sintatico\" {\n";
        // Ahora vamos a obtener la informacion
        for (String info : information) {
            contentScript += info + "\n";
        }
        contentScript += "\n}";    
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

    public static ArrayList<String> readFileContent(String file){
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader bf;
        
        try {
            bf = new BufferedReader(new FileReader(file));
            String bfRead;

            while((bfRead = bf.readLine()) != null){
                lines.add(bfRead);
            }
        } catch (Exception e) {
            lines = null;
        }

        return lines;
    }
    
    public static void createImgDot(String fileInputPath, String fileOutputPath){
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

    public static void createPdfDot(String fileInputPath, String fileOutputPath){
        try {
            String[] cmd = new String[8];
            cmd[0] = "dot";
            cmd[1] = "-Tpdf";
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
