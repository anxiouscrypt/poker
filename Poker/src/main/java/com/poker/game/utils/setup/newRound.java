package com.poker.game.utils.setup;

import com.poker.game.classes.Player;
import com.poker.game.classes.PokerGame;
import com.poker.game.classes.StandardCard;

public interface newRound {
    default void newRound() {
        PokerGame.gameDeck.reset();
        PokerGame.gameDeck.shuffleDeck();
        PokerGame.communityCards = new StandardCard[5];
        StandardCard[] dealerHoleCards = {PokerGame.gameDeck.getNextCard(), PokerGame.gameDeck.getNextCard()};
        StandardCard[] tempHoleCards = {PokerGame.gameDeck.getNextCard(), PokerGame.gameDeck.getNextCard()};
        for (Player player : PokerGame.playerList) {
            player.setHoleCards(tempHoleCards);
        }
        PokerGame.playerList.remove(PokerGame.dealer);
        PokerGame.dealer.setHoleCards(dealerHoleCards);
        PokerGame.playersTotalBets = new int[PokerGame.playerList.size()];
    }
}
