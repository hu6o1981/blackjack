package com.gmail.hugoleemet;

/**
 * Launches card game Blackjack.
 * Blackjack - A card game where you win by having more points than dealer (without going bust).
 * 
 * @author Hugo
 * @version 0.1
 */
public final class BlackjackLauncher {
    
    public static void main(String[] args) {
//        Card kaart1 = new Card(Suit.CLUB, Rank.THREE);
//        System.out.println(kaart1);
        
        DecksOfCards cards = new DecksOfCards(1);
        System.out.println(cards);
        System.out.println(cards.pop());
        System.out.println(cards.pop());
        for (int i = 0; i < 38; i++) {
            System.out.println(cards.pop());
        }
        System.out.println(cards.needsShuffling());
        System.out.println(cards);
        cards.setShuffledAtPercent(75);
        System.out.println(cards.needsShuffling());
        
        
    }
}
