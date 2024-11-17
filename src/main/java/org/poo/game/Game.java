package org.poo.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.fileio.CardInput;
import org.poo.fileio.DecksInput;
import org.poo.fileio.GameInput;
import org.poo.fileio.Input;
import org.poo.fileio.Coordinates;
import org.poo.fileio.StartGameInput;
import org.poo.game.heroes.EmpressThorina;
import org.poo.game.heroes.GeneralKocioraw;
import org.poo.game.heroes.KingMudface;
import org.poo.game.heroes.LordRoyce;

import java.util.ArrayList;

/**
 * Represents a game session, managing players, decks, actions, and the game board.
 * Includes methods to initialize and execute the game, handle actions, and update
 * game states like mana, rounds, and card states.
 */
public class Game {
    private Player player1;
    private Player player2;
    private int startingPlayer;
    private final ArrayList<Action> actions = new ArrayList<>();
    private final GameBoard board = new GameBoard();
    private final Stats stats;

    /**
     * Constructor for initializing a new game with provided statistics.
     *
     * @param stats the {@link .Stats} object tracking game statistics
     */
    public Game(final Stats stats) {
        this.stats = stats;
    }

    /**
     * Parses the decks for a player from the provided input.
     *
     * @param decks the {@link DecksInput} containing deck data
     * @return a list of {@link Deck} objects
     */
    public ArrayList<Deck> parseDecks(final DecksInput decks) {
        ArrayList<Deck> playerDecks = new ArrayList<>();
        for (int i = 0; i < decks.getNrDecks(); i++) {
            ArrayList<CardInput> deckInput = decks.getDecks().get(i);
            Deck deck = new Deck(deckInput);
            playerDecks.add(deck);
        }
        return playerDecks;
    }

    /**
     * Parses a hero from the provided card input.
     *
     * @param hero the {@link CardInput} describing the hero
     * @return a {@link Hero} object corresponding to the input
     */
    public Hero parseHero(final CardInput hero) {
        return switch (hero.getName()) {
            case "Lord Royce" -> new LordRoyce(hero);
            case "Empress Thorina" -> new EmpressThorina(hero);
            case "King Mudface" -> new KingMudface(hero);
            case "General Kocioraw" -> new GeneralKocioraw(hero);
            default -> null;
        };
    }

    /**
     * Prepares the game by initializing players, decks, and starting settings.
     *
     * @param input   the {@link Input} containing game setup data
     * @param numGame the index of the current game in the input
     */
    public void prepareGame(final Input input, final int numGame) {
        GameInput game = input.getGames().get(numGame);
        StartGameInput startGame = game.getStartGame();

        ArrayList<Deck> player1Decks = parseDecks(input.getPlayerOneDecks());
        ArrayList<Deck> player2Decks = parseDecks(input.getPlayerTwoDecks());

        Deck player1Deck = player1Decks.get(startGame.getPlayerOneDeckIdx());
        Deck player2Deck = player2Decks.get(startGame.getPlayerTwoDeckIdx());
        player1Deck.shuffleDeck(startGame.getShuffleSeed());
        player2Deck.shuffleDeck(startGame.getShuffleSeed());

        Hero player1Hero = parseHero(startGame.getPlayerOneHero());
        Hero player2Hero = parseHero(startGame.getPlayerTwoHero());

        player1 = new Player(player1Deck, player1Hero);
        player2 = new Player(player2Deck, player2Hero);

        startingPlayer = startGame.getStartingPlayer();
    }

    /**
     * Parses and initializes actions for the game, adding them to the action list.
     *
     * @param input   the {@link Input} containing action data
     * @param numGame the index of the current game in the input
     */
    public void parseActions(final Input input, final int numGame) {
        GameInput game = input.getGames().get(numGame);
        for (ActionsInput action : game.getActions()) {
            Action newAction;
            if (action.getCommand().contains("get")) {
                newAction = new DebugAction(action);
            } else {
                newAction = new PlayAction(action);
            }
            actions.add(newAction);
        }
    }

    /**
     * Prepares the game for a new round by drawing cards, updating mana,
     * and resetting attack and ability statuses.
     *
     * @param round the current round number
     */
    public void prepareRound(final int round) {
        player1.drawCard();
        player2.drawCard();

        if (round < GlobalVariables.MANA_LIMIT) {
            player1.setMana(player1.getMana() + round);
            player2.setMana(player2.getMana() + round);
        } else {
            player1.setMana(player1.getMana() + GlobalVariables.MANA_LIMIT);
            player2.setMana(player2.getMana() + GlobalVariables.MANA_LIMIT);
        }

        board.setCardsNotAttacked();
        player1.getHero().setHasAttacked(false);
        player2.getHero().setHasAttacked(false);
    }

