package org.poo.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;

/**
 * Represents a Hero card, which is a special type of card in the game.
 * A Hero card inherits from {@link Card} and has additional properties,
 * such as a fixed health value and the ability to use special abilities.
 */
public class Hero extends Card {

    public Hero() {
    }

    /**
     * Constructor that initializes a Hero object based on the given {@link CardInput}.
     * The health of the Hero is set to 30 by default.
     *
     * @param cardInput the {@link CardInput} object that contains the card's attributes
     */
    public Hero(final CardInput cardInput) {
        super(cardInput);
        health = GlobalVariables.HERO_HEALTH;
    }

    /**
     * Uses the Hero's special ability on the given row (deck of cards).
     * This method can be overridden by subclasses to implement specific abilities.
     *
     * @param row the {@link Deck} where the ability is used
     */
    public void useAbility(final Deck row) {
    }

    /**
     * Generates a JSON representation of the Hero's attributes.
     * This method includes the Hero's mana, description, colors, name, and health.
     *
     * @param mapper the {@link ObjectMapper} used to create the JSON representation
     * @return an {@link ObjectNode} containing the Hero's attributes as a JSON object
     */
    public ObjectNode getHeroNode(final ObjectMapper mapper) {
        ObjectNode node = mapper.createObjectNode();
        node.put("mana", mana);
        node.put("description", description);
        ArrayNode colorsArray = mapper.createArrayNode();
        for (String color : colors) {
            colorsArray.add(color);
        }
        node.set("colors", colorsArray);
        node.put("name", name);
        node.put("health", health);
        return node;
    }
}
