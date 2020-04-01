package mines.zinno.clue.util;

import java.util.*;
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
            
            if(spread > 5) {
                for(int i = 0; i < this.getChildren().length; i++) {
                    if(this.getChildren()[i] == null)
                        continue;
                    this.getChildren()[i] = findBestPath(this.getChildren()[i].getValue(), copyRemover);
                }
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
        return this.findBestPath(target, null, copyRemover);
    }
    
    private Tree<T> findBestPath(T target, Tree<T> curClosest, BiPredicate<Tree<T>, Tree<T>> copyRemover) {
        if(this.getChildren() == null)
            return curClosest;
        
        for(Tree<T> child : this.getChildren()) {

            if(curClosest == child)
                continue;

            if(target.equals(child.getValue())) {

                // If current closest > other
                // Remove current closest
                if (copyRemover.test(curClosest, child)) {
                    if(curClosest instanceof Node) {
                        Tree<T>[] bestParentChildren = ((Node<T>) curClosest).getParent().getChildren();
                        for(int i = 0; i < bestParentChildren.length; i++) {
                            if(bestParentChildren[i] == curClosest)
                                bestParentChildren[i] = null;
                        }
                    }
                    
                    curClosest = child;
                } else {
                    if(child instanceof Node) {
                        Tree<T>[] bestParentChildren = ((Node<T>) child).getParent().getChildren();
                        for(int i = 0; i < bestParentChildren.length; i++) {
                            if(bestParentChildren[i] == child)
                                bestParentChildren[i] = null;
                        }
                    }
                }
                continue;
            }
            
            if(child.getChildren() == null || child.getChildren().length == 0)
                continue;
            
            child.findBestPath(target, curClosest, copyRemover);
        }
        return curClosest;
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