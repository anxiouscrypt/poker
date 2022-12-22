package com.poker.game.classes;

import com.poker.game.utils.deal.deal;
import com.poker.game.utils.setup.Betting;
import com.poker.game.utils.setup.PlayerSetup;
import com.poker.game.utils.setup.newRound;
import com.poker.game.utils.winningCalculations.FindWinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;



public class PokerGame implements Betting, newRound, deal, PlayerSetup {
    public static ArrayList<Player> playerList;
    public static StandardDeck gameDeck;
    public static StandardCard[] communityCards;
    public static int winningPot;
    public static int[] playersTotalBets;
    public static Player dealer;
    public static float dealerWins;
    public static float playerWins;

    Scanner scan = new Scanner(System.in);

    public PokerGame() {
        playerList = new ArrayList<>();
        gameDeck = new StandardDeck();
        communityCards = new StandardCard[5];
        winningPot = 0;
        boolean keepPlaying = true;
        dealer = new Player();
        playerSetup();
        while (keepPlaying) {
            for (Player player : playerList) {
                player.setIsInGame(true);
            }

            // First round of betting (pre-flop)
            bettingRound();
            // Deal the flop to the community cards
            dealFlop();
            printCommunityCards();
            // Second round of betting
            bettingRound();
            // Deal the turn (1 more community card)
            dealTurn();
            printCommunityCards();
            // Third round of betting
            bettingRound();
            // Deal the river (last community card)
            dealRiver();
            printCommunityCards();
            // Determine winner
            printDealerHoleCards();
            FindWinner.find();

            System.out.println("Do you want to play another round? (Y/N)");
            String newGame = scan.next();
            if (newGame.toLowerCase().contains("n")) {
                keepPlaying = false;
            }
            newRound();

        }
        System.out.println("Dealer Wins: " + dealerWins + " " + (dealerWins / (dealerWins + playerWins) * 100) + "%");
        System.out.println("Player Wins: " + playerWins + " " + (playerWins / (dealerWins + playerWins) * 100) + "%");
        System.out.println("Games Played: " + (dealerWins + playerWins));

    }

    private void printDealerHoleCards() {
        System.out.println(Arrays.toString(dealer.getHoleCards()));

    }

    public void printCommunityCards() {
        System.out.println("-----------------------------------------------------");
        if (communityCards[0] != null && communityCards[3] == null && communityCards[4] == null) { // Flop
            System.out.println("The flop:");
        }
        else if (communityCards[3] != null && communityCards[4] == null) { // Turn
            System.out.println("The turn:");
        }
        else if (communityCards[4] != null) {
            System.out.println("The river:");
        }
        System.out.println(Arrays.toString(communityCards));
        System.out.println("-----------------------------------------------------");
    }
}