    /**
     * Executes the game by processing all actions and updating the output.
     *
     * @param mapper      the {@link ObjectMapper} for JSON operations
     * @param outputArray the {@link ArrayNode} to store output results
     */
    public void playGame(final ObjectMapper mapper, final ArrayNode outputArray) {
        stats.setCurrentPlayer(startingPlayer);
        stats.setNumRound(1);
        prepareRound(1);

        for (Action action : actions) {
            ObjectNode objectNode = action.execute(mapper, this, stats);
            if (objectNode != null) {
                outputArray.add(objectNode);
            }
        }
    }

    /**
     * Retrieves the player corresponding to the given index.
     *
     * @param idx the player index (1 or 2)
     * @return the {@link Player} object
     */
    public Player getPlayer(final int idx) {
        if (idx == 1) {
            return player1;
        } else {
            return player2;
        }
    }

    /**
     * Sets all cards in the player's rows on the board to not frozen.
     *
     * @param board  the {@link GameBoard} containing the cards
     * @param player the {@link Player} whose rows will be updated
     */
    public void setCardsNotFrozen(final GameBoard board, final Player player) {
        if (player.equals(player1)) {
            board.getBoard().get(GlobalVariables.P_1_FRONT_ROW).setNotFrozen();
            board.getBoard().get(GlobalVariables.P_1_BACK_ROW).setNotFrozen();
        } else {
            board.getBoard().get(GlobalVariables.P_2_FRONT_ROW).setNotFrozen();
            board.getBoard().get(GlobalVariables.P_2_BACK_ROW).setNotFrozen();
        }
    }

    /**
     * Determines the row index for a card based on the player and the card's mandatory row.
     *
     * @param card   the {@link Card} whose row is being determined
     * @param player the {@link Player} who owns the card
     * @return the index of the row where the card should be placed
     */
    public int getRowIdx(final Card card, final Player player) {
        int row = card.getMandatoryRow();
        if (player.equals(player1)) {
            if (row == 1) {
                return GlobalVariables.P_1_FRONT_ROW;
            } else {
                return GlobalVariables.P_1_BACK_ROW;
            }
        } else {
            if (row == 1) {
                return GlobalVariables.P_2_FRONT_ROW;
            } else {
                return GlobalVariables.P_2_BACK_ROW;
            }
        }
    }

    /**
     * Checks if the given coordinates correspond to the opponent's rows.
     *
     * @param coordinates the {@link Coordinates} to verify
     * @param player      the {@link Player} to compare against
     * @return true if the coordinates belong to the opponent's rows, false otherwise
     */
    public boolean verifyEnemyCoordinates(final Coordinates coordinates, final Player player) {
        if (player.equals(player1)) {
            return coordinates.getX() != GlobalVariables.P_1_FRONT_ROW
                    && coordinates.getX() != GlobalVariables.P_1_BACK_ROW;
        } else {
            return coordinates.getX() != GlobalVariables.P_2_FRONT_ROW
                    && coordinates.getX() != GlobalVariables.P_2_BACK_ROW;
        }
    }

    /**
     * Verifies if a row belongs to the opponent of the given player.
     *
     * @param row    the row index to check
     * @param player the {@link Player} to compare against
     * @return true if the row belongs to the opponent, false otherwise
     */
    public boolean verifyRowBelongsToEnemy(final int row, final Player player) {
        if (player.equals(player1)) {
            return row == GlobalVariables.P_2_FRONT_ROW || row == GlobalVariables.P_2_BACK_ROW;
        } else {
            return row == GlobalVariables.P_1_FRONT_ROW || row == GlobalVariables.P_1_BACK_ROW;
        }
    }

    /**
     * Checks if any tank cards exist in the opponent's rows.
     *
     * @param player the {@link Player} to check against
     * @return true if a tank exists, false otherwise
     */
    public boolean verifyTankExists(final Player player) {
        if (player.equals(player1)) {
            for (Card card : board.getBoard().get(GlobalVariables.P_1_FRONT_ROW).getCards()) {
                if (card.isTank()) {
                    return true;
                }
            }
        } else {
            for (Card card : board.getBoard().get(GlobalVariables.P_2_FRONT_ROW).getCards()) {
                if (card.isTank()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retrieves the opponent of the specified player.
     *
     * @param player the {@link Player} whose opponent is being retrieved
     * @return the opponent {@link Player} object
     */
    public Player getOpponent(final Player player) {
        if (player.equals(player1)) {
            return player2;
        } else {
            return player1;
        }
    }

    /**
     * Retrieves the game board.
     *
     * @return the {@link GameBoard} object
     */
    public GameBoard getBoard() {
        return board;
    }

    /**
     * Retrieves the first player.
     *
     * @return the first {@link Player} object
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Retrieves the second player.
     *
     * @return the second {@link Player} object
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Retrieves the starting player.
     *
     * @return the index of the starting player
     */
    public int getStartingPlayer() {
        return startingPlayer;
    }
}
