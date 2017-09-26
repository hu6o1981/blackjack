package com.gmail.hugoleemet;

/**
 * The {@code Model} class contains blackjack game logic.
 * 
 * @author Hugo
 */
final class Model {
    
    private static final int MIN_BET = 10;
    
    private final Deck deck = new Deck(6);
    private final Player player = new Player(100);
    private final Dealer dealer = new Dealer(player, deck);
    
    // Controls
    private boolean canStart = true;
    private boolean canClearAll = false;
    private boolean canHit = false;
    private boolean canStand = false;
    private boolean canDouble = false;
    private boolean canSplit = false;
    private boolean canSurrender = false;
    // Split flags (if player is split in different stages of the game)
    private boolean playerSplit = false;
    private boolean playerOnSecondHand = false; // Player plays on second (last playable) hand, when split.
    
    private int win = 0;
    
    Player getPlayer() {
        return player;
    }
    
    Dealer getDealer() {
        return dealer;
    }
    
    int getWin() {
        return win;
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
     * Returns {@code true} if bet can be raised.
     */
    boolean canPressRaise() {
        return canStart && player.getDefaultBet() + MIN_BET <= player.getBalance();
    }
    
    /**
     * Returns {@code true} if bet can be lowered.
     */
    boolean canPressLower() {
        return canStart && player.getDefaultBet() > MIN_BET;
    }
    
    /**
     * Computer turn (does what ever is necessary at this point in game).
     */
    void start() {
        if (canStart) {
//            System.out.println("Starting...");
            player.resetBalanceSpent();
            player.setStatusText("");
            player.setInactiveStatusText("");
            dealer.setStatusText("");
            win = 0;
            player.removeCards();
            dealer.removeCards();
            canStart = false;
            dealer.dealCards();
            canClearAll = true;
            canHit = true;
            canStand = true;
            // For testing set to true (TODO must be based on game logic)
            canSplit = true;
            
            player.setBet(player.getDefaultBet() * 2);
            player.setInactiveBet(0);
            player.changeBalance(-player.getDefaultBet());
            
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
//            System.out.println("Clearing all...");
            player.resetBalanceSpent();
            player.setStatusText("PRESS S TO START GAME");
            player.setInactiveStatusText("");
            dealer.setStatusText("");
            player.removeCards();
            dealer.removeCards();
            player.resetBalance();
            player.setDefaultBet(10);
            resetControlsForNewGame();
            canClearAll = false;
            playerSplit = false;
            playerOnSecondHand = false;
        }
    }
    
    void hit() {
        if (canHit) {
//            System.out.println("Hitting...");
            dealer.dealACard();
            checkIfPlayerBust();
        }
    }
    
    void stand() {
//        System.out.println("playerOnSecondHand = " + playerOnSecondHand);
//        System.out.println("playerSplit = " + playerSplit);
        if (canStand) {
            // If player is standing second time when split (standing on last playable hand).
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
                }
            // If player is standing while being split (standing on first hand).
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
            // TODO Can only split with similar cards
        }
    }
    
    void surrender() {
        if (canSurrender) {
            System.out.println("Surrendering...");
            // TODO
        }
    }
    
    void raise() {
        if (canPressRaise()) {
//            System.out.println("Raising...");
            player.setDefaultBet(player.getDefaultBet() + MIN_BET);
        }
    }
    
    void lower() {
        if (canPressLower()) {
//            System.out.println("Lowering...");
            player.setDefaultBet(player.getDefaultBet() - MIN_BET);
        }
    }
    
    private void checkIfPlayerBust() {
        if (Util.handIsBust(player.getCards())) {
            // If players busted on second hands (when split)
            if (playerOnSecondHand) {
                player.swapActiveHand();
                dealer.checkToTakeCards();
                isDealerBust();
                checkWhoWon();
                player.swapActiveHand();
            // If players busted on first hands  (when split)
            } else if (playerSplit) {
            // Player busted while not split
            } else {}
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
//        System.out.println("playerScore = " + playerScore + " dealerScore = " + dealerScore);
        if (playerScore > dealerScore && playerScore <= 21) {
            playerWon();
        } else if (playerScore < dealerScore && dealerScore <= 21) {
            playerLost();
        } else if (playerScore == dealerScore) {
            draw();
        }
    }
    
    private void checkBlackjack() {
        // TODO
    }
    
    private void playerWon() {
        player.setStatusText("YOU WIN");
        gameEnded();
    }
    
    private void playerLost() {
        player.setBet(0);
        if (Util.handIsBust(player.getCards())) {
            player.setStatusText("BUST");
        } else {
            player.setStatusText("YOU LOSE");
        }
        gameEnded();
    }
    
    private void draw() {
        player.setStatusText("DRAW");
        player.setBet(player.getBet() / 2);
        gameEnded();
    }
    
    // One game ended (can start again).
    private void gameEnded() {
        // If players second hands game ended (when split)
        if (playerOnSecondHand) {
            win = player.getBet() + player.getInactiveBet();
            player.changeBalance(player.getBet());
            resetControlsForNewGame();
            System.out.println("can start again");
        // If players first hands game ended (when split)
        } else if (playerSplit) {
            player.swapActiveHand();
//            System.out.println("player.getStatusText() = " + player.getStatusText());
//            System.out.println("player.getInactiveStatusText() = " + player.getInactiveStatusText());
            playerOnSecondHand = true;
        // Game ended when not split
        } else {
            win = player.getBet();
            player.changeBalance(player.getBet());
            resetControlsForNewGame();
        }
    }
    
    // TODO Can get bugged when split and run out of money (seems to be fixed now).
    private void resetControlsForNewGame() {
        // Checks if enough money (so that can start another game).
        canStart = player.getBalance() >= MIN_BET;
        if (canStart) {
            while (player.getBalance() < player.getDefaultBet()) {
                lower();
            }
        } else {
            String noMoneyWarning = " / NOT ENOUGH MONEY FOR MINIMUM BET OF " + MIN_BET + " €";
            player.setStatusText(player.getStatusText() + noMoneyWarning);
            String inactiveText = player.getInactiveStatusText();
            // To prevent noMoneyWarning to be displayed twice (is erased from inactive hand status text).
            player.setInactiveStatusText(inactiveText.replace(noMoneyWarning, ""));
        }
        canClearAll = true;
        canHit = false;
        canStand = false;
        canDouble = false;
        canSplit = false;
        canSurrender = false;
    }
    
}
