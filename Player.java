package com.gmail.hugoleemet;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Player} class contains information about player.
 * 
 * @author Hugo
 */
final class Player {
    
    private int balance;
    private int bet = 10;
    private String statusText = "PRESS S TO START GAME";
    private final List<Card> cards = new ArrayList<>();
    
    /**
     * Constructs {@code Player} object with set initial balance.
     */
    Player(int balance) {
        this.balance = balance;
    }
    
    List<Card> getCards() {
        return cards;
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
    }
    

    
    /**
     * Changes player balance by given amount (negative subtracts).
     */
    void changeBalance(int change) {
        balance += change;
    }
    
}
