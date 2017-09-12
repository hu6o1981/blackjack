package com.gmail.hugoleemet;

/**
 * This enum contains ranks for cards.
 * 
 * @author Hugo
 */
enum Rank {
    TWO("2", "Two", 2),
    THREE("3", "Three", 3),
    FOUR("4", "Four", 4),
    FIVE("5", "Five", 5),
    SIX("6", "Six", 6),
    SEVEN("7", "Seven", 7),
    EIGHT("8", "Eight", 8),
    NINE("9", "Nine", 9),
    TEN("10", "Ten", 10),
    JACK("J", "Jack", 10),
    QUEEN("Q", "Queen", 10),
    KING("K", "King", 10),
    ACE("A", "Ace", 11);
    
    private final String name;
    private final String fullName;
    private final int value;

    Rank(String name, String fullName, int value) {
        this.name = name;
        this.fullName = fullName;
        this.value = value;
    }
    
    String getName() {
        return name;
    }
    
    String getFullName() {
        return fullName;
    }
    
    int getValue() {
        return value;
    }
    
}
