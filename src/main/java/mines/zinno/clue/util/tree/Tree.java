package mines.zinno.clue.util.tree;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class Tree<T> {
    
    private T value;
    private Tree<T>[] children;

    public Tree(T value) {
        this(value, null);
    }

    public Tree(T value, Tree<T>[] children) {
        this.value = value;
        this.children = children;
    }

    public void populate(Function<Tree<T>, Node<T>[]> populator, int maxSpread) {
        this.populate(populator, null, maxSpread);
    }
    
    public void populate(Function<Tree<T>, Node<T>[]> populator, BiPredicate<Tree<T>, Tree<T>> copyRemover, int maxSpread) {
        this.populate(this, populator, copyRemover, maxSpread);
    }
    
    private void populate(Tree<T> top, Function<Tree<T>, Node<T>[]> populator, BiPredicate<Tree<T>, Tree<T>> copyRemover, int spread) {
        if(spread <= 0 || populator == null)
            return;

        if(this.getChildren() == null || this.getChildren().length == 0) {
            this.setChildren(populator.apply(this));

            if(this.getChildren() == null)
                return;

            for(int i = 0; i < this.getChildren().length; i++) {
                if(this.getChildren()[i] == null)
                    continue;
                this.getChildren()[i] = top.findBestPath(this.getChildren()[i].getValue(), copyRemover);
            }
        }
        
        for(Tree<T> child : this.getChildren()) {
            if(child == null)
                continue;
            
            child.populate(top, populator, copyRemover, spread-1);
        }
    }

    public Tree<T> findBestPath(T target) {
        return this.findBestPath(target, null);
    }
    
    public Tree<T> findBestPath(T target,  BiPredicate<Tree<T>, Tree<T>> copyRemover) {
        return this.findBestPath(target, new HashSet<>(), null, copyRemover);
    }
    
    private Tree<T> findBestPath(T target, Set<Tree<T>> curPath, Tree<T> curClosest, BiPredicate<Tree<T>, Tree<T>> copyRemover) {
        if(this.getChildren() == null || target == null)
            return curClosest;
        
        if(curPath.contains(this))
            return curClosest;
        curPath.add(this);
        
        for(Tree<T> child : this.getChildren()) {

            if(child == null || curClosest == child)
                continue;

            if(target.equals(child.getValue())) {

                // If current closest > other
                // Remove current closest
                if (curClosest == null || copyRemover.test(curClosest, child)) {
                    curClosest = child;
                }
                continue;
            }
            
            if(child.getChildren() == null || child.getChildren().length == 0)
                continue;
            
            curClosest = child.findBestPath(target, curPath, curClosest, copyRemover);
        }
        return curClosest;
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

    public Tree<T>[] getChildren() {
        return children;
    }

    public void setChildren(Tree<T>[] children) {
        this.children = children;
    }
}