package org.poo.game.heroes;

import org.poo.fileio.CardInput;
import org.poo.game.Card;
import org.poo.game.Deck;
import org.poo.game.Hero;

/**
 * Represents the hero King Mudface, a subclass of {@link Hero}, with the ability "Earth Born".
 */
public class KingMudface extends Hero {

    public KingMudface(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * Applies the "Earth Born" ability: all cards in the specified row receive +1 health.
     *
     * @param row the {@link Deck} representing the row where the ability is used.
     */
    @Override
    public void useAbility(final Deck row) {
        for (Card card : row.getCards()) {
            card.setHealth(card.getHealth() + 1);
        }
    }
}
