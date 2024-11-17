package org.poo.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;

/**
 * Represents the game board that holds rows of cards. It manages the cards in each row,
 * tracks attacked and frozen states, and provides methods for retrieving and updating
 * the cards on the board.
 */
public class GameBoard {
    private final ArrayList<Deck> board = new ArrayList<>(GlobalVariables.ROWS_NUMBER);

    /**
     * Initializes the game board by creating a set of rows for the board,
     * with each row represented by a {@link Deck}.
     */
    public GameBoard() {
        for (int i = 0; i < GlobalVariables.ROWS_NUMBER; i++) {
            board.add(new Deck());
        }
    }

    /**
     * Converts the game board into an {@link ArrayNode} (JSON array) representing all cards
     * in the board.
     *
     * @param mapper the {@link ObjectMapper} used to create the JSON output
     * @return an {@link ArrayNode} containing the JSON representation of the board
     */
    public ArrayNode getBoardArray(final ObjectMapper mapper) {
        ArrayNode boardArray = mapper.createArrayNode();
        for (int i = 0; i < GlobalVariables.ROWS_NUMBER; i++) {
            boardArray.add(board.get(i).getDeckArray(mapper));
        }
        return boardArray;
    }

    /**
     * Converts the game board into an {@link ArrayNode} (JSON array) containing only the
     * frozen cards on the board.
     *
     * @param mapper the {@link ObjectMapper} used to create the JSON output
     * @return an {@link ArrayNode} containing the JSON representation of the frozen cards
     */
    public ArrayNode getFrozenCardsArray(final ObjectMapper mapper) {
        ArrayNode boardArray = mapper.createArrayNode();
        for (Deck row : board) {
            for (Card card : row.getCards()) {
                if (card.isFrozen) {
                    boardArray.add(card.getCardNode(mapper));
                }
            }
        }
        return boardArray;
    }

    /**
     * Retrieves the card at a specific position on the board.
     *
     * @param x the row index of the card
     * @param y the column index of the card within the row
     * @return the {@link Card} at the specified position, or null if the coordinates are invalid
     */
    public Card getCardAtPosition(final int x, final int y) {
        if (x < 0 || x >= GlobalVariables.ROWS_NUMBER || y < 0 || y >= board.get(x).getSize()) {
            return null;
        }
        return board.get(x).getCards().get(y);
    }

    /**
     * Sets the `hasAttacked` flag for all cards on the board to false
     * Used at the beginning of each round to reset the attack status of all cards.
     */
    public void setCardsNotAttacked() {
        for (Deck deck : board) {
            for (Card card : deck.getCards()) {
                card.setHasAttacked(false);
            }
        }
    }

    /**
     * Retrieves the entire game board as a list of decks, representing the rows of cards.
     *
     * @return a list of {@link Deck} objects representing the rows of the board
     */
    public ArrayList<Deck> getBoard() {
        return board;
    }
}
