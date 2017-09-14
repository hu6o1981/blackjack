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
            player.setStatusText("");
            dealer.setStatusText("");
            player.removeCards();
            dealer.removeCards();
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
            player.setStatusText("PRESS S TO START GAME");
            dealer.setStatusText("");
            player.removeCards();
            dealer.removeCards();
            canStart = true;
            canClearAll = false;
            canHit = false;
            canStand = false;
            canDouble = false;
            canSplit = false;
            canSurrender = false;
        }
    }
    
    void hit() {
        if (canHit) {
            System.out.println("Hitting...");
            dealer.dealACard();
            checkIfPlayerBust();
        }
    }
    
    void stand() {
        if (canStand) {
            System.out.println("Standing...");
            dealer.checkToTakeCards();
            if (!isDealerBust()) {
                checkWhoWon();
            }
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
    
    private void checkIfPlayerBust() {
        if (Util.handIsBust(player.getCards())) {
            playerLost();
            player.setStatusText("BUST");
        }
    }
    
    private boolean isDealerBust() {
        if (Util.handIsBust(dealer.getCards())) {
            playerWon();
            dealer.setStatusText("BUST");
            return true;
        }
        return false;
    }
    
    private void checkWhoWon() {
        if (Util.cardsValue(player.getCards()) > Util.cardsValue(dealer.getCards())) {
            player.setStatusText("YOU WIN");
            playerWon();
        } else if (Util.cardsValue(player.getCards()) < Util.cardsValue(dealer.getCards())) {
            player.setStatusText("YOU LOSE");
            playerLost();
        } else {
            player.setStatusText("DRAW");
            draw();
        }
    }
    
    private void checkBlackjack() {
        
    }
    
    private void playerWon() {
        canStart = true;
        canClearAll = true;
        canHit = false;
        canStand = false;
        canDouble = false;
        canSplit = false;
        canSurrender = false;
        player.changeBalance(+player.getBet());
    }
    
    private void playerLost() {
        canStart = true;
        canClearAll = true;
        canHit = false;
        canStand = false;
        canDouble = false;
        canSplit = false;
        canSurrender = false;
        player.changeBalance(-player.getBet());
    }
    
    private void draw() {
        canStart = true;
        canClearAll = true;
        canHit = false;
        canStand = false;
        canDouble = false;
        canSplit = false;
        canSurrender = false;
    }
    
 
    
}
