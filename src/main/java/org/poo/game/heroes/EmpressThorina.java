package org.poo.game.heroes;

import org.poo.fileio.CardInput;
import org.poo.game.Card;
import org.poo.game.Deck;
import org.poo.game.Hero;

/**
 * Represents the hero Empress Thorina, a subclass of {@link Hero},
 * with the ability "Low Blow",
 */
public class EmpressThorina extends Hero {

    public EmpressThorina(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * Applies the "Low Blow" ability: destroys the card with the highest health
     * in the specified row.
     *
     * @param row the {@link Deck} representing the row where the ability is used.
     */
    @Override
    public void useAbility(final Deck row) {
        int maxHealth = 0;
        Card maxCard = null;
        for (Card card : row.getCards()) {
            if (card.getHealth() > maxHealth) {
                maxHealth = card.getHealth();
                maxCard = card;
            }
        }
        row.getCards().remove(maxCard);
    }
}
