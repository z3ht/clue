package mines.zinno.clue.shape.character.listener;

import mines.zinno.clue.controller.ClueController;
import mines.zinno.clue.game.BoardGame;
import mines.zinno.clue.control.menu.SelectableMenu;
import mines.zinno.clue.control.menu.ValueMenuItem;
import mines.zinno.clue.constant.Room;
import mines.zinno.clue.shape.character.Character;
import mines.zinno.clue.shape.character.Player;
import mines.zinno.clue.shape.character.constant.Turn;
import mines.zinno.clue.shape.place.RoomPlace;

/**
 * The {@link UpdateRoomGuess} class is an observer in the observer design pattern. It implements the
 * {@link OnTurnListener} interface and is responsible for updating the room menu in the guess dialogue. The
 * room menu must be updated when players move because a guess in clue must involve the room a player is inside.
 */
public class UpdateRoomGuess implements OnTurnListener<Character> {

    private BoardGame<ClueController> boardGame;

    public UpdateRoomGuess(BoardGame<ClueController> boardGame) {
        this.boardGame = boardGame;
    }

    @Override
    public void update(Character character) {
        if(!(character instanceof Player))
            return;
        Player player = (Player) character;

        if(player.getTurn() != Turn.POST_MOVE)
            return;

        SelectableMenu<Room> roomMenu = boardGame.getController().getGuessDialogue().getController().getRoomMenu();
        
        // Unlock and clear room menu
        roomMenu.setLocked(false);
        roomMenu.setSelectedItem((ValueMenuItem<Room>) null);

        // Return if player is not in a room
        if(!(player.getCurPlace() instanceof RoomPlace)) {
            return;
        }
        RoomPlace roomPlace = (RoomPlace) player.getCurPlace();

        // Return if player is in the exit room
        if(roomPlace.getRoom().equals(Room.EXIT)) {
            return;
        }

        roomMenu.setSelectedItem(roomPlace.getRoom());
        roomMenu.setLocked(true);
    }
    
}
