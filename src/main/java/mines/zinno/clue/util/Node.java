package mines.zinno.clue.util;

public class Node<T> extends Tree<T> {
    
    private Tree<T> parent;
    
    public Node(T value, Tree<T> parent) {
        this(value, parent, null);
    }

    public Node(T value, Tree<T> parent, Tree<T>[] children) {
        super(value, children);
        
        this.parent = parent;
    }

    public Tree<T> getParent() {
        return parent;
    }
}
