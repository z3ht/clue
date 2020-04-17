package mines.zinno.clue.util.tree;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class Tree<T> {

    private T value;
    private Node<T>[] children;

    public Tree(T value) {
        this(value, null);
    }

    public Tree(T value, Node<T>[] children) {
        this.value = value;
        this.children = children;
    }

    public void populate(Function<Tree<T>, Node<T>[]> populator, int maxSpread) {
        this.populate(populator, null, maxSpread);
    }

    public void populate(Function<Tree<T>, Node<T>[]> populator, Comparator<Tree<T>> copyRemover, int maxSpread) {
        this.populate(this, populator, copyRemover, maxSpread);
    }
    
    private void populate(Tree<T> top, Function<Tree<T>, Node<T>[]> populator, Comparator<Tree<T>> copyRemover, int spread) {
        if(spread <= 0 || populator == null)
            return;

        if(this.getChildren() == null || this.getChildren().length == 0) {
            this.setChildren(populator.apply(this));

            if(this.getChildren() == null)
                return;

            for(int i = 0; i < this.getChildren().length; i++) {
                if(this.getChildren()[i] == null)
                    continue;

                Tree<T> posPath = top.findPath(this.getChildren()[i]);

                if(posPath == null)
                    continue;

                int comparison = copyRemover.compare(this.getChildren()[i], posPath);
                if(comparison == 0)
                    comparison = (this.getChildren()[i].getDepth() > posPath.getDepth()) ? 1 : -1;
                if(comparison > 0) {
                    this.getChildren()[i] = null;
                } else {
                    if(posPath instanceof Node)
                        ((Node<T>) posPath).setParent(this);
                    this.getChildren()[i].setChildren(posPath.getChildren());
                    posPath.setChildren(null);
                }
            }
        }
        
        for(Tree<T> child : this.getChildren()) {
            if(child == null)
                continue;
            
            child.populate(top, populator, copyRemover, spread-1);
        }
    }

    public Tree<T> findPath(T value) {
        return this.findPath(new Tree<T>(value));
    }

    protected Tree<T> findPath(Tree<T> curTree) {
        return this.findPath(curTree, new HashSet<>());
    }
    
    private Tree<T> findPath(Tree<T> curTree, Set<Tree<T>> curPath) {
        if(this.getChildren() == null || curTree == null || curTree.getValue() == null)
            return null;
        
        if(curPath.contains(this))
            return null;
        curPath.add(this);

        if(this != curTree && curTree.getValue().equals(this.getValue()))
            return this;

        for(Tree<T> child : this.getChildren()) {

            if(child == null)
                continue;

            if(child.getChildren() == null || child.getChildren().length == 0)
                continue;

            Tree<T> posReturnVal = child.findPath(curTree, curPath);
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