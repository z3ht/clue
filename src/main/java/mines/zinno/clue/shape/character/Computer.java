package mines.zinno.clue.shape.character;

import mines.zinno.clue.constant.*;
import mines.zinno.clue.game.Clue;
import mines.zinno.clue.shape.character.constant.Result;
import mines.zinno.clue.shape.character.constant.RevealContext;
import mines.zinno.clue.shape.character.constant.Turn;
import mines.zinno.clue.shape.character.handler.GuessHandler;
import mines.zinno.clue.shape.character.vo.GuessVO;
import mines.zinno.clue.shape.place.DoorPlace;
import mines.zinno.clue.shape.place.Entrance;
import mines.zinno.clue.shape.place.Place;
import mines.zinno.clue.shape.place.RoomPlace;
import mines.zinno.clue.stage.dialogue.BasicInfoDialogue;
import mines.zinno.clue.util.handler.Handler;
import mines.zinno.clue.util.tree.Node;
import mines.zinno.clue.util.tree.Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;


public class Computer extends Character {

    public Computer(Clue game, GuessHandler guessHandler, Suspect suspect, Place startPlace) {
        super(game, guessHandler, suspect, startPlace);
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

        if(curRoomPlace.getRoom() == Room.EXIT) {
            List<Room> rooms = new ArrayList<>(Arrays.asList(Room.values()));
            for(Card card : this.getCards()) {
                if(!(card instanceof Room))
                    continue;
                rooms.remove(card);
            }
            bestGuess.room = rooms.get(0);
        } else
            bestGuess.room = curRoomPlace.getRoom();

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

        return bestGuess;
    }

    public Place calcBestMove() {

        Tree<Place> curLocTree = generateTree(curPlace);

        // Determine the closest door that hasn't been ruled out yet
        int minDoorDistance = Integer.MAX_VALUE;
        Entrance closestGoodEntrance = null;
        for (Place[] places : game.getController().getBoard().getGrid()) {
            for(Place place : places) {
                if(!(place instanceof Entrance))
                    continue;
                Entrance entrance = (Entrance) place;

                boolean shouldContinue = false;
                for(Card c : getCards()) {
                    if(!(c instanceof DoorPlace))
                        continue;
                    DoorPlace doorCard = (DoorPlace) c;

                    if(doorCard.equals(entrance)) {
                        shouldContinue = true;
                        break;
                    }
                }
                if(shouldContinue)
                    continue;

                int curDistance = curLocTree.findPath(entrance).getCost();
                if(curDistance == -1 || curDistance > minDoorDistance)
                    continue;

                minDoorDistance = curDistance;
                closestGoodEntrance = entrance;
            }
        }

        Tree<Place> targetTree = generateTree(closestGoodEntrance);

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

        return bestMove;
    }

    @SuppressWarnings("unchecked")
    private Tree<Place> generateTree(Place startLoc) {
        Tree<Place> tree = new Tree<>(startLoc);
        tree.populate((curNode) ->
                        // This casts correctly. Java doesn't like Parameterized arrays
                        Arrays.stream(curNode.getValue().getAdjacent())
                                .filter(Objects::nonNull)
                                .map((adj) -> new Node<>(adj, curNode))
                                .toArray(Node[]::new),
                75);
        return tree;
    }

    @Override
    public void onWin() {
        new BasicInfoDialogue(Result.COMPUTER_WIN.getName(), Result.COMPUTER_WIN.getText(this.getCharacter(), game.getMurderer())).show();
    }

    @Override
    public void onLose() {
        new BasicInfoDialogue(Result.COMPUTER_LOSE.getName(), Result.COMPUTER_LOSE.getText(this.getCharacter())).show();
    }

    private int getManhattanDistance(Place p1, Place p2) {
        return (int) (Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getX() - p2.getX()));
    }
}
