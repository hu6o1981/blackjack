package com.gmail.hugoleemet;

/**
 * The {@code Model} class contains blackjack game logic.
 * 
 * @author Hugo
 */
final class Model {
    
    private final Deck deck = new Deck(6);
    private final Player player = new Player(1000);
    private final Dealer dealer = new Dealer(player, deck);
    
    private boolean canStart = true;
    private boolean canClearAll = false;
    private boolean canHit = false;
    private boolean canStand = false;
    private boolean canDouble = false;
    private boolean canSplit = false;
    private boolean canSurrender = false;
    
    Player getPlayer() {
        return player;
    }
    
    Dealer getDealer() {
        return dealer;
    }
    
    boolean canPressStart() {
        return canStart;
    }
    
    boolean canPressClearAll() {
        return canClearAll;
    }

    boolean canPressHit() {
        return canHit;
    }

    boolean canPressStand() {
        return canStand;
    }

    boolean canPressDouble() {
        return canDouble;
    }

    boolean canPressSplit() {
        return canSplit;
    }

    boolean canPressSurrender() {
        return canSurrender;
    }
    
    /**
     * Computer turn (does what ever is necessary at this point in game).
     */
    void start() {
        if (canStart) {
            System.out.println("Starting...");
            canStart = false;
            dealer.dealCards();
            canClearAll = true;
            canHit = true;
            canStand = true;
        }
    }
    
    void clearAll() {
        if (canClearAll) {
            System.out.println("Clearing all...");
            // TODO
        }
    }
    
    void hit() {
        if (canHit) {
            System.out.println("Hitting...");
            dealer.dealCards();
        }
    }
    
    void stand() {
        if (canStand) {
            System.out.println("Standing...");
            dealer.checkCards();
        }
    }
    
    void doubleing() {
        if (canDouble) {
            System.out.println("Doubleing...");
            // TODO
        }
    }
    
    void split() {
        if (canSplit) {
            System.out.println("Splitting...");
            // TODO
        }
    }
    
    void surrender() {
        if (canSurrender) {
            System.out.println("Surrendering...");
            // TODO
        }
    }
    
}
