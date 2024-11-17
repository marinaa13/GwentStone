package org.poo.game;

/**
 * Represents the statistics of a card game, including the current player,
 * total games played, wins for both players, and the current round number.
 */
public class Stats {
    private int currentPlayer;
    private int totalGamesPlayed;
    private int player1Wins;
    private int player2Wins;
    private int numRound;

    /**
     * Gets the current round number.
     *
     * @return the current round number
     */
    public int getNumRound() {
        return numRound;
    }

    /**
     * Sets the current round number.
     *
     * @param numRound the round number to set
     */
    public void setNumRound(final int numRound) {
        this.numRound = numRound;
    }

    /**
     * Gets the index of the current player (1 or 2).
     *
     * @return the current player index
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the index of the current player (1 or 2).
     *
     * @param currentPlayer the current player index to set
     */
    public void setCurrentPlayer(final int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Gets the total number of games played.
     *
     * @return the total number of games played
     */
    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    /**
     * Sets the total number of games played.
     *
     * @param totalGamesPlayed the total number of games played to set
     */
    public void setTotalGamesPlayed(final int totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    /**
     * Gets the number of wins for Player 1.
     *
     * @return the number of wins for Player 1
     */
    public int getPlayer1Wins() {
        return player1Wins;
    }

    /**
     * Sets the number of wins for Player 1.
     *
     * @param player1Wins the number of wins for Player 1 to set
     */
    public void setPlayer1Wins(final int player1Wins) {
        this.player1Wins = player1Wins;
    }

    /**
     * Gets the number of wins for Player 2.
     *
     * @return the number of wins for Player 2
     */
    public int getPlayer2Wins() {
        return player2Wins;
    }

    /**
     * Sets the number of wins for Player 2.
     *
     * @param player2Wins the number of wins for Player 2 to set
     */
    public void setPlayer2Wins(final int player2Wins) {
        this.player2Wins = player2Wins;
    }
}
