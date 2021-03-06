package com.gmail.hugoleemet;

import java.util.List;

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * The {@code View} class displays blackjack game to the player.
 * 
 * @author Hugo
 */
final class View {
    
    private final Model model;
    private final Canvas canvas = new Canvas(800, 600);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private final Parent layout = new VBox(canvas);
    
    /**
     * Constructs {@code View} object that uses given {@code Model} object.
     */
    View(Model model) {
        this.model = model;
        update();
    }
    
    /**
     * Returns main blackjack layout (where the whole game is displayed).
     */
    Parent getLayout() {
        return layout;
    }
    
    /**
     * Updates Blackjack game GUI (all of it)
     */
    void update() {
        // Background
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, 800, 600);
        
        updateText();
        updateRules();
        updateCards();
    }

    // Updates cards shown in GUI
    private void updateCards() {
        displayHand(20, model.getDealer().getCards());
        updateCardValues(135, model.getDealer().getCards());
        updateHandStatusText(155, model.getDealer().getStatusText());
        if (model.getPlayerSplit()) {
            displaySplitPleyer(300, model.getPlayer().getCards());
        } else {
            displayPlayerHand(300, model.getPlayer().getCards());
        }
    }
    
    // Display player hands when it has split.
    private void displaySplitPleyer(double height, List<Card> cards) {
        // Active hand
        displayPlayerHand(height - 90, cards);
        // Inactive hand
        updateHandStatusText(height + 51, model.getPlayer().getInactiveStatusText());
        updateCardValues(height + 71, model.getPlayer().getInactiveCards());
        displayHand(height + 80, model.getPlayer().getInactiveCards());
    }
    
    // Displays player hand
    private void displayPlayerHand(double height, List<Card> cards) {
        updateHandStatusText(height - 20, model.getPlayer().getStatusText());
        updateCardValues(height , cards);
        displayHand(height + 10, cards);
    }
    
    private void updateHandStatusText(double height, String statusText) {
        if (statusText.length() > 0) {
          gc.setFill(Color.YELLOW);
          gc.setFont(Font.font("monospaced", FontWeight.NORMAL, 20));
          gc.fillText(statusText, 395 - (statusText.length() * 6), height + 4);
        }
    }
    
    private void updateCardValues(double height, List<Card> cards) {
        if (cards.size() > 0) {
            int cardsValue = Util.cardsValue(cards);
            String valuesText = String.format("%s", cardsValue);
            if (Util.cardsValueCanBeLowered(cards) && cardsValue != 21) {
                valuesText += String.format("%s", " / " + (cardsValue - 10));
            } 
          gc.setFill(Color.YELLOW);
          gc.setFont(Font.font("monospaced", FontWeight.NORMAL, 20));
          gc.fillText(valuesText, 395 - (valuesText.length() * 6), height + 4);
        }
    }
    
    // Display hand (list of cards) in specified height (position on the table)
    private void displayHand(double height, List<Card> cards) {
        int handPosition = 400 - (cards.size() * 35);
        for (Card card : cards) {
            gc.setFill(Color.BLACK);
            gc.fillRect(handPosition - 1, height - 1, 62, 102);
            gc.setFill(Color.LIGHTGOLDENRODYELLOW);
            gc.fillRect(handPosition, height, 60, 100);
            // Sets needed colors (to cards)
            if (card.getSuit() == Suit.HEART || card.getSuit() == Suit.DIAMOND) {
                gc.setFill(Color.RED);
            } else {
                gc.setFill(Color.BLACK);
            }
            // Grays out inactive hand (when there is at least one hand in play but not when game has already ended).
            if ((cards == model.getPlayer().getInactiveCards() && !model.canPressStart()) 
                    && (cards == model.getPlayer().getInactiveCards() && !canOnlyClearAll())) {
                gc.setFill(Color.DARKGRAY);
            }
            gc.setFont(Font.font("monospaced", FontWeight.BOLD, 14));
            gc.fillText(card.getRank().getName(), handPosition + 1, height + 11);
            gc.fillText(String.format("%s", card.getSuit().getUnicode()), handPosition + 1, height + 22);
            gc.setFont(Font.font("monospaced", FontWeight.BOLD, 60));
            gc.fillText(String.format("%s", card.getSuit().getUnicode()), handPosition + 13, height + 68);
            handPosition += 70;
        }
    }
    
    // Only clear all button can be pressed (return true if so).
    private boolean canOnlyClearAll() {
        return !model.canPressHit() && !model.canPressStand() && !model.canPressDouble() && !model.canPressSplit()
                && !model.canPressSurrender() && !model.canPressStart() && model.canPressClearAll();
    }
    
    // Updates text part of GUI
    private void updateText() {
        // Player actions
        gc.setFont(Font.font("monospaced", FontWeight.BOLD, 20));
        Color active = Color.BLUE;  // Color.DARKBLUE;
        fillText("S.START", 30, 460, model.canPressStart(), active);
        fillText("C.CLEAR ALL", 630, 460, model.canPressClearAll(), Color.DARKRED);
        fillText("1.HIT", 30, 500, model.canPressHit(), active);
        fillText("2.STAND", 180, 500, model.canPressStand(), active);
        fillText("3.DOUBLE", 330, 500, model.canPressDouble(), active);
        fillText("4.SPLIT", 480, 500, model.canPressSplit(), active);
        fillText("5.SURRENDER", 630, 500, model.canPressSurrender(), active);
        
        // Bottom part (balance, bet etc):
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 530, 800, 70);
        gc.setFont(Font.font("monospaced", FontWeight.NORMAL, 14));
        gc.setFill(Color.YELLOW);
        gc.fillText("BALANCE", 30, 555);
        if (model.canPressStart()) {
            gc.fillText("NEW BET", 330, 555);
        } else {
            gc.fillText("INITIAL BET", 330, 555);
        }
        gc.fillText("BET", 480, 555);
        gc.fillText("WIN", 630, 555);
        // Lowering rising bet
        fillText("R.RAISE", 180, 555, model.canPressRaise(), Color.YELLOW);
        fillText("F.LOWER", 180, 575, model.canPressLower(), Color.YELLOW);
        
        gc.setFont(Font.font("monospaced", FontWeight.BOLD, 20));
        gc.setFill(Color.YELLOW);
        if (model.getPlayer().getBalance() < 10) {
            gc.setFill(Color.RED);
        } else {
            gc.setFill(Color.YELLOW);
        }
        gc.fillText(String.format("%s �", model.getPlayer().getBalance()), 30, 575);
        gc.setFill(Color.YELLOW);
        gc.fillText(String.format("%s �", model.getPlayer().getDefaultBet()), 330, 575);
        gc.fillText(String.format("%s �", model.getPlayer().getBalanceSpent()), 480, 575);
        // Displays win (money) if there is any.
        if (model.getWin() != 0) {
            gc.fillText(String.format("%s �", model.getWin()), 630, 575);
        }
    }
    
    // Changes color depending if button can be pressed (set by canPress boolean).
    private void fillText(String text, double x, double y, boolean canPress, Color colorWhenActive) {
        if (canPress) {
            gc.setFill(colorWhenActive);
        } else {
            if (y < 530) {
                gc.setFill(Color.DARKGREEN);
            } else {
                gc.setFill(Color.BLACK);
            }
        }
        gc.fillText(text, x, y);
    }
    
    // Displays rules used in current game TODO Add more options etc
    private void updateRules() {
        position = 30;
        gc.setFill(Color.DARKGRAY);
        gc.setFont(Font.font("monospaced", FontWeight.NORMAL, 14));
        gc.fillText("Q.RULES", 30, 30);
        if (model.getShowRules()) {
            gc.setFill(Color.WHITE);
            gc.fillText("Q.RULES", 30, 30);
            gc.setFont(Font.font("monospaced", FontWeight.NORMAL, 14));
            if (model.isHoleCardGame()) {
                gc.fillText("Blackjack rules used:", 30, rulesPosition());
                gc.fillText("* This is a hole card game (USA)", 30, rulesPosition());
            } else {
                gc.fillText("Blackjack rules used:", 30, rulesPosition());
                gc.fillText("* This is a no hole card game (Europe)", 30, rulesPosition());
            }
            gc.fillText("* Double after split allowed", 30, rulesPosition());
            gc.fillText("* Surrender allowed", 30, rulesPosition());
            gc.fillText("* Dealer hits on soft 17", 30, rulesPosition());
            gc.fillText("* Split once to make 2 hands", 30, rulesPosition());
            gc.fillText("* Unlike 10-point cards can be split", 30, rulesPosition());
            gc.fillText("* A split Ace and 10 counts as 21", 30, rulesPosition());
            gc.fillText("* Decks: 6", 30, rulesPosition());
            gc.fillText("* Minimum bet: 10 �", 30, rulesPosition());
            gc.fillText("* Maximum bet: 1000 �", 30, rulesPosition());
        }
    }
    
    int position = 30;
    private int rulesPosition() {
        return position += 15;
    }

}
