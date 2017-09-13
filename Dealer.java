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
    private final List<Card> cards = new ArrayList<>();
    
    /**
     * Constructs {@code Dealer} object who deals with given player.
     */
    Dealer(Player player) {
        this.player = player;
    }
    
    /**
     * Deals cards (however many are needed at that time).
     */
    void dealCards() {
        
    }
    
}
