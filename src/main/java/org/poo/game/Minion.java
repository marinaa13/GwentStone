package org.poo.game;

import org.poo.fileio.CardInput;

/**
 * Represents a Minion card, which is a type of {@link Card} in the game.
 * Minions have specific attributes such as whether they are a tank or not, and
 * which row they must be placed in. The Minion class is used to represent
 * specific minion cards like "Sentinel", "Berserker", "Goliath", and "Warden".
 */
public class Minion extends Card {
    private int isTank;

    /**
     * Constructor for creating a Minion object based on the provided {@link CardInput}.
     * The constructor initializes the Minion's tank status based on the card's name.
     *
     * @param cardInput the {@link CardInput} object containing the Minion's attributes
     */
    public Minion(final CardInput cardInput) {
        super(cardInput);
        if (cardInput.getName().equals("Sentinel") || cardInput.getName().equals("Berserker")) {
            isTank = 0;
        } else if (cardInput.getName().equals("Warden") || cardInput.getName().equals("Goliath")) {
            isTank = 1;
        }
    }

    /**
     * Determines if the Minion is a tank.
     *
     * @return true if the Minion is a tank (Goliath, Warden),
     * false otherwise (Sentinel, Berserker)
     */
    @Override
    public boolean isTank() {
        return isTank == 1;
    }

    /**
     * Defines the behavior of the Minion's ability.
     * This method is currently a placeholder and does not perform any actions.
     * It can be overridden in subclasses or specific Minion types to implement unique abilities.
     *
     * @param attackedCard the {@link Card} that is attacked by this Minion (if applicable)
     */
    @Override
    public void useAbility(final Card attackedCard) {
    }

    /**
     * Determines the mandatory row for this Minion to be placed in.
     * - Minions that are tanks or specific Minions like "The Ripper" and "Miraj"
     * must be placed in the front row.
     * - All other Minions must be placed in the back row.
     *
     * @return the row index where the Minion must be placed:
     * 1 for the front row, 0 for the back row
     */
    @Override
    public int getMandatoryRow() {
        if (this.isTank() || this.name.equals("The Ripper") || this.name.equals("Miraj")) {
            return 1;
        } else {
            return 0;
        }
    }
}


