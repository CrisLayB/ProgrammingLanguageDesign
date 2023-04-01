package controllers;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Tokenizer {
    // Tokends a definir
    /*
     * let
     * letter
     * number
     * .
     * (
     * )
     * *
     * [
     * ]
     * '
     * -
     * "
     * =
     * |
     * rule tokens
     * +
     * ?
     */
    private List<String> tokens = Arrays.asList("let");
    private Map<String, String> installId = new HashMap<String, String>();
    private ArrayList<String> code;
        
    public Tokenizer(ArrayList<String> code){
        this.code = code;
    }
    
    public void process(){
        for (String string : code) {
            for (int i = 0; i < string.length(); i++) {
                char c = string.charAt(i);
                
            }
        }
    }
}
