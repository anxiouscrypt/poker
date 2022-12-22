package com.poker.game.utils.winningCalculations;

import com.poker.game.classes.PokerGame;
import com.poker.game.classes.Player;
import com.poker.game.classes.StandardCard;

import java.util.ArrayList;
import java.util.Collections;

import static com.poker.game.classes.PokerGame.playerList;

public class WinningCalculations {
    public static ArrayList<Player> royalFlush() {
        ArrayList<Player> winningPlayers = new ArrayList<>();
        for (Player player : playerList) {
            int hearts = 0;
            int diamonds = 0;
            int spades = 0;
            int clubs = 0;
            if (player.getIsInGame()) {
                ArrayList<Integer> tempPlayerCards = new ArrayList<>();
                ArrayList<StandardCard> tempPlayersCardsAll = tempPlayersCardsAll(player);
                for (StandardCard standardCard : tempPlayersCardsAll) {
                    if (standardCard.getSuit().contains("Hearts")) {
                        hearts++;
                    }
                    if (standardCard.getSuit().contains("Diamonds")) {
                        diamonds++;
                    }
                    if (standardCard.getSuit().contains("Spades")) {
                        spades++;
                    }
                    if (standardCard.getSuit().contains("Clubs")) {
                        clubs++;
                    }
                }
                if (hearts >= 5) {
                    tempPlayerCards = StandardCard.getValuesOfSuit(tempPlayersCardsAll, "Hearts");
                } else if (diamonds >= 5) {
                    tempPlayerCards = StandardCard.getValuesOfSuit(tempPlayersCardsAll, "Diamonds");
                } else if (spades >= 5) {
                    tempPlayerCards = StandardCard.getValuesOfSuit(tempPlayersCardsAll, "Spades");
                } else if (clubs >= 5) {
                    tempPlayerCards = StandardCard.getValuesOfSuit(tempPlayersCardsAll, "Clubs");
                }

                if (tempPlayerCards.contains(10) &&
                        tempPlayerCards.contains(11) &&
                        tempPlayerCards.contains(12) &&
                        tempPlayerCards.contains(13) &&
                        tempPlayerCards.contains(14)) {
                    winningPlayers.add(player);
                }
            }
        }
        return winningPlayers;
    }

    public static ArrayList<Player> straightFlush() {
        ArrayList<Player> winningPlayers = new ArrayList<>();
        for (Player player : PokerGame.playerList) {
            int hearts = 0;
            int diamonds = 0;
            int spades = 0;
            int clubs = 0;
            if (player.getIsInGame()) {
                ArrayList<Integer> tempPlayerCards = new ArrayList<>();
                ArrayList<StandardCard> tempPlayersCardsAll = tempPlayersCardsAll(player);
                for (StandardCard standardCard : tempPlayersCardsAll) {
                    if (standardCard.getSuit().contains("Hearts")) {
                        hearts++;
                    }
                    if (standardCard.getSuit().contains("Diamonds")) {
                        diamonds++;
                    }
                    if (standardCard.getSuit().contains("Spades")) {
                        spades++;
                    }
                    if (standardCard.getSuit().contains("Clubs")) {
                        clubs++;
                    }
                }
                if (hearts >= 5) {
                    tempPlayerCards = StandardCard.getValuesOfSuit(tempPlayersCardsAll, "Hearts");
                } else if (diamonds >= 5) {
                    tempPlayerCards = StandardCard.getValuesOfSuit(tempPlayersCardsAll, "Diamonds");
                } else if (spades >= 5) {
                    tempPlayerCards = StandardCard.getValuesOfSuit(tempPlayersCardsAll, "Spades");
                } else if (clubs >= 5) {
                    tempPlayerCards = StandardCard.getValuesOfSuit(tempPlayersCardsAll, "Clubs");
                }
                Collections.sort(tempPlayerCards);
                int lowestValue;
                int secondHighestValue = 15;
                int thirdHighestValue = 15;
                if (tempPlayerCards.size() >= 5) {
                    lowestValue = tempPlayerCards.get(0);
                    secondHighestValue = tempPlayerCards.get(1);
                    thirdHighestValue = tempPlayerCards.get(2);
                    if (tempPlayerCards.contains(lowestValue + 1) &&
                            tempPlayerCards.contains(lowestValue + 2) &&
                            tempPlayerCards.contains(lowestValue + 3) &&
                            tempPlayerCards.contains(lowestValue + 4)) {
                        winningPlayers.add(player);
                    }
                }
                if (tempPlayerCards.size() >= 6) {
                    if (tempPlayerCards.contains(secondHighestValue + 1) &&
                            tempPlayerCards.contains(secondHighestValue + 2) &&
                            tempPlayerCards.contains(secondHighestValue + 3) &&
                            tempPlayerCards.contains(secondHighestValue + 4)) {
                        winningPlayers.add(player);
                    }
                }
                if (tempPlayerCards.size() >= 7) {
                    if (tempPlayerCards.contains(thirdHighestValue + 1) &&
                            tempPlayerCards.contains(thirdHighestValue + 2) &&
                            tempPlayerCards.contains(thirdHighestValue + 3) &&
                            tempPlayerCards.contains(thirdHighestValue + 4)) {
                        winningPlayers.add(player);
                    }
                }
            }
        }
        return winningPlayers;
    }

