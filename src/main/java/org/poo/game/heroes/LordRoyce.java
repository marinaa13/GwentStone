package org.poo.game.heroes;

import org.poo.fileio.CardInput;
import org.poo.game.Card;
import org.poo.game.Deck;
import org.poo.game.Hero;

/**
 * Represents the hero Lord Royce, a subclass of {@link Hero}, with the ability "Sub-Zero".
 */
public class LordRoyce extends Hero {

    public LordRoyce(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * Applies the "Sub-Zero" ability: all cards in the specified row become frozen.
     *
     * @param row the {@link Deck} representing the row where the ability is used.
     */
    @Override
    public void useAbility(final Deck row) {
        for (Card card : row.getCards()) {
            card.setIsFrozen(true);
        }
    }
}
