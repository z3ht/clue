package mines.zinno.clue.listener.settings;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import mines.zinno.clue.control.menu.SelectableMenu;
import mines.zinno.clue.control.menu.ValueMenuItem;
import mines.zinno.clue.layout.board.ClueBoard;

public class OnSelectCustomBoard implements EventHandler<MouseEvent> {

    private final ClueBoard clueBoard;

    public OnSelectCustomBoard(ClueBoard clueBoard) {
        this.clueBoard = clueBoard;
    }

    @Override
    public void handle(MouseEvent event) {
        System.out.println("reached 1");
        
        if(event.getTarget() instanceof SelectableMenu) {
            System.out.println("selectable menu");
        }
        else if (event.getTarget() instanceof ValueMenuItem)
            System.out.println("value menu item");
    }
}