    public static ArrayList<Player> fourOfAKind() {
        ArrayList<Player> winningPlayers = new ArrayList<>();
        for (Player tempPlayer : PokerGame.playerList) {
            if (tempPlayer.getIsInGame()) {
                int[] allValues = new int[13];
                ArrayList<StandardCard> tempPlayersCardsAll = tempPlayersCardsAll(tempPlayer);
                for (StandardCard standardCard : tempPlayersCardsAll) {
                    allValues[standardCard.getValue() - 2]++;
                }
                for (int allValue : allValues) {
                    if (allValue >= 4) {
                        winningPlayers.add(tempPlayer);
                    }
                }
            }
        }
        return winningPlayers;
    }

    public static ArrayList<Player> fullHouse() {
        ArrayList<Player> winningPlayers = new ArrayList<>();
        for (Player tempPlayer : PokerGame.playerList) {
            if (tempPlayer.getIsInGame()) {
                int[] allValues = new int[13];
                boolean threeCard = false;
                boolean twoCard = false;

                ArrayList<StandardCard> tempPlayersCardsAll = tempPlayersCardsAll(tempPlayer);
                for (StandardCard standardCard : tempPlayersCardsAll) {
                    allValues[standardCard.getValue() - 2]++;
                }
                for (int allValue : allValues) {
                    if (allValue >= 3) {
                        threeCard = true;
                    } else if (allValue > 1) {
                        twoCard = true;
                    }
                }
                if (threeCard && twoCard) {
                    winningPlayers.add(tempPlayer);
                }
            }
        }
        return winningPlayers;
    }

    public static ArrayList<Player> flush() {
        ArrayList<Player> winningPlayers = new ArrayList<>();
        for (int i = 0; i < PokerGame.playerList.size(); i++) {
            int hearts = 0;
            int diamonds = 0;
            int spades = 0;
            int clubs = 0;
            Player tempPlayer = PokerGame.playerList.get(i);
            if (tempPlayer.getIsInGame()) {
                ArrayList<StandardCard> tempPlayersCardsAll = tempPlayersCardsAll(tempPlayer);
                for (StandardCard standardCard : tempPlayersCardsAll) {
                    if (standardCard.getSuit().contains("Hearts")) {
                        hearts++;
                    }
                    if (standardCard.getSuit().contains("Diamonds")) {
                        diamonds++;
                    }
                    if (standardCard.getSuit().contains("Spades")) {
                        spades++;
                    }
                    if (standardCard.getSuit().contains("Clubs")) {
                        clubs++;
                    }
                }
                if (hearts >= 5 || diamonds >= 5 || spades >= 5 || clubs >= 5) {
                    winningPlayers.add(tempPlayer);
                }
            }
        }
        return winningPlayers;
    }

    public static ArrayList<Player> straight() {
        ArrayList<Player> winningPlayers = new ArrayList<>();
        for (int i = 0; i < PokerGame.playerList.size(); i++) {
            Player tempPlayer = PokerGame.playerList.get(i);
            if (tempPlayer.getIsInGame()) {
                ArrayList<StandardCard> tempPlayersCardsAll = tempPlayersCardsAll(tempPlayer);
                ArrayList<Integer> tempPlayerCards = StandardCard.getValuesOfAllCards(tempPlayersCardsAll);

                Collections.sort(tempPlayerCards);
                int lowestValue;
                int secondHighestValue = 15;
                int thirdHighestValue = 15;
                if (tempPlayerCards.size() >= 5) {
                    lowestValue = tempPlayerCards.get(0);
                    secondHighestValue = tempPlayerCards.get(1);
                    thirdHighestValue = tempPlayerCards.get(2);
                    if(tempPlayerCards.contains(lowestValue + 1) &&
                            tempPlayerCards.contains(lowestValue + 2) &&
                            tempPlayerCards.contains(lowestValue + 3) &&
                            tempPlayerCards.contains(lowestValue + 4)) {
                        winningPlayers.add(tempPlayer);
                    }
                }
                if (tempPlayerCards.size() >= 6) {
                    if(tempPlayerCards.contains(secondHighestValue + 1) &&
                            tempPlayerCards.contains(secondHighestValue + 2) &&
                            tempPlayerCards.contains(secondHighestValue + 3) &&
                            tempPlayerCards.contains(secondHighestValue + 4)) {
                        winningPlayers.add(tempPlayer);
                    }
                }
                if (tempPlayerCards.size() >= 7) {
                    if(tempPlayerCards.contains(thirdHighestValue + 1) &&
                            tempPlayerCards.contains(thirdHighestValue + 2) &&
                            tempPlayerCards.contains(thirdHighestValue + 3) &&
                            tempPlayerCards.contains(thirdHighestValue + 4)) {
                        winningPlayers.add(tempPlayer);
                    }
                }
            }
        }
        return winningPlayers;
    }

