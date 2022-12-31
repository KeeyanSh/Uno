package com.company;

import java.util.ArrayList;
import java.util.Random;

/**
 * The type Bot is a bot player.
 */
public class Bot extends Player {
    /**
     * The list of cards that are not going to be shown.
     */
    ArrayList<Card> hiddenCards;

    /**
     * Instantiates a new Bot.
     *
     * @param name the name
     */
    public Bot(String name) {
        super(name);
        hiddenCards = new ArrayList<>();
    }

    /**
     * Checks if the middle-card is a seven card.
     *
     * @param matchings the cards that can be played
     * @return the card chosen to be played
     */
    public Card checkSeven(ArrayList<Integer> matchings) {
        for (Integer index : matchings)
            if (cards.get(index).getValue().equals("7")) {
                Card chosen = cards.get(index);
                cards.remove(chosen);
                System.out.println("Chosen card by the Bot:");
                chosen.printCard();
                return chosen;
            }
        return null;
    }

    @Override
    public void printCards() {
        int num = cards.size();
        for (int i = 0; i < num; i++)
            System.out.print("┍━━━━");
        System.out.println("━━┑");

        for (int i = 0; i < num; i++)
            System.out.print("│    ");
        System.out.println("  |");

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < num; j++)
                System.out.print("│    ");
            System.out.println("  │");
        }
        for (int i = 0; i < num; i++)
            System.out.print("┕━━━━");
        System.out.println("━━┙");
        System.out.println();

    }

    @Override
    public Card choose(Card middle) {
        super.matchingCards(middle);
        ArrayList<Integer> matchings = matchingCards(middle);
        if (matchings.size() == 0)
            return null;
        Card chosen;
        if (middle.getValue().equals("7")) {
            chosen = checkSeven(matchings);
            if (chosen != null) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException x) {
                }
            }
            return chosen;
        }
        Random random = new Random();
        chosen = cards.get(matchings.get(random.nextInt(matchings.size())));
        cards.remove(chosen);
        System.out.println("Chosen card by the Bot:");
        chosen.printCard();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException x) {
        }
        return chosen;
    }
}
