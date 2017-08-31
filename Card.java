package com.gmail.hugoleemet;

/**
 * A class containing information about card
 * 
 * @author Hugo
 */
final class Card {
    
    private final Suit suit;
    private final Rank rank;
    
    Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }
    
    Suit getSuit() {
        return suit;
    }
    
    Rank getRank() {
        return rank;
    }

    /**
     * Returns String representation of suit and rank of this card.
     */
    @Override
    public String toString() {
        return suit.getName() + "-" + rank.getName();
    }
    
    
    
}
