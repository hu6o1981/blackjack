package com.gmail.hugoleemet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class containing all cards used in current game (cards in the deck, waiting to be dealt).
 * Number of decks used can be specified in constructor (default 1).
 * 
 * @author Hugo
 *
 */
final class DecksOfCards {
    
    private static final int CARDS_IN_DECK = 52;
    private final List<Card> cards = new ArrayList<>();
    private final int numberOfDecks;
    private int shuffledAt = 13;
    
    /**
     * Default constructor that uses one deck of cards.
     */
    DecksOfCards() {
        this(1);
    }
    
    /**
     * Constructor that takes number of decks used as argument.
     * 
     * @param numberOfDecks the number of decks used
     */
    DecksOfCards(int numberOfDecks) {
        this.numberOfDecks = numberOfDecks;
        initCards(numberOfDecks);
        Collections.shuffle(cards);
        setShuffledAtPercent(75);
//        System.out.println(needsShuffle);
    }
    
    // Initializes cards list (adds cards to the list), number of decks added depends on argument
    private void initCards(int numberOfDecks) {
        for (int i = 0; i < numberOfDecks; i++) {
            for (Suit s : Suit.values()) {
                for (Rank r : Rank.values()) {
                    cards.add(new Card(s, r));
//                    System.out.println(s + " " + r); 
                }
            }
        }
    }
    
    /**
     * Returns and removes last card from the deck.
     * 
     * @return last element in list
     */
    Card pop() {
        // Creates new deck if ran out of cards (can happen if shuffledAt is low enough)
        if (cards.size() < 1) {
            System.out.println("NEW DECK WAS CREATED !!! (because deck ran out of cards)");
            initCards(numberOfDecks);
        }
        Card result = cards.get(cards.size() - 1);
        cards.remove(cards.size() - 1);
        return result;
    }
    
    /**
     * Set how large percentage of cards are needed to be dealt before deck of cards 
     * needs to be shuffled (needsShuffling() methods starts to return true). Default 75%.
     * 
     * @param shuffledAtPercent the percent (cards dealt) at witch deck of cards needs to be shuffled
     */
    void setShuffledAtPercent(int shuffledAtPercent) {
        shuffledAt = (numberOfDecks + CARDS_IN_DECK) * (100 - shuffledAtPercent) / 100;
//        System.out.println("shuffledAt = " + shuffledAt);
    }
    
    /**
     * Returns true if deck of cards needs to be shuffled (new deck needs to be created)
     */
    boolean needsShuffling() {
        return shuffledAt > cards.size();
    }
    
    /**
     * Returns deck size
     */
    int getSize() {
        return cards.size();
    }
    
    /**
     * Returns deck size and cards currently in deck (in string form).
     */
    @Override
    public String toString() {
        return "DecksOfCards(size " + cards.size() + ") = " + cards;
    }
    
    
}
