package com.poker.game.utils.setup;

import com.poker.game.classes.Player;
import com.poker.game.classes.PokerGame;

import java.util.Scanner;

import static com.poker.game.classes.PokerGame.playerList;

public interface Betting {

    Scanner scan = new Scanner(System.in);

    default void bettingRound() {
        if (playerList.get(0).getIsInGame()) {
            individualBet(0);
        }
        while (!areAllBetsEqual()) {
            individualBet(0);
        }
    }
     default void individualBet(int currentPlayerIndex) {
        String checkOrCall = areAllBetsEqual() ? "check" : "call";
        System.out.println("The pot is: " + PokerGame.winningPot);
        System.out.println(playerList.get(currentPlayerIndex).toString() +
                "\nDo you want to fold, " + checkOrCall + " or raise?");
        String answer = scan.next();
        if (answer.toLowerCase().contains("fold")) {
            fold(playerList.get(currentPlayerIndex));
        }
        else if (answer.toLowerCase().contains("call")) {
            call(playerList.get(currentPlayerIndex));
        }
        else if (answer.toLowerCase().contains("raise")) {
            raise(playerList.get(currentPlayerIndex));
        }
        // Check will do nothing in the program just moves the index to the next player.
    }

     default boolean areAllBetsEqual() {
        int highestBet = getHighestBet();
        for (int j = 0; j < PokerGame.playersTotalBets.length; j++) {
            if (PokerGame.playersTotalBets[j] < highestBet && playerList.get(j).getIsInGame()) {
                return false;
            }
        }
        return true;
    }

     default int getHighestBet() {
        int highestBet = 0;
        for (int i = 0; i < PokerGame.playersTotalBets.length; i++) {
            if (PokerGame.playersTotalBets[i] > highestBet && playerList.get(i).getIsInGame()) {
                highestBet = PokerGame.playersTotalBets[i];
            }
        }
        return highestBet;
    }

    default void fold(Player player) {
        player.setIsInGame(false);
    }

    default void call(Player player) {
        int highestBet = getHighestBet();
        int playersBetDifference = highestBet - (PokerGame.playersTotalBets[playerList.indexOf(player)]);
        player.reduceFromBalance(playersBetDifference);
        PokerGame.playersTotalBets[playerList.indexOf(player)] += playersBetDifference;
        updateWinningPot();
    }

    default void raise(Player player) {
        System.out.println(player.getName() + ": How much do you want to raise?");
        int raiseAmount = scan.nextInt();
        call(player);
        player.reduceFromBalance(raiseAmount);

        PokerGame.playersTotalBets[playerList.indexOf(player)] += raiseAmount;
        updateWinningPot();
    }
     default void updateWinningPot() {
        PokerGame.winningPot = 0;
        for (int playersTotalBet : PokerGame.playersTotalBets) {
            PokerGame.winningPot += playersTotalBet;
        }
    }
}
