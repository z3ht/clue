package mines.zinno.clue.layout.board.validator;

import mines.zinno.clue.layout.board.Board;

import java.util.function.Function;

/**
 * The {@link MapValidator} is used to validate a {@link Board}'s map meets specific structural requirements
 */
public interface MapValidator extends Function<Character[][][], Boolean> {

    @Override
    default Function compose(Function before) {
        return null;
    }

    @Override
    default Function andThen(Function after) {
        return null;
    }
}
