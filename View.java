package com.gmail.hugoleemet;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;

/**
 * The {@code View} class displays blackjack game to the player.
 * 
 * @author Hugo
 */
final class View {
    
    private final Controller controller;
    private final Model model;
    private final Parent layout = new VBox();
    
    /**
     * Specify {@code Controller} and {@code Model} objects used by this {@code View}.
     */
    View(Controller controller, Model model) {
        this.controller = controller;
        this.model = model;
    }
    
    /**
     * Returns main blackjack layout (where the whole game is displayed).
     */
    Parent getLayout() {
        return layout;
    }
    
    
    

}
