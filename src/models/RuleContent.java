package models;

import java.util.ArrayList;

public class RuleContent {    
    private ArrayList<String> args;
    private ArrayList<String> namesBuffer;
    private ArrayList<String> actions;

    // ---> Constructurres
    public RuleContent(){
        namesBuffer = new ArrayList<>();
        actions = new ArrayList<>();
    }

    public RuleContent(ArrayList<String> args){
        this.args = args;
        namesBuffer = new ArrayList<>();
        actions = new ArrayList<>();
    }

    // --> Getters
    public ArrayList<String> getArgs() {
        return args;
    }
    
    // --> Setters
    public void setArgs(ArrayList<String> args) {
        this.args = args;
    }

    // --> Metodos
    public void addNameBuffer(String buffer){
        if(namesBuffer.contains(buffer)) return; // Para evitar repetir
        namesBuffer.add(buffer);
    }

    public void addAction(String action){
        if(actions.contains(action)) return;
        actions.add(action);
    }
    
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

        // Mostrar los buffers con sus respectivas acciones
        for (int i = 0; i < namesBuffer.size(); i++) {            
            information += (actions.get(i) == null) 
                ? namesBuffer.get(i) + "\n" 
                : namesBuffer.get(i) + " & " + actions.get(i) + "\n";
        }

        return information;
    }
}
