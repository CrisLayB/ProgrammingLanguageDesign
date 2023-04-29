package models;

import java.util.ArrayList;

public class RuleContent {    
    private ArrayList<String> args;
    private ArrayList<String> namesBuffer;
    private ArrayList<String> actions;
    private String regex = "";
    // help for inserta a new Action
    private boolean addAction = false;
    private String bufferForAction = "";

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

    public String getRegex() {
        return regex;
    }

    public boolean getAddAction(){
        return addAction;
    }

    public ArrayList<String> getActions() {
        return actions;
    }

    public ArrayList<String> getNamesBuffer() {
        return namesBuffer;
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
    
    public void updateRegex(String expression){
        regex += expression;
    }

    public void addAction(){        
        actions.add(bufferForAction);
        bufferForAction = ""; // Reiniciar buffer para la accion
    }

    public void allowAddAction(){
        addAction = true;
    }

    public void stopAddAction(){
        addAction = false;
    }

    public void updateBufferForAction(String expression){        
        bufferForAction += (bufferForAction.length() == 0) ? expression : " " + expression;
    }

    public boolean emptyAction(){
        return (bufferForAction.length() == 0 && actions.isEmpty());
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
            information += "[" + namesBuffer.get(i) + "] CODE: " + actions.get(i);
            information += "\n";
        }

        return information;
    }
}
