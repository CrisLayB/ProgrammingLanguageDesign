// Importar codigo java propio
import controllers.FilesCreator;
import controllers.YaparAnalyzer;

// Importar librerias de java framework collections
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * <h1>Diseño De Lenguajes de Programacion - UVG</h1>
 * <h2> Parser Generator </h2>
 * 
 * Programa donde se analizara un archivo YAPar para crear un
 * analizador sintático. 
 * 
 * Creado por:
 * @author Cristian Laynez - 201281
 * @since 2023
 **/

public class ParserGenerator {
    public static void main(String[] args) {
        // Si no ingreso la cantidad de argumentos esperados
        if (args.length <= 1){
            System.out.println("Ejemplo de como ejecutar el programa e el nombre del archivo YALex:");
            System.out.println("\njava ParserGenerator 'src/docs/YAPar/slr-1.yalp' 'docs/tokens'\n");
            return;
        }

        // Leer el contenido del archivo Yalp
        ArrayList<String> yalpContent = FilesCreator.readFileContent(args[0], false);

        if(yalpContent == null){ // Si dado caso no se detecta de fomra correcta el codigo yal
            System.out.println("\n--> No se a detectado un archivo valido para YALP\n");
            return;
        }

        System.out.println("========================================================");
        System.out.println("Contenido del archivo yalp =============================");
        System.out.println("========================================================");
        for (String string : yalpContent) {
            System.out.println(string);
        }
        System.out.println("========================================================");

        // Leer el contenido del output de los tokens
        ArrayList<String> tokens = FilesCreator.readFileContent(args[1], false);

        if(tokens == null){ // Si dado caso no se detecta de fomra correcta el codigo yal
            System.out.println("\n--> No se a detectado un archivo valido para TOKENS\n");
            return;
        }
        
        // Vamos a guardar los tokens generados por el Scanner
        HashMap<String, String> tokensDetected = getTokens(tokens);

        // Mostrar los tokens encontrados
        System.out.println("\n========================================================");
        System.out.println("TOKENS detectados del Scanner ==========================");
        System.out.println("========================================================");
        for(Map.Entry<String, String> token: tokensDetected.entrySet()){
            String tokenN = token.getKey();
            String tokenC = token.getValue();
            System.out.println(tokenN + "->" + tokenC);
        }
        System.out.println("========================================================");

        // * ===> Vamos a analizar el contenido YAPar
        YaparAnalyzer yapar = new YaparAnalyzer(yalpContent, tokensDetected);

        if(yapar.getHasErrors()) return; // No continuar SI hay errores
        
        System.out.println("\n========================================================");
        System.out.println("TOKENS Yapar ===========================================");
        System.out.println("========================================================");
        yapar.seeTokensYapar();
        System.out.println("========================================================");

        System.out.println("\n========================================================");
        System.out.println("PRODUCTIONS Yapar ======================================");
        System.out.println("========================================================");
        yapar.seeAllProductions();
        System.out.println("========================================================");

        // * ===> Empezar a construir el Automata LR(0)
        // ! ...
    }

    private static HashMap<String, String> getTokens(ArrayList<String> tokens){
        HashMap<String, String> tokensDetected = new HashMap<String, String>();

        boolean saveNameToken = true;
        String tokenName = "", tokenContent = "";
        for (String string : tokens) {
            for (int i = 0; i < string.length(); i++) {
                char c = string.charAt(i);
                if(c == '→'){ // Para indicar que se asignara contenido
                    saveNameToken = false;
                }
                else if(c == '↑'){ // Para indicar que se asignara contenido
                    // Ingresar nuevo token
                    tokensDetected.put(tokenName, tokenContent);                                       
                    // Resetear valores por defecto
                    saveNameToken = true;
                    tokenName = "";
                    tokenContent = "";
                }
                else{ // Verificiar si guardar nombre o contenido del token definido
                    if(saveNameToken) tokenName += c;
                    if(!saveNameToken) tokenContent += c;
                }
            }
        }

        return tokensDetected;
    }
}
