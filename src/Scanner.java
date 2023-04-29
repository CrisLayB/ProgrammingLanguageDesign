import controllers.FilesCreator;
import models.*;

import java.util.List;
import java.util.ArrayList;

public class Scanner {
	private final static String NULL = "NULL";
	private final static String WHITESPACE = "WHITESPACE";
	private final static String ID = "ID";
	private final static String NUMBER = "NUMBER";
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
		}
		System.out.println("===> Scanner.java");

		// Obtener Automata 
		NFA automata = megaAutomata(); 
		
		// Leer contenido del archivo 
		ArrayList<String> fileContent = FilesCreator.readFileContent(args[0]); 
		ArrayList<String> results = new ArrayList<String>(); 
		for (String string : fileContent) { 
		    for (int i = 0; i < string.length(); i++) {
		        char c = string.charAt(i); 
		        int ascii = (int)c; 
		        String[] result = automata.simulateMega(ascii+"");
		        results.add("[(" + result[0] + ", " + c + " - Token: " + result[1] + "]");
		    } 
		} 
	}

	private static NFA megaAutomata() { 
		// Automata Generado 
		State initialState = new State(0, Types.Initial);
		List<State> finalStates = new ArrayList<State>();
		finalStates.add(new State("23", Types.Final));
		finalStates.add(new State("109", Types.Final));
		finalStates.add(new State("391", Types.Final));
		finalStates.add(new State("393", Types.Final));
		finalStates.add(new State("395", Types.Final));
		finalStates.add(new State("397", Types.Final));
		finalStates.add(new State("399", Types.Final));
		finalStates.add(new State("401", Types.Final));
		finalStates.add(new State("403", Types.Final));
		List<State> states = new ArrayList<State>();
		states.add(new State("23", Types.Final));
		states.add(new State("109", Types.Final));
		states.add(new State("391", Types.Final));
		states.add(new State("393", Types.Final));
		states.add(new State("395", Types.Final));
		states.add(new State("397", Types.Final));
		states.add(new State("399", Types.Final));
		states.add(new State("401", Types.Final));
		states.add(new State("403", Types.Final));
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
		transitions.add(new Transition(new Symbol("48", "id"), new State("68", Types.Transition), new State("69", Types.Transition)));
		transitions.add(new Transition(new Symbol("49", "id"), new State("70", Types.Transition), new State("71", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("72", Types.Transition), new State("68", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("72", Types.Transition), new State("70", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("69", Types.Transition), new State("73", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("71", Types.Transition), new State("73", Types.Transition)));
		transitions.add(new Transition(new Symbol("50", "id"), new State("74", Types.Transition), new State("75", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("76", Types.Transition), new State("72", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("76", Types.Transition), new State("74", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("73", Types.Transition), new State("77", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("75", Types.Transition), new State("77", Types.Transition)));
		transitions.add(new Transition(new Symbol("51", "id"), new State("78", Types.Transition), new State("79", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("80", Types.Transition), new State("76", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("80", Types.Transition), new State("78", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("77", Types.Transition), new State("81", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("79", Types.Transition), new State("81", Types.Transition)));
		transitions.add(new Transition(new Symbol("52", "id"), new State("82", Types.Transition), new State("83", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("84", Types.Transition), new State("80", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("84", Types.Transition), new State("82", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("81", Types.Transition), new State("85", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("83", Types.Transition), new State("85", Types.Transition)));
		transitions.add(new Transition(new Symbol("53", "id"), new State("86", Types.Transition), new State("87", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("88", Types.Transition), new State("84", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("88", Types.Transition), new State("86", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("85", Types.Transition), new State("89", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("87", Types.Transition), new State("89", Types.Transition)));
		transitions.add(new Transition(new Symbol("54", "id"), new State("90", Types.Transition), new State("91", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("92", Types.Transition), new State("88", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("92", Types.Transition), new State("90", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("89", Types.Transition), new State("93", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("91", Types.Transition), new State("93", Types.Transition)));
		transitions.add(new Transition(new Symbol("55", "id"), new State("94", Types.Transition), new State("95", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("96", Types.Transition), new State("92", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("96", Types.Transition), new State("94", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("93", Types.Transition), new State("97", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("95", Types.Transition), new State("97", Types.Transition)));
		transitions.add(new Transition(new Symbol("56", "id"), new State("98", Types.Transition), new State("99", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("100", Types.Transition), new State("96", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("100", Types.Transition), new State("98", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("97", Types.Transition), new State("101", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("99", Types.Transition), new State("101", Types.Transition)));
		transitions.add(new Transition(new Symbol("57", "id"), new State("102", Types.Transition), new State("103", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("104", Types.Transition), new State("100", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("104", Types.Transition), new State("102", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("101", Types.Transition), new State("105", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("103", Types.Transition), new State("105", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("106", Types.Transition), new State("66", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("106", Types.Transition), new State("104", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("67", Types.Transition), new State("107", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("105", Types.Transition), new State("107", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("107", Types.Transition), new State("106", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("108", Types.Transition), new State("109", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("108", Types.Transition), new State("106", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("107", Types.Transition), new State("109", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("45", Types.Transition), new State("108", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("44", Types.Initial)));
		transitions.add(new Transition(new Symbol(".", "number"), new State("294", Types.Initial), new State("295", Types.Transition)));
		transitions.add(new Transition(new Symbol("69", "number"), new State("296", Types.Transition), new State("297", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("298", Types.Transition), new State("299", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("300", Types.Transition), new State("296", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("300", Types.Transition), new State("298", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("297", Types.Transition), new State("301", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("299", Types.Transition), new State("301", Types.Transition)));
		transitions.add(new Transition(new Symbol("48", "number"), new State("302", Types.Transition), new State("303", Types.Transition)));
		transitions.add(new Transition(new Symbol("49", "number"), new State("304", Types.Transition), new State("305", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("306", Types.Transition), new State("302", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("306", Types.Transition), new State("304", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("303", Types.Transition), new State("307", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("305", Types.Transition), new State("307", Types.Transition)));
		transitions.add(new Transition(new Symbol("50", "number"), new State("308", Types.Transition), new State("309", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("310", Types.Transition), new State("306", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("310", Types.Transition), new State("308", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("307", Types.Transition), new State("311", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("309", Types.Transition), new State("311", Types.Transition)));
		transitions.add(new Transition(new Symbol("51", "number"), new State("312", Types.Transition), new State("313", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("314", Types.Transition), new State("310", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("314", Types.Transition), new State("312", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("311", Types.Transition), new State("315", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("313", Types.Transition), new State("315", Types.Transition)));
		transitions.add(new Transition(new Symbol("52", "number"), new State("316", Types.Transition), new State("317", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("318", Types.Transition), new State("314", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("318", Types.Transition), new State("316", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("315", Types.Transition), new State("319", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("317", Types.Transition), new State("319", Types.Transition)));
		transitions.add(new Transition(new Symbol("53", "number"), new State("320", Types.Transition), new State("321", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("322", Types.Transition), new State("318", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("322", Types.Transition), new State("320", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("319", Types.Transition), new State("323", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("321", Types.Transition), new State("323", Types.Transition)));
		transitions.add(new Transition(new Symbol("54", "number"), new State("324", Types.Transition), new State("325", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("326", Types.Transition), new State("322", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("326", Types.Transition), new State("324", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("323", Types.Transition), new State("327", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("325", Types.Transition), new State("327", Types.Transition)));
		transitions.add(new Transition(new Symbol("55", "number"), new State("328", Types.Transition), new State("329", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("330", Types.Transition), new State("326", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("330", Types.Transition), new State("328", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("327", Types.Transition), new State("331", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("329", Types.Transition), new State("331", Types.Transition)));
		transitions.add(new Transition(new Symbol("56", "number"), new State("332", Types.Transition), new State("333", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("334", Types.Transition), new State("330", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("334", Types.Transition), new State("332", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("331", Types.Transition), new State("335", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("333", Types.Transition), new State("335", Types.Transition)));
		transitions.add(new Transition(new Symbol("57", "number"), new State("336", Types.Transition), new State("337", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("338", Types.Transition), new State("334", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("338", Types.Transition), new State("336", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("335", Types.Transition), new State("339", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("337", Types.Transition), new State("339", Types.Transition)));
		transitions.add(new Transition(new Symbol("48"), new State("340", Types.Transition), new State("341", Types.Transition)));
		transitions.add(new Transition(new Symbol("49"), new State("342", Types.Transition), new State("343", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("344", Types.Transition), new State("340", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("344", Types.Transition), new State("342", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("341", Types.Transition), new State("345", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("343", Types.Transition), new State("345", Types.Transition)));
		transitions.add(new Transition(new Symbol("50"), new State("346", Types.Transition), new State("347", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("348", Types.Transition), new State("344", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("348", Types.Transition), new State("346", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("345", Types.Transition), new State("349", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("347", Types.Transition), new State("349", Types.Transition)));
		transitions.add(new Transition(new Symbol("51"), new State("350", Types.Transition), new State("351", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("352", Types.Transition), new State("348", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("352", Types.Transition), new State("350", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("349", Types.Transition), new State("353", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("351", Types.Transition), new State("353", Types.Transition)));
		transitions.add(new Transition(new Symbol("52"), new State("354", Types.Transition), new State("355", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("356", Types.Transition), new State("352", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("356", Types.Transition), new State("354", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("353", Types.Transition), new State("357", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("355", Types.Transition), new State("357", Types.Transition)));
		transitions.add(new Transition(new Symbol("53"), new State("358", Types.Transition), new State("359", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("360", Types.Transition), new State("356", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("360", Types.Transition), new State("358", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("357", Types.Transition), new State("361", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("359", Types.Transition), new State("361", Types.Transition)));
		transitions.add(new Transition(new Symbol("54"), new State("362", Types.Transition), new State("363", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("364", Types.Transition), new State("360", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("364", Types.Transition), new State("362", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("361", Types.Transition), new State("365", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("363", Types.Transition), new State("365", Types.Transition)));
		transitions.add(new Transition(new Symbol("55"), new State("366", Types.Transition), new State("367", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("368", Types.Transition), new State("364", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("368", Types.Transition), new State("366", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("365", Types.Transition), new State("369", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("367", Types.Transition), new State("369", Types.Transition)));
		transitions.add(new Transition(new Symbol("56"), new State("370", Types.Transition), new State("371", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("372", Types.Transition), new State("368", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("372", Types.Transition), new State("370", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("369", Types.Transition), new State("373", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("371", Types.Transition), new State("373", Types.Transition)));
		transitions.add(new Transition(new Symbol("57"), new State("374", Types.Transition), new State("375", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("376", Types.Transition), new State("372", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("376", Types.Transition), new State("374", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("373", Types.Transition), new State("377", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("375", Types.Transition), new State("377", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("377", Types.Transition), new State("376", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("386", Types.Transition), new State("387", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("386", Types.Transition), new State("376", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("377", Types.Transition), new State("387", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("339", Types.Transition), new State("386", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("301", Types.Transition), new State("338", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("388", Types.Transition), new State("389", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("390", Types.Transition), new State("300", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("390", Types.Transition), new State("388", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("387", Types.Transition), new State("391", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("389", Types.Transition), new State("391", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("295", Types.Transition), new State("390", Types.Transition)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("294", Types.Initial)));
		transitions.add(new Transition(new Symbol("43", "43"), new State("392", Types.Initial), new State("393", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("392", Types.Initial)));
		transitions.add(new Transition(new Symbol("45", "45"), new State("394", Types.Initial), new State("395", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("394", Types.Initial)));
		transitions.add(new Transition(new Symbol("42", "42"), new State("396", Types.Initial), new State("397", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("396", Types.Initial)));
		transitions.add(new Transition(new Symbol("47", "47"), new State("398", Types.Initial), new State("399", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("398", Types.Initial)));
		transitions.add(new Transition(new Symbol("40", "40"), new State("400", Types.Initial), new State("401", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("400", Types.Initial)));
		transitions.add(new Transition(new Symbol("41", "41"), new State("402", Types.Initial), new State("403", Types.Final)));
		transitions.add(new Transition(new Symbol("€"), new State("0", Types.Initial), new State("402", Types.Initial)));
		return new NFA(initialState, finalStates, states, transitions);
	}
}
