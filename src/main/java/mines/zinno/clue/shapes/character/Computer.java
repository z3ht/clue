package mines.zinno.clue.shapes.character;

import mines.zinno.clue.enums.Card;
import mines.zinno.clue.enums.Suspect;
import mines.zinno.clue.game.BoardGame;
import mines.zinno.clue.shapes.character.enums.RevealContext;
import mines.zinno.clue.shapes.place.Place;

import java.util.List;


public class Computer extends Character {

    public Computer(BoardGame boardGame, Suspect suspect, Place startPlace) {
        super(boardGame, suspect, startPlace);
    }

    @Override
    public void beginTurn() {
        super.beginTurn();
        this.roll();
        this.calcPosMoves();
//        Place place = calcBestMove();
//        this.moveTo(place);
//        if(place instanceof RoomPlace) {
////            this.guess(calcBestGuess());
//        }
//        this.setTurn(Turn.POST_MOVE);
    }

    @Override
    public void receiveCard(Character sender, Card card, RevealContext revealContext) {
        
    }
//
//    public Place calcBestMove() {
//        int minDistance = Integer.MAX_VALUE;
//        DoorPlace closestGoodDoor = null;
//        for (Place[] places : this.game.getController().getBoard().getGrid()) {
//            for(Place place : places) {
//                if(!(place instanceof DoorPlace))
//                    continue;
//                DoorPlace door = (DoorPlace) place;
//                
//                for(Card c : getCards()) {
//                    
//                }
//                
//                if(getKnownRooms().contains(door.getRoom()))
//                    continue;
//                int curDistance = this.getCurPlace().getDistance(door);
//                if(curDistance >= minDistance)
//                    continue;
//
//                minDistance = curDistance;
//                closestGoodDoor = door;
//            }
//        }
//        
//        Place bestMove = null;
//        minDistance = Integer.MAX_VALUE;
//        for(Place place : this.getPosMoves()) {
//            int curDistance = closestGoodDoor.getDistance(place);
//            if(curDistance > minDistance)
//                continue;
//            
//            minDistance = curDistance;
//            bestMove = place;
//        }
//        
//        return bestMove;
//    }

    @Override
    public void onWin() {
        
    }

    @Override
    public void onLose() {

    }

    private <T> T calcGuessItem(T[] items, List<T> known, T defaultVal) {
        T guessT = null;
        for(T t : items) {
            if (known.contains(t))
                continue;
            guessT = t;
        }
        if(guessT == null)
            guessT = defaultVal;
        return guessT;
    }
    
}
