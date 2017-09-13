package com.gmail.hugoleemet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The {@code Deck} class contains all cards used in current game (cards in the deck, waiting to be dealt).
 * Number of decks used can be specified in constructor (default 1).
 * 
 * @author Hugo
 */
final class Deck {
    
    private static final int CARDS_IN_DECK = 52;
    private final List<Card> cards = new ArrayList<>();
    private final int numberOfDecks;
    private int shuffledAt = 13;
    
    /**
     * Takes number of decks used as argument (each deck will add 52 cards to the overall deck).
     * 
     * @param numberOfDecks the number of decks used
     */
    Deck(int numberOfDecks) {
//        if (numberOfDecks < 1) {
//            numberOfDecks = 1;
//        }
//        assert numberOfDecks < 1;
        this.numberOfDecks = numberOfDecks;
        shuffleCards(numberOfDecks);
        Collections.shuffle(cards);
        setShuffledAtPercent(75);
//        System.out.println(needsShuffle);
    }
    
    /**
     * Makes new deck (uses number of decks that was specified in construction).
     * Use overloaded method (by adding {@code int} argument) to use specific number of decks.
     */
    void shuffleCards() {
        shuffleCards(numberOfDecks);
    }
    
    /**
     * Makes new deck (using amount of decks specified by the argument).
     * 
     * @param numberOfDecks the number of decks added (to the overall deck)
     */
    void shuffleCards(int numberOfDecks) {
        cards.clear();
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
            shuffleCards(numberOfDecks);
        }
        Card result = cards.get(cards.size() - 1);
        cards.remove(cards.size() - 1);
        return result;
    }
    
    /**
     * Set how large percentage of cards are needed to be dealt before deck of cards 
     * needs to be shuffled ({@code needsShuffling()} methods starts to return {@code true}). Default 75%.
     * 
     * @param shuffledAtPercent the percent (cards dealt) at witch deck of cards needs to be shuffled
     */
    void setShuffledAtPercent(int shuffledAtPercent) {
        shuffledAt = (numberOfDecks + CARDS_IN_DECK) * (100 - shuffledAtPercent) / 100;
//        System.out.println("shuffledAt = " + shuffledAt);
    }
    
    /**
     * Returns {@code true} if deck of cards needs to be shuffled (and new cards added).
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
     * Returns deck size and cards currently in deck (in {@code String} form).
     */
    @Override
    public String toString() {
        return "Deck(size " + cards.size() + ") = " + cards;
    }
    
    
}
