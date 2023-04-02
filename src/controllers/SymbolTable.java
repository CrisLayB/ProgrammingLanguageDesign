package controllers;

import java.util.List;
import java.util.Arrays;

public class SymbolTable {
    public static List<String> tokens = Arrays.asList(
        "let",
        "rule",
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
        "+",
        "?"
    );
}
