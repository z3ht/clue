package mines.zinno.clue.shape.character.listeners;

import mines.zinno.clue.constant.Alert;
import mines.zinno.clue.constant.Room;
import mines.zinno.clue.listener.OnMoveListener;
import mines.zinno.clue.shape.character.Player;
import mines.zinno.clue.shape.place.RoomPlace;
import mines.zinno.clue.stage.dialogue.BasicInfoDialogue;

/**
 * The {@link OnExitEnter} class is an observer in the observer design pattern. It implements the
 * {@link OnMoveListener} interface and is responsible for warning the player when he enters the exit room. In
 * the exit room, a player's guess will win or lose them the game so a warning message is sent out.
 */
public class OnExitEnter implements OnMoveListener<Player> {
    
    @Override
    public void update(Player p) {

        // Return if player is not in a room
        if(!(p.getCurPlace() instanceof RoomPlace)) {
            return;
        }
        RoomPlace roomPlace = (RoomPlace) p.getCurPlace();

        // Return if player is not in the exit room
        if(!(roomPlace.getRoom().equals(Room.EXIT)))
            return;

        // Send exit entrance warning
        new BasicInfoDialogue(Alert.REACHED_EXIT.getName(), Alert.REACHED_EXIT.getText()).show();
    }
}
