package com.poker.game.utils.winningCalculations;

import com.poker.game.classes.PokerGame;
import com.poker.game.classes.Player;

import java.util.ArrayList;

import static com.poker.game.classes.PokerGame.playerList;

public class FindWinner extends WinningCalculations {
    public static void find() {
        /*
         *  Switch if statements to switch case
         */
        ArrayList<Player> winningPlayers;

        playerList.add(PokerGame.dealer);

        //royalFlush
        winningPlayers = royalFlush();
        if (winningPlayers.size() > 0) {
            HandleWinners.handleWinners(winningPlayers);
            System.out.println("Royal Flush");
            System.out.println("Winner(s) are: " + winningPlayers);
            return;
        }
        //straightFlush
        winningPlayers = straightFlush();
        if (winningPlayers.size() > 0) {
            HandleWinners.handleWinners(winningPlayers);
            System.out.println("Straight Flush");
            System.out.println("Winner(s) are: " + winningPlayers);
            return;
        }
        //fourOfAKind
        winningPlayers = fourOfAKind();
        if (winningPlayers.size() > 0) {
            HandleWinners.handleWinners(winningPlayers);
            System.out.println("Four of a Kind");
            System.out.println("Winner(s) are: " + winningPlayers);
            return;
        }
        //fullHouse
        winningPlayers = fullHouse();
        if (winningPlayers.size() > 0) {
            HandleWinners.handleWinners(winningPlayers);
            System.out.println("Full House");
            System.out.println("Winner(s) are: " + winningPlayers);
            return;
        }
        //flush
        winningPlayers = flush();
        if (winningPlayers.size() > 0) {
            HandleWinners.handleWinners(winningPlayers);
            System.out.println("Flush");
            System.out.println("Winner(s) are: " + winningPlayers);
            return;
        }
        //straight
        winningPlayers = straight();
        if (winningPlayers.size() > 0) {
            HandleWinners.handleWinners(winningPlayers);
            System.out.println("Straight");
            System.out.println("Winner(s) are: " + winningPlayers);
            return;
        }
        //threeOfAKind
        winningPlayers = threeOfAKind();
        if (winningPlayers.size() > 0) {
            HandleWinners.handleWinners(winningPlayers);
            System.out.println("Three of a Kind");
            System.out.println("Winner(s) are: " + winningPlayers);
            return;
        }
        //twoPair
        winningPlayers = twoPair();
        if (winningPlayers.size() > 0) {
            HandleWinners.handleWinners(winningPlayers);
            System.out.println("Two Pair");
            System.out.println("Winner(s) are: " + winningPlayers);
            return;
        }
        //onePair
        winningPlayers = onePair();
        if (winningPlayers.size() > 0) {
            HandleWinners.handleWinners(winningPlayers);
            System.out.println("One Pair");
            System.out.println("Winner(s) are: " + winningPlayers);
            return;
        }
        //highCard
        winningPlayers = highCard();
        if (winningPlayers.size() > 0) {
            HandleWinners.handleWinners(winningPlayers);
            System.out.println("High Card");
            System.out.println("Winner(s) are: " + winningPlayers);
        }

    }
}
