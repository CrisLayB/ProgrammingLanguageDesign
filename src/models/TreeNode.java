package models;

public class TreeNode<E> {
    // -> Atributos
    protected E value;
    protected TreeNode<E> left, right;
    protected int pos = -1;

    // -> Constructores
    public TreeNode(){
        value = null;
        left = null;
        right = null;
    }
    
    public TreeNode(E value)
    {
        this.value = value;
        right = left = new TreeNode<E>();
        setLeft(left);
        setRight(right);
    }    

    public TreeNode(E value, int pos){
        this.pos = pos;
        this.value = value;
        right = left = new TreeNode<E>();
        setLeft(left);
        setRight(right);
    }

    public TreeNode(E value, TreeNode<E> left, TreeNode<E> right){
        this.value = value;
        if (left == null) { left = new TreeNode<E>(); }
        setLeft(left);
        if (right == null) { right = new TreeNode<E>(); }
        setRight(right);
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
}
