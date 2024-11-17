package org.poo.game.heroes;

import org.poo.fileio.CardInput;
import org.poo.game.Card;
import org.poo.game.Deck;
import org.poo.game.Hero;

/**
 * Represents the hero General Kocioraw, a subclass of {@link Hero},
 * with the ability "Blood Thirst".
 */
public class GeneralKocioraw extends Hero {

    public GeneralKocioraw(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * Applies the "Blood Thirst" ability: all cards in the specified row receive +1 attack damage.
     *
     * @param row the {@link Deck} representing the row where the ability is used.
     */
    @Override
    public void useAbility(final Deck row) {
        for (Card card : row.getCards()) {
            card.setAttackDamage(card.getAttackDamage() + 1);
        }
    }
}
