package org.poo.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.fileio.Coordinates;


/**
 * Represents an action that can be performed in the game.
 * The action may involve commands, player interactions, and card operations.
 * It can either be a debug action or a game/player action.
 */
public class Action {
    protected String command;
    protected int handIdx;
    protected Coordinates cardAttacker;
    protected Coordinates cardAttacked;
    protected int affectedRow;
    protected int playerIdx;
    protected int x;
    protected int y;

    /**
     * Constructs an Action object using the provided {@link ActionsInput}.
     * This constructor initializes the action's attributes based on the input data.
     *
     * @param action the {@link ActionsInput} object containing the action details
     */
    public Action(final ActionsInput action) {
        command = action.getCommand();
        handIdx = action.getHandIdx();
        cardAttacker = action.getCardAttacker();
        cardAttacked = action.getCardAttacked();
        affectedRow = action.getAffectedRow();
        playerIdx = action.getPlayerIdx();
        x = action.getX();
        y = action.getY();
    }

    /**
     * Executes the action and returns a JSON representation of the result.
     *
     * @param mapper the {@link ObjectMapper} used to create the JSON object
     * @param game   the {@link Game} object representing the current game
     * @param stats  the {@link Stats} object representing the current game statistics
     * @return an {@link ObjectNode} containing the result of the action, or null if the result
     * does not need to be printed
     */
    public ObjectNode execute(final ObjectMapper mapper, final Game game, final Stats stats) {
        return null;
    }
}
