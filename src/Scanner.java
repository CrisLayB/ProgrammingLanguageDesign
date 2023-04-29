import controllers.FilesCreator;
import models.*;

import java.util.List;
import java.util.ArrayList;

public class Scanner {
	public static void main(String[] args) {
		if (args.length == 0){
			System.out.println("Te falto ingresar un archivo en los argumentos");
			System.out.println("EJEMPLO: java Scanner file");
		}
		System.out.println("===> Scanner.java");

		// Obtener Automata 
		
		// Leer contenido del archivo 
		ArrayList<String> fileContent = FilesCreator.readFileContent(args[0]); 
		 
	}
	private static String scan(){
		return null;
 	}
	private static NFA megaAutomata() { 
		// Automata Generado 
		State initialState = new State(0, Types.Initial);
		List<State> finalStates = new ArrayList<State>();
		finalStates.add(new State("23", Types.Final));
		finalStates.add(new State("109", Types.Final));
		finalStates.add(new State("111", Types.Final));
		finalStates.add(new State("113", Types.Final));
		finalStates.add(new State("115", Types.Final));
		finalStates.add(new State("117", Types.Final));
		List<State> states = new ArrayList<State>();
		states.add(new State("23", Types.Final));
		states.add(new State("109", Types.Final));
		states.add(new State("111", Types.Final));
		states.add(new State("113", Types.Final));
		states.add(new State("115", Types.Final));
		states.add(new State("117", Types.Final));
		List<Transition> transitions = new ArrayList<Transition>();
		transitions.add(new Transition(new Symbol("32"), new State("1", Types.Transition), new State("2", Types.Transition)));
		transitions.add(new Transition(new Symbol("9"), new State("3", Types.Transition), new State("4", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("5", Types.Transition), new State("1", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("5", Types.Transition), new State("3", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("2", Types.Transition), new State("6", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("4", Types.Transition), new State("6", Types.Transition)));
		transitions.add(new Transition(new Symbol("10"), new State("7", Types.Transition), new State("8", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("9", Types.Initial), new State("5", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("9", Types.Initial), new State("7", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("6", Types.Transition), new State("10", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("8", Types.Transition), new State("10", Types.Transition)));
		transitions.add(new Transition(new Symbol("32"), new State("11", Types.Transition), new State("12", Types.Transition)));
		transitions.add(new Transition(new Symbol("9"), new State("13", Types.Transition), new State("14", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("15", Types.Transition), new State("11", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("15", Types.Transition), new State("13", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("12", Types.Transition), new State("16", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("14", Types.Transition), new State("16", Types.Transition)));
		transitions.add(new Transition(new Symbol("10"), new State("17", Types.Transition), new State("18", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("19", Types.Transition), new State("15", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("19", Types.Transition), new State("17", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("16", Types.Transition), new State("20", Types.Final)));
		transitions.add(new Transition(new Symbol("E"), new State("18", Types.Transition), new State("20", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("20", Types.Transition), new State("19", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("22", Types.Transition), new State("23", Types.Final)));
		transitions.add(new Transition(new Symbol("E"), new State("22", Types.Transition), new State("19", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("20", Types.Transition), new State("23", Types.Final)));
		transitions.add(new Transition(new Symbol("E"), new State("10", Types.Transition), new State("22", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("0", Types.Initial), new State("9", Types.Initial)));
		transitions.add(new Transition(new Symbol("65"), new State("24", Types.Transition), new State("25", Types.Transition)));
		transitions.add(new Transition(new Symbol("66"), new State("26", Types.Transition), new State("27", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("28", Types.Transition), new State("24", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("28", Types.Transition), new State("26", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("25", Types.Transition), new State("29", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("27", Types.Transition), new State("29", Types.Transition)));
		transitions.add(new Transition(new Symbol("67"), new State("30", Types.Transition), new State("31", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("32", Types.Transition), new State("28", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("32", Types.Transition), new State("30", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("29", Types.Transition), new State("33", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("31", Types.Transition), new State("33", Types.Transition)));
		transitions.add(new Transition(new Symbol("97"), new State("34", Types.Transition), new State("35", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("36", Types.Transition), new State("32", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("36", Types.Transition), new State("34", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("33", Types.Transition), new State("37", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("35", Types.Transition), new State("37", Types.Transition)));
		transitions.add(new Transition(new Symbol("98"), new State("38", Types.Transition), new State("39", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("40", Types.Transition), new State("36", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("40", Types.Transition), new State("38", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("37", Types.Transition), new State("41", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("39", Types.Transition), new State("41", Types.Transition)));
		transitions.add(new Transition(new Symbol("99"), new State("42", Types.Transition), new State("43", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("44", Types.Initial), new State("40", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("44", Types.Initial), new State("42", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("41", Types.Transition), new State("45", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("43", Types.Transition), new State("45", Types.Transition)));
		transitions.add(new Transition(new Symbol("65"), new State("46", Types.Transition), new State("47", Types.Transition)));
		transitions.add(new Transition(new Symbol("66"), new State("48", Types.Transition), new State("49", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("50", Types.Transition), new State("46", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("50", Types.Transition), new State("48", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("47", Types.Transition), new State("51", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("49", Types.Transition), new State("51", Types.Transition)));
		transitions.add(new Transition(new Symbol("67"), new State("52", Types.Transition), new State("53", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("54", Types.Transition), new State("50", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("54", Types.Transition), new State("52", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("51", Types.Transition), new State("55", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("53", Types.Transition), new State("55", Types.Transition)));
		transitions.add(new Transition(new Symbol("97"), new State("56", Types.Transition), new State("57", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("58", Types.Transition), new State("54", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("58", Types.Transition), new State("56", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("55", Types.Transition), new State("59", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("57", Types.Transition), new State("59", Types.Transition)));
		transitions.add(new Transition(new Symbol("98"), new State("60", Types.Transition), new State("61", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("62", Types.Transition), new State("58", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("62", Types.Transition), new State("60", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("59", Types.Transition), new State("63", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("61", Types.Transition), new State("63", Types.Transition)));
		transitions.add(new Transition(new Symbol("99"), new State("64", Types.Transition), new State("65", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("66", Types.Transition), new State("62", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("66", Types.Transition), new State("64", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("63", Types.Transition), new State("67", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("65", Types.Transition), new State("67", Types.Transition)));
		transitions.add(new Transition(new Symbol("48"), new State("68", Types.Transition), new State("69", Types.Transition)));
		transitions.add(new Transition(new Symbol("49"), new State("70", Types.Transition), new State("71", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("72", Types.Transition), new State("68", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("72", Types.Transition), new State("70", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("69", Types.Transition), new State("73", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("71", Types.Transition), new State("73", Types.Transition)));
		transitions.add(new Transition(new Symbol("50"), new State("74", Types.Transition), new State("75", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("76", Types.Transition), new State("72", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("76", Types.Transition), new State("74", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("73", Types.Transition), new State("77", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("75", Types.Transition), new State("77", Types.Transition)));
		transitions.add(new Transition(new Symbol("51"), new State("78", Types.Transition), new State("79", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("80", Types.Transition), new State("76", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("80", Types.Transition), new State("78", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("77", Types.Transition), new State("81", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("79", Types.Transition), new State("81", Types.Transition)));
		transitions.add(new Transition(new Symbol("52"), new State("82", Types.Transition), new State("83", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("84", Types.Transition), new State("80", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("84", Types.Transition), new State("82", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("81", Types.Transition), new State("85", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("83", Types.Transition), new State("85", Types.Transition)));
		transitions.add(new Transition(new Symbol("53"), new State("86", Types.Transition), new State("87", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("88", Types.Transition), new State("84", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("88", Types.Transition), new State("86", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("85", Types.Transition), new State("89", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("87", Types.Transition), new State("89", Types.Transition)));
		transitions.add(new Transition(new Symbol("54"), new State("90", Types.Transition), new State("91", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("92", Types.Transition), new State("88", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("92", Types.Transition), new State("90", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("89", Types.Transition), new State("93", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("91", Types.Transition), new State("93", Types.Transition)));
		transitions.add(new Transition(new Symbol("55"), new State("94", Types.Transition), new State("95", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("96", Types.Transition), new State("92", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("96", Types.Transition), new State("94", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("93", Types.Transition), new State("97", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("95", Types.Transition), new State("97", Types.Transition)));
		transitions.add(new Transition(new Symbol("56"), new State("98", Types.Transition), new State("99", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("100", Types.Transition), new State("96", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("100", Types.Transition), new State("98", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("97", Types.Transition), new State("101", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("99", Types.Transition), new State("101", Types.Transition)));
		transitions.add(new Transition(new Symbol("57"), new State("102", Types.Transition), new State("103", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("104", Types.Transition), new State("100", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("104", Types.Transition), new State("102", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("101", Types.Transition), new State("105", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("103", Types.Transition), new State("105", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("106", Types.Transition), new State("66", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("106", Types.Transition), new State("104", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("67", Types.Transition), new State("107", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("105", Types.Transition), new State("107", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("107", Types.Transition), new State("106", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("108", Types.Transition), new State("109", Types.Final)));
		transitions.add(new Transition(new Symbol("E"), new State("108", Types.Transition), new State("106", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("107", Types.Transition), new State("109", Types.Final)));
		transitions.add(new Transition(new Symbol("E"), new State("45", Types.Transition), new State("108", Types.Transition)));
		transitions.add(new Transition(new Symbol("E"), new State("0", Types.Initial), new State("44", Types.Initial)));
		transitions.add(new Transition(new Symbol("43"), new State("110", Types.Initial), new State("111", Types.Final)));
		transitions.add(new Transition(new Symbol("E"), new State("0", Types.Initial), new State("110", Types.Initial)));
		transitions.add(new Transition(new Symbol("42"), new State("112", Types.Initial), new State("113", Types.Final)));
		transitions.add(new Transition(new Symbol("E"), new State("0", Types.Initial), new State("112", Types.Initial)));
		transitions.add(new Transition(new Symbol("40"), new State("114", Types.Initial), new State("115", Types.Final)));
		transitions.add(new Transition(new Symbol("E"), new State("0", Types.Initial), new State("114", Types.Initial)));
		transitions.add(new Transition(new Symbol("41"), new State("116", Types.Initial), new State("117", Types.Final)));
		transitions.add(new Transition(new Symbol("E"), new State("0", Types.Initial), new State("116", Types.Initial)));
		return new NFA(initialState, finalStates, states, transitions);
	}
}
