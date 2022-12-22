package com.poker.game.utils.setup;

import com.poker.game.classes.PokerGame;
import com.poker.game.classes.Player;
import com.poker.game.classes.StandardCard;

import java.util.Scanner;

public class PlayerSetup {

    private static final int numberOfPlayers = 1;

    static Scanner scan = new Scanner(System.in);
    public static void playerSetup() {
        StandardCard[] tempHoleCards = {PokerGame.gameDeck.getNextCard(), PokerGame.gameDeck.getNextCard()};
        addPlayer();
        PokerGame.playersTotalBets = new int[PokerGame.playerList.size()];
        PokerGame.dealer.setHoleCards(tempHoleCards);
    }

    public static void addPlayer() {
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Enter player name:");
            String tempName = scan.next();
            System.out.println("Enter " + tempName + "'s starting balance:");
            int tempBalance = scan.nextInt();
            StandardCard[] tempHoleCards = {PokerGame.gameDeck.getNextCard(), PokerGame.gameDeck.getNextCard()};
            PokerGame.playerList.add(new Player(tempName, tempBalance, tempHoleCards));
        }
    }
}
