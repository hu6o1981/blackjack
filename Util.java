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
        int aces = numberOfAces(cards);
        int sum = cardsFullValue(cards);
        
        // Calculates if needs to use aces in their lower value points (and then does so)
        while (aces >= 0) {
            if (!(sum < 17 || (sum == 17 && aces > 0))) {
                if (sum > 21 && aces > 0) {
                    sum -= 10;
                }
            }
            aces--;
        }
        return sum;
    }
    
    // Returns number of aces in given list.
    private static int numberOfAces(List<Card> cards) {
        int aces = 0;
        for(Card card : cards) {
            if (card.getRank() == Rank.ACE) {
                aces++;
            }
        }
        return aces;
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
     * Returns {@code true} if given cards value can still be lowered (has aces, so hand wont go bust).
     */
    static boolean cardsValueCanBeLowered(List<Card> cards) {
        return cardsFullValue(cards) - (numberOfAces(cards) * 10) < cardsValue(cards);
    }
    
    /**
     * Returns {@code true} if hand with given cards has a blackjack (2 cards and value of 21).
     */
    static boolean hasBlackjack(List<Card> cards) {
        return cardsValue(cards) == 21 && cards.size() == 2;
    }
    
    /**
     * Returns {@code true} if given list is bust (above 21 points).
     */
    static boolean handIsBust(List<Card> cards) {
        return cardsValue(cards) > 21;
    }
    
}
