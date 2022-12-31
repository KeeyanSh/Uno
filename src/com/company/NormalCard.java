package com.company;

/**
 * Normal card is a card having no special action.
 */
public class NormalCard extends Card {
    /**
     * The enum Value of the card.
     */
    public enum Value {
        THREE {
            public String toString() {
                return "3";
            }
        },
        FOUR {
            public String toString() {
                return "4";
            }
        },
        FIVE {
            public String toString() {
                return "5";
            }
        },
        SIX {
            public String toString() {
                return "6";
            }
        },
        NINE {
            public String toString() {
                return "9";
            }
        },
        C, D
    }

    private final Value value;

    /**
     * Instantiates a new Normal card.
     *
     * @param color the color
     * @param value the value
     * @param score the score
     */
    public NormalCard(Color color, Value value, int score) {
        super(color, score);
        this.value = value;
    }

    @Override
    public String toString() {
        return "NormalCard{" + super.toString() +
                "value=" + value +
                '}';
    }

    @Override
    public String getValue() {
        return value.toString();
    }
}
