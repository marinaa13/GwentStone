package org.poo.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.fileio.CardInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Represents a deck of cards in the game. Provides functionality for initializing,
 * shuffling, freezing, and retrieving cards in various formats.
 */
public class Deck {
    private final ArrayList<Card> cards = new ArrayList<>();

    public Deck() {
    }

    /**
     * Constructs a {@code Deck} using a list of card inputs. Creates appropriate card
     * objects based on their name and adds them to the deck.
     *
     * @param cardsInput a list of {@link CardInput} objects to populate the deck
     */
    public Deck(final ArrayList<CardInput> cardsInput) {
        for (CardInput cardInput : cardsInput) {
            switch (cardInput.getName()) {
                case "Sentinel", "Berserker", "Goliath", "Warden" ->
                        cards.add(new Minion(cardInput));
                case "The Ripper" -> cards.add(new TheRipper(cardInput));
                case "Miraj" -> cards.add(new Miraj(cardInput));
                case "The Cursed One" -> cards.add(new TheCursedOne(cardInput));
                case "Disciple" -> cards.add(new Disciple(cardInput));
                default -> cards.add(new Card(cardInput));
            }
        }
    }

    /**
     * Shuffles the deck using a specified random seed.
     *
     * @param seed the seed used to shuffle the deck
     */
    public void shuffleDeck(final int seed) {
        Random rand = new Random(seed);
        Collections.shuffle(cards, rand);
    }

    /**
     * Unfreezes all cards in the deck by setting their frozen state to {@code false}.
     */
    public void setNotFrozen() {
        for (Card card : cards) {
            card.setIsFrozen(false);
        }
    }

    /**
     * Generates a JSON array representation of the deck's cards.
     *
     * @param mapper the {@link ObjectMapper} used to create the JSON array
     * @return an {@link ArrayNode} containing JSON representations of the cards
     */
    public ArrayNode getDeckArray(final ObjectMapper mapper) {
        ArrayNode deckArray = mapper.createArrayNode();
        for (Card card : cards) {
            deckArray.add(card.getCardNode(mapper));
        }
        return deckArray;
    }

    /**
     * Retrieves the size of the deck.
     *
     * @return the number of cards in the deck
     */
    public int getSize() {
        return cards.size();
    }

    /**
     * Retrieves the list of cards in the deck.
     *
     * @return an {@link ArrayList} of {@link Card} objects in the deck
     */
    public ArrayList<Card> getCards() {
        return cards;
    }
}
