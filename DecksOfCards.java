package com.gmail.hugoleemet;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class containing all cards present in one deck
 * 
 * @author Hugo
 *
 */
final class DecksOfCards {
    
    List<Card> cards = new LinkedList<>();
    
    /**
     * Default constructor that uses one deck of cards.
     */
    public DecksOfCards() {
        this(1);
    }
    
    /**
     * Constructor that takes number of decks used as argument.
     * 
     * @param numberOfDecks the number of decks used
     */
    public DecksOfCards(int numberOfDecks) {
        for (int i = 0; i < numberOfDecks; i++) {
            for (Suit s : Suit.values()) {
                for (Rank r : Rank.values()) {
                    cards.add(new Card(s, r));
//                    System.out.println(s + " " + r); 
                    
                }
            }
        }

        Collections.shuffle(cards);
        System.out.println(cards);
        System.out.println(cards.size());
    }
    
}
