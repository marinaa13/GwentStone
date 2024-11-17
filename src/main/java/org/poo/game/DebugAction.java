package org.poo.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;

/**
 * Represents a debug action that provides information about the game state,
 * including player decks, hero details, cards in hand, cards on the table,
 * and statistics. The action is executed based on the command specified
 * in the {@link ActionsInput}.
 */
public class DebugAction extends Action {

    public DebugAction(final ActionsInput action) {
        super(action);
    }

    /**
     * Executes the debug action based on the specified command and returns a JSON representation
     * of the result.
     *
     * @param mapper the {@link ObjectMapper} used to create JSON nodes
     * @param game   the current {@link Game} instance
     * @param stats  the {@link Stats} object containing game statistics
     * @return an {@link ObjectNode} containing the output of the executed command
     */
    @Override
    public ObjectNode execute(final ObjectMapper mapper, final Game game, final Stats stats) {
        ObjectNode node = mapper.createObjectNode();
        node.put("command", command);

        switch (command) {
            case "getPlayerDeck" -> {
                node.put("playerIdx", playerIdx);

                Player player = game.getPlayer(playerIdx);
                Deck deck = player.getDeck();
                ArrayNode deckArray = deck.getDeckArray(mapper);
                node.set("output", deckArray);
            }
            case "getPlayerHero" -> {
                node.put("playerIdx", playerIdx);

                Player player = game.getPlayer(playerIdx);
                Hero hero = player.getHero();
                node.set("output", hero.getHeroNode(mapper));
            }
            case "getCardsInHand" -> {
                node.put("playerIdx", playerIdx);
                Player player = game.getPlayer(playerIdx);
                Deck hand = player.getHand();
                ArrayNode deckArray = hand.getDeckArray(mapper);
                node.set("output", deckArray);
            }
            case "getCardsOnTable" -> {
                GameBoard board = game.getBoard();
                ArrayNode boardArray = board.getBoardArray(mapper);
                node.set("output", boardArray);
            }
            case "getPlayerTurn" -> node.put("output", stats.getCurrentPlayer());
            case "getTotalGamesPlayed" -> node.put("output", stats.getTotalGamesPlayed());
            case "getPlayerOneWins" -> node.put("output", stats.getPlayer1Wins());
            case "getPlayerTwoWins" -> node.put("output", stats.getPlayer2Wins());
            case "getPlayerMana" -> {
                node.put("playerIdx", playerIdx);
                Player player = game.getPlayer(playerIdx);
                node.put("output", player.getMana());
            }
            case "getCardAtPosition" -> {
                node.put("x", x);
                node.put("y", y);
                GameBoard board = game.getBoard();
                Card card = board.getCardAtPosition(x, y);
                if (card == null) {
                    node.put("output", "No card available at that position.");
                } else {
                    node.set("output", card.getCardNode(mapper));
                }
            }
            case "getFrozenCardsOnTable" -> {
                GameBoard board = game.getBoard();
                ArrayNode boardArray = board.getFrozenCardsArray(mapper);
                node.set("output", boardArray);
            }
            default -> {
                node.put("output", "Invalid command.");
            }
        }
        return node;
    }
}
