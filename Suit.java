package com.gmail.hugoleemet;

enum Suit {
    HEART("Heart"),
    DIAMOND("Diamond"),
    CLUB("Club"),
    SPADE("Spade");
    
    private final String name;
    
    Suit(String name) {
        this.name = name;
    }
    
    String getName() {
        return name;
    }
    
}
