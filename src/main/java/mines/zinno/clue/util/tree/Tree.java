package mines.zinno.clue.util.tree;

import mines.zinno.clue.shape.place.Place;

import java.util.*;
import java.util.function.Function;

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

    public void populate(Function<Tree<T>, Node<T>[]> populator, int maxSpread) {
        LinkedHashMap<Tree<T>, Integer> queue = new LinkedHashMap<>();
        queue.put(this, maxSpread);
        this.populate(this, queue, populator);
    }
    
    private void populate(Tree<T> top, LinkedHashMap<Tree<T>, Integer> queue, Function<Tree<T>, Node<T>[]> populator) {
        while(queue.entrySet().iterator().hasNext() && queue.size() != 0) {

            Map.Entry<Tree<T>, Integer> current = queue.entrySet().iterator().next();

            if(current.getKey() == null || current.getValue() <= 0 || populator == null)
                return;

            if(current.getKey().getTop() == null) {
                queue.remove(current.getKey());
                continue;
            }

            current.getKey().setChildren(populator.apply(current.getKey()));

            if(current.getKey().getChildren() == null)
                return;

            for(int i = 0; i < current.getKey().getChildren().length; i++) {
                if(current.getKey().getChildren()[i] == null)
                    continue;

                Tree<T> existingPath = top.findPath(current.getKey().getChildren()[i]);

                if(existingPath == null)
                    continue;

                int comparison = 0;
                if(current.getKey().value instanceof Costable)
                    comparison = current.getKey().getChildren()[i].getCost() - existingPath.getCost();

                if(comparison == 0)
                    comparison = current.getKey().getChildren()[i].getDepth() - existingPath.getDepth();

                if(comparison >= 0) {
                    current.getKey().getChildren()[i] = null;
                    continue;
                }

                if(existingPath.getChildren() != null) {
                    for(Node<T> child : existingPath.getChildren()) {
                        if(child == null)
                            continue;

                        child.setParent(null);
                    }
                }

                if(existingPath instanceof Node) {
                    Node<T>[] family = ((Node<T>) existingPath).getParent().getChildren();
                    for(int j = 0; j < family.length; j++) {
                        if(existingPath != family[j])
                            continue;

                        family[j] = null;
                    }
                }
            }

            for(Tree<T> child : current.getKey().getChildren()) {
                if(child == null)
                    continue;

                queue.put(child, current.getValue() - 1);
            }
            queue.remove(current.getKey());
        }
    }

    public int getCost() {
        if(!(this instanceof Node) || ((Node<Place>) this).getParent() == null)
            return 0;

        return ((value instanceof Costable) ? ((Costable) value).getCost() : 1)
                + ((Node<T>) this).getParent().getCost();
    }

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

    public int getDepth() {
        if(!(this instanceof Node))
            return 0;
        return 1 + ((Node<T>) this).getParent().getDepth();
    }

    public Tree<T> getTop() {
        if(!(this instanceof Node))
            return this;
        Tree<T> val = ((Node<T>) this).getParent();
        return (val == null) ? null : val.getTop();
    }

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
    
    public T getValue() {
        return value;
    }

    public Node<T>[] getChildren() {
        return children;
    }

    public void setChildren(Node<T>[] children) {
        this.children = children;
    }
}