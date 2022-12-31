package com.company;

import java.util.Scanner;

/**
 * The Main.
 */
public class Main {
    private static Scanner sc = new Scanner(System.in);

    /**
     * Creats a new game.
     *
     * @return the created game
     */
    public static Game creator() {
        System.out.println("Enter the number of players : ");
        int playerNum;
        while (true) {
            playerNum = sc.nextInt();
            if (playerNum <= 52 && playerNum > 0)
                break;
            System.out.println("\t\t\t> Invalid Input; Try Once More. <");
        }
        int botNum;
        while (true) {
            System.out.println("How many of these players will be bots ?");
            botNum = sc.nextInt();
            if (botNum <= playerNum)
                break;
            System.out.println("\t\t\t> Invalid Input; Try Once More. <");

        }
        playerNum -= botNum;
        System.out.println("Enter The Number Of Starting Cards : ");
        int cardNum;
        while (true) {
            cardNum = sc.nextInt();
            if (cardNum <= 52 / (playerNum + botNum))
                break;
            System.out.println("\t\t\t> Invalid Input; Try Once More. <");
        }

        String[] names = playerName(playerNum);
        Game game = new Game(playerNum, botNum, names);
        game.deckGenerator();
        game.handGenerator(cardNum);
        return game;
    }

    /**
     *Initializes Player-names.
     *
     * @param num the number of players
     * @return the array of names
     */
    public static String[] playerName(int num) {
        if (num == 0)
            return null;
        String[] names = new String[num];
        System.out.println("Do you wanna enter names or choose by default ??\n>1.Enter\n>Other numbers .Default");
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice == 1)
            for (int i = 0; i < num; i++) {
                System.out.println("Player " + (i + 1) + "'s name :");
                names[i] = sc.nextLine();
            }
        else
            for (int i = 0; i < num; i++)
                names[i] = "Player " + (i + 1);
        return names;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Game game = creator();
        game.gameProcess();
        game.printResults();
    }
}
