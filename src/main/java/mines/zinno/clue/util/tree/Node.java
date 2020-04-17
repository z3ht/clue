package mines.zinno.clue.util.tree;

public class Node<T> extends Tree<T> {
    
    private Tree<T> parent;
    
    public Node(T value, Tree<T> parent) {
        this(value, parent, null);
    }

    public Node(T value, Tree<T> parent, Node<T>[] children) {
        super(value, children);
        
        this.parent = parent;
    }

    public void setParent(Tree<T> parent) {
        this.parent = parent;
    }

    public Tree<T> getParent() {
        return parent;
    }
}
