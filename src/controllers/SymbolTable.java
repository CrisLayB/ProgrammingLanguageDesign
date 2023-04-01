package controllers;

import java.util.List;
import java.util.Arrays;

public class SymbolTable {
    public static List<String> tokens = Arrays.asList(
        "let",
        "letter",
        "number",
        ".",
        "(",
        ")",
        "*",
        "[",
        "]",
        "'",
        "-",
        "\"",
        "=",
        "|",
        "rule",
        "+",
        "?"
    );    
    public static List<String> startingTokens = Arrays.asList(
        "let",
        "rule"
    );
}
