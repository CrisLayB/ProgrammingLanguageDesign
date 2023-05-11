import controllers.FilesCreator;
import models.*;

import java.util.List;
import java.util.ArrayList;

public class Scanner {
	private final static String NULL = "NULL";
	private final static String ID = "ID";
	private final static String NUMBER = "NUMBER";
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
			return NULL;
		}
		if(token.equals("id")){
			return ID;
		}
		if(token.equals("number")){
			return NUMBER;
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
			// System.out.println(l);
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
		finalStates.add(new State("23", Types.Final, "ws"));
		finalStates.add(new State("115", Types.Final, "id"));
		finalStates.add(new State("397", Types.Final, "number"));
		finalStates.add(new State("399", Types.Final, ";"));
		finalStates.add(new State("403", Types.Final, ":="));
		finalStates.add(new State("405", Types.Final, "<"));
		finalStates.add(new State("407", Types.Final, "="));
		finalStates.add(new State("409", Types.Final, "+"));
		finalStates.add(new State("411", Types.Final, "-"));
		finalStates.add(new State("413", Types.Final, "*"));
		finalStates.add(new State("415", Types.Final, "/"));
		finalStates.add(new State("417", Types.Final, "("));
		finalStates.add(new State("419", Types.Final, ")"));
		List<State> states = new ArrayList<State>();
		states.add(new State("23", Types.Final));
		states.add(new State("115", Types.Final));
		states.add(new State("397", Types.Final));
		states.add(new State("399", Types.Final));
		states.add(new State("403", Types.Final));
		states.add(new State("405", Types.Final));
		states.add(new State("407", Types.Final));
		states.add(new State("409", Types.Final));
		states.add(new State("411", Types.Final));
		states.add(new State("413", Types.Final));
		states.add(new State("415", Types.Final));
		states.add(new State("417", Types.Final));
		states.add(new State("419", Types.Final));
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
		transitions.add(new Transition(new Symbol(".", "number"), new State("300", Types.Initial), new State("301", Types.Transition)));
		transitions.add(new Transition(new Symbol("69", "number"), new State("302", Types.Transition), new State("303", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("304", Types.Transition), new State("305", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("306", Types.Transition), new State("302", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("306", Types.Transition), new State("304", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("303", Types.Transition), new State("307", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("305", Types.Transition), new State("307", Types.Transition)));
		transitions.add(new Transition(new Symbol("48", "number"), new State("308", Types.Transition), new State("309", Types.Transition)));
		transitions.add(new Transition(new Symbol("49", "number"), new State("310", Types.Transition), new State("311", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("312", Types.Transition), new State("308", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("312", Types.Transition), new State("310", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("309", Types.Transition), new State("313", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("311", Types.Transition), new State("313", Types.Transition)));
		transitions.add(new Transition(new Symbol("50", "number"), new State("314", Types.Transition), new State("315", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("316", Types.Transition), new State("312", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("316", Types.Transition), new State("314", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("313", Types.Transition), new State("317", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("315", Types.Transition), new State("317", Types.Transition)));
		transitions.add(new Transition(new Symbol("51", "number"), new State("318", Types.Transition), new State("319", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("320", Types.Transition), new State("316", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("320", Types.Transition), new State("318", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("317", Types.Transition), new State("321", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("319", Types.Transition), new State("321", Types.Transition)));
		transitions.add(new Transition(new Symbol("52", "number"), new State("322", Types.Transition), new State("323", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("324", Types.Transition), new State("320", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("324", Types.Transition), new State("322", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("321", Types.Transition), new State("325", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("323", Types.Transition), new State("325", Types.Transition)));
		transitions.add(new Transition(new Symbol("53", "number"), new State("326", Types.Transition), new State("327", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("328", Types.Transition), new State("324", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("328", Types.Transition), new State("326", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("325", Types.Transition), new State("329", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("327", Types.Transition), new State("329", Types.Transition)));
		transitions.add(new Transition(new Symbol("54", "number"), new State("330", Types.Transition), new State("331", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("332", Types.Transition), new State("328", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("332", Types.Transition), new State("330", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("329", Types.Transition), new State("333", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("331", Types.Transition), new State("333", Types.Transition)));
		transitions.add(new Transition(new Symbol("55", "number"), new State("334", Types.Transition), new State("335", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("336", Types.Transition), new State("332", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("336", Types.Transition), new State("334", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("333", Types.Transition), new State("337", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("335", Types.Transition), new State("337", Types.Transition)));
		transitions.add(new Transition(new Symbol("56", "number"), new State("338", Types.Transition), new State("339", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("340", Types.Transition), new State("336", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("340", Types.Transition), new State("338", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("337", Types.Transition), new State("341", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("339", Types.Transition), new State("341", Types.Transition)));
		transitions.add(new Transition(new Symbol("57", "number"), new State("342", Types.Transition), new State("343", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("344", Types.Transition), new State("340", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("344", Types.Transition), new State("342", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("341", Types.Transition), new State("345", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("343", Types.Transition), new State("345", Types.Transition)));
		transitions.add(new Transition(new Symbol("48"), new State("346", Types.Transition), new State("347", Types.Transition)));
		transitions.add(new Transition(new Symbol("49"), new State("348", Types.Transition), new State("349", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("350", Types.Transition), new State("346", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("350", Types.Transition), new State("348", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("347", Types.Transition), new State("351", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("349", Types.Transition), new State("351", Types.Transition)));
		transitions.add(new Transition(new Symbol("50"), new State("352", Types.Transition), new State("353", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("354", Types.Transition), new State("350", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("354", Types.Transition), new State("352", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("351", Types.Transition), new State("355", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("353", Types.Transition), new State("355", Types.Transition)));
		transitions.add(new Transition(new Symbol("51"), new State("356", Types.Transition), new State("357", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("358", Types.Transition), new State("354", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("358", Types.Transition), new State("356", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("355", Types.Transition), new State("359", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("357", Types.Transition), new State("359", Types.Transition)));
		transitions.add(new Transition(new Symbol("52"), new State("360", Types.Transition), new State("361", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("362", Types.Transition), new State("358", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("362", Types.Transition), new State("360", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("359", Types.Transition), new State("363", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("361", Types.Transition), new State("363", Types.Transition)));
		transitions.add(new Transition(new Symbol("53"), new State("364", Types.Transition), new State("365", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("366", Types.Transition), new State("362", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("366", Types.Transition), new State("364", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("363", Types.Transition), new State("367", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("365", Types.Transition), new State("367", Types.Transition)));
		transitions.add(new Transition(new Symbol("54"), new State("368", Types.Transition), new State("369", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("370", Types.Transition), new State("366", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("370", Types.Transition), new State("368", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("367", Types.Transition), new State("371", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("369", Types.Transition), new State("371", Types.Transition)));
		transitions.add(new Transition(new Symbol("55"), new State("372", Types.Transition), new State("373", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("374", Types.Transition), new State("370", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("374", Types.Transition), new State("372", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("371", Types.Transition), new State("375", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("373", Types.Transition), new State("375", Types.Transition)));
		transitions.add(new Transition(new Symbol("56"), new State("376", Types.Transition), new State("377", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("378", Types.Transition), new State("374", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("378", Types.Transition), new State("376", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("375", Types.Transition), new State("379", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("377", Types.Transition), new State("379", Types.Transition)));
		transitions.add(new Transition(new Symbol("57"), new State("380", Types.Transition), new State("381", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("382", Types.Transition), new State("378", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("382", Types.Transition), new State("380", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("379", Types.Transition), new State("383", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("381", Types.Transition), new State("383", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("383", Types.Transition), new State("382", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("392", Types.Transition), new State("393", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("392", Types.Transition), new State("382", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("383", Types.Transition), new State("393", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("345", Types.Transition), new State("392", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("307", Types.Transition), new State("344", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("394", Types.Transition), new State("395", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("396", Types.Transition), new State("306", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("396", Types.Transition), new State("394", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("393", Types.Transition), new State("397", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("395", Types.Transition), new State("397", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("301", Types.Transition), new State("396", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("300", Types.Initial)));
		transitions.add(new Transition(new Symbol("59", ";"), new State("398", Types.Initial), new State("399", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("398", Types.Initial)));
		transitions.add(new Transition(new Symbol("58", ":="), new State("400", Types.Initial), new State("401", Types.Transition)));
		transitions.add(new Transition(new Symbol("61", ":="), new State("402", Types.Transition), new State("403", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("401", Types.Transition), new State("402", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("400", Types.Initial)));
		transitions.add(new Transition(new Symbol("60", "<"), new State("404", Types.Initial), new State("405", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("404", Types.Initial)));
		transitions.add(new Transition(new Symbol("61", "="), new State("406", Types.Initial), new State("407", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("406", Types.Initial)));
		transitions.add(new Transition(new Symbol("43", "+"), new State("408", Types.Initial), new State("409", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("408", Types.Initial)));
		transitions.add(new Transition(new Symbol("45", "-"), new State("410", Types.Initial), new State("411", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("410", Types.Initial)));
		transitions.add(new Transition(new Symbol("42", "*"), new State("412", Types.Initial), new State("413", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("412", Types.Initial)));
		transitions.add(new Transition(new Symbol("47", "/"), new State("414", Types.Initial), new State("415", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("414", Types.Initial)));
		transitions.add(new Transition(new Symbol("40", "("), new State("416", Types.Initial), new State("417", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("416", Types.Initial)));
		transitions.add(new Transition(new Symbol("41", ")"), new State("418", Types.Initial), new State("419", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("418", Types.Initial)));
		return new NFA(initialState, finalStates, states, transitions);
	}
}
