import controllers.FilesCreator;
import models.*;

import java.util.List;
import java.util.ArrayList;

public class Scanner {
	private final static String NULL = "NULL";
	private final static String WHITESPACE = "WHITESPACE";
	private final static String NUMBER = "NUMBER";
	private final static String PLUS = "PLUS";
	private final static String TIMES = "TIMES";
	private final static String LPAREN = "LPAREN";
	private final static String RPAREN = "RPAREN";

	private static String scan(String token){
		if(token.equals("ws")){
			return WHITESPACE;
		}
		if(token.equals("number")){
			return NUMBER;
		}
		if(token.equals("+")){
			return PLUS;
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

		String lexema = "", idDetected = ""; 
		for (String string : fileContent) { 
		    for (int i = 0; i < string.length(); i++) {
		        char c = string.charAt(i); 
		        int ascii = (int)c; 
		        String[] result = automata.simulateMega(ascii+"");
		        // Vamos a evaluar si el id detectado esta vacio
		        if(idDetected.length() == 0){ 
		        	idDetected = result[1];
		        	lexema += c;
		        }				
		        else if(idDetected.equals(result[1])) lexema += c;
		        else{ // Si ya no es igual entonces se detiene
		            results.add("[" + lexema + " - Token: " + idDetected + "]\n");
		            // Resetear el id Detectado
		        	idDetected = result[1];
		            lexema = c + "";
		        }
		    } 
		} 
		if(!FilesCreator.createFileTokens(results, "docs/outputFile")){
		    System.out.println("Error a la hora de crear el output :(");
		    return;
		}
	}

	private static NFA megaAutomata() { 
		// Automata Generado 
		State initialState = new State(0, Types.Initial);
		List<State> finalStates = new ArrayList<State>();
		finalStates.add(new State("25", Types.Final));
		finalStates.add(new State("309", Types.Final));
		finalStates.add(new State("313", Types.Final));
		finalStates.add(new State("317", Types.Final));
		finalStates.add(new State("321", Types.Final));
		finalStates.add(new State("325", Types.Final));
		List<State> states = new ArrayList<State>();
		states.add(new State("25", Types.Final));
		states.add(new State("309", Types.Final));
		states.add(new State("313", Types.Final));
		states.add(new State("317", Types.Final));
		states.add(new State("321", Types.Final));
		states.add(new State("325", Types.Final));
		List<Transition> transitions = new ArrayList<Transition>();
		transitions.add(new Transition(new Symbol("32", "ws"), new State("1", Types.Transition), new State("2", Types.Transition)));
		transitions.add(new Transition(new Symbol("9", "ws"), new State("3", Types.Transition), new State("4", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("5", Types.Transition), new State("1", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("5", Types.Transition), new State("3", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("2", Types.Transition), new State("6", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("4", Types.Transition), new State("6", Types.Transition)));
		transitions.add(new Transition(new Symbol("10", "ws"), new State("7", Types.Transition), new State("8", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("9", Types.Initial), new State("5", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("9", Types.Initial), new State("7", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("6", Types.Transition), new State("10", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("8", Types.Transition), new State("10", Types.Transition)));
		transitions.add(new Transition(new Symbol("32"), new State("11", Types.Transition), new State("12", Types.Transition)));
		transitions.add(new Transition(new Symbol("9"), new State("13", Types.Transition), new State("14", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("15", Types.Transition), new State("11", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("15", Types.Transition), new State("13", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("12", Types.Transition), new State("16", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("14", Types.Transition), new State("16", Types.Transition)));
		transitions.add(new Transition(new Symbol("10"), new State("17", Types.Transition), new State("18", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("19", Types.Transition), new State("15", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("19", Types.Transition), new State("17", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("16", Types.Transition), new State("20", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("18", Types.Transition), new State("20", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("20", Types.Transition), new State("19", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("22", Types.Transition), new State("23", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("22", Types.Transition), new State("19", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("20", Types.Transition), new State("23", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("10", Types.Transition), new State("22", Types.Transition)));
		transitions.add(new Transition(new Symbol("#ws", "ws"), new State("24", Types.Transition), new State("25", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("23", Types.Transition), new State("24", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("9", Types.Initial)));
		transitions.add(new Transition(new Symbol(".", "number"), new State("210", Types.Initial), new State("211", Types.Transition)));
		transitions.add(new Transition(new Symbol("69", "number"), new State("212", Types.Transition), new State("213", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("214", Types.Transition), new State("215", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("216", Types.Transition), new State("212", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("216", Types.Transition), new State("214", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("213", Types.Transition), new State("217", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("215", Types.Transition), new State("217", Types.Transition)));
		transitions.add(new Transition(new Symbol("48", "number"), new State("218", Types.Transition), new State("219", Types.Transition)));
		transitions.add(new Transition(new Symbol("49", "number"), new State("220", Types.Transition), new State("221", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("222", Types.Transition), new State("218", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("222", Types.Transition), new State("220", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("219", Types.Transition), new State("223", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("221", Types.Transition), new State("223", Types.Transition)));
		transitions.add(new Transition(new Symbol("50", "number"), new State("224", Types.Transition), new State("225", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("226", Types.Transition), new State("222", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("226", Types.Transition), new State("224", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("223", Types.Transition), new State("227", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("225", Types.Transition), new State("227", Types.Transition)));
		transitions.add(new Transition(new Symbol("51", "number"), new State("228", Types.Transition), new State("229", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("230", Types.Transition), new State("226", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("230", Types.Transition), new State("228", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("227", Types.Transition), new State("231", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("229", Types.Transition), new State("231", Types.Transition)));
		transitions.add(new Transition(new Symbol("52", "number"), new State("232", Types.Transition), new State("233", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("234", Types.Transition), new State("230", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("234", Types.Transition), new State("232", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("231", Types.Transition), new State("235", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("233", Types.Transition), new State("235", Types.Transition)));
		transitions.add(new Transition(new Symbol("53", "number"), new State("236", Types.Transition), new State("237", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("238", Types.Transition), new State("234", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("238", Types.Transition), new State("236", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("235", Types.Transition), new State("239", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("237", Types.Transition), new State("239", Types.Transition)));
		transitions.add(new Transition(new Symbol("54", "number"), new State("240", Types.Transition), new State("241", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("242", Types.Transition), new State("238", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("242", Types.Transition), new State("240", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("239", Types.Transition), new State("243", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("241", Types.Transition), new State("243", Types.Transition)));
		transitions.add(new Transition(new Symbol("55", "number"), new State("244", Types.Transition), new State("245", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("246", Types.Transition), new State("242", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("246", Types.Transition), new State("244", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("243", Types.Transition), new State("247", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("245", Types.Transition), new State("247", Types.Transition)));
		transitions.add(new Transition(new Symbol("56", "number"), new State("248", Types.Transition), new State("249", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("250", Types.Transition), new State("246", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("250", Types.Transition), new State("248", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("247", Types.Transition), new State("251", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("249", Types.Transition), new State("251", Types.Transition)));
		transitions.add(new Transition(new Symbol("57", "number"), new State("252", Types.Transition), new State("253", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("254", Types.Transition), new State("250", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("254", Types.Transition), new State("252", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("251", Types.Transition), new State("255", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("253", Types.Transition), new State("255", Types.Transition)));
		transitions.add(new Transition(new Symbol("48"), new State("256", Types.Transition), new State("257", Types.Transition)));
		transitions.add(new Transition(new Symbol("49"), new State("258", Types.Transition), new State("259", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("260", Types.Transition), new State("256", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("260", Types.Transition), new State("258", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("257", Types.Transition), new State("261", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("259", Types.Transition), new State("261", Types.Transition)));
		transitions.add(new Transition(new Symbol("50"), new State("262", Types.Transition), new State("263", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("264", Types.Transition), new State("260", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("264", Types.Transition), new State("262", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("261", Types.Transition), new State("265", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("263", Types.Transition), new State("265", Types.Transition)));
		transitions.add(new Transition(new Symbol("51"), new State("266", Types.Transition), new State("267", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("268", Types.Transition), new State("264", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("268", Types.Transition), new State("266", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("265", Types.Transition), new State("269", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("267", Types.Transition), new State("269", Types.Transition)));
		transitions.add(new Transition(new Symbol("52"), new State("270", Types.Transition), new State("271", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("272", Types.Transition), new State("268", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("272", Types.Transition), new State("270", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("269", Types.Transition), new State("273", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("271", Types.Transition), new State("273", Types.Transition)));
		transitions.add(new Transition(new Symbol("53"), new State("274", Types.Transition), new State("275", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("276", Types.Transition), new State("272", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("276", Types.Transition), new State("274", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("273", Types.Transition), new State("277", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("275", Types.Transition), new State("277", Types.Transition)));
		transitions.add(new Transition(new Symbol("54"), new State("278", Types.Transition), new State("279", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("280", Types.Transition), new State("276", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("280", Types.Transition), new State("278", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("277", Types.Transition), new State("281", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("279", Types.Transition), new State("281", Types.Transition)));
		transitions.add(new Transition(new Symbol("55"), new State("282", Types.Transition), new State("283", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("284", Types.Transition), new State("280", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("284", Types.Transition), new State("282", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("281", Types.Transition), new State("285", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("283", Types.Transition), new State("285", Types.Transition)));
		transitions.add(new Transition(new Symbol("56"), new State("286", Types.Transition), new State("287", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("288", Types.Transition), new State("284", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("288", Types.Transition), new State("286", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("285", Types.Transition), new State("289", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("287", Types.Transition), new State("289", Types.Transition)));
		transitions.add(new Transition(new Symbol("57"), new State("290", Types.Transition), new State("291", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("292", Types.Transition), new State("288", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("292", Types.Transition), new State("290", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("289", Types.Transition), new State("293", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("291", Types.Transition), new State("293", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("293", Types.Transition), new State("292", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("302", Types.Transition), new State("303", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("302", Types.Transition), new State("292", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("293", Types.Transition), new State("303", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("255", Types.Transition), new State("302", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("217", Types.Transition), new State("254", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("304", Types.Transition), new State("305", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("306", Types.Transition), new State("216", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("306", Types.Transition), new State("304", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("303", Types.Transition), new State("307", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("305", Types.Transition), new State("307", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("211", Types.Transition), new State("306", Types.Transition)));
		transitions.add(new Transition(new Symbol("#number", "number"), new State("308", Types.Transition), new State("309", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("307", Types.Transition), new State("308", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("210", Types.Initial)));
		transitions.add(new Transition(new Symbol("43", "+"), new State("310", Types.Initial), new State("311", Types.Transition)));
		transitions.add(new Transition(new Symbol("#+", "+"), new State("312", Types.Transition), new State("313", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("311", Types.Transition), new State("312", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("310", Types.Initial)));
		transitions.add(new Transition(new Symbol("42", "*"), new State("314", Types.Initial), new State("315", Types.Transition)));
		transitions.add(new Transition(new Symbol("#*", "*"), new State("316", Types.Transition), new State("317", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("315", Types.Transition), new State("316", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("314", Types.Initial)));
		transitions.add(new Transition(new Symbol("40", "("), new State("318", Types.Initial), new State("319", Types.Transition)));
		transitions.add(new Transition(new Symbol("#(", "("), new State("320", Types.Transition), new State("321", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("319", Types.Transition), new State("320", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("318", Types.Initial)));
		transitions.add(new Transition(new Symbol("41", ")"), new State("322", Types.Initial), new State("323", Types.Transition)));
		transitions.add(new Transition(new Symbol("#)", ")"), new State("324", Types.Transition), new State("325", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("323", Types.Transition), new State("324", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("322", Types.Initial)));
		return new NFA(initialState, finalStates, states, transitions);
	}
}
