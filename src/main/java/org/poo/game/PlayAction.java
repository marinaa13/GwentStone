package org.poo.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;


/**
 * Represents a specific action where a player performs an action with cards in the game.
 * This includes placing cards, attacking with cards, using card abilities,
 * using the hero's ability.
 */
public class PlayAction extends Action {

    /**
     * Constructs a PlayAction object using the provided {@link ActionsInput}.
     * This constructor initializes the action with the details from the input.
     *
     * @param action the {@link ActionsInput} object containing the action details
     */
    public PlayAction(final ActionsInput action) {
        super(action);
    }

    /**
     * Executes the play action based on the current game state and player stats.
     * This method handles various commands like placing a card, using an attack, using an ability,
     * and ending the player's turn. It returns a JSON object with the result of the action.
     *
     * @param mapper the {@link ObjectMapper} used to create the JSON object
     * @param game   the {@link Game} object representing the current game state
     * @param stats  the {@link Stats} object representing the current player stats
     * @return an {@link ObjectNode} containing the result of the action,
     * or null if the action does not need
     *         to return output.
     */
    @Override
    public ObjectNode execute(final ObjectMapper mapper, final Game game, final Stats stats) {
        if (game.getPlayer1().getHero().getHealth() <= 0
                && game.getPlayer2().getHero().getHealth() <= 0) {
            return null;
        }
        ObjectNode node = mapper.createObjectNode();
        node.put("command", command);
        Player player = game.getPlayer(stats.getCurrentPlayer());
        GameBoard board = game.getBoard();

        switch (command) {
            case "placeCard" -> {
                Card card = player.getHand().getCards().get(handIdx);

                if (player.getMana() < card.getMana()) {
                    node.put("handIdx", handIdx);
                    node.put("error", "Not enough mana to place card on table.");
                    return node;
                }

                int idx = game.getRowIdx(card, player);
                Deck row = board.getBoard().get(idx);

                if (row.getCards().size() == GlobalVariables.MAX_ROW_SIZE) {
                    node.put("error", "Cannot place card on table since row is full.");
                    return node;
                }

                player.setMana(player.getMana() - card.getMana());
                row.getCards().add(card);
                player.getHand().getCards().remove(handIdx);
            }
            case "cardUsesAttack" -> {

                Card cardToAttack = board.getCardAtPosition(cardAttacker.getX(),
                        cardAttacker.getY());
                Card cardToBeAttacked = board.getCardAtPosition(cardAttacked.getX(),
                        cardAttacked.getY());
                Player opponent = game.getOpponent(player);

                if (!game.verifyEnemyCoordinates(cardAttacked, player)) {
                    node.put("cardAttacker", cardAttacker.getCoordinatesObject(mapper));
                    node.put("cardAttacked", cardAttacked.getCoordinatesObject(mapper));
                    node.put("error", "Attacked card does not belong to the enemy.");
                    return node;
                }

                if (cardToAttack.hasAttacked || cardToAttack.hasUsedAbility) {
                    node.put("cardAttacker", cardAttacker.getCoordinatesObject(mapper));
                    node.put("cardAttacked", cardAttacked.getCoordinatesObject(mapper));
                    node.put("error", "Attacker card has already attacked this turn.");
                    return node;
                }

                if (cardToAttack.isFrozen) {
                    node.put("cardAttacker", cardAttacker.getCoordinatesObject(mapper));
                    node.put("cardAttacked", cardAttacked.getCoordinatesObject(mapper));
                    node.put("error", "Attacker card is frozen.");
                    return node;
                }

                if (!cardToBeAttacked.isTank()) {
                    if (game.verifyTankExists(opponent)) {
                        node.put("cardAttacker", cardAttacker.getCoordinatesObject(mapper));
                        node.put("cardAttacked", cardAttacked.getCoordinatesObject(mapper));
                        node.put("error", "Attacked card is not of type 'Tank'.");
                        return node;
                    }
                }

                cardToBeAttacked.setHealth(cardToBeAttacked.getHealth()
                        - cardToAttack.getAttackDamage());
                cardToAttack.hasAttacked = true;
                if (cardToBeAttacked.getHealth() <= 0) {
                    board.getBoard().get(cardAttacked.getX()).getCards().remove(cardAttacked.getY());
                }
            }
            case "cardUsesAbility" -> {
                Card cardToAttack = board.getCardAtPosition(cardAttacker.getX(),
                        cardAttacker.getY());
                Card cardToBeAttacked = board.getCardAtPosition(cardAttacked.getX(),
                        cardAttacked.getY());
                Player opponent = game.getOpponent(player);

                if (cardToAttack.isFrozen) {
                    node.put("cardAttacker", cardAttacker.getCoordinatesObject(mapper));
                    node.put("cardAttacked", cardAttacked.getCoordinatesObject(mapper));
                    node.put("error", "Attacker card is frozen.");
                    return node;
                }

                if (cardToAttack.hasAttacked) {
                    node.put("cardAttacker", cardAttacker.getCoordinatesObject(mapper));
                    node.put("cardAttacked", cardAttacked.getCoordinatesObject(mapper));
                    node.put("error", "Attacker card has already attacked this turn.");
                    return node;
                }

                if (cardToAttack.getName().equals("Disciple")) {
                    if (game.verifyEnemyCoordinates(cardAttacked, player)) {
                        node.put("cardAttacker", cardAttacker.getCoordinatesObject(mapper));
                        node.put("cardAttacked", cardAttacked.getCoordinatesObject(mapper));
                        node.put("error",
                                "Attacked card does not belong to the current player.");
                        return node;
                    }
                } else {
                    if (!game.verifyEnemyCoordinates(cardAttacked, player)) {
                        node.put("cardAttacker", cardAttacker.getCoordinatesObject(mapper));
                        node.put("cardAttacked", cardAttacked.getCoordinatesObject(mapper));
                        node.put("error", "Attacked card does not belong to the enemy.");
                        return node;
                    }

                    if (!cardToBeAttacked.isTank()) {
                        if (game.verifyTankExists(opponent)) {
                            node.put("cardAttacker", cardAttacker.getCoordinatesObject(mapper));
                            node.put("cardAttacked", cardAttacked.getCoordinatesObject(mapper));
                            node.put("error", "Attacked card is not of type 'Tank'.");
                            return node;
                        }
                    }
                }

                cardToAttack.useAbility(cardToBeAttacked);
                cardToAttack.hasAttacked = true;

                if (cardToBeAttacked.getHealth() <= 0) {
                    Deck row = board.getBoard().get(cardAttacked.getX());
                    Card card = row.getCards().get(cardAttacked.getY());
                    row.getCards().remove(cardAttacked.getY());
                }
            }
            case "useAttackHero" -> {
                Card cardToAttack = board.getCardAtPosition(cardAttacker.getX(),
                        cardAttacker.getY());
                Player opponent = game.getOpponent(player);

                if (cardToAttack.isFrozen) {
                    node.put("cardAttacker", cardAttacker.getCoordinatesObject(mapper));
                    node.put("error", "Attacker card is frozen.");
                    return node;
                }

                if (cardToAttack.hasAttacked) {
                    node.put("cardAttacker", cardAttacker.getCoordinatesObject(mapper));
                    node.put("error", "Attacker card has already attacked this turn.");
                    return node;
                }
                if (game.verifyTankExists(opponent)) {
                    node.put("cardAttacker", cardAttacker.getCoordinatesObject(mapper));
                    node.put("error", "Attacked card is not of type 'Tank'.");
                    return node;
                }

                opponent.getHero().setHealth(opponent.getHero().getHealth()
                        - cardToAttack.getAttackDamage());
                cardToAttack.hasAttacked = true;

                if (opponent.getHero().getHealth() <= 0) {
                    stats.setTotalGamesPlayed(stats.getTotalGamesPlayed() + 1);
                    node.remove("command");
                    if (stats.getCurrentPlayer() == 1) {
                        node.put("gameEnded", "Player one killed the enemy hero.");
                        stats.setPlayer1Wins(stats.getPlayer1Wins() + 1);
                    } else {
                        node.put("gameEnded", "Player two killed the enemy hero.");
                        stats.setPlayer2Wins(stats.getPlayer2Wins() + 1);
                    }
                    return node;
                }
            }
            case "useHeroAbility" -> {
                if (player.getHero().getMana() > player.getMana()) {
                    node.put("affectedRow", affectedRow);
                    node.put("error", "Not enough mana to use hero's ability.");
                    return node;
                }

                if (player.getHero().hasAttacked) {
                    node.put("affectedRow", affectedRow);
                    node.put("error", "Hero has already attacked this turn.");
                    return node;
                }

                if (player.getHero().getName().equals("Lord Royce")
                        || player.getHero().getName().equals("Empress Thorina")) {
                    if (!game.verifyRowBelongsToEnemy(affectedRow, player)) {
                        node.put("affectedRow", affectedRow);
                        node.put("error", "Selected row does not belong to the enemy.");
                        return node;
                    }
                } else {
                    if (game.verifyRowBelongsToEnemy(affectedRow, player)) {
                        node.put("affectedRow", affectedRow);
                        node.put("error", "Selected row does not belong to the current player.");
                        return node;
                    }
                }

                player.getHero().useAbility(board.getBoard().get(affectedRow));
                player.getHero().hasAttacked = true;
                player.setMana(player.getMana() - player.getHero().getMana());
            }
            case "endPlayerTurn" -> {
                game.setCardsNotFrozen(board, player);
                if (stats.getCurrentPlayer() == 1) {
                    stats.setCurrentPlayer(2);
                } else {
                    stats.setCurrentPlayer(1);
                }

                if (stats.getCurrentPlayer() == game.getStartingPlayer()) {
                    stats.setNumRound(stats.getNumRound() + 1);
                    game.prepareRound(stats.getNumRound());
                }
            }
            default -> {
                node.put("error", "Invalid command.");
                return node;
            }
        }
        return null;
    }
}
