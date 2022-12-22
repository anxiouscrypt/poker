package com.poker.game.utils.winningCalculations;

import com.poker.game.classes.PokerGame;
import com.poker.game.classes.Player;

import java.util.ArrayList;

public class HandleWinners {
    public static void handleWinners(ArrayList<Player> winningPlayers) {
        int tempWinnerAward = PokerGame.winningPot * 2;
        for (Player winningPlayer : winningPlayers) {
            String name = winningPlayer.getName();
            winningPlayer.addToBalance(tempWinnerAward);
            if (name != null) {
                PokerGame.playerWins += 1;
            } else {
                PokerGame.dealerWins += 1;
            }
        }
        PokerGame.winningPot = 0;
    }
}
