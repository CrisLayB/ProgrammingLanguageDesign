// Importar codigo java propio
import controllers.AdminFiles;
import controllers.YalChecker;
import models.Tree;
import models.PairData;
import models.NFA;
import algorithms.ShuntingYardAlgorithm;
import algorithms.ThompsonAlgorithmMega;

// Importar librerias de java framework collections
import java.util.ArrayList;

/**
 * <h1>Diseño De Lenguajes de Programacion - UVG</h1>
 * <h2> Yal </h2>
 * Programa donde se lleva a cabo el analisis de los archivos yal increados
 * 
 * Creado por:
 * @author Cristian Laynez - 201281
 * @since 2023
 **/

public class Yal {
    public static void main(String[] args) {
        int amountArgs = args.length;

        // Si no ingreso ningun argumento
        if (amountArgs == 0){
            System.out.println("Ejemplo de como ejecutar el programa e el nombre del archivo YALex:");
            System.out.println("\njava Yal 'src/docs/slr-1.yal'\n");
            return;
        }
        
        // * ====> Vamos a obtener todo el contenido del archivo
        ArrayList<String> yalContent = AdminFiles.readFileContent(args[0]);

        if(yalContent == null){ // Si dado caso no se detecta de fomra correcta el codigo yal
            System.out.println("\n--> No se a detectado un archivo valido\n");
            return;
        }

        System.out.println("========================================================");
        System.out.println("Contenido del archivo yal ==============================");
        System.out.println("========================================================");
        for (String string : yalContent) {
            System.out.println(string);
        }
        System.out.println("========================================================");
                
        // * ====> Obtener Regex del yal
        YalChecker tokenizer = new YalChecker(yalContent); // Procesar la data
        ArrayList<String> regex = tokenizer.process(); 
        if(regex == null){
            return;
        }
        tokenizer.seeIds(); // Ver detalles de los ids
        tokenizer.seeRules(); // Ver detalles de las reglas        
        System.out.println("Regex Expression: ");
        for (String string : regex) { // Vamos a ver toda la expresion regex
            System.out.print(string);
        }
        System.out.println("\n\nPostfix Expression: ");
        ArrayList<String> regexPostfix = ShuntingYardAlgorithm.infixToPostfix(regex);
        if(regexPostfix == null){
            System.out.println("F");
            return;
        }
        // Mostrar todas las expresions de postfix y agregar ids para cada elemento
        ArrayList<PairData<String, String>> regexExpression = new ArrayList<PairData<String, String>>();
        int idCounter = 0;        
        for (String string : regexPostfix) {
            System.out.print(string + ",");
            regexExpression.add(new PairData<String,String>("n"+idCounter, string));
            idCounter++;
        }
        regexExpression.add(new PairData<String,String>("n"+idCounter, "#"));
        idCounter++;
        regexExpression.add(new PairData<String,String>("n"+idCounter, "·"));
        System.out.println("");

        // * ====> Obtener el arbol sintatico
        Tree regexTree = new Tree(regexExpression);
        
        ArrayList<String> transitionsTree = regexTree.getTransitions();
        String scriptTree = AdminFiles.readContentTree(transitionsTree);
        String fileSintaxTree = "docs/SintaxTree.dot";
        if(!AdminFiles.writeFileCode(scriptTree, fileSintaxTree)){
            System.out.println("No se pudo guardar ni sobreescribir el archivo .dot");
            return;
        }
        AdminFiles.createImgDot(fileSintaxTree, "img/resultsSintaxTree.png");

        // * ====> Crear un automata
        ThompsonAlgorithmMega megaAutomata = new ThompsonAlgorithmMega(tokenizer.getIdsExtended());
                
                
        // * ====> Crear el scanner con todos los datos generados        
    }
}