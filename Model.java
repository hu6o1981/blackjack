package com.gmail.hugoleemet;

/**
 * The {@code Model} class contains blackjack game logic.
 * 
 * @author Hugo
 */
final class Model {
    // TODO Split dealer and both hands busted bugged (showed d:bust h1:bust h2:push/draw, all where 26p) SEEMS FIXED
    // player blackjack wont win against dealer 21 / this time seemed to work SEEMS FIXED
    
    private static final int MIN_BET = 10;
    private static final int MAX_BET = 1000;
    
    // TODO Something is bugged (same cards). Seems ok now (Collections.shuffle() was not used).
    private final Deck deck = new Deck(6);
    private final Player player = new Player(1000);
    private final Dealer dealer = new Dealer(player, deck);
    
    private boolean showRules = false;
    // In European casinos, no hole card games are prevalent (hole card in US).
    private boolean holeCardGame = false; // default false

    private int win = 0;
    
    // Controls
    private boolean canStart = true;
    private boolean canClearAll = false;
    private boolean canHit = false;
    private boolean canStand = false;
    private boolean canSplit = false;
//    private boolean canSurrender = false;
    // Split flags (if player is split in different stages of the game)
    private boolean playerSplit = false;
    private boolean playerOnSecondHand = false; // Player plays on second (last playable) hand (while split).
    
    Player getPlayer() {
        return player;
    }
    
    Dealer getDealer() {
        return dealer;
    }
    
    boolean getShowRules() {
        return showRules;
    }

    void setShowRules(boolean showRules) {
        this.showRules = showRules;
    }
    
    boolean isHoleCardGame() {
        return holeCardGame;
    }

