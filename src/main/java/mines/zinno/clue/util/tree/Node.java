package mines.zinno.clue.util.tree;

/**
 * The {@link Node}<{@link T}> class is a subclass of the {@link Tree} class. It holds information on it's parent value
 *
 * @param <T> Value type
 */
public class Node<T> extends Tree<T> {
    
    private Tree<T> parent;
    
    public Node(T value, Tree<T> parent) {
        this(value, parent, null);
    }

    public Node(T value, Tree<T> parent, Node<T>[] children) {
        super(value, children);
        
        this.parent = parent;
    }

    /**
     * Set the parent
     */
    public void setParent(Tree<T> parent) {
        this.parent = parent;
    }

    /**
     * Get the parent
     */
    public Tree<T> getParent() {
        return parent;
    }
}
