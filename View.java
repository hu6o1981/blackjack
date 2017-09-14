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
        
        updateCards();
        updateText();
        
        
        // Displays array of card symbols
//        gc.setFill(Color.BLACK);
//        gc.setFont(Font.font("monospaced", FontWeight.BOLD, 30));
//        int pos = 30;
//        int pos2 = 20;
//        for (String s : cardSymbols) {
//            gc.fillText(String.format("%s", s), pos, pos2);
//            pos += 40;
//            if (pos > 750) {
//                pos = 30;
//                pos2 += 50;
//            }
//        }
    }
    
    // Updates cards shown in GUI
    private void updateCards() {
        displayHand(40, model.getDealer().getCards());
        updateCardValues(160, model.getDealer().getCards());
        updateHandStatusText(180, model.getDealer().getStatusText());
        displayPlayerHand(300, model.getPlayer().getCards(), model.getPlayer().getStatusText());
    }
    
    // Displays player hand
    private void displayPlayerHand(double height, List<Card> cards, String statusText) {
        updateHandStatusText(height - 20, statusText);
        updateCardValues(height, cards);
        displayHand(height + 10, cards);
    }
    
    private void updateHandStatusText(double height, String statusText) {
        if (statusText.length() > 0) {
          gc.setFill(Color.YELLOW);
          gc.setFont(Font.font("monospaced", FontWeight.NORMAL, 20));
          gc.fillText(statusText, 395 - (statusText.length() * 6), height);
        }
    }
    
    private void updateCardValues(double height, List<Card> cards) {
        if (cards.size() > 0) {
            String valuesText = String.format("%s", Util.cardsValue(cards));
//          valuesText = "Sample Text";
          gc.setFill(Color.YELLOW);
          gc.setFont(Font.font("monospaced", FontWeight.NORMAL, 20));
          gc.fillText(valuesText, 395 - (valuesText.length() * 6), height);
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
            if (card.getSuit() == Suit.HEART || card.getSuit() == Suit.DIAMOND) {
                gc.setFill(Color.RED);
            } else {
                gc.setFill(Color.BLACK);
            }
            gc.setFont(Font.font("monospaced", FontWeight.BOLD, 14));
            gc.fillText(card.getRank().getName(), handPosition + 1, height + 11);
            gc.fillText(String.format("%s", card.getSuit().getUnicode()), handPosition + 1, height + 22);
            gc.setFont(Font.font("monospaced", FontWeight.BOLD, 60));
            gc.fillText(String.format("%s", card.getSuit().getUnicode()), handPosition + 13, height + 68);
            handPosition += 70;
        }
    }
    
    // Updates text part of GUI
    private void updateText() {
        // Player actions
        gc.setFont(Font.font("monospaced", FontWeight.BOLD, 20));
        gc.setFill(Color.BLUE);
        fillText("S.START", 30, 460, model.canPressStart());
        fillText("C.CLEAR ALL", 630, 460, model.canPressClearAll());
        fillText("1.HIT", 30, 500, model.canPressHit());
        fillText("2.STAND", 180, 500, model.canPressStand());
        fillText("3.DOUBLE", 330, 500, model.canPressDouble());
        fillText("4.SPLIT", 480, 500, model.canPressSplit());
        fillText("5.SURRENDER", 630, 500, model.canPressSurrender());
        
        // Balance
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 530, 800, 70);
        gc.setFont(Font.font("monospaced", FontWeight.NORMAL, 14));
        gc.setFill(Color.YELLOW);
        gc.fillText("BALANCE", 30, 555);
        gc.fillText("BET", 330, 555);
        gc.fillText("WIN", 630, 555);
        
        gc.setFont(Font.font("monospaced", FontWeight.BOLD, 20));
        gc.setFill(Color.YELLOW);
        gc.fillText(String.format("%s €", model.getPlayer().getBalance()), 30, 575);
        gc.fillText(String.format("%s €", model.getPlayer().getBet()), 330, 575);
        gc.fillText(String.format("%s €", model.getPlayer().getBet()), 630, 575);
    }
    
    // Changes color depending if button can be pressed (set by canPress boolean).
    private void fillText(String text, double x, double y, boolean canPress) {
        if (canPress) {
            gc.setFill(Color.BLUE);
        } else {
            gc.setFill(Color.DARKGREEN);
        }
        gc.fillText(text, x, y);
    }
    
    
    
    
    
    
    // This might come useful
    public String[] cardSymbols = {
             "\uD83C\uDCA0",
             "\uD83C\uDCA1",
             "\uD83C\uDCA2",
             "\uD83C\uDCA3",
             "\uD83C\uDCA4",
             "\uD83C\uDCA5",
             "\uD83C\uDCA6",
             "\uD83C\uDCA7",
             "\uD83C\uDCA8",
             "\uD83C\uDCA9",
             "\uD83C\uDCAA",
             "\uD83C\uDCAB",
             "\uD83C\uDCAC",
             "\uD83C\uDCAD",
             "\uD83C\uDCAE",
             "\uD83C\uDCAF",
             "\uD83C\uDCB0",
             "\uD83C\uDCB1",
             "\uD83C\uDCB2",
             "\uD83C\uDCB3",
             "\uD83C\uDCB4",
             "\uD83C\uDCB5",
             "\uD83C\uDCB6",
             "\uD83C\uDCB7",
             "\uD83C\uDCB8",
             "\uD83C\uDCB9",
             "\uD83C\uDCBA",
             "\uD83C\uDCBB",
             "\uD83C\uDCBC",
             "\uD83C\uDCBD",
             "\uD83C\uDCBE",
             "\uD83C\uDCBF",
             "\uD83C\uDCC0",
             "\uD83C\uDCC1",
             "\uD83C\uDCC2",
             "\uD83C\uDCC3",
             "\uD83C\uDCC4",
             "\uD83C\uDCC5",
             "\uD83C\uDCC6",
             "\uD83C\uDCC7",
             "\uD83C\uDCC8",
             "\uD83C\uDCC9",
             "\uD83C\uDCCA",
             "\uD83C\uDCCB",
             "\uD83C\uDCCC",
             "\uD83C\uDCCD",
             "\uD83C\uDCCE",
             "\uD83C\uDCCF",
             "\uD83C\uDCD0",
             "\uD83C\uDCD1",
             "\uD83C\uDCD2",
             "\uD83C\uDCD3",
             "\uD83C\uDCD4",
             "\uD83C\uDCD5",
             "\uD83C\uDCD6",
             "\uD83C\uDCD7",
             "\uD83C\uDCD8",
             "\uD83C\uDCD9",
             "\uD83C\uDCDA",
             "\uD83C\uDCDB",
             "\uD83C\uDCDC",
             "\uD83C\uDCDD",
             "\uD83C\uDCDE",
             "\uD83C\uDCDF",
             "\uD83C\uDCE0",
             "\uD83C\uDCE1",
             "\uD83C\uDCE2",
             "\uD83C\uDCE3",
             "\uD83C\uDCE4",
             "\uD83C\uDCE5",
             "\uD83C\uDCE6",
             "\uD83C\uDCE7",
             "\uD83C\uDCE8",
             "\uD83C\uDCE9",
             "\uD83C\uDCEA",
             "\uD83C\uDCEB",
             "\uD83C\uDCEC",
             "\uD83C\uDCED",
             "\uD83C\uDCEE",
             "\uD83C\uDCEF",
             "\uD83C\uDCF0",
             "\uD83C\uDCF1",
             "\uD83C\uDCF2",
             "\uD83C\uDCF3",
             "\uD83C\uDCF4",
             "\uD83C\uDCF5",
             "\uD83C\uDCF6",
             "\uD83C\uDCF7",
             "\uD83C\uDCF8",
             "\uD83C\uDCF9",
             "\uD83C\uDCFA",
             "\uD83C\uDCFB",
             "\uD83C\uDCFC",
             "\uD83C\uDCFD",
             "\uD83C\uDCFE",
             "\uD83C\uDCFF"
    };
}
