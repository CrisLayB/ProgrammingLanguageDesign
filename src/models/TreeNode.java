package models;

import java.util.List;
import java.util.ArrayList;

public class TreeNode<E extends Comparable<?>> {
    // -> Atributos
    protected E value;
    protected TreeNode<E> left, right;
    protected int pos = -1;
    protected List<Integer> firstpos, lastpos;

    // -> Constructores
    public TreeNode(){
        value = null;
        firstpos = lastpos = new ArrayList<>();
    }
    
    public TreeNode(E value)
    {
        this.value = value;
        firstpos = lastpos = new ArrayList<>();
    }    

    public TreeNode(E value, int pos){
        this.pos = pos;
        this.value = value;
        firstpos = lastpos = new ArrayList<>();
    }

    public TreeNode(E value, TreeNode<E> left, TreeNode<E> right){
        this.value = value;
        if(left != null) setLeft(left);
        if(right != null) setRight(right);
        firstpos = lastpos = new ArrayList<>();
    }

    // -> Getters
    public E value()
    {
        return this.value;
    }

    public TreeNode<E> left()
    {
        return left;
    }

    public TreeNode<E> right(){
        return right;
    }

    public int getPos() {
        return pos;
    }

    public List<Integer> getFirstPoses() {
        return firstpos;
    }

    public List<Integer> getLastPoses() {
        return lastpos;
    }

    // -> Setters
    public void setLeft(TreeNode<E> newLeft)
    {
        left = newLeft;
    }

    public void setRight(TreeNode<E> newRight){
        right = newRight;
    }

    public void setValue(E value)
    {
        this.value = value;
    }

    // -> Metodos
    public String traverse(){
        String information = "";

        if (left != null) information += left.traverse();
        information += value.toString() + "\n";
        if (right != null) information +=  right.traverse();

        return information;
    }

    public boolean isNull(){
        return (value == null);
    }

    public boolean invalidPos(){
        return (pos < 0);
    }
}
