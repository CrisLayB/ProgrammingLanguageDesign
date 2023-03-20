package models;

public class BinaryTree<E> {

    protected E value;
    protected BinaryTree<E> parent;
    protected BinaryTree<E> left, right;

    public BinaryTree(){
        value = null;
        parent = null;
        left = null;
        right = null;
    }
    
    public BinaryTree(E value)
    {
        // Assert.pre(value != null, "Tree values must be non-null.");
        this.value = value;
        right = left = new BinaryTree<E>();
        setLeft(left);
        setRight(right);
    }

    public BinaryTree(E value, BinaryTree<E> left, BinaryTree<E> right){
        // Assert.pre(value != null, "Tree values must be non-null.");
        this.value = value;
        if (left == null) { left = new BinaryTree<E>(); }
        setLeft(left);
        if (right == null) { right = new BinaryTree<E>(); }
        setRight(right);
    }

    public void setLeft(BinaryTree<E> newLeft)
    {
        // if (isEmpty()) return;
        if (left != null && left.parent() == this) left.setParent(null);
        left = newLeft;
        left.setParent(this);
    }

    public void setRight(BinaryTree<E> newRight){
        // if (isEmpty()) return;
        if (right != null && right.parent() == this) right.setParent(null);
        right = newRight;
        right.setParent(this);
    }

    public void setParent(BinaryTree<E> newParent){
        // if (!isEmpty()) {
        //     parent = newParent;
        // }
        parent = newParent;
    }

    public BinaryTree<E> left()
    {
        return left;
    }

    public E value()
    {
        return this.value;
    }

    public void setValue(E value)
    {
        this.value = value;
    }

    // public boolean isEmpty(){
    //     return false;
    // }

    public E parent(){
        return null;
    }
}
