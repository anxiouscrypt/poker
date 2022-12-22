package com.poker.game.utils.deal;

import com.poker.game.classes.PokerGame;

public interface deal {
    default void dealFlop() {
        PokerGame.communityCards[0] = PokerGame.gameDeck.getNextCard();
        PokerGame.communityCards[1] = PokerGame.gameDeck.getNextCard();
        PokerGame.communityCards[2] = PokerGame.gameDeck.getNextCard();
    }

    default void dealTurn() {
        PokerGame.communityCards[3] = PokerGame.gameDeck.getNextCard();
    }

    default void dealRiver() {
        PokerGame.communityCards[4] = PokerGame.gameDeck.getNextCard();
    }
}
