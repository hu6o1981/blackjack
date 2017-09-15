package com.gmail.hugoleemet;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Player} class contains information about player.
 * 
 * @author Hugo
 */
final class Player {
    
    private final int initialBalance;
    private int balance;
    private int bet = 10;
    
    private String statusText = "PRESS S TO START GAME";
    private String inactiveStatusText = "SAMPLE TEXT";
    private List<Card> cards = new ArrayList<>();
    private List<Card> inactiveHand = new ArrayList<>();
    
    /**
     * Constructs {@code Player} object with set initial balance.
     */
    Player(int balance) {
        this.balance = balance;
        this.initialBalance = balance;
    }
    
    List<Card> getCards() {
        return cards;
    }
    
    List<Card> getInactiveHand() {
        return inactiveHand;
    }
    
    int getBet() {
        return bet;
    }
    
    int getBalance() {
        return balance;
    }
    
    String getStatusText() {
        return statusText;
    }
    
    void setStatusText(String statusText) {
        this.statusText = statusText;
    }
    
    String getInactiveStatusText() {
        return inactiveStatusText;
    }
    
    void setInactiveStatusText(String inactiveStatusText) {
        this.inactiveStatusText = inactiveStatusText;
    }
    
    void resetBalance() {
        balance = initialBalance;
    }
    
    /**
     * Give player a card.
     */
    void giveCard(Card card) {
        cards.add(card);
    }
    
    /**
     * Removes all cards from the player.
     */
    void removeCards() {
        cards.clear();
        inactiveHand.clear();
    }
    
    /**
     * Splits player hand into two hands
     */
    void split() {
        if (cards.size() == 2) {
            Card cardToBeMoved = cards.get(cards.size() - 1);
            cards.remove(cards.size() - 1);
            inactiveHand.add(cardToBeMoved);
        }
    }
    
    /**
     * Swaps active ({@code cards}) and inactive hand ({@code inactiveHand}).
     * Swaps all relevant information (both cards and texts).
     */
    void swapActiveHand() {
        // Swapping cards (lists)
        List<Card> temp = inactiveHand;
        inactiveHand = cards;
        cards = temp;
        
        // Swapping texts
        String tempText = inactiveStatusText;
        inactiveStatusText = statusText;
        statusText = tempText;
    }
    
    /**
     * Changes player balance by given amount (negative subtracts).
     */
    void changeBalance(int change) {
        balance += change;
    }
    
}
