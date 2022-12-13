package com.poker.game;

import com.poker.game.utils.Player;
import com.poker.game.utils.StandardCard;
import com.poker.game.utils.StandardDeck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class PokerGame {
    private ArrayList<Player> playerList;
    private StandardDeck gameDeck;
    private StandardCard[] communityCards;
    private int winningPot;
    private int[] playersTotalBets;
    private boolean keepPlaying;
    private Player dealer;
    private float dealerWins;
    private float playerWins;

    Scanner scan = new Scanner(System.in);

    public PokerGame(int smallBlind) {
        this.playerList = new ArrayList<Player>();
        this.gameDeck = new StandardDeck();
        this.communityCards = new StandardCard[5];
        this.winningPot = 0;
        this.keepPlaying = true;
        this.dealer = new Player();
        playerSetup();
        while (this.keepPlaying) {
            for (Player player : this.playerList) {
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
            findWinner();

            System.out.println("Do you want to play another round? (Y/N)");
            String newGame = scan.next();
            if (newGame.toLowerCase().contains("n")) {
                this.keepPlaying = false;
            }
            newRound();

        }
        System.out.println("Dealer Wins: " + dealerWins + " " + (dealerWins / (dealerWins + playerWins) * 100) + "%");
        System.out.println("Player Wins: " + playerWins + " " + (playerWins / (dealerWins + playerWins) * 100) + "%");
        System.out.println("Games Played: " + (dealerWins + playerWins));

    }

    private void printDealerHoleCards() {
        System.out.println(Arrays.toString(this.dealer.getHoleCards()));

    }

    public void newRound() {
        Player tempPlayer;

        gameDeck.reset();
        gameDeck.shuffleDeck();
        this.communityCards = new StandardCard[5];
        StandardCard[] dealerHoleCards = {gameDeck.getNextCard(), gameDeck.getNextCard()};
        StandardCard[] tempHoleCards = {gameDeck.getNextCard(), gameDeck.getNextCard()};
        for (Player player : this.playerList) {
            tempPlayer = player;
            tempPlayer.setHoleCards(tempHoleCards);
        }
        this.playerList.remove(this.dealer);
        this.dealer.setHoleCards(dealerHoleCards);
        this.playersTotalBets = new int[playerList.size()];
    }

    public void playerSetup() {
        StandardCard[] tempHoleCards = {gameDeck.getNextCard(), gameDeck.getNextCard()};
        addPlayer();
        this.playersTotalBets = new int[playerList.size()];
        this.dealer.setHoleCards(tempHoleCards);
    }

    public void addPlayer() {
        System.out.println("Enter player name:");
        String tempName = scan.next();
        System.out.println("Enter " + tempName + "'s starting balance:");
        int tempBalance = scan.nextInt();
        StandardCard[] tempHoleCards = {gameDeck.getNextCard(), gameDeck.getNextCard()};
        playerList.add(new Player(tempName, tempBalance, tempHoleCards));
    }

    public void bettingRound() {
        if (this.playerList.get(0).getIsInGame()) {
            individualBet(0);
        }
        while (!areAllBetsEqual()) {
            individualBet(0);
        }
    }

    public void individualBet(int currentPlayerIndex) {
        String checkOrCall = areAllBetsEqual() ? "check" : "call";
        System.out.println("The pot is: " + this.winningPot);
        System.out.println(this.playerList.get(currentPlayerIndex).toString() +
                "\nDo you want to fold, " + checkOrCall + " or raise?");
        String answer = scan.next();
        if (answer.toLowerCase().contains("fold")) {
            fold(this.playerList.get(currentPlayerIndex));
        }
        else if (answer.toLowerCase().contains("call")) {
            call(this.playerList.get(currentPlayerIndex));
        }
        else if (answer.toLowerCase().contains("raise")) {
            raise(this.playerList.get(currentPlayerIndex));
        }
        // Check will do nothing in the program just moves the index to the next player.
    }

    public boolean areAllBetsEqual() {
        int highestBet = getHighestBet();
        for (int j = 0; j < this.playersTotalBets.length; j++) {
            if (this.playersTotalBets[j] < highestBet && this.playerList.get(j).getIsInGame()) {
                return false;
            }
        }
        return true;
    }

    public int getHighestBet() {
        int highestBet = 0;
        for (int i = 0; i < this.playersTotalBets.length; i++) {
            if (this.playersTotalBets[i] > highestBet && this.playerList.get(i).getIsInGame()) {
                highestBet = this.playersTotalBets[i];
            }
        }
        return highestBet;
    }

    public void fold(Player player) {
        player.setIsInGame(false);
    }

    public void call(Player player) {
        int highestBet = getHighestBet();
        int playersBetDifference = highestBet - (this.playersTotalBets[this.playerList.indexOf(player)]);
        player.reduceFromBalance(playersBetDifference);
        this.playersTotalBets[this.playerList.indexOf(player)] += playersBetDifference;
        updateWinningPot();
    }

    public void raise(Player player) {
        System.out.println(player.getName() + ": How much do you want to raise?");
        int raiseAmount = scan.nextInt();
        call(player);
        player.reduceFromBalance(raiseAmount);
        this.playersTotalBets[this.playerList.indexOf(player)] += raiseAmount;
        updateWinningPot();
    }

    public void updateWinningPot() {
        this.winningPot = 0;
        for (int i = 0; i < this.playersTotalBets.length; i++) {
            this.winningPot += this.playersTotalBets[i];
        }
    }

    public void dealFlop() {
        this.communityCards[0] = this.gameDeck.getNextCard();
        this.communityCards[1] = this.gameDeck.getNextCard();
        this.communityCards[2] = this.gameDeck.getNextCard();
    }

    public void dealTurn() {
        this.communityCards[3] = this.gameDeck.getNextCard();
    }

    public void dealRiver() {
        this.communityCards[4] = this.gameDeck.getNextCard();
    }



    public void printCommunityCards() {
        System.out.println("-----------------------------------------------------");
        if (this.communityCards[0] != null && this.communityCards[3] == null && this.communityCards[4] == null) { // Flop
            System.out.println("The flop:");
        }
        else if (this.communityCards[3] != null && this.communityCards[4] == null) { // Turn
            System.out.println("The turn:");
        }
        else if (this.communityCards[4] != null) {
            System.out.println("The river:");
        }
        System.out.println(Arrays.toString(this.communityCards));
        System.out.println("-----------------------------------------------------");
    }

    public void findWinner() {
        /*
         *  Switch if statements to switch case
         */
        ArrayList<Player> winningPlayers = new ArrayList<Player>();

        this.playerList.add(this.dealer);

        //royalFlush
        winningPlayers = royalFlush();
        if (winningPlayers.size() > 0) {
            handleWinners(winningPlayers);
            System.out.println("Royal Flush");
            System.out.println("Winner(s) are: " + winningPlayers);
            return;
        }
        //straightFlush
        winningPlayers = straightFlush();
        if (winningPlayers.size() > 0) {
            handleWinners(winningPlayers);
            System.out.println("Straight Flush");
            System.out.println("Winner(s) are: " + winningPlayers);
            return;
        }
        //fourOfAKind
        winningPlayers = fourOfAKind();
        if (winningPlayers.size() > 0) {
            handleWinners(winningPlayers);
            System.out.println("Four of a Kind");
            System.out.println("Winner(s) are: " + winningPlayers);
            return;
        }
        //fullHouse
        winningPlayers = fullHouse();
        if (winningPlayers.size() > 0) {
            handleWinners(winningPlayers);
            System.out.println("Full House");
            System.out.println("Winner(s) are: " + winningPlayers);
            return;
        }
        //flush
        winningPlayers = flush();
        if (winningPlayers.size() > 0) {
            handleWinners(winningPlayers);
            System.out.println("Flush");
            System.out.println("Winner(s) are: " + winningPlayers);
            return;
        }
        //straight
        winningPlayers = straight();
        if (winningPlayers.size() > 0) {
            handleWinners(winningPlayers);
            System.out.println("Straight");
            System.out.println("Winner(s) are: " + winningPlayers);
            return;
        }
        //threeOfAKind
        winningPlayers = threeOfAKind();
        if (winningPlayers.size() > 0) {
            handleWinners(winningPlayers);
            System.out.println("Three of a Kind");
            System.out.println("Winner(s) are: " + winningPlayers);
            return;
        }
        //twoPair
        winningPlayers = twoPair();
        if (winningPlayers.size() > 0) {
            handleWinners(winningPlayers);
            System.out.println("Two Pair");
            System.out.println("Winner(s) are: " + winningPlayers);
            return;
        }
        //onePair
        winningPlayers = onePair();
        if (winningPlayers.size() > 0) {
            handleWinners(winningPlayers);
            System.out.println("One Pair");
            System.out.println("Winner(s) are: " + winningPlayers);
            return;
        }
        //highCard
        winningPlayers = highCard();
        if (winningPlayers.size() > 0) {
            handleWinners(winningPlayers);
            System.out.println("High Card");
            System.out.println("Winner(s) are: " + winningPlayers);
            return;
        }

    }

    public void handleWinners(ArrayList<Player> winningPlayers) {
        int tempWinnerAward = this.winningPot * 2;
        for (Player winningPlayer : winningPlayers) {
            String name = winningPlayer.getName();
            winningPlayer.addToBalance(tempWinnerAward);
            if (name == null) {
                dealerWins += 1;
            } else {
                playerWins += 1;
            }
        }
        this.winningPot = 0;
    }

    public ArrayList<Player> royalFlush() {
        ArrayList<Player> winningPlayers = new ArrayList<Player>();
        for (Player player : this.playerList) {
            int hearts = 0;
            int diamonds = 0;
            int spades = 0;
            int clubs = 0;
            Player tempPlayer = player;
            if (tempPlayer.getIsInGame()) {
                ArrayList<Integer> tempPlayerCards = new ArrayList<Integer>();
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
                if (hearts >= 5) {
                    tempPlayerCards = getValuesOfSuit(tempPlayersCardsAll, "Hearts");
                } else if (diamonds >= 5) {
                    tempPlayerCards = getValuesOfSuit(tempPlayersCardsAll, "Diamonds");
                } else if (spades >= 5) {
                    tempPlayerCards = getValuesOfSuit(tempPlayersCardsAll, "Spades");
                } else if (clubs >= 5) {
                    tempPlayerCards = getValuesOfSuit(tempPlayersCardsAll, "Clubs");
                }

                if (tempPlayerCards.contains(10) &&
                        tempPlayerCards.contains(11) &&
                        tempPlayerCards.contains(12) &&
                        tempPlayerCards.contains(13) &&
                        tempPlayerCards.contains(14)) {
                    winningPlayers.add(tempPlayer);
                }
            }
        }
        return winningPlayers;
    }

    public ArrayList<Player> straightFlush() {
        ArrayList<Player> winningPlayers = new ArrayList<Player>();
        for (Player player : this.playerList) {
            int hearts = 0;
            int diamonds = 0;
            int spades = 0;
            int clubs = 0;
            Player tempPlayer = player;
            if (tempPlayer.getIsInGame()) {
                ArrayList<Integer> tempPlayerCards = new ArrayList<Integer>();
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
                if (hearts >= 5) {
                    tempPlayerCards = getValuesOfSuit(tempPlayersCardsAll, "Hearts");
                } else if (diamonds >= 5) {
                    tempPlayerCards = getValuesOfSuit(tempPlayersCardsAll, "Diamonds");
                } else if (spades >= 5) {
                    tempPlayerCards = getValuesOfSuit(tempPlayersCardsAll, "Spades");
                } else if (clubs >= 5) {
                    tempPlayerCards = getValuesOfSuit(tempPlayersCardsAll, "Clubs");
                }
                Collections.sort(tempPlayerCards);
                int lowestValue = 15;
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
                        winningPlayers.add(tempPlayer);
                    }
                }
                if (tempPlayerCards.size() >= 6) {
                    if (tempPlayerCards.contains(secondHighestValue + 1) &&
                            tempPlayerCards.contains(secondHighestValue + 2) &&
                            tempPlayerCards.contains(secondHighestValue + 3) &&
                            tempPlayerCards.contains(secondHighestValue + 4)) {
                        winningPlayers.add(tempPlayer);
                    }
                }
                if (tempPlayerCards.size() >= 7) {
                    if (tempPlayerCards.contains(thirdHighestValue + 1) &&
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

    public ArrayList<Player> fourOfAKind() {
        ArrayList<Player> winningPlayers = new ArrayList<>();
        for (Player tempPlayer : this.playerList) {
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

    public ArrayList<Player> fullHouse() {
        ArrayList<Player> winningPlayers = new ArrayList<Player>();
        for (Player tempPlayer : this.playerList) {
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
                    } else if (allValue >= 2) {
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

    public ArrayList<Player> flush() {
        ArrayList<Player> winningPlayers = new ArrayList<Player>();
        for (int i = 0; i < this.playerList.size(); i++) {
            int hearts = 0;
            int diamonds = 0;
            int spades = 0;
            int clubs = 0;
            Player tempPlayer = this.playerList.get(i);
            if (tempPlayer.getIsInGame()) {
                ArrayList<StandardCard> tempPlayersCardsAll = tempPlayersCardsAll(tempPlayer);
                for (int l = 0; l < tempPlayersCardsAll.size(); l++) {
                    if (tempPlayersCardsAll.get(l).getSuit().contains("Hearts")) {
                        hearts++;
                    }
                    if (tempPlayersCardsAll.get(l).getSuit().contains("Diamonds")) {
                        diamonds++;
                    }
                    if (tempPlayersCardsAll.get(l).getSuit().contains("Spades")) {
                        spades++;
                    }
                    if (tempPlayersCardsAll.get(l).getSuit().contains("Clubs")) {
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

    public ArrayList<Player> straight() {
        ArrayList<Player> winningPlayers = new ArrayList<Player>();
        for (int i = 0; i < this.playerList.size(); i++) {
            Player tempPlayer = this.playerList.get(i);
            if (tempPlayer.getIsInGame()) {
                ArrayList<StandardCard> tempPlayersCardsAll = tempPlayersCardsAll(tempPlayer);
                ArrayList<Integer> tempPlayerCards = getValuesOfAllCards(tempPlayersCardsAll);

                Collections.sort(tempPlayerCards);
                int lowestValue = 15;
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

    public ArrayList<Player> threeOfAKind() {
        ArrayList<Player> winningPlayers = new ArrayList<Player>();
        for (int i = 0; i < this.playerList.size(); i++) {
            Player tempPlayer = this.playerList.get(i);
            if (tempPlayer.getIsInGame()) {
                int [] allValues = new int[13];
                ArrayList<StandardCard> tempPlayersCardsAll = tempPlayersCardsAll(tempPlayer);
                for (int j = 0; j < tempPlayersCardsAll.size(); j++) {
                    allValues[tempPlayersCardsAll.get(j).getValue()-2]++;
                }
                for (int k = 0; k < allValues.length; k++) {
                    if (allValues[k] >= 3) {
                        winningPlayers.add(tempPlayer);
                    }
                }
            }
        }
        return winningPlayers;
    }

    public ArrayList<Player> twoPair() {
        ArrayList<Player> winningPlayers = new ArrayList<Player>();
        for (int i = 0; i < this.playerList.size(); i++) {
            Player tempPlayer = this.playerList.get(i);
            if (tempPlayer.getIsInGame()) {
                int [] allValues = new int[13];
                int pairCount = 0;
                ArrayList<StandardCard> tempPlayersCardsAll = tempPlayersCardsAll(tempPlayer);
                for (int j = 0; j < tempPlayersCardsAll.size(); j++) {
                    allValues[tempPlayersCardsAll.get(j).getValue()-2]++;
                }
                for (int k = 0; k < allValues.length; k++) {
                    if (allValues[k] >= 2) {
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

    public ArrayList<Player> onePair() {
        ArrayList<Player> winningPlayers = new ArrayList<Player>();
        for (int i = 0; i < this.playerList.size(); i++) {
            Player tempPlayer = this.playerList.get(i);
            if (tempPlayer.getIsInGame()) {
                int [] allValues = new int[13];
                ArrayList<StandardCard> tempPlayersCardsAll = tempPlayersCardsAll(tempPlayer);
                for (int j = 0; j < tempPlayersCardsAll.size(); j++) {
                    allValues[tempPlayersCardsAll.get(j).getValue()-2]++;
                }
                for (int k = 0; k < allValues.length; k++) {
                    if (allValues[k] >= 2 && !winningPlayers.contains(tempPlayer)) {
                        winningPlayers.add(tempPlayer);
                    }
                }
            }
        }
        return winningPlayers;
    }

    public ArrayList<Player> highCard() {
        ArrayList<Player> winningPlayers = new ArrayList<Player>();
        int[][] sortedPlayersHoleCards = new int[this.playerList.size()][2];
        // The first highest card
        int highestCardColTwo = 0;
        // The second-highest card
        int highestCardColOne = 0;
        boolean tie = false;

        for (int i = 0; i < this.playerList.size(); i++) {
            Player tempPlayer = this.playerList.get(i);
            if (tempPlayer.getIsInGame()) {
                ArrayList<Integer> tempHoleCards = new ArrayList<Integer>();
                tempHoleCards.add(tempPlayer.getHoleCards()[0].getValue());
                tempHoleCards.add(tempPlayer.getHoleCards()[1].getValue());
                Collections.sort(tempHoleCards);
                if (tempHoleCards.get(1) > highestCardColTwo) {
                    highestCardColTwo = tempHoleCards.get(1);
                    winningPlayers.clear();
                    winningPlayers.add(tempPlayer);
                    sortedPlayersHoleCards = new int[this.playerList.size()][2];
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
            for (int k = 0; k < sortedPlayersHoleCards.length; k++) {
                if (sortedPlayersHoleCards[k][1] > highestCardColOne) {
                    highestCardColOne = sortedPlayersHoleCards[k][1];
                }
            }
            for (int l = 0; l < sortedPlayersHoleCards.length; l++) {
                if (sortedPlayersHoleCards[l][1] == highestCardColOne) {
                    winningPlayers.add(this.playerList.get(l));
                }
            }
        }
        return winningPlayers;
    }

    public ArrayList<StandardCard> tempPlayersCardsAll(Player player) {
        ArrayList<StandardCard> result = new ArrayList<StandardCard>();
        for (int i = 0; i < this.communityCards.length; i++) {
            result.add(this.communityCards[i]);
        }
        result.add(player.getHoleCards()[0]);
        result.add(player.getHoleCards()[1]);
        return result;
    }

    public ArrayList<Integer> getValuesOfSuit(ArrayList<StandardCard> playersCardsAll, String suit) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < playersCardsAll.size(); i++) {
            if (playersCardsAll.get(i).getSuit().contains(suit)) {
                result.add(playersCardsAll.get(i).getValue());
            }
        }
        return result;
    }

    public ArrayList<Integer> getValuesOfAllCards(ArrayList<StandardCard> playersCardsAll) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < playersCardsAll.size(); i++) {
            result.add(playersCardsAll.get(i).getValue());
        }
        return result;
    }
}
