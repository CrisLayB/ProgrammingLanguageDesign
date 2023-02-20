package controllers;

import java.util.Stack;

import models.Symbol;
import models.NFA;

public class ThompsonAlgorithm {
    public static NFA constructNFA(Symbol[] symbols){
        // Stack<NFA> stack = new Stack<NFA>();

        for (int i = 0; i < symbols.length; i++) {
            System.out.println(symbols[i].getId());
        }
                
        return null;
    }
}
