package com.company;

/**
 * The type Special card can do action.
 */
public class SpecialCard extends Card {
    private final String value;

    /**
     * Instantiates a new Special card.
     *
     * @param color the color
     * @param score the score
     * @param value the value
     */
    public SpecialCard(Color color, int score, String value) {
        super(color, score);
        this.value = value;
    }

    /**
     * The type Skip card skips the next player.
     */
    public static class SkipCard extends SpecialCard {
        /**
         * Instantiates a new Skip card.
         *
         * @param color the color
         */
        public SkipCard(Color color) {
            super(color, 11, "A");
        }
    }

    @Override
    public String getValue() {
        return value;
    }

    /**
     * The type Draw card draws cards from the deck.
     */
    public static class DrawCard extends SpecialCard {
        /**
         * Instantiates a new Draw card.
         *
         * @param color the color
         * @param score the score
         */
        public DrawCard(Color color, int score) {
            super(color, score, "7");
        }
    }

    /**
     * The type Direction card changes the game direction.
     */
    public static class DirectionCard extends SpecialCard {
        /**
         * Instantiates a new Direction card.
         *
         * @param color the color
         */
        public DirectionCard(Color color) {
            super(color, 10, "10");
        }
    }

    /**
     * The type Color change card changes the middle card's color.
     */
    public static class ColorChangeCard extends SpecialCard {
        /**
         * Instantiates a new Color change card.
         *
         * @param color the color
         */
        public ColorChangeCard(Color color) {
            super(color, 12, "B");
        }
    }

    /**
     * The type Giver card gives a randomr card to another player.
     */
    public static class GiverCard extends SpecialCard {
        /**
         * Instantiates a new Giver card.
         *
         * @param color the color
         */
        public GiverCard(Color color) {
            super(color, 2, "2");
        }
    }

    /**
     * The type Prize card gives the player one more chance to play.
     */
    public static class PrizeCard extends SpecialCard {
        /**
         * Instantiates a new Prize card.
         *
         * @param color the color
         */
        public PrizeCard(Color color) {
            super(color, 8, "8");
        }
    }
}
