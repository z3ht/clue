package mines.zinno.clue.shape.character;

import mines.zinno.clue.constant.*;
import mines.zinno.clue.game.Clue;
import mines.zinno.clue.shape.character.constant.Result;
import mines.zinno.clue.shape.character.constant.Turn;
import mines.zinno.clue.shape.character.handler.GuessHandler;
import mines.zinno.clue.shape.character.vo.GuessVO;
import mines.zinno.clue.shape.place.DoorPlace;
import mines.zinno.clue.shape.place.Entrance;
import mines.zinno.clue.shape.place.Place;
import mines.zinno.clue.shape.place.RoomPlace;
import mines.zinno.clue.stage.dialogue.ShortDialogue;
import mines.zinno.clue.util.tree.Node;
import mines.zinno.clue.util.tree.Tree;

import java.util.*;
import java.util.stream.Collectors;


public class Computer extends Character {

    private boolean gotoExit;

    public Computer(Clue game, GuessHandler guessHandler, Suspect suspect, Place startPlace) {
        super(game, guessHandler, suspect, startPlace);

        gotoExit = false;
    }

    @Override
    public void beginTurn() {
        super.beginTurn();
        this.roll();

        this.moveTo(calcBestMove());
        if(this.curPlace instanceof RoomPlace) {
            GuessVO guess = calcBestGuess();

            if(guess == null)
                return;

            this.guess(guess);
            this.setTurn(Turn.POST_GUESS);
        }
    }

    private GuessVO calcBestGuess() {
        GuessVO bestGuess = new GuessVO();

        if(!(this.curPlace instanceof RoomPlace))
            return null;

        RoomPlace curRoomPlace = (RoomPlace) this.curPlace;

        boolean isAccusation = curRoomPlace.getRoom() == Room.EXIT;

        List<Room> room = new ArrayList<>(Arrays.asList(Room.values()));
        room.removeIf(Room::isExcluded);
        for(Card card : this.getCards()) {
            if(!(card instanceof Room))
                continue;

            room.remove(card);
        }

        bestGuess.room = (isAccusation) ? room.get(0) : curRoomPlace.getRoom();

        List<Weapon> weapon = new ArrayList<>(Arrays.asList(Weapon.values()));
        for(Card card : this.getCards()) {
            if(!(card instanceof Weapon))
                continue;
            weapon.remove(card);
        }
        bestGuess.weapon = weapon.get(0);

        List<Suspect> suspect = new ArrayList<>(Arrays.asList(Suspect.values()));
        for(Card card : this.getCards()) {
            if(!(card instanceof Suspect))
                continue;
            suspect.remove(card);
        }
        bestGuess.suspect = suspect.get(0);

        this.gotoExit = suspect.size() + weapon.size() + room.size() <= 4;

        return bestGuess;
    }

    public Place calcBestMove() {

        Tree<Place> curLocTree = generateFinderTree(curPlace);

        // Determine the closest door that hasn't been ruled out yet
        int minDoorDistance = Integer.MAX_VALUE;
        Place closestGoodRoom = null;

        if(gotoExit)
            closestGoodRoom = this.game.getController().getBoard().getItemFromCoordinate(Room.EXIT.getCenter());
        else {
            for(Room room : Room.values()) {
                if(room.isExcluded())
                    continue;

                boolean shouldContinue = false;
                for(Card c : getCards()) {
                    if(!(c instanceof Room))
                        continue;
                    Room knownRoom = (Room) c;

                    if(knownRoom != room)
                        continue;

                    shouldContinue = true;
                    break;
                }
                if(shouldContinue)
                    continue;

                Place centerOfRoom = this.game.getController().getBoard().getItemFromCoordinate(room.getCenter());
                int newRoomDistance = curLocTree.findPath(centerOfRoom).getCost();

                if(newRoomDistance == -1 || newRoomDistance > minDoorDistance)
                    continue;

                minDoorDistance = newRoomDistance;
                closestGoodRoom = centerOfRoom;
            }
        }

        Tree<Place> targetTree = generateFinderTree(closestGoodRoom);

        // Do the move that puts the computer closest to the best door
        Place bestMove = null;
        int minDistance = Integer.MAX_VALUE;
        for(Place place : this.moveTree.retrieveAllValues()) {
            if(place.isOccupied())
                continue;

            int curDistance = targetTree.findPath(place).getCost();

            if(curDistance > minDistance)
                continue;

            minDistance = curDistance;
            bestMove = place;
        }

        return randomizeLoc(bestMove);
    }

    @SuppressWarnings("unchecked")
    protected Place randomizeLoc(Place loc) {
        Tree<Place> randRoomLoc = new Tree<>(loc);
        randRoomLoc.populate((curNode) ->
                        // This casts correctly. Java doesn't like Parameterized arrays
                        Arrays.stream(curNode.getValue().getAdjacent())
                                .filter((place) -> place != null && !place.isOccupied())
                                .filter((adj) -> (calcDistance(curNode) + adj.getMoveCost() <= 0))
                                .map((adj) -> new Node<>(adj, curNode))
                                .toArray(Node[]::new),
                25);
        Set<Place> posLocs = randRoomLoc.retrieveAllValues().stream()
                .filter((place) -> place != null && !place.isOccupied())
                .collect(Collectors.toSet());
        return (Place) posLocs.toArray()[(int) (posLocs.size()*Math.random())];
    }

    @SuppressWarnings("unchecked")
    private Tree<Place> generateFinderTree(Place startLoc) {
        Tree<Place> tree = new Tree<>(startLoc);
        tree.populate((curNode) ->
                        // This casts correctly. Java doesn't like Parameterized arrays
                        Arrays.stream(curNode.getValue().getAdjacent())
                                .filter(Objects::nonNull)
                                .map((adj) -> new Node<>(adj, curNode))
                                .toArray(Node[]::new),
                150);
        return tree;
    }

    @Override
    public void onWin() {
        new ShortDialogue(Result.COMPUTER_WIN.getName(), Result.COMPUTER_WIN.getText(this.getCharacter(), game.getMurderer())).show();
    }

    @Override
    public void onLose() {
        new ShortDialogue(Result.COMPUTER_LOSE.getName(), Result.COMPUTER_LOSE.getText(this.getCharacter())).show();
    }
}
