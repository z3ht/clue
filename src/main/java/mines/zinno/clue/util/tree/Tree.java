package mines.zinno.clue.util.tree;

import mines.zinno.clue.shape.place.Place;

import java.util.*;
import java.util.function.Function;

/**
 * The {@link Tree}<{@link T}> utility holds {@link Node} children forming a tree
 *
 * @param <T> Type of stored value
 */
public class Tree<T> {

    private final T value;

    private Node<T>[] children;


    public Tree(T value) {
        this(value, null);
    }

    public Tree(T value, Node<T>[] children) {
        this.value = value;
        this.children = children;
    }

    /**
     * Populate the tree with new children in a breadth first order. Duplicate values will be removed based off
     * which has the highest {@link Costable#getCost()} or furthest down the tree
     *
     * @param populator The populator {@link Function}
     * @param maxSpread The maximum tree height (top to child)
     */
    public void populate(Function<Tree<T>, Node<T>[]> populator, int maxSpread) {
        LinkedHashMap<Tree<T>, Integer> queue = new LinkedHashMap<>();
        queue.put(this, maxSpread);

        // If the provided populator is null, return
        if(populator == null)
            return;

        // While the queue is not empty
        while(queue.entrySet().iterator().hasNext() && queue.size() != 0) {

            // Get and remove current value
            Map.Entry<Tree<T>, Integer> current = queue.entrySet().iterator().next();
            queue.remove(current.getKey());

            // If current value is null or parent no longer exists, continue
            if (current.getKey() == null || current.getKey().getValue() == null || current.getValue() <= 0
                    || current.getKey().getTop() == null)
                continue;

            // Populate current value's children
            current.getKey().setChildren(populator.apply(current.getKey()));

            // Continue if current value has no children
            if(current.getKey().getChildren() == null)
                continue;

            // Loop through each child
            for(int i = 0; i < current.getKey().getChildren().length; i++) {
                if(current.getKey().getChildren()[i] == null)
                    continue;

                // Another tree with the same value as this child
                Tree<T> existingPath = this.findPath(current.getKey().getChildren()[i]);

                if(existingPath == null)
                    continue;

                // Perform comparison
                int comparison = 0;
                if(current.getKey().value instanceof Costable)
                    comparison = current.getKey().getChildren()[i].getCost() - existingPath.getCost();

                if(comparison == 0)
                    comparison = current.getKey().getChildren()[i].getDepth() - existingPath.getDepth();

                // The new key has a higher cost than the old one
                if(comparison >= 0) {
                    current.getKey().getChildren()[i] = null;
                    continue;
                }

                // The old key has a higher cost than the old one

                // Set parent value of old key's children to null
                if(existingPath.getChildren() != null) {
                    for(Node<T> child : existingPath.getChildren()) {
                        if(child == null)
                            continue;

                        child.setParent(null);
                    }
                }

                // Set old key's value to null
                if(existingPath instanceof Node) {
                    Node<T>[] siblings = ((Node<T>) existingPath).getParent().getChildren();
                    for(int j = 0; j < siblings.length; j++) {
                        if(existingPath != siblings[j])
                            continue;

                        siblings[j] = null;
                    }
                }
            }

            // Add new children to the queue
            for(Tree<T> child : current.getKey().getChildren()) {
                if(child == null)
                    continue;

                queue.put(child, current.getValue() - 1);
            }
        }
    }

    /**
     * Get the cost of moving to this tree location
     *
     * @return {@link Integer}
     */
    public int getCost() {
        if(!(this instanceof Node) || ((Node<Place>) this).getParent() == null)
            return 0;

        return ((value instanceof Costable) ? ((Costable) value).getCost() : 1)
                + ((Node<T>) this).getParent().getCost();
    }

    /**
     * Find the path to a provided value
     *
     * @param value {@link T}
     * @return {@link Tree} holding value or null
     */
    public Tree<T> findPath(T value) {
        return this.findPath(new Tree<>(value));
    }

    protected Tree<T> findPath(Tree<T> target) {
        if(target == null || target.getValue() == null)
            return null;

        if(this != target && target.getValue().equals(this.getValue()))
            return this;

        if(this.getChildren() == null)
            return null;
        for(Tree<T> child : this.getChildren()) {
            if(child == null)
                continue;
            Tree<T> posReturnVal = child.findPath(target);
            if(posReturnVal != null)
                return posReturnVal;
        }
        return null;
    }

    /**
     * Get depth of this tree
     */
    public int getDepth() {
        if(!(this instanceof Node))
            return 0;
        return 1 + ((Node<T>) this).getParent().getDepth();
    }

    /**
     * Get the top value of this tree (or null)
     */
    public Tree<T> getTop() {
        if(!(this instanceof Node))
            return this;
        Tree<T> val = ((Node<T>) this).getParent();
        return (val == null) ? null : val.getTop();
    }

    /**
     * Retrieve all children of this tree
     */
    public Set<T> retrieveAllValues() {
        return this.retrieveAllValues(new HashSet<>());
    }

    private Set<T> retrieveAllValues(Set<Tree<T>> curPath) {
        Set<T> vals = new HashSet<>();
        if(this.getValue() != null)
            vals.add(this.getValue());

        if(curPath.contains(this))
            return vals;
        curPath.add(this);

        if(this.getChildren() == null || this.getChildren().length == 0) {
            return vals;
        }

        for(Tree<T> child : this.getChildren()) {
            if (child == null)
                continue;

            vals.addAll(child.retrieveAllValues(curPath));
        }

        return vals;
    }

    /**
     * Get this tree's value
     */
    public T getValue() {
        return value;
    }

    /**
     * Get local children of this tree
     */
    public Node<T>[] getChildren() {
        return children;
    }

    /**
     * Set local children of this tree
     */
    public void setChildren(Node<T>[] children) {
        this.children = children;
    }
}