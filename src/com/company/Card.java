package com.company;

/**
 * The type Card.
 */
abstract public class Card {
    /**
     * The enum Color.
     */
    public enum Color {
        /**
         * Red color.
         */
        RED,
        /**
         * Blue color.
         */
        BLUE,
        /**
         * Black color.
         */
        BLACK,
        /**
         * Green color.
         */
        GREEN
    }

    private Color color;
    private final int score;

    /**
     * Instantiates a new Card.
     *
     * @param color the color
     * @param score the score
     */
    public Card(Color color, int score) {
        this.color = color;
        this.score = score;
    }

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Check if a card can be played.
     *
     * @param card the card to be checked
     * @return a boolean to show if it can be played
     */
    public boolean match(Card card) {
        if (this instanceof SpecialCard.ColorChangeCard
                || card.getColor().equals(this.getColor())
                || card.getValue().equals(this.getValue()))
            return true;

        return false;
    }

    /**
     * Prints card.
     */
    public void printCard() {
        System.out.println(Player.printColor.valueOf(getColor()) + "\t\t\t┍━━━━━━┑");
        System.out.printf("\t\t\t│%2s    │\n", getValue());
        System.out.println("\t\t\t│      │");
        System.out.printf("\t\t\t│   %2s │\n", getValue());
        System.out.println("\t\t\t┕━━━━━━┙");
        System.out.println(Player.printColor.valueOf("RESET"));
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public String getColor() {
        return color.toString();
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    abstract public String getValue();
}
