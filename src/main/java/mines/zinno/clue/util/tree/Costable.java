package mines.zinno.clue.util.tree;

/**
 * The {@link Costable} interface should be used by all classes used as a value in the {@link Tree} utility that
 * have a cost for crossing the value
 */
public interface Costable {

    int getCost();

}
