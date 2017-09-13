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
    
    /**
     * Deals cards (however many are needed at that time).
     */
    void dealCards() {
        System.out.println("Dealing cards...");
        if (cards.size() < 1) {
            cards.add(deck.pop());
            cards.add(deck.pop());
            player.giveCard(deck.pop());
            player.giveCard(deck.pop());
        } else {
            player.giveCard(deck.pop());
        }
    }
    
    /**
     * Checks if he needs to take more cards.
     */
    void checkCards() {
        // TODO
    }
    
}
