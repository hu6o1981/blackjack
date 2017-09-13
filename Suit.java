package com.gmail.hugoleemet;

/**
 * This enum contains suits for cards.
 * 
 * @author Hugo
 *
 */
enum Suit {
    HEART("Heart", '\u2665'),
    DIAMOND("Diamond", '\u2666'),
    CLUB("Club", '\u2663'),
    SPADE("Spade", '\u2660');
    
    private final String name;
    private final char unicode;
    
    Suit(String name, char unicode) {
        this.name = name;
        this.unicode = unicode;
    }
    
    String getName() {
        return name;
    }
    
    char getUnicode() {
        return unicode;
    }
    
}
