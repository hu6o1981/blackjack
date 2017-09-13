package com.gmail.hugoleemet;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
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
    
    private final Controller controller;
    private final Model model;
    private final Canvas canvas = new Canvas(800, 600);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private final Parent layout = new VBox(canvas);
    
    /**
     * Specify {@code Controller} and {@code Model} objects used by this {@code View}.
     */
    View(Controller controller, Model model) {
        this.controller = controller;
        this.model = model;
//        layout.setOnKeyTyped(controller);
//        layout.setOnMouseClicked(controller);
        updateGraphics();
    }
    
    /**
     * Returns main blackjack layout (where the whole game is displayed).
     */
    Parent getLayout() {
        return layout;
    }
    
    // Updates Blackjack game GUI (all of it)
    private void updateGraphics() {
        // Background
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, 800, 600);
        
        // Player actions
        gc.setFont(Font.font("monospaced", FontWeight.BOLD, 20));
        gc.setFill(Color.BLUE);
        gc.fillText("1.HIT", 30, 500);
        gc.fillText("2.STAND", 180, 500);
        gc.fillText("3.DOUBLE", 330, 500);
        gc.fillText("4.SPLIT", 480, 500);
        gc.fillText("5.SURRENDER", 630, 500);
//        gc.fillText(String.format("%s", (char)8730), 630, 450);
        
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
        gc.fillText("1000 €", 30, 575);
        gc.fillText("10", 330, 575);
        gc.fillText("-10", 630, 575);
    }
    

}
