package com.poker.game.classes;

import java.util.ArrayList;

public class StandardCard {
    private int value;
    private String suit;
    private String color;
    private String src;

    public StandardCard(int value, String suit) {
        this.value = value;
        this.suit = suit;
        this.src = setSrc();
        if (this.suit.equals("Hearts") || this.suit.equals("Diamonds")) {
            this.color = "Red";
        }
        if (this.suit.equals("Clubs") || this.suit.equals("Spades")) {
            this.color = "Black";
        }
    }

    private String setSrc() {
        String src = this.value + this.suit + ".png";
        return src;
    }

    public String getSrc() {
        return src;
    }

    public int getValue() {
        return this.value;
    }

    public String getSuit() {
        return this.suit;
    }

    public String getColor() {
        return this.color;
    }

    public String convertValueToName() {
        switch(this.value) {
            case 1: return "Ace";
            case 2: return "Two";
            case 3: return "Three";
            case 4: return "Four";
            case 5: return "Five";
            case 6: return "Six";
            case 7: return "Seven";
            case 8: return "Eight";
            case 9: return "Nine";
            case 10: return "Ten";
            case 11: return "Jack";
            case 12: return "Queen";
            case 13: return "King";
            case 14: return "Ace";
            default: return "Value not Valid";
        }
    }

    public String toString() {
        return (convertValueToName() + " of " + this.suit);
    }

    public static void main(String[] args) {
        // Suits - Done
        // Value - Done
        // Color - Done
        // getters - Done
        // toString - Done
        // convertValueToName - Done

        StandardCard card = new StandardCard(1,"Hearts");
        System.out.println(card.toString());

    }

    public static ArrayList<Integer> getValuesOfSuit(ArrayList<StandardCard> playersCardsAll, String suit) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < playersCardsAll.size(); i++) {
            if (playersCardsAll.get(i).getSuit().contains(suit)) {
                result.add(playersCardsAll.get(i).getValue());
            }
        }
        return result;
    }

    public static ArrayList<Integer> getValuesOfAllCards(ArrayList<StandardCard> playersCardsAll) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < playersCardsAll.size(); i++) {
            result.add(playersCardsAll.get(i).getValue());
        }
        return result;
    }

}