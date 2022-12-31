package com.company;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Player of a game.
 */
public class Player {
    /**
     * The enum Print color of the cards.
     */
    public enum printColor {
        /**
         * The Reset.
         */
        RESET {
            public String toString() {
                return "\u001B[0m";
            }
        },
        /**
         * The Black.
         */
        BLACK {
            public String toString() {
                return "\u001B[30m";
            }
        },
        /**
         * The Red.
         */
        RED {
            public String toString() {
                return "\u001B[31m";
            }
        },
        /**
         * The Green.
         */
        GREEN {
            public String toString() {
                return "\u001B[32m";
            }
        },
        /**
         * The Blue.
         */
        BLUE {
            public String toString() {
                return "\u001B[34m";
            }
        }
    }

    /**
     * The Cards.
     */
    protected ArrayList<Card> cards;
    private final String name;

    /**
     * Instantiates a new Player.
     *
     * @param name the name
     */
    public Player(String name) {
        this.name = name;
        cards = new ArrayList<>();
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets total score.
     *
     * @return the score
     */
    public int getScore() {
        int score = 0;
        for (Card card : cards)
            score += card.getScore();

        return score;
    }

    /**
     * Print cards.
     */
    public void printCards() {
        for (Card card : cards) {
            System.out.print(printColor.valueOf(card.getColor()) + "┍━━━━");
        }
        System.out.println("━━┑");

        for (Card card : cards) {
            System.out.print(printColor.valueOf(card.getColor()) + "│");
            System.out.printf("%2s  ", card.getValue());
        }
        System.out.println("  |");

        for (int i = 0; i < 2; i++) {
            for (Card card : cards)
                System.out.print(printColor.valueOf(card.getColor()) + "│    ");

            if (i == 0)
                System.out.println("  │");
            else {
                System.out.printf("%2s", cards.get(cards.size() - 1).getValue());
                System.out.println("│");
            }
        }
        for (Card card : cards)
            System.out.print(printColor.valueOf(card.getColor()) + "┕━━━━");

        System.out.println("━━┙" + printColor.valueOf("RESET"));
        for (int i = 1; i <= cards.size(); i++)
            System.out.print(i + "    ");
        System.out.println();
    }

    /**
     * Gets cards.
     *
     * @return the cards
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Gets the number of the cards.
     *
     * @return the cards num
     */
    public int getCardsNum() {
        return cards.size();
    }

    /**
     * Adds a card.
     *
     * @param card the new card
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Produces a list of cards which can be played.
     *
     * @param middle the middle card in the game
     * @return the list of cards that can be played
     */
    public ArrayList<Integer> matchingCards(Card middle) {
        ArrayList<Integer> matchings = new ArrayList<>();
        int index = 0;
        for (Card card : cards) {
            if (card.match(middle))
                matchings.add(index);
            index++;
        }
        return matchings;
    }

    /**
     * Choose card.
     *
     * @param middle the middle card in the game
     * @return the card chosen to be played
     */
    public Card choose(Card middle) {
        ArrayList<Integer> matchings = matchingCards(middle);
        if (matchings.size() == 0)
            return null;

        Scanner sc = new Scanner(System.in);
        while (true) {
            int cardNum = sc.nextInt();
            if (cardNum > 0 && cardNum <= (cards.size())) {
                if (matchings.contains(cardNum - 1)) {
                    Card chosen = cards.get(cardNum - 1);
                    cards.remove(cardNum - 1);
                    return chosen;
                }
            }
            System.out.println("Invalid Input; Try Once More");
        }
    }
}

