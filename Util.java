package com.gmail.hugoleemet;

import java.util.List;

/**
 * Utility class for Blackjack.
 * 
 * @author Hugo
 */
final class Util {
    // To prevent initialization.
    private Util() {
    }
    
    /**
     * Returns current value of cards in given list (aces are used in lower values when needed)
     */
    static int cardsValue(List<Card> cards) {
        int aces = 0;
        int sum = 0;
        // Calculates sum and amount of aces in list
        for(Card card : cards) {
            sum += card.getRank().getValue();
            if (card.getRank() == Rank.ACE) {
                aces++;
            }
        }
//        System.out.println("sum:" + sum + " aces:" + aces);
        // Calculates if needs to use aces in their lower value points (and then does so)
        while (aces >= 0) {
            if (!(sum < 17 || (sum == 17 && aces > 0))) {
                if (sum > 21 && aces > 0) {
                    sum -= 10;
                }
            }
            aces--;
        }
//        System.out.println("sum:" + sum + " full aces:" + aces);
        return sum;
    }
    
    /**
     * Returns current value of cards in given list (all aces add 11 points).
     */
    static int cardsFullValue(List<Card> cards) {
        int sum = 0;
        for(Card card : cards) {
            sum += card.getRank().getValue();
        }
        return sum;
    }
    
    /**
     * Returns true if given list is bust (above 21 points).
     */
    static boolean handIsBust(List<Card> cards) {
        return cardsValue(cards) > 21;
    }
}
