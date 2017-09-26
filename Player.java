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
    private int balanceSpent = 0;
    private int defaultBet = 10;
    
    private int bet = 0;
    private int inactiveBet = 0;
    private String statusText = "PRESS S TO START GAME";
    private String inactiveStatusText = "SAMPLE TEXT";
    private List<Card> cards = new ArrayList<>();
    private List<Card> inactiveCards = new ArrayList<>();
    
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
    
    List<Card> getInactiveCards() {
        return inactiveCards;
    }
    
    int getDefaultBet() {
        return defaultBet;
    }
    
    void setBet(int bet) {
        this.bet = bet;
    }
    
    int getBet() {
        return bet;
    }
    
    void setDefaultBet(int defaultBet) {
        this.defaultBet = defaultBet;
    }
    
    int getInactiveBet() {
        return inactiveBet;
    }
    
    void setInactiveBet(int inactiveBet) {
        this.inactiveBet = inactiveBet;
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
    
    int getBalance() {
        return balance;
    }
    
    void resetBalance() {
        balance = initialBalance;
    }
    
    int getBalanceSpent() {
        return balanceSpent;
    }
    
    void resetBalanceSpent() {
        balanceSpent = 0;
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
        inactiveCards.clear();
    }
    
    /**
     * Splits player hand into two hands
     */
    void split() {
        if (cards.size() == 2) {
            Card cardToBeMoved = cards.get(cards.size() - 1);
            cards.remove(cards.size() - 1);
            inactiveCards.add(cardToBeMoved);
            changeBalance(-defaultBet);
            inactiveBet = bet;
        }
    }
    
    /**
     * Swaps all relevant information between active and inactive hand(cards, texts and bets).
     * For Example swaps active ({@code cards}) and inactive hand ({@code inactiveCards}) lists.
     */
    void swapActiveHand() {
        // TODO separate hand objects (and swap those)?
        // Swapping cards (lists)
        List<Card> temp = inactiveCards;
        inactiveCards = cards;
        cards = temp;
        
        // Swapping texts
        String tempText = inactiveStatusText;
        inactiveStatusText = statusText;
        statusText = tempText;
        
        // Swapping bets
        int tempBet = inactiveBet;
        inactiveBet = bet;
        bet = tempBet;
    }
    
    /**
     * Changes player balance by given amount (negative subtracts).
     * Also changes banaceSpent (if needed).
     */
    void changeBalance(int change) {
        balance += change;
        if (change < 0) {
            balanceSpent += -change;
        }
    }
    
}
