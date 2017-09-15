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
    
    // Controls
    private boolean canStart = true;
    private boolean canClearAll = false;
    private boolean canHit = false;
    private boolean canStand = false;
    private boolean canDouble = false;
    private boolean canSplit = false;
    private boolean canSurrender = false;
    
    private boolean playerSplit = false;
    private boolean playerOnSecondHand = false;
    private String winText = "";
    
    Player getPlayer() {
        return player;
    }
    
    Dealer getDealer() {
        return dealer;
    }
    
    String getWinText() {
        return winText;
    }
    
    boolean getPlayerSplit() {
        return playerSplit;
    }
    
    void setPleyerSplit(boolean playerSplit) {
        this.playerSplit = playerSplit;
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
            player.setInactiveStatusText("");
            dealer.setStatusText("");
            winText = "";
            player.removeCards();
            dealer.removeCards();
            canStart = false;
            dealer.dealCards();
            canClearAll = true;
            canHit = true;
            canStand = true;
            // For testing
            canSplit = true;
            
            // Starts new game after split
            if (playerOnSecondHand) {
                playerSplit = false;
                playerOnSecondHand = false;
            } else {
                playerSplit = false;
            }
        }
    }
    
    void clearAll() {
        if (canClearAll) {
            System.out.println("Clearing all...");
            player.setStatusText("PRESS S TO START GAME");
            player.setInactiveStatusText("");
            dealer.setStatusText("");
            winText = "";
            player.removeCards();
            dealer.removeCards();
            player.resetBalance();
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
        System.out.println("playerOnSecondHand = " + playerOnSecondHand);
        System.out.println("playerSplit = " + playerSplit);
        
        if (canStand) {
            // If player is standing second time when split (standing on last playable hand)
            if (playerOnSecondHand) {
                dealer.checkToTakeCards();
                if (!isDealerBust()) {
                    player.swapActiveHand();
                    checkWhoWon();
                    player.swapActiveHand();
                    checkWhoWon();
                } else {
                    player.swapActiveHand();
                    isDealerBust();
                    player.swapActiveHand();
                    
//                    player.swapActiveHand();
//                    checkWhoWon();
//                    player.swapActiveHand();
                }
             // If player is standing while being split (standing on first hand)
            } else if (playerSplit) {
                playerOnSecondHand = true;
                canStart = false;
                canClearAll = true;
                canHit = true;
                canStand = true;
//                canDouble = false;
                canSplit = false;
//                canSurrender = false;
                player.swapActiveHand();
            } else {
                System.out.println("Standing...");
                dealer.checkToTakeCards();
                if (!isDealerBust()) {
                    checkWhoWon();
                }
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
        if (canSplit && player.getCards().size() == 2 && !playerSplit) {
            System.out.println("Splitting...");
            player.split();
            playerSplit = true;
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
//            player.setStatusText("BUST");
//            playerLost();
            // If players busted on second hands (when split)
            if (playerOnSecondHand) {
                player.swapActiveHand();
                dealer.checkToTakeCards();
                // Needs testing
                isDealerBust();
                checkWhoWon();
                player.swapActiveHand();
            // If players busted on first hands  (when split)
            } else if (playerSplit) {
                
                
            // Player busted while not split
            } else {
            }
            player.setStatusText("BUST");
            playerLost();
        }
    }
    
    private boolean isDealerBust() {
        if (Util.handIsBust(dealer.getCards())) {
            dealer.setStatusText("BUST");
            if (Util.cardsValue(player.getCards()) <= 21) {
                playerWon();
                return true;
            }
        }
        return false;
    }
    
    private void checkWhoWon() {
        int playerScore = Util.cardsValue(player.getCards());
        int dealerScore = Util.cardsValue(dealer.getCards());
        System.out.println("playerScore = " + playerScore + " dealerScore = " + dealerScore);
        if (playerScore > dealerScore && playerScore <= 21) {
            player.setStatusText("YOU WIN");
            System.out.println("YOU WIN");
            playerWon();
        } else if (playerScore < dealerScore && dealerScore <= 21) {
            player.setStatusText("YOU LOSE");
            System.out.println("YOU LOSE");
            playerLost();
        } else if (playerScore == dealerScore) {
            player.setStatusText("DRAW");
            System.out.println("DRAW");
            draw();
        }
    }
    
    private void checkBlackjack() {
        // TODO
    }
    
    private void playerWon() {
        player.changeBalance(player.getBet());
        winText = String.format("+%s", player.getBet());
        gameEnded();
    }
    
    private void playerLost() {
        player.changeBalance(-player.getBet());
        winText = String.format("-%s", player.getBet());
        gameEnded();
    }
    
    private void draw() {
        winText = String.format("%s", 0);
        gameEnded();
    }
    
    // One game ended (can start again)
    private void gameEnded() {
        // If players second hands game ended (when split)
        if (playerOnSecondHand) {
            canStart = true;
            canClearAll = true;
            canHit = false;
            canStand = false;
            canDouble = false;
            canSplit = false;
            canSurrender = false;
            System.out.println("can start again");
        // If players first hands game ended (when split)
        } else if (playerSplit) {
            player.swapActiveHand();
//            System.out.println("player.getStatusText() = " + player.getStatusText());
//            System.out.println("player.getInactiveStatusText() = " + player.getInactiveStatusText());
            playerOnSecondHand = true;
        // Game ended when not split
        } else {
            canStart = true;
            canClearAll = true;
            canHit = false;
            canStand = false;
            canDouble = false;
            canSplit = false;
            canSurrender = false;
        }

    }
 
    
}