    void setHoleCardGame(boolean holeCardGame) {
        this.holeCardGame = holeCardGame;
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

    /**
     * Returns {@code true} if player can press double.
     */
    boolean canPressDouble() {
        return playerHasTwoCards() && !canStart && playerCanDoubleItsBet();
    }

    boolean canPressSplit() {
        return canSplit && playerCanDoubleItsBet();
    }

    boolean canPressSurrender() {
        // Can surrender only as a first action of the hand (also can't surrender after splitting).
        // That is so in vast majority of casinos.
        return playerHasTwoCards() && !playerSplit && !canStart;
    }
    
    private boolean playerHasTwoCards() {
        return player.getCards().size() == 2;
    }
    
    private boolean playerCanDoubleItsBet() {
        return player.getBalance() >= player.getDefaultBet();
    }
    
    /**
     * Returns {@code true} if bet can be raised.
     */
    boolean canPressRaise() {
        return canStart && player.getDefaultBet() + MIN_BET <= player.getBalance()
                && player.getDefaultBet() + MIN_BET <= MAX_BET;
    }
    
    /**
     * Returns {@code true} if bet can be lowered.
     */
    boolean canPressLower() {
        return canStart && player.getDefaultBet() > MIN_BET;
    }
    
    /**
     * Starts new game. 
     * Computers turn (does what ever is necessary at this point in game).
     */
    void start() {
        if (canStart) {
//            System.out.println("Starting...");
//            System.out.println(dealer.getCards());
            player.resetBalanceSpent();
            player.setStatusText("");
            player.setInactiveStatusText("");
            dealer.setStatusText("");
            win = 0;
            player.removeCards();
            dealer.removeCards();
            canStart = false;
            dealer.dealCards(holeCardGame);
            canClearAll = true;
            canHit = true;
            canStand = true;
            // For testing set to true (TODO must be based on game logic)
            canSplit = true;
            
            // Takes money from balance
            player.setBet(player.getDefaultBet() * 2);
            player.setInactiveBet(0);
            player.changeBalance(-player.getDefaultBet());
            
            // Checks for blackjack DELETE
//            if (!playerSplit) {
//                if (holeCardGame = true 
//                        && (Util.hasBlackjack(dealer.getCards()) || Util.hasBlackjack(player.getCards()))) {
//                    checkWhoWon();
//                }
//            }
            
            // Starts new game after split
            if (playerOnSecondHand) {
                playerSplit = false;
                playerOnSecondHand = false;
            } else {
                playerSplit = false;
            }
            
            // Checks for blackjack in hole card game.
            if (holeCardGame && Util.hasBlackjack(dealer.getCards())) {
                checkWhoWon();
            }
            
            checkTwentyOne();
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
            if(!checkIfPlayerBust()) {
                checkTwentyOne();
            }
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
                dealer.dealACard();
                checkTwentyOne();
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
        if (canPressDouble()) {
//            System.out.println("Doubleing...");
            player.changeBalance(-player.getDefaultBet());
            player.setBet(player.getBet() * 2);
            boolean playerOnSecondhand = this.playerOnSecondHand;
            hit();
            // Prevents standing immediately when player goes to play next hand (when split and busted on hit).
            if (playerOnSecondhand == this.playerOnSecondHand) {
                stand();
            }
        }
    }
    
    void split() {
        if (canPressSplit() && player.getCards().size() == 2 && !playerSplit) {
//            System.out.println("Splitting...");
            player.split();
            playerSplit = true;
            dealer.dealACard();
            checkTwentyOne();
            // TODO Can only split with similar cards
        }
    }
    
    void surrender() {
        if (canPressSurrender()) {
            System.out.println("Surrendering...");
            player.setStatusText("SURRENDERED");
            player.setBet(player.getBet() / 4);
            gameEnded();
        }
    }
    
    /**
     * Raises the bet (by the amount of minimum allowed bet).
     */
    void raise() {
        if (canPressRaise()) {
//            System.out.println("Raising...");
            player.setDefaultBet(player.getDefaultBet() + MIN_BET);
        }
    }
    
    /**
     * Lowers the bet (by the amount of minimum allowed bet).
     */
    void lower() {
        if (canPressLower()) {
//            System.out.println("Lowering...");
            player.setDefaultBet(player.getDefaultBet() - MIN_BET);
        }
    }
    
    /**
     * Switches {@code showRules} flag (that used by {@code View}).
     */
    void rules() {
        showRules = !showRules;
    }
    
    private boolean checkIfPlayerBust() {
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
            return true;
        }
        return false;
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
        // Checks who win by score (blackjack is checked in other method)
        if (playerScore > dealerScore && playerScore <= 21) {
            playerWon();
        } else if (playerScore < dealerScore && dealerScore <= 21) {
            playerLost();
        } else if (playerScore == dealerScore) {
            if (playerScore == 21) {
                checkBlackjackWhenDraw();
            } else {
                if (!checkIfPlayerBust()) {
                    draw();
                }
            }
        }
    }
    
    // Checks if somebody has blackjack.
    private void checkBlackjackWhenDraw() {
        System.out.println("checkBlackjackWhenDraw()...");
        // If only player has blackjack and player is not split.
        if ((Util.hasBlackjack(player.getCards()) && !Util.hasBlackjack(dealer.getCards()))
                && !playerSplit) {
            System.out.println("player blackjack and dealer 21...");
            playerWon();
        // If only dealer has blackjack or if player is split (and might have a "blackjack").
        } else if ((Util.hasBlackjack(dealer.getCards()) && !Util.hasBlackjack(player.getCards()))
                || (Util.hasBlackjack(dealer.getCards()) && playerSplit)) {
            System.out.println("Dealer blackjack and player 21...");
            playerLost();
        } else {
            System.out.println("draw with blackjacks/21...");
            draw();
        }
    }
    
    // Checks if player got 21 (stands with this hand if so).
    private void checkTwentyOne() {
        System.out.println("checkTwentyOne()...");
        if(Util.cardsValue(player.getCards()) == 21) {
            stand();
        }
    }
    
    private void playerWon() {
        if (!playerSplit && Util.hasBlackjack(player.getCards())) {
            player.setBet(player.getBet() + (player.getBet() / 4));
            player.setStatusText("BLACKJACK");
        } else {
            player.setStatusText("YOU WIN");
        }
        gameEnded();
    }
    
    private void playerLost() {
        if (Util.hasBlackjack(dealer.getCards())) {
            dealer.setStatusText("BLACKJACK");
        }
//        if (!playerSplit && Util.hasBlackjack(dealer.getCards())) {
//            dealer.setStatusText("BLACKJACK");
//        }
        if (Util.handIsBust(player.getCards())) {
            player.setStatusText("BUST");
        } else {
            player.setStatusText("YOU LOSE");
        }
        player.setBet(0);
        gameEnded();
    }
    
    private void draw() {
        player.setStatusText("PUSH / DRAW");
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
            dealer.dealACard();
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
    
    String noMoneyWarning = " / NOT ENOUGH MONEY FOR MINIMUM BET OF " + MIN_BET + " €";
    
    // TODO Can get bugged when split and run out of money (seems to be fixed now).
    private void resetControlsForNewGame() {
        // To prevent noMoneyWarning to be displayed twice (is erased from inactive hand status text).
        if (playerSplit) {
            String inactiveText = player.getInactiveStatusText();
            player.setInactiveStatusText(inactiveText.replace(noMoneyWarning, ""));
        }

        // Checks if enough money (so that can start another game).
        canStart = player.getBalance() >= MIN_BET;
        if (canStart) {
            while (player.getBalance() < player.getDefaultBet()) {
                lower();
            }
        } else {
            player.setStatusText(player.getStatusText() + noMoneyWarning);
//            String inactiveText = player.getInactiveStatusText();
//            // To prevent noMoneyWarning to be displayed twice (is erased from inactive hand status text).
//            player.setInactiveStatusText(inactiveText.replace(noMoneyWarning, ""));
        }
        canClearAll = true;
        canHit = false;
        canStand = false;
        canSplit = false;
//        canSurrender = false;
    }

}
