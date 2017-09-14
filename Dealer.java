package com.gmail.hugoleemet;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Dealer} class contains information about dealer.
 *  
 * @author Hugo
 */
final class Dealer {
    
    private final Player player;
    private final Deck deck;
    private String statusText = "";
    private final List<Card> cards = new ArrayList<>();
    
    
    /**
     * Constructs {@code Dealer} object who deals with given player using given deck.
     */
    Dealer(Player player, Deck deck) {
        this.player = player;
        this.deck = deck;
    }
    
    List<Card> getCards() {
        return cards;
    }
    
    String getStatusText() {
        return statusText;
    }
    
    void setStatusText(String statusText) {
        this.statusText = statusText;
    }
    
    /**
     * Deals cards (at the start).
     */
    void dealCards() {
        System.out.println("Dealing cards...");
        if (cards.size() < 1) {
            givePlayerACard();
            takeHimselfACard();
            givePlayerACard();
            takeHimselfACard();
        } else {
            givePlayerACard();
        }
    }
    
    /**
     * Deals a card (one card to a player).
     */
    void dealACard() {
        System.out.println("Dealing cards...");
        givePlayerACard();
    }
    
    /**
     * Takes cards if needed (must take until the cards total 17 or more points).
     */
    void checkToTakeCards() {
        boolean take = false;
        int sum = Util.cardsValue(cards);
        int fullValueAces = (Util.cardsFullValue(cards) - sum) / 10;
        System.out.println("FULL ACES: " + fullValueAces);
        if (sum < 17 || (sum == 17 && fullValueAces > 0)) {
            take = true;
        } 
        if (take) {
            takeHimselfACard();
            checkToTakeCards();
        }
    }
    
    /**
     * Removes all cards from the dealer.
     */
    void removeCards() {
        cards.clear();
    }
    
    private void givePlayerACard() {
        player.giveCard(deck.pop());
    }
    
    private void takeHimselfACard() {
        cards.add(deck.pop());
    }
    
}
