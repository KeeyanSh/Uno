package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * The type Game manages a game process.
 */
public class Game {
    private ArrayList<Card> deck;
    private Player[] players;
    private Card middle;
    private Random random;
    private int direction;
    private int turn;
    private Scanner sc = new Scanner(System.in);
    private int drawNum = 0, hasDrawn = 0;

    /**
     * Instantiates a new Game.
     *
     * @param playerNum the player's number
     * @param botNum    the bot's number
     * @param names     the names of players
     */
    public Game(int playerNum, int botNum, String[] names) {
        random = new Random();
        deck = new ArrayList<>();
        players = new Player[playerNum + botNum];
        for (int i = 0; i < playerNum; i++)
            players[i] = new Player(names[i]);
        for (int i = 0; i < botNum; i++)
            players[i + playerNum] = new Bot("Bot " + (i + 1));
        direction = 1;
        turn = 0;
    }

    /**
     * Generates a new deck of cars.
     */
    public void deckGenerator() {
        int[] normalScores = {3, 4, 5, 6, 9, 12, 13};
        int i = 0;
        for (Card.Color color : Card.Color.values()) {
            for (NormalCard.Value value : NormalCard.Value.values())
                deck.add(new NormalCard(color, value, normalScores[i++]));
            i = 0;
        }
        middle = deck.get(random.nextInt(deck.size()));
        deck.remove(middle);

        for (Card.Color color : Card.Color.values()) {
            deck.add(new SpecialCard.DirectionCard(color));
            deck.add(new SpecialCard.GiverCard(color));
            deck.add(new SpecialCard.ColorChangeCard(color));
            deck.add(new SpecialCard.SkipCard(color));
            deck.add(new SpecialCard.PrizeCard(color));
            if (color.toString().equals("BLACK"))
                deck.add(new SpecialCard.DrawCard(color, 15));
            else
                deck.add(new SpecialCard.DrawCard(color, 10));
        }
    }

    /**
     * Gives each player an specified number of cards.
     *
     * @param num the number of cards to be given to each player
     */
    public void handGenerator(int num) {
        for (Player player : players)
            for (int i = 0; i < num; i++) {
                Card card = deck.get(random.nextInt(deck.size()));
                player.addCard(card);
                deck.remove(card);
            }
    }

    /**
     * Moves the play-turn forward.
     */
    public void nextTurn() {
        turn = (turn + direction) % players.length;
        if (turn < 0)
            turn += players.length;
    }

    /**
     * Print hidden.
     *
     * @param numerizer the numerizer
     */
    public void printHidden(int numerizer) {
        int holder = turn;
        nextTurn();
        String numString = "";
        while (turn != holder) {
            if (numerizer == 1)
                numString = (turn + 1) + " ";
            System.out.println(numString + "> \"" + players[turn].getName() + "\" : " + players[turn].getCardsNum() + " Cards");
            nextTurn();
        }
        turn = holder;
    }

    /**
     * Is finished boolean.
     *
     * @return the boolean
     */
    public boolean isFinished() {
        for (Player player : players)
            if (player.getCards().size() == 0)
                return true;
        return false;
    }

    /**
     * Special action.
     */
    public void specialAction() {
        if (middle instanceof SpecialCard.DrawCard)
            draw(1);
        else if (middle instanceof SpecialCard.GiverCard)
            giveCard();
        else if (middle instanceof SpecialCard.ColorChangeCard)
            colorChange();
        else if (middle instanceof SpecialCard.PrizeCard) {
            direction *= -1;
            nextTurn();
            direction *= -1;
            System.out.println("\t\t\t> Prize Turn!!! <");
        } else if (middle instanceof SpecialCard.SkipCard) {
            nextTurn();
            System.out.println("\t\t\t> Next Player Skipped <");
        } else if (middle instanceof SpecialCard.DirectionCard) {
            direction *= -1;
            System.out.println("\t\t\t> Direction Changed!! <");
        }
    }

    /**
     * Give card.
     */
    public void giveCard() {
        ArrayList<Card> cards = players[turn].getCards();
        Card chosen = cards.get(random.nextInt(cards.size()));
        players[turn].getCards().remove(chosen);
        int index = -1;

        if (players[turn] instanceof Bot)
            while (index == turn)
                index = random.nextInt(players.length);
        else {
            System.out.println("\nNow choose a Player by entering \"THE NUMBER\" to give a random card :");
            printHidden(1);
            while (true) {
                index = sc.nextInt();
                if (index > 0 && index <= players.length && index - 1 != turn)
                    break;
                System.out.println("Invalid Input;Try Once More");
            }
            index--;
        }
        System.out.println("Do you accept the card  \"" + players[index].getName() + "\" ?\n1.Yes\n Any other number means \"No\"");
        int choice;
        if (players[index] instanceof Bot)
            choice = random.nextInt(2);
        else
            choice = sc.nextInt();

        if (choice == 1) {
            players[turn].getCards().remove(chosen);
            players[index].addCard(chosen);
            System.out.println("\t\t\t> Accepted. <");
        } else
            System.out.println("\t\t\t> Declined <");
        sc.nextLine();
        return;
    }

