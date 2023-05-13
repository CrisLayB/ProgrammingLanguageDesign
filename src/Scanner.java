import controllers.FilesCreator;
import models.*;

import java.util.List;
import java.util.ArrayList;

public class Scanner {
	private final static String NULL = "NULL";
	private final static String PLUS = "PLUS";
	private final static String ASIGNOP = "ASIGNOP";
	private final static String TIMES = "TIMES";
	private final static String LPAREN = "LPAREN";
	private final static String RPAREN = "RPAREN";

	private static String scan(String token){
		if(token.equals("+")){
			return PLUS;
		}
		if(token.equals(":=")){
			return ASIGNOP;
		}
		if(token.equals("*")){
			return TIMES;
		}
		if(token.equals("(")){
			return LPAREN;
		}
		if(token.equals(")")){
			return RPAREN;
		}
		return NULL;
	}

	public static void main(String[] args) {
		if (args.length == 0){
			System.out.println("Te falto ingresar un archivo en los argumentos");
			System.out.println("EJEMPLO: java Scanner file");
			return;
		}
		System.out.println("===> Scanner.java");

		// Obtener Automata 
		NFA automata = megaAutomata(); 
		
		// Leer contenido del archivo 
		ArrayList<String> fileContent = FilesCreator.readFileContent(args[0]); 
		ArrayList<String> results = new ArrayList<String>(); 
		ArrayList<String> actions = new ArrayList<String>(); 
		ArrayList<String> lexemas = new ArrayList<String>(); 
		 

		String lexema = "", idDetected = ""; 
		for (String string : fileContent) { 
		    for (int i = 0; i < string.length(); i++) {
		        char c = string.charAt(i); 
		        int ascii = (int)c; 
		        String[] result = automata.identifyId(ascii+"");
		        // Vamos a evaluar si el id detectado esta vacio
		        if(idDetected.length() == 0){ 
		        	idDetected = result[1];
		        	lexema += c;
		        	if(result[1].equals("ERROR LEXICO")){
		        		results.add("[" + c + " - Token: " + result[1] + "]\n");
		        		actions.add("[" + c + " - Token Action: " + scan(result[1]) + "]\n");
		                lexemas.add(c+"");
		        	    idDetected = "";
		        	    lexema = "";
		        	}
		        }
		        else if(idDetected.equals(result[1])) lexema += c;
		        else{ // Si ya no es igual entonces se detiene
		            results.add("[" + lexema + " - Token: " + idDetected + "]\n");
		        	actions.add("[" + lexema + " - Token Action: " + scan(idDetected) + "]\n");
		            lexemas.add(lexema);
		            // Resetear el id Detectado
		        	idDetected = result[1];
		            lexema = c + "";
		        }
		    } 
		} 
		for(String l : lexemas){
		    String[] resultsSim = automata.simulateMega(l);
		    System.out.println(resultsSim[0] + " and " + resultsSim[1]);
		} 
		if(!FilesCreator.createFileTokens(results, "docs/outputFile") || !FilesCreator.createFileTokens(actions, "docs/outputFileActions")){
		    System.out.println("Error a la hora de crear el output :(");
		    return;
		}
	}

	private static NFA megaAutomata() { 
		// Automata Generado 
		State initialState = new State(0, Types.Initial);
		List<State> finalStates = new ArrayList<State>();
		finalStates.add(new State("2", Types.Final, "+"));
		finalStates.add(new State("6", Types.Final, ":="));
		finalStates.add(new State("8", Types.Final, "*"));
		finalStates.add(new State("10", Types.Final, "("));
		finalStates.add(new State("12", Types.Final, ")"));
		List<State> states = new ArrayList<State>();
		states.add(new State("2", Types.Final));
		states.add(new State("6", Types.Final));
		states.add(new State("8", Types.Final));
		states.add(new State("10", Types.Final));
		states.add(new State("12", Types.Final));
		List<Transition> transitions = new ArrayList<Transition>();
		transitions.add(new Transition(new Symbol("43", "+"), new State("1", Types.Initial), new State("2", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("1", Types.Initial)));
		transitions.add(new Transition(new Symbol("58", ":="), new State("3", Types.Initial), new State("4", Types.Transition)));
		transitions.add(new Transition(new Symbol("61", ":="), new State("5", Types.Transition), new State("6", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("4", Types.Transition), new State("5", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("3", Types.Initial)));
		transitions.add(new Transition(new Symbol("42", "*"), new State("7", Types.Initial), new State("8", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("7", Types.Initial)));
		transitions.add(new Transition(new Symbol("40", "("), new State("9", Types.Initial), new State("10", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("9", Types.Initial)));
		transitions.add(new Transition(new Symbol("41", ")"), new State("11", Types.Initial), new State("12", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("11", Types.Initial)));
		return new NFA(initialState, finalStates, states, transitions);
	}
}
