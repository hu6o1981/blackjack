package com.gmail.hugoleemet;

/**
 * The {@code Card} class contains information about card.
 * 
 * @author Hugo
 */
final class Card {
    
    private final Suit suit;
    private final Rank rank;
    
    /**
     * Creates a {@code Card} object with specified suit and rank.
     */
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
     * Returns {@code String} representation of suit and rank of this card.
     */
    @Override
    public String toString() {
        return suit.getName() + "-" + rank.getName();
    }
    
}