    /**
     * Draw.
     *
     * @param num the num
     */
    public void draw(int num) {
        if (num == 1) {
            if (hasDrawn == 0)
                hasDrawn = 1;

            drawNum += 2;
            if (middle.getColor().equals("BLACK"))
                drawNum += 2;
            return;
        }
        for (int i = 0; i < drawNum; i++) {
            Card draw = deck.get(random.nextInt(deck.size()));
            deck.remove(draw);
            players[turn].addCard(draw);
            if(!(players[turn] instanceof Bot))
            draw.printCard();
        }
        System.out.println("\t\t\tYou Have Drawn " + drawNum + " Cards :(");
        drawNum = 0;
        hasDrawn = 0;
    }

    /**
     * Color change.
     */
    public void colorChange() {
        System.out.println("Enter The New Color :  ");
        int index;
        Card.Color[] colors = Card.Color.values();
        if (players[turn] instanceof Bot)
            index = random.nextInt(colors.length);
        else
            while (true) {
                int i = 1;
                for (Card.Color color : colors)
                    System.out.println(i++ + " > " + color.toString());
                System.out.print("choose : ");
                index = sc.nextInt();
                if (index > 0 && index <= colors.length) {
                    index--;
                    break;
                }
                System.out.println("\t\t\t> Invalid Input;Try Once More <");
            }
        middle.setColor(colors[index]);
        System.out.println("\t\t\t> Color changed to " + colors[index].toString() + " <");
        return;
    }

    /**
     * Pick int.
     *
     * @param isPicked the is picked
     * @return the int
     */
    public int pick(int isPicked) {
        if (isPicked == 0) {
            if (!(players[turn] instanceof Bot)) {
                System.out.println("No Matching Cards\nPress Enter To Pick One From The Deck");
                sc.nextLine();
            }
            Card picked = deck.get(random.nextInt(deck.size()));
            players[turn].addCard(picked);
            deck.remove(picked);
            if (!(players[turn] instanceof Bot))
            picked.printCard();

            System.out.println("\t\t\tPicked A Card From The Deck!!!\n\t\t\tNow play once more");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException x) {
            }
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            return 1;
        } else {
            System.out.println("No Matching Cards; Next Turn");
            if (hasDrawn == 1)
                draw(-1);
            return 0;
        }
    }

    /**
     * Manages the Game's process.
     */
    public void gameProcess() {
        int isPicked = 0;
        turn = random.nextInt(players.length);

        while (!isFinished()) {
            System.out.println("━━━━━━━━━━━━ " + ((direction == 1) ? "ClockWise" : "Anti-ClockWise") + " ━━━━━━━━━━━━\nNext Turns :");
            printHidden(0);
            middle.printCard();
            players[turn].printCards();
            System.out.println("\"" + players[turn].getName() + "\" it is your turn :  ");
            Card chosen = players[turn].choose(middle);
            if (chosen == null) {
                if(hasDrawn==1)
                    draw(-1);
                isPicked = pick(isPicked);
                if (isPicked == 1)
                    continue;
            } else {
                deck.add(middle);
                middle = chosen;
                System.out.println("\t\t\t> Nice Play <");
            }
            if (isFinished())
                break;
            if (!(middle instanceof SpecialCard.DrawCard) && hasDrawn == 1) {
                draw(-1);

            }
            if (chosen instanceof SpecialCard)
                specialAction();

            nextTurn();
            isPicked = 0;
            System.out.println("\n\t> Press enter to continue < ");
            sc.nextLine();
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        }
    }

    /**
     * Prints the final results.
     */
    public void printResults() {
        int[] scores = new int[players.length];

        for (int i = 0; i < players.length; i++)
            for (int j = i + 1; j < players.length; j++)
                if (players[j].getScore() < players[i].getScore()) {
                    Player tmp = players[i];
                    players[i] = players[j];
                    players[j] = tmp;
                }
        int i = 1;
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n━━━━━━━━━━━━ Results ━━━━━━━━━━━━");
        for (Player player : players)
            System.out.println((i++) + " > " + player.getName() + " -- Score : " + player.getScore());
    }
}