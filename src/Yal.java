// Importar codigo java propio
import controllers.AdminFiles;
import controllers.LexicalAnalyzer;

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
        
        // Vamos a obtener todo el contenido del archivo
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

        // Procesar la data
        LexicalAnalyzer tokenizer = new LexicalAnalyzer(yalContent);
        System.out.println("Expresion regular: ");
        String regularExpression = tokenizer.getRegularExpression();
        System.out.println(regularExpression);
    }
}