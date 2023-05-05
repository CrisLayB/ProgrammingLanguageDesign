package controllers;

// Para revisar y sobreescribir el contenido del archivo
import java.io.BufferedWriter;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

// Leer archivos
import java.io.BufferedReader;
import java.io.FileReader;

// Para retornar la data
import java.util.ArrayList;

// Para leer el contenido del Automata
import models.NFA;
import models.DFA;
import models.Transition;
import models.State;

public class FilesCreator {        
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

    public static String readContentMegaNFA(NFA nfa, String r){
        String contentScript = "";
        contentScript += "digraph \"Resultado Automata AFN\" {\n";
        contentScript += "\tlabel = \"" + r + "  [AFN]\"\n";
        contentScript += "\tlabelloc  =  t\n";
        contentScript += "\tfontsize  = 25\n";
        contentScript += "\trankdir=LR size=\"8,5\"\n";
        contentScript += "\tnode [shape=doublecircle]\n";
        for (State state : nfa.getStatesFinal()) {
            contentScript += "\t" + state.getId() + "\n";
        }
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

    public static boolean createScannerJava(ArrayList<String> codeMegaAutomata, ArrayList<String> codeRule){
        try {
            FileWriter myWriter = new FileWriter("src/Scanner.java");
            // myWriter.write("\t\t");
            myWriter.write("import controllers.FilesCreator;\n");
            myWriter.write("import models.*;\n");
            myWriter.write("\n");
            myWriter.write("import java.util.List;\n");
            myWriter.write("import java.util.ArrayList;\n");
            myWriter.write("\n");
            myWriter.write("public class Scanner {\n");

            // Implementar variables para retornar el scan y codigo generado
            myWriter.write("\tprivate final static String NULL = \"NULL\";\n");
            for (String code : codeRule) {
                myWriter.write(code);
            }
            
            // ! ============================================================================
            // Menu principal del programa
            myWriter.write("\n\tpublic static void main(String[] args) {\n");
            myWriter.write("\t\tif (args.length == 0){\n");
            myWriter.write("\t\t\tSystem.out.println(\"Te falto ingresar un archivo en los argumentos\");\n");
            myWriter.write("\t\t\tSystem.out.println(\"EJEMPLO: java Scanner file\");\n");
            myWriter.write("\t\t\treturn;\n");
            myWriter.write("\t\t}\n");
            myWriter.write("\t\tSystem.out.println(\"===> Scanner.java\");\n");
            
            // Obtener automata generado
            myWriter.write("\n\t\t// Obtener Automata \n");
            myWriter.write("\t\tNFA automata = megaAutomata(); \n");
            myWriter.write("\t\t");

            // Recibir contenido del input
            myWriter.write("\n\t\t// Leer contenido del archivo \n");
            myWriter.write("\t\tArrayList<String> fileContent = FilesCreator.readFileContent(args[0]); \n");
            myWriter.write("\t\tArrayList<String> results = new ArrayList<String>(); \n");
            myWriter.write("\t\tArrayList<String> actions = new ArrayList<String>(); \n");
            myWriter.write("\n\t\tString lexema = \"\", idDetected = \"\"; \n");
            myWriter.write("\t\tfor (String string : fileContent) { \n");
            myWriter.write("\t\t    for (int i = 0; i < string.length(); i++) {\n");
            myWriter.write("\t\t        char c = string.charAt(i); \n");
            myWriter.write("\t\t        int ascii = (int)c; \n");
            myWriter.write("\t\t        String[] result = automata.simulateMega(ascii+\"\");\n");
            myWriter.write("\t\t        // Vamos a evaluar si el id detectado esta vacio\n");
            myWriter.write("\t\t        if(idDetected.length() == 0){ \n");
            myWriter.write("\t\t        	idDetected = result[1];\n");
            myWriter.write("\t\t        	lexema += c;\n");
            myWriter.write("\t\t        	if(result[1].equals(\"ERROR LEXICO\")){\n");
            myWriter.write("\t\t        		results.add(\"[\" + c + \" - Token: \" + result[1] + \"]\\n\");\n");
            myWriter.write("\t\t        		actions.add(\"[\" + c + \" - Token Action: \" + scan(result[1]) + \"]\\n\");\n");
            myWriter.write("\t\t        	    idDetected = \"\";\n");
            myWriter.write("\t\t        	    lexema = \"\";\n");
            myWriter.write("\t\t        	}\n");
            myWriter.write("\t\t        }\n");
            myWriter.write("\t\t        else if(idDetected.equals(result[1])) lexema += c;\n");
            myWriter.write("\t\t        else{ // Si ya no es igual entonces se detiene\n");
            myWriter.write("\t\t            results.add(\"[\" + lexema + \" - Token: \" + idDetected + \"]\\n\");\n");
            myWriter.write("\t\t        	actions.add(\"[\" + lexema + \" - Token Action: \" + scan(idDetected) + \"]\\n\");\n");
            myWriter.write("\t\t            // Resetear el id Detectado\n");
            myWriter.write("\t\t        	idDetected = result[1];\n");
            myWriter.write("\t\t            lexema = c + \"\";\n");
            myWriter.write("\t\t        }\n");
            myWriter.write("\t\t    } \n");
            myWriter.write("\t\t} \n");
            myWriter.write("\t\tif(!FilesCreator.createFileTokens(results, \"docs/outputFile\") || !FilesCreator.createFileTokens(actions, \"docs/outputFileActions\")){\n");
            myWriter.write("\t\t    System.out.println(\"Error a la hora de crear el output :(\");\n");
            myWriter.write("\t\t    return;\n");
            myWriter.write("\t\t}\n");
            myWriter.write("\t}\n");
            // ! ============================================================================            

            // Implementar codigo de automatas            
            myWriter.write("\n\tprivate static NFA megaAutomata() { \n");
            myWriter.write("\t\t// Automata Generado \n");
            for (String lineCode : codeMegaAutomata) {
                myWriter.write("\t\t" + lineCode + "\n");
            }
            myWriter.write("\t}\n");
            
            myWriter.write("}\n");
            myWriter.close();
          } catch (IOException e) {            
            return false;
          }
        return true;
    }

    public static boolean createFileTokens(ArrayList<String> resultsTokens, String path){
        try {
            FileWriter myWriter = new FileWriter(path);
            for (String result : resultsTokens) {
                myWriter.write(result);
            }
            myWriter.close();
        } catch (IOException e) {
            return false;
        }
        return true;
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
                lines.add(bfRead + "\n"); // Tomar en cuenta los enters
            }
        } catch (Exception e) {
            lines = null;
        }

        return lines;
    }

    // TypeFile: -"Tpng" or "-Tpdf"
    public static void createDot(String fileInputPath, String fileOutputPath, String typeFile){
        String[] cmd = new String[8];
        cmd[0] = "dot";
        cmd[1] = typeFile;
        cmd[2] = fileInputPath;
        cmd[3] = "-o";
        cmd[4] = fileOutputPath;
        cmd[5] = "&&";
        cmd[6] = "sxiv";
        cmd[7] = fileOutputPath;
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec( cmd );
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

// PD: Unica clase que utiliza Try and Catch JAJAJAJA