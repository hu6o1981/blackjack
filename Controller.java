package com.gmail.hugoleemet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * The {@code Controller} class executes player inputs.
 * 
 * @author Hugo
 */
final class Controller implements EventHandler<KeyEvent>{
    
    private final Model model;
    
    /**
     * Specify {@code Model} object used by this {@code Controller}.
     */
    Controller(Model model) {
        this.model = model;
    }

    @Override
    public void handle(KeyEvent event) {
        System.out.println(event.getText());
        System.out.println(event.getTarget());
    }

//    @Override
//    public void handle(MouseEvent event) {
//        System.out.println("mouse clicked");
//        
//    }

}
