package org.poo.game;

import org.poo.fileio.CardInput;

/**
 * Represents a special class of cards that inherit from {@link Minion} and have a unique ability.
 * These cards have special abilities that can affect other cards in the game.
 */
public class SpecialAbilityCards extends Minion {

    /**
     * Constructs a new SpecialAbilityCards instance from the given {@link CardInput}.
     *
     * @param cardInput the {@link CardInput} object containing the card's attributes
     */
    public SpecialAbilityCards(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * This method is meant to be overridden by subclasses to implement the unique ability
     * of the card. In the base class, it does nothing.
     *
     * @param attackedCard the {@link Card} that is being attacked or interacted with
     */
    @Override
    public void useAbility(final Card attackedCard) {
    }
}

/**
 * Represents the "The Ripper" card, a type of {@link SpecialAbilityCards}.
 * This card's ability decreases the attack damage to the attacked card by 2.
 */
class TheRipper extends SpecialAbilityCards {

    TheRipper(final CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public void useAbility(final Card attackedCard) {
        attackedCard.setAttackDamage(attackedCard.getAttackDamage() - 2);
        if (attackedCard.getAttackDamage() < 0) {
            attackedCard.setAttackDamage(0);
        }
    }
}

/**
 * Represents the "Miraj" card, a type of {@link SpecialAbilityCards}.
 * This card's ability swaps its health with that of the attacked card.
 */
class Miraj extends SpecialAbilityCards {

    Miraj(final CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public void useAbility(final Card attackedCard) {
        int aux;
        aux = attackedCard.getHealth();
        attackedCard.setHealth(health);
        health = aux;
    }
}

/**
 * Represents the "The Cursed One" card, a type of {@link SpecialAbilityCards}.
 * This card's ability swaps the health and attack damage of the attacked card.
 */
class TheCursedOne extends SpecialAbilityCards {

    TheCursedOne(final CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public void useAbility(final Card attackedCard) {
        int aux;
        aux = attackedCard.getHealth();
        attackedCard.setHealth(attackedCard.getAttackDamage());
        attackedCard.setAttackDamage(aux);
    }
}

/**
 * Represents the "Disciple" card, a type of {@link SpecialAbilityCards}.
 * This card's ability heals the attacked card by 2 health points.
 */
class Disciple extends SpecialAbilityCards {

    Disciple(final CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public void useAbility(final Card attackedCard) {
        attackedCard.setHealth(attackedCard.getHealth() + 2);
    }
}
