import controllers.FilesCreator;
import models.*;

import java.util.List;
import java.util.ArrayList;

public class Scanner {
	private final static String NULL = "NULL";
	private final static String WHITESPACE = "WHITESPACE";
	private final static String ID = "ID";
	private final static String NUMBER = "NUMBER";
	private final static String IF = "IF";
	private final static String SEMICOLON = "SEMICOLON";
	private final static String ASSIGNOP = "ASSIGNOP";
	private final static String LT = "LT";
	private final static String EQ = "EQ";
	private final static String PLUS = "PLUS";
	private final static String MINUS = "MINUS";
	private final static String TIMES = "TIMES";
	private final static String DIV = "DIV";
	private final static String LPAREN = "LPAREN";
	private final static String RPAREN = "RPAREN";

	private static String scan(String token){
		if(token.equals("ws")){
			return WHITESPACE;
		}
		if(token.equals("id")){
			return ID;
		}
		if(token.equals("number")){
			return NUMBER;
		}
		if(token.equals("if")){
			return IF;
		}
		if(token.equals(";")){
			return SEMICOLON;
		}
		if(token.equals(":=")){
			return ASSIGNOP;
		}
		if(token.equals("<")){
			return LT;
		}
		if(token.equals("=")){
			return EQ;
		}
		if(token.equals("+")){
			return PLUS;
		}
		if(token.equals("-")){
			return MINUS;
		}
		if(token.equals("*")){
			return TIMES;
		}
		if(token.equals("/")){
			return DIV;
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
		                lexemas.add(c+"");
		        	    idDetected = "";
		        	    lexema = "";
		        	}
		        }
		        else if(idDetected.equals(result[1])) lexema += c;
		        else{ // Si ya no es igual entonces se detiene
		            lexemas.add(lexema);
		        	idDetected = result[1];
		            lexema = c + "";
		        }
		    } 
		} 
		for(String l : lexemas){
		    String[] resultsSim = automata.simulateMega(l);
		    System.out.println(resultsSim[0] + " -> " + resultsSim[1]);
		    results.add(resultsSim[0] + " -> Token: " + resultsSim[1] + "\n");
		    actions.add(resultsSim[0] + " -> Token Action: " + scan(resultsSim[1]) + "\n");
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
		finalStates.add(new State("23", Types.Final, "ws"));
		finalStates.add(new State("115", Types.Final, "id"));
		finalStates.add(new State("395", Types.Final, "number"));
		finalStates.add(new State("399", Types.Final, "if"));
		finalStates.add(new State("401", Types.Final, ";"));
		finalStates.add(new State("405", Types.Final, ":="));
		finalStates.add(new State("407", Types.Final, "<"));
		finalStates.add(new State("409", Types.Final, "="));
		finalStates.add(new State("411", Types.Final, "+"));
		finalStates.add(new State("413", Types.Final, "-"));
		finalStates.add(new State("415", Types.Final, "*"));
		finalStates.add(new State("417", Types.Final, "/"));
		finalStates.add(new State("419", Types.Final, "("));
		finalStates.add(new State("421", Types.Final, ")"));
		List<State> states = new ArrayList<State>();
		states.add(new State("23", Types.Final));
		states.add(new State("115", Types.Final));
		states.add(new State("395", Types.Final));
		states.add(new State("399", Types.Final));
		states.add(new State("401", Types.Final));
		states.add(new State("405", Types.Final));
		states.add(new State("407", Types.Final));
		states.add(new State("409", Types.Final));
		states.add(new State("411", Types.Final));
		states.add(new State("413", Types.Final));
		states.add(new State("415", Types.Final));
		states.add(new State("417", Types.Final));
		states.add(new State("419", Types.Final));
		states.add(new State("421", Types.Final));
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
		transitions.add(new Transition(new Symbol("€"), new State("22", Types.Transition), new State("23", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("22", Types.Transition), new State("19", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("20", Types.Transition), new State("23", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("10", Types.Transition), new State("22", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("9", Types.Initial)));
		transitions.add(new Transition(new Symbol("65", "id"), new State("24", Types.Transition), new State("25", Types.Transition)));
		transitions.add(new Transition(new Symbol("66", "id"), new State("26", Types.Transition), new State("27", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("28", Types.Transition), new State("24", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("28", Types.Transition), new State("26", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("25", Types.Transition), new State("29", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("27", Types.Transition), new State("29", Types.Transition)));
		transitions.add(new Transition(new Symbol("67", "id"), new State("30", Types.Transition), new State("31", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("32", Types.Transition), new State("28", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("32", Types.Transition), new State("30", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("29", Types.Transition), new State("33", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("31", Types.Transition), new State("33", Types.Transition)));
		transitions.add(new Transition(new Symbol("97", "id"), new State("34", Types.Transition), new State("35", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("36", Types.Transition), new State("32", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("36", Types.Transition), new State("34", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("33", Types.Transition), new State("37", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("35", Types.Transition), new State("37", Types.Transition)));
		transitions.add(new Transition(new Symbol("98", "id"), new State("38", Types.Transition), new State("39", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("40", Types.Transition), new State("36", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("40", Types.Transition), new State("38", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("37", Types.Transition), new State("41", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("39", Types.Transition), new State("41", Types.Transition)));
		transitions.add(new Transition(new Symbol("99", "id"), new State("42", Types.Transition), new State("43", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("44", Types.Initial), new State("40", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("44", Types.Initial), new State("42", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("41", Types.Transition), new State("45", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("43", Types.Transition), new State("45", Types.Transition)));
		transitions.add(new Transition(new Symbol("65", "id"), new State("46", Types.Transition), new State("47", Types.Transition)));
		transitions.add(new Transition(new Symbol("66", "id"), new State("48", Types.Transition), new State("49", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("50", Types.Transition), new State("46", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("50", Types.Transition), new State("48", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("47", Types.Transition), new State("51", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("49", Types.Transition), new State("51", Types.Transition)));
		transitions.add(new Transition(new Symbol("67", "id"), new State("52", Types.Transition), new State("53", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("54", Types.Transition), new State("50", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("54", Types.Transition), new State("52", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("51", Types.Transition), new State("55", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("53", Types.Transition), new State("55", Types.Transition)));
		transitions.add(new Transition(new Symbol("97", "id"), new State("56", Types.Transition), new State("57", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("58", Types.Transition), new State("54", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("58", Types.Transition), new State("56", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("55", Types.Transition), new State("59", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("57", Types.Transition), new State("59", Types.Transition)));
		transitions.add(new Transition(new Symbol("98", "id"), new State("60", Types.Transition), new State("61", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("62", Types.Transition), new State("58", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("62", Types.Transition), new State("60", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("59", Types.Transition), new State("63", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("61", Types.Transition), new State("63", Types.Transition)));
		transitions.add(new Transition(new Symbol("99", "id"), new State("64", Types.Transition), new State("65", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("66", Types.Transition), new State("62", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("66", Types.Transition), new State("64", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("63", Types.Transition), new State("67", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("65", Types.Transition), new State("67", Types.Transition)));
		transitions.add(new Transition(new Symbol("95", "id"), new State("68", Types.Transition), new State("69", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("69", Types.Transition), new State("68", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("70", Types.Transition), new State("71", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("70", Types.Transition), new State("68", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("69", Types.Transition), new State("71", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("72", Types.Transition), new State("66", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("72", Types.Transition), new State("70", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("67", Types.Transition), new State("73", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("71", Types.Transition), new State("73", Types.Transition)));
		transitions.add(new Transition(new Symbol("48", "id"), new State("74", Types.Transition), new State("75", Types.Transition)));
		transitions.add(new Transition(new Symbol("49", "id"), new State("76", Types.Transition), new State("77", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("78", Types.Transition), new State("74", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("78", Types.Transition), new State("76", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("75", Types.Transition), new State("79", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("77", Types.Transition), new State("79", Types.Transition)));
		transitions.add(new Transition(new Symbol("50", "id"), new State("80", Types.Transition), new State("81", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("82", Types.Transition), new State("78", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("82", Types.Transition), new State("80", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("79", Types.Transition), new State("83", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("81", Types.Transition), new State("83", Types.Transition)));
		transitions.add(new Transition(new Symbol("51", "id"), new State("84", Types.Transition), new State("85", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("86", Types.Transition), new State("82", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("86", Types.Transition), new State("84", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("83", Types.Transition), new State("87", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("85", Types.Transition), new State("87", Types.Transition)));
		transitions.add(new Transition(new Symbol("52", "id"), new State("88", Types.Transition), new State("89", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("90", Types.Transition), new State("86", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("90", Types.Transition), new State("88", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("87", Types.Transition), new State("91", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("89", Types.Transition), new State("91", Types.Transition)));
		transitions.add(new Transition(new Symbol("53", "id"), new State("92", Types.Transition), new State("93", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("94", Types.Transition), new State("90", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("94", Types.Transition), new State("92", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("91", Types.Transition), new State("95", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("93", Types.Transition), new State("95", Types.Transition)));
		transitions.add(new Transition(new Symbol("54", "id"), new State("96", Types.Transition), new State("97", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("98", Types.Transition), new State("94", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("98", Types.Transition), new State("96", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("95", Types.Transition), new State("99", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("97", Types.Transition), new State("99", Types.Transition)));
		transitions.add(new Transition(new Symbol("55", "id"), new State("100", Types.Transition), new State("101", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("102", Types.Transition), new State("98", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("102", Types.Transition), new State("100", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("99", Types.Transition), new State("103", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("101", Types.Transition), new State("103", Types.Transition)));
		transitions.add(new Transition(new Symbol("56", "id"), new State("104", Types.Transition), new State("105", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("106", Types.Transition), new State("102", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("106", Types.Transition), new State("104", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("103", Types.Transition), new State("107", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("105", Types.Transition), new State("107", Types.Transition)));
		transitions.add(new Transition(new Symbol("57", "id"), new State("108", Types.Transition), new State("109", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("110", Types.Transition), new State("106", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("110", Types.Transition), new State("108", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("107", Types.Transition), new State("111", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("109", Types.Transition), new State("111", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("112", Types.Transition), new State("72", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("112", Types.Transition), new State("110", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("73", Types.Transition), new State("113", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("111", Types.Transition), new State("113", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("113", Types.Transition), new State("112", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("114", Types.Transition), new State("115", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("114", Types.Transition), new State("112", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("113", Types.Transition), new State("115", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("45", Types.Transition), new State("114", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("44", Types.Initial)));
		transitions.add(new Transition(new Symbol("48", "number"), new State("116", Types.Transition), new State("117", Types.Transition)));
		transitions.add(new Transition(new Symbol("49", "number"), new State("118", Types.Transition), new State("119", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("120", Types.Transition), new State("116", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("120", Types.Transition), new State("118", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("117", Types.Transition), new State("121", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("119", Types.Transition), new State("121", Types.Transition)));
		transitions.add(new Transition(new Symbol("50", "number"), new State("122", Types.Transition), new State("123", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("124", Types.Transition), new State("120", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("124", Types.Transition), new State("122", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("121", Types.Transition), new State("125", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("123", Types.Transition), new State("125", Types.Transition)));
		transitions.add(new Transition(new Symbol("51", "number"), new State("126", Types.Transition), new State("127", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("128", Types.Transition), new State("124", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("128", Types.Transition), new State("126", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("125", Types.Transition), new State("129", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("127", Types.Transition), new State("129", Types.Transition)));
		transitions.add(new Transition(new Symbol("52", "number"), new State("130", Types.Transition), new State("131", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("132", Types.Transition), new State("128", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("132", Types.Transition), new State("130", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("129", Types.Transition), new State("133", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("131", Types.Transition), new State("133", Types.Transition)));
		transitions.add(new Transition(new Symbol("53", "number"), new State("134", Types.Transition), new State("135", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("136", Types.Transition), new State("132", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("136", Types.Transition), new State("134", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("133", Types.Transition), new State("137", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("135", Types.Transition), new State("137", Types.Transition)));
		transitions.add(new Transition(new Symbol("54", "number"), new State("138", Types.Transition), new State("139", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("140", Types.Transition), new State("136", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("140", Types.Transition), new State("138", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("137", Types.Transition), new State("141", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("139", Types.Transition), new State("141", Types.Transition)));
		transitions.add(new Transition(new Symbol("55", "number"), new State("142", Types.Transition), new State("143", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("144", Types.Transition), new State("140", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("144", Types.Transition), new State("142", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("141", Types.Transition), new State("145", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("143", Types.Transition), new State("145", Types.Transition)));
		transitions.add(new Transition(new Symbol("56", "number"), new State("146", Types.Transition), new State("147", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("148", Types.Transition), new State("144", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("148", Types.Transition), new State("146", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("145", Types.Transition), new State("149", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("147", Types.Transition), new State("149", Types.Transition)));
		transitions.add(new Transition(new Symbol("57", "number"), new State("150", Types.Transition), new State("151", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("152", Types.Initial), new State("148", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("152", Types.Initial), new State("150", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("149", Types.Transition), new State("153", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("151", Types.Transition), new State("153", Types.Transition)));
		transitions.add(new Transition(new Symbol("48"), new State("154", Types.Transition), new State("155", Types.Transition)));
		transitions.add(new Transition(new Symbol("49"), new State("156", Types.Transition), new State("157", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("158", Types.Transition), new State("154", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("158", Types.Transition), new State("156", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("155", Types.Transition), new State("159", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("157", Types.Transition), new State("159", Types.Transition)));
		transitions.add(new Transition(new Symbol("50"), new State("160", Types.Transition), new State("161", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("162", Types.Transition), new State("158", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("162", Types.Transition), new State("160", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("159", Types.Transition), new State("163", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("161", Types.Transition), new State("163", Types.Transition)));
		transitions.add(new Transition(new Symbol("51"), new State("164", Types.Transition), new State("165", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("166", Types.Transition), new State("162", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("166", Types.Transition), new State("164", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("163", Types.Transition), new State("167", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("165", Types.Transition), new State("167", Types.Transition)));
		transitions.add(new Transition(new Symbol("52"), new State("168", Types.Transition), new State("169", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("170", Types.Transition), new State("166", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("170", Types.Transition), new State("168", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("167", Types.Transition), new State("171", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("169", Types.Transition), new State("171", Types.Transition)));
		transitions.add(new Transition(new Symbol("53"), new State("172", Types.Transition), new State("173", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("174", Types.Transition), new State("170", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("174", Types.Transition), new State("172", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("171", Types.Transition), new State("175", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("173", Types.Transition), new State("175", Types.Transition)));
		transitions.add(new Transition(new Symbol("54"), new State("176", Types.Transition), new State("177", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("178", Types.Transition), new State("174", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("178", Types.Transition), new State("176", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("175", Types.Transition), new State("179", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("177", Types.Transition), new State("179", Types.Transition)));
		transitions.add(new Transition(new Symbol("55"), new State("180", Types.Transition), new State("181", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("182", Types.Transition), new State("178", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("182", Types.Transition), new State("180", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("179", Types.Transition), new State("183", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("181", Types.Transition), new State("183", Types.Transition)));
		transitions.add(new Transition(new Symbol("56"), new State("184", Types.Transition), new State("185", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("186", Types.Transition), new State("182", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("186", Types.Transition), new State("184", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("183", Types.Transition), new State("187", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("185", Types.Transition), new State("187", Types.Transition)));
		transitions.add(new Transition(new Symbol("57"), new State("188", Types.Transition), new State("189", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("190", Types.Transition), new State("186", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("190", Types.Transition), new State("188", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("187", Types.Transition), new State("191", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("189", Types.Transition), new State("191", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("191", Types.Transition), new State("190", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("200", Types.Transition), new State("201", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("200", Types.Transition), new State("190", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("191", Types.Transition), new State("201", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("153", Types.Transition), new State("200", Types.Transition)));
		transitions.add(new Transition(new Symbol("46", "number"), new State("202", Types.Transition), new State("203", Types.Transition)));
		transitions.add(new Transition(new Symbol("48", "number"), new State("204", Types.Transition), new State("205", Types.Transition)));
		transitions.add(new Transition(new Symbol("49", "number"), new State("206", Types.Transition), new State("207", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("208", Types.Transition), new State("204", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("208", Types.Transition), new State("206", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("205", Types.Transition), new State("209", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("207", Types.Transition), new State("209", Types.Transition)));
		transitions.add(new Transition(new Symbol("50", "number"), new State("210", Types.Transition), new State("211", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("212", Types.Transition), new State("208", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("212", Types.Transition), new State("210", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("209", Types.Transition), new State("213", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("211", Types.Transition), new State("213", Types.Transition)));
		transitions.add(new Transition(new Symbol("51", "number"), new State("214", Types.Transition), new State("215", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("216", Types.Transition), new State("212", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("216", Types.Transition), new State("214", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("213", Types.Transition), new State("217", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("215", Types.Transition), new State("217", Types.Transition)));
		transitions.add(new Transition(new Symbol("52", "number"), new State("218", Types.Transition), new State("219", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("220", Types.Transition), new State("216", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("220", Types.Transition), new State("218", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("217", Types.Transition), new State("221", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("219", Types.Transition), new State("221", Types.Transition)));
		transitions.add(new Transition(new Symbol("53", "number"), new State("222", Types.Transition), new State("223", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("224", Types.Transition), new State("220", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("224", Types.Transition), new State("222", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("221", Types.Transition), new State("225", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("223", Types.Transition), new State("225", Types.Transition)));
		transitions.add(new Transition(new Symbol("54", "number"), new State("226", Types.Transition), new State("227", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("228", Types.Transition), new State("224", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("228", Types.Transition), new State("226", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("225", Types.Transition), new State("229", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("227", Types.Transition), new State("229", Types.Transition)));
		transitions.add(new Transition(new Symbol("55", "number"), new State("230", Types.Transition), new State("231", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("232", Types.Transition), new State("228", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("232", Types.Transition), new State("230", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("229", Types.Transition), new State("233", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("231", Types.Transition), new State("233", Types.Transition)));
		transitions.add(new Transition(new Symbol("56", "number"), new State("234", Types.Transition), new State("235", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("236", Types.Transition), new State("232", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("236", Types.Transition), new State("234", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("233", Types.Transition), new State("237", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("235", Types.Transition), new State("237", Types.Transition)));
		transitions.add(new Transition(new Symbol("57", "number"), new State("238", Types.Transition), new State("239", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("240", Types.Transition), new State("236", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("240", Types.Transition), new State("238", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("237", Types.Transition), new State("241", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("239", Types.Transition), new State("241", Types.Transition)));
		transitions.add(new Transition(new Symbol("48"), new State("242", Types.Transition), new State("243", Types.Transition)));
		transitions.add(new Transition(new Symbol("49"), new State("244", Types.Transition), new State("245", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("246", Types.Transition), new State("242", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("246", Types.Transition), new State("244", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("243", Types.Transition), new State("247", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("245", Types.Transition), new State("247", Types.Transition)));
		transitions.add(new Transition(new Symbol("50"), new State("248", Types.Transition), new State("249", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("250", Types.Transition), new State("246", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("250", Types.Transition), new State("248", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("247", Types.Transition), new State("251", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("249", Types.Transition), new State("251", Types.Transition)));
		transitions.add(new Transition(new Symbol("51"), new State("252", Types.Transition), new State("253", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("254", Types.Transition), new State("250", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("254", Types.Transition), new State("252", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("251", Types.Transition), new State("255", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("253", Types.Transition), new State("255", Types.Transition)));
		transitions.add(new Transition(new Symbol("52"), new State("256", Types.Transition), new State("257", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("258", Types.Transition), new State("254", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("258", Types.Transition), new State("256", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("255", Types.Transition), new State("259", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("257", Types.Transition), new State("259", Types.Transition)));
		transitions.add(new Transition(new Symbol("53"), new State("260", Types.Transition), new State("261", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("262", Types.Transition), new State("258", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("262", Types.Transition), new State("260", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("259", Types.Transition), new State("263", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("261", Types.Transition), new State("263", Types.Transition)));
		transitions.add(new Transition(new Symbol("54"), new State("264", Types.Transition), new State("265", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("266", Types.Transition), new State("262", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("266", Types.Transition), new State("264", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("263", Types.Transition), new State("267", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("265", Types.Transition), new State("267", Types.Transition)));
		transitions.add(new Transition(new Symbol("55"), new State("268", Types.Transition), new State("269", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("270", Types.Transition), new State("266", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("270", Types.Transition), new State("268", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("267", Types.Transition), new State("271", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("269", Types.Transition), new State("271", Types.Transition)));
		transitions.add(new Transition(new Symbol("56"), new State("272", Types.Transition), new State("273", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("274", Types.Transition), new State("270", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("274", Types.Transition), new State("272", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("271", Types.Transition), new State("275", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("273", Types.Transition), new State("275", Types.Transition)));
		transitions.add(new Transition(new Symbol("57"), new State("276", Types.Transition), new State("277", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("278", Types.Transition), new State("274", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("278", Types.Transition), new State("276", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("275", Types.Transition), new State("279", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("277", Types.Transition), new State("279", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("279", Types.Transition), new State("278", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("288", Types.Transition), new State("289", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("288", Types.Transition), new State("278", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("279", Types.Transition), new State("289", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("241", Types.Transition), new State("288", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("203", Types.Transition), new State("240", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("290", Types.Transition), new State("291", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("292", Types.Transition), new State("202", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("292", Types.Transition), new State("290", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("289", Types.Transition), new State("293", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("291", Types.Transition), new State("293", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("201", Types.Transition), new State("292", Types.Transition)));
		transitions.add(new Transition(new Symbol("69", "number"), new State("294", Types.Transition), new State("295", Types.Transition)));
		transitions.add(new Transition(new Symbol("43", "number"), new State("296", Types.Transition), new State("297", Types.Transition)));
		transitions.add(new Transition(new Symbol("45", "number"), new State("298", Types.Transition), new State("299", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("300", Types.Transition), new State("296", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("300", Types.Transition), new State("298", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("297", Types.Transition), new State("301", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("299", Types.Transition), new State("301", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("302", Types.Transition), new State("303", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("304", Types.Transition), new State("300", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("304", Types.Transition), new State("302", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("301", Types.Transition), new State("305", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("303", Types.Transition), new State("305", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("295", Types.Transition), new State("304", Types.Transition)));
		transitions.add(new Transition(new Symbol("48", "number"), new State("306", Types.Transition), new State("307", Types.Transition)));
		transitions.add(new Transition(new Symbol("49", "number"), new State("308", Types.Transition), new State("309", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("310", Types.Transition), new State("306", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("310", Types.Transition), new State("308", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("307", Types.Transition), new State("311", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("309", Types.Transition), new State("311", Types.Transition)));
		transitions.add(new Transition(new Symbol("50", "number"), new State("312", Types.Transition), new State("313", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("314", Types.Transition), new State("310", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("314", Types.Transition), new State("312", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("311", Types.Transition), new State("315", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("313", Types.Transition), new State("315", Types.Transition)));
		transitions.add(new Transition(new Symbol("51", "number"), new State("316", Types.Transition), new State("317", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("318", Types.Transition), new State("314", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("318", Types.Transition), new State("316", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("315", Types.Transition), new State("319", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("317", Types.Transition), new State("319", Types.Transition)));
		transitions.add(new Transition(new Symbol("52", "number"), new State("320", Types.Transition), new State("321", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("322", Types.Transition), new State("318", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("322", Types.Transition), new State("320", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("319", Types.Transition), new State("323", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("321", Types.Transition), new State("323", Types.Transition)));
		transitions.add(new Transition(new Symbol("53", "number"), new State("324", Types.Transition), new State("325", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("326", Types.Transition), new State("322", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("326", Types.Transition), new State("324", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("323", Types.Transition), new State("327", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("325", Types.Transition), new State("327", Types.Transition)));
		transitions.add(new Transition(new Symbol("54", "number"), new State("328", Types.Transition), new State("329", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("330", Types.Transition), new State("326", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("330", Types.Transition), new State("328", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("327", Types.Transition), new State("331", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("329", Types.Transition), new State("331", Types.Transition)));
		transitions.add(new Transition(new Symbol("55", "number"), new State("332", Types.Transition), new State("333", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("334", Types.Transition), new State("330", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("334", Types.Transition), new State("332", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("331", Types.Transition), new State("335", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("333", Types.Transition), new State("335", Types.Transition)));
		transitions.add(new Transition(new Symbol("56", "number"), new State("336", Types.Transition), new State("337", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("338", Types.Transition), new State("334", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("338", Types.Transition), new State("336", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("335", Types.Transition), new State("339", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("337", Types.Transition), new State("339", Types.Transition)));
		transitions.add(new Transition(new Symbol("57", "number"), new State("340", Types.Transition), new State("341", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("342", Types.Transition), new State("338", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("342", Types.Transition), new State("340", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("339", Types.Transition), new State("343", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("341", Types.Transition), new State("343", Types.Transition)));
		transitions.add(new Transition(new Symbol("48"), new State("344", Types.Transition), new State("345", Types.Transition)));
		transitions.add(new Transition(new Symbol("49"), new State("346", Types.Transition), new State("347", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("348", Types.Transition), new State("344", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("348", Types.Transition), new State("346", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("345", Types.Transition), new State("349", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("347", Types.Transition), new State("349", Types.Transition)));
		transitions.add(new Transition(new Symbol("50"), new State("350", Types.Transition), new State("351", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("352", Types.Transition), new State("348", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("352", Types.Transition), new State("350", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("349", Types.Transition), new State("353", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("351", Types.Transition), new State("353", Types.Transition)));
		transitions.add(new Transition(new Symbol("51"), new State("354", Types.Transition), new State("355", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("356", Types.Transition), new State("352", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("356", Types.Transition), new State("354", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("353", Types.Transition), new State("357", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("355", Types.Transition), new State("357", Types.Transition)));
		transitions.add(new Transition(new Symbol("52"), new State("358", Types.Transition), new State("359", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("360", Types.Transition), new State("356", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("360", Types.Transition), new State("358", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("357", Types.Transition), new State("361", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("359", Types.Transition), new State("361", Types.Transition)));
		transitions.add(new Transition(new Symbol("53"), new State("362", Types.Transition), new State("363", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("364", Types.Transition), new State("360", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("364", Types.Transition), new State("362", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("361", Types.Transition), new State("365", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("363", Types.Transition), new State("365", Types.Transition)));
		transitions.add(new Transition(new Symbol("54"), new State("366", Types.Transition), new State("367", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("368", Types.Transition), new State("364", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("368", Types.Transition), new State("366", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("365", Types.Transition), new State("369", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("367", Types.Transition), new State("369", Types.Transition)));
		transitions.add(new Transition(new Symbol("55"), new State("370", Types.Transition), new State("371", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("372", Types.Transition), new State("368", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("372", Types.Transition), new State("370", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("369", Types.Transition), new State("373", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("371", Types.Transition), new State("373", Types.Transition)));
		transitions.add(new Transition(new Symbol("56"), new State("374", Types.Transition), new State("375", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("376", Types.Transition), new State("372", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("376", Types.Transition), new State("374", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("373", Types.Transition), new State("377", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("375", Types.Transition), new State("377", Types.Transition)));
		transitions.add(new Transition(new Symbol("57"), new State("378", Types.Transition), new State("379", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("380", Types.Transition), new State("376", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("380", Types.Transition), new State("378", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("377", Types.Transition), new State("381", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("379", Types.Transition), new State("381", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("381", Types.Transition), new State("380", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("390", Types.Transition), new State("391", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("390", Types.Transition), new State("380", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("381", Types.Transition), new State("391", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("343", Types.Transition), new State("390", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("305", Types.Transition), new State("342", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("392", Types.Transition), new State("393", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("394", Types.Transition), new State("294", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("394", Types.Transition), new State("392", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("391", Types.Transition), new State("395", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("393", Types.Transition), new State("395", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("293", Types.Transition), new State("394", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("152", Types.Initial)));
		transitions.add(new Transition(new Symbol("105", "if"), new State("396", Types.Initial), new State("397", Types.Transition)));
		transitions.add(new Transition(new Symbol("102", "if"), new State("398", Types.Transition), new State("399", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("397", Types.Transition), new State("398", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("396", Types.Initial)));
		transitions.add(new Transition(new Symbol("59", ";"), new State("400", Types.Initial), new State("401", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("400", Types.Initial)));
		transitions.add(new Transition(new Symbol("58", ":="), new State("402", Types.Initial), new State("403", Types.Transition)));
		transitions.add(new Transition(new Symbol("61", ":="), new State("404", Types.Transition), new State("405", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("403", Types.Transition), new State("404", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("402", Types.Initial)));
		transitions.add(new Transition(new Symbol("60", "<"), new State("406", Types.Initial), new State("407", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("406", Types.Initial)));
		transitions.add(new Transition(new Symbol("61", "="), new State("408", Types.Initial), new State("409", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("408", Types.Initial)));
		transitions.add(new Transition(new Symbol("43", "+"), new State("410", Types.Initial), new State("411", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("410", Types.Initial)));
		transitions.add(new Transition(new Symbol("45", "-"), new State("412", Types.Initial), new State("413", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("412", Types.Initial)));
		transitions.add(new Transition(new Symbol("42", "*"), new State("414", Types.Initial), new State("415", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("414", Types.Initial)));
		transitions.add(new Transition(new Symbol("47", "/"), new State("416", Types.Initial), new State("417", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("416", Types.Initial)));
		transitions.add(new Transition(new Symbol("40", "("), new State("418", Types.Initial), new State("419", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("418", Types.Initial)));
		transitions.add(new Transition(new Symbol("41", ")"), new State("420", Types.Initial), new State("421", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("420", Types.Initial)));
		return new NFA(initialState, finalStates, states, transitions);
	}
}
