package org.poo.game;

import org.poo.fileio.CardInput;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Represents a card in the game with attributes such as mana cost, attack damage,
 * health, description, colors, and name. It includes methods to manipulate card
 * properties and perform actions like generating a JSON representation.
 */
public class Card {
    protected int mana;
    protected int attackDamage;
    protected int health;
    protected String description;
    protected ArrayList<String> colors;
    protected String name;
    protected boolean hasAttacked;
    protected boolean hasUsedAbility;
    protected boolean isFrozen;

    public Card() {
    }

    /**
     * Constructs a card with attributes initialized from the given {@link CardInput}.
     *
     * @param cardInput the input data used to initialize the card's attributes
     */
    public Card(final CardInput cardInput) {
        mana = cardInput.getMana();
        attackDamage = cardInput.getAttackDamage();
        health = cardInput.getHealth();
        description = cardInput.getDescription();
        colors = cardInput.getColors();
        name = cardInput.getName();
    }

    /**
     * Generates a JSON representation of the card's attributes.
     *
     * @param mapper the {@link ObjectMapper} used to create the JSON object
     * @return an {@link ObjectNode} containing the card's attributes
     */
    public ObjectNode getCardNode(final ObjectMapper mapper) {
        ObjectNode node = mapper.createObjectNode();
        node.put("mana", mana);
        node.put("attackDamage", attackDamage);
        node.put("health", health);
        node.put("description", description);
        ArrayNode colorsArray = mapper.createArrayNode();
        for (String color : colors) {
            colorsArray.add(color);
        }
        node.set("colors", colorsArray);
        node.put("name", name);
        return node;
    }

    /**
     * Placeholder method for using the card's ability on another card.
     *
     * @param attackedCard the {@link Card} that is targeted by this card's ability
     */
    public void useAbility(final Card attackedCard) {
    }

    /**
     * Returns the mandatory row for the card, if applicable.
     *
     * @return an integer representing the mandatory row, default is 0
     */
    public int getMandatoryRow() {
        return 0;
    }

    /**
     * Determines if the card is a "Tank."
     *
     * @return {@code true} if the card is a Tank; {@code false} otherwise
     */
    public boolean isTank() {
        return false;
    }

    /**
     * Gets the mana cost of the card.
     *
     * @return the mana cost
     */
    public int getMana() {
        return mana;
    }

    /**
     * Gets the attack damage of the card.
     *
     * @return the attack damage
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Sets the attack damage of the card.
     *
     * @param attackDamage the new attack damage
     */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * Gets the health of the card.
     *
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of the card.
     *
     * @param health the new health value
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Gets the colors associated with the card.
     *
     * @return an {@link ArrayList} of colors
     */
    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     * Gets the name of the card.
     *
     * @return the card's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets whether the card has attacked.
     *
     * @param hasAttacked {@code true} if the card has attacked; {@code false} otherwise
     */
    public void setHasAttacked(final boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    /**
     * Sets whether the card is frozen.
     *
     * @param isFrozen {@code true} if the card is frozen; {@code false} otherwise
     */
    public void setIsFrozen(final boolean isFrozen) {
        this.isFrozen = isFrozen;
    }
}
