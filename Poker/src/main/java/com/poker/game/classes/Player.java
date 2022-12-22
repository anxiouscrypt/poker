package com.poker.game.classes;

import java.util.Arrays;

public class Player {
    private String name;
    private int balance;
    private StandardCard[] holeCards = new StandardCard[2];
    private boolean isInGame;

    public Player() {
        this.isInGame = true;
    }

    public Player(String name, int balance, StandardCard[] holeCards) {
        this.name = name;
        this.balance = balance;
        this.holeCards = holeCards;
        this.isInGame = true;
    }

    public String getName() {
        return this.name;
    }

    public int getBalance() {
        return this.balance;
    }

    public void addToBalance(int additionAmount) {
        this.balance += additionAmount;
    }

    public void reduceFromBalance(int reduceAmount) {
        this.balance -= reduceAmount;
    }

    public StandardCard[] getHoleCards() {
        return this.holeCards;
    }

    public void setHoleCards(StandardCard[] holeCards) {
        this.holeCards = holeCards;
    }

    public boolean getIsInGame() {
        return this.isInGame;
    }

    public void setIsInGame(boolean isInGame) {
        this.isInGame = isInGame;
    }

    public String toString() {
        return ("Player: " + this.name + " has a balance of " + this.balance +
                ".\nHole cards: " + Arrays.toString(this.holeCards) + ".\nIn the game: " + this.isInGame);
    }

}