package org.poo.game;

import java.util.ArrayList;

/**
 * Represents a player in the game, holding info about the player's deck, hero, hand, and mana.
 * The player can draw cards from the deck and interact with their cards and hero during the game.
 */
public class Player {
    private Deck deck;
    private Hero hero;
    private final Deck hand;
    private int mana;

    /**
     * Constructs a new player with the specified deck and hero.
     * Initializes the player's hand as an empty deck.
     *
     * @param deck the {@link Deck} object representing the player's deck
     * @param hero the {@link Hero} object representing the player's hero
     */
    public Player(final Deck deck, final Hero hero) {
        this.hero = hero;
        this.deck = deck;
        this.hand = new Deck(new ArrayList<>());
    }

    /**
     * Draws a card from the player's deck and adds it to the player's hand.
     * If the deck is empty, no card is drawn.
     */
    public void drawCard() {
        if (deck.getSize() > 0) {
            this.getHand().getCards().add(this.getDeck().getCards().get(0));
            this.getDeck().getCards().remove(0);
        }
    }

    /**
     * Gets the current mana of the player.
     *
     * @return the current mana of the player
     */
    public int getMana() {
        return mana;
    }

    /**
     * Sets the mana of the player.
     *
     * @param mana the new mana value to be set
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * Gets the hand of the player, which contains the cards currently in play.
     *
     * @return the player's {@link Deck} representing their hand
     */
    public Deck getHand() {
        return hand;
    }

    /**
     * Gets the deck of the player, which contains the cards the player can draw from.
     *
     * @return the player's {@link Deck} representing their deck
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Sets the deck of the player to a new {@link Deck}.
     *
     * @param deck the new {@link Deck} object representing the player's deck
     */
    public void setDeck(final Deck deck) {
        this.deck = deck;
    }

    /**
     * Gets the hero of the player, which represents the player's character with special abilities.
     *
     * @return the player's {@link Hero} object representing their hero
     */
    public Hero getHero() {
        return hero;
    }

    /**
     * Sets the hero of the player to a new {@link Hero}.
     *
     * @param hero the new {@link Hero} object representing the player's hero
     */
    public void setHero(final Hero hero) {
        this.hero = hero;
    }
}


