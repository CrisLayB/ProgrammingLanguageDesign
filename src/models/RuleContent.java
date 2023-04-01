package models;

import java.util.ArrayList;

public class RuleContent {    
    private ArrayList<String> args;
    private ArrayList<PairData<String, String>> bufferAndActions;

    // ---> Constructurres
    public RuleContent(){
        bufferAndActions = new ArrayList<PairData<String, String>>();
    }

    public RuleContent(ArrayList<String> args){
        this.args = args;
        bufferAndActions = new ArrayList<PairData<String, String>>();
    }

    // --> Getters
    public ArrayList<String> getArgs() {
        return args;
    }

    public ArrayList<PairData<String, String>> getBufferAndActions() {
        return bufferAndActions;
    }

    // --> Metodos
    @Override
    public String toString() {
        String information = "";

        // Mostrar argumentos de funciones si es que existen
        if(args != null){
            System.out.println("Argumentos:");
            for (String string : args) {
                information += string + "\n";
            }
        }

        // Mostrar los buffers junto con sus acciones
        for (PairData<String, String> buffer : bufferAndActions) {
            String nameBuffer = buffer.first;
            String action = buffer.second;
            information += nameBuffer + " {" + action + "}\n";
        }

        return information;
    }
}