    public static ArrayList<Player> threeOfAKind() {
        ArrayList<Player> winningPlayers = new ArrayList<>();
        for (int i = 0; i < PokerGame.playerList.size(); i++) {
            Player tempPlayer = PokerGame.playerList.get(i);
            if (tempPlayer.getIsInGame()) {
                int [] allValues = new int[13];
                ArrayList<StandardCard> tempPlayersCardsAll = tempPlayersCardsAll(tempPlayer);
                for (StandardCard standardCard : tempPlayersCardsAll) {
                    allValues[standardCard.getValue() - 2]++;
                }
                for (int allValue : allValues) {
                    if (allValue >= 3) {
                        winningPlayers.add(tempPlayer);
                    }
                }
            }
        }
        return winningPlayers;
    }

    public static ArrayList<Player> twoPair() {
        ArrayList<Player> winningPlayers = new ArrayList<>();
        for (int i = 0; i < PokerGame.playerList.size(); i++) {
            Player tempPlayer = PokerGame.playerList.get(i);
            if (tempPlayer.getIsInGame()) {
                int [] allValues = new int[13];
                int pairCount = 0;
                ArrayList<StandardCard> tempPlayersCardsAll = tempPlayersCardsAll(tempPlayer);
                for (StandardCard standardCard : tempPlayersCardsAll) {
                    allValues[standardCard.getValue() - 2]++;
                }
                for (int allValue : allValues) {
                    if (allValue >= 2) {
                        pairCount++;
                        if (pairCount == 2) {
                            winningPlayers.add(tempPlayer);
                        }
                    }
                }
            }
        }
        return winningPlayers;
    }

    public static ArrayList<Player> onePair() {
        ArrayList<Player> winningPlayers = new ArrayList<>();
        for (int i = 0; i < PokerGame.playerList.size(); i++) {
            Player tempPlayer = PokerGame.playerList.get(i);
            if (tempPlayer.getIsInGame()) {
                int [] allValues = new int[13];
                ArrayList<StandardCard> tempPlayersCardsAll = tempPlayersCardsAll(tempPlayer);
                for (StandardCard standardCard : tempPlayersCardsAll) {
                    allValues[standardCard.getValue() - 2]++;
                }
                for (int allValue : allValues) {
                    if (allValue >= 2 && !winningPlayers.contains(tempPlayer)) {
                        winningPlayers.add(tempPlayer);
                    }
                }
            }
        }
        return winningPlayers;
    }

    public static ArrayList<Player> highCard() {
        ArrayList<Player> winningPlayers = new ArrayList<>();
        int[][] sortedPlayersHoleCards = new int[PokerGame.playerList.size()][2];
        // The first highest card
        int highestCardColTwo = 0;
        // The second-highest card
        int highestCardColOne = 0;
        boolean tie = false;

        for (int i = 0; i < PokerGame.playerList.size(); i++) {
            Player tempPlayer = PokerGame.playerList.get(i);
            if (tempPlayer.getIsInGame()) {
                ArrayList<Integer> tempHoleCards = new ArrayList<>();
                tempHoleCards.add(tempPlayer.getHoleCards()[0].getValue());
                tempHoleCards.add(tempPlayer.getHoleCards()[1].getValue());
                Collections.sort(tempHoleCards);
                if (tempHoleCards.get(1) > highestCardColTwo) {
                    highestCardColTwo = tempHoleCards.get(1);
                    winningPlayers.clear();
                    winningPlayers.add(tempPlayer);
                    sortedPlayersHoleCards = new int[PokerGame.playerList.size()][2];
                    sortedPlayersHoleCards[i][0] = i;
                    sortedPlayersHoleCards[i][1] = tempHoleCards.get(0);
                }
                else if (tempHoleCards.get(1) == highestCardColTwo) {
                    winningPlayers.add(tempPlayer);
                    tie = true;
                    // Saves the index of the player and the value of the players second-highest card
                    sortedPlayersHoleCards[i][0] = i;
                    sortedPlayersHoleCards[i][1] = tempHoleCards.get(0);
                }
            }
        }
        if (tie) {
            winningPlayers.clear();
            for (int[] sortedPlayersHoleCard : sortedPlayersHoleCards) {
                if (sortedPlayersHoleCard[1] > highestCardColOne) {
                    highestCardColOne = sortedPlayersHoleCard[1];
                }
            }
            for (int l = 0; l < sortedPlayersHoleCards.length; l++) {
                if (sortedPlayersHoleCards[l][1] == highestCardColOne) {
                    winningPlayers.add(PokerGame.playerList.get(l));
                }
            }
        }
        return winningPlayers;
    }

    public static ArrayList<StandardCard> tempPlayersCardsAll(Player player) {
        ArrayList<StandardCard> result = new ArrayList<>();
        Collections.addAll(result, PokerGame.communityCards);
        result.add(player.getHoleCards()[0]);
        result.add(player.getHoleCards()[1]);
        return result;
    }

}
