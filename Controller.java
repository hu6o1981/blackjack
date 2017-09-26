package com.gmail.hugoleemet;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * The {@code Controller} class executes player inputs.
 * 
 * @author Hugo
 */
final class Controller implements EventHandler<KeyEvent>{
    
    private final Model model;
    private final View view;
    
    /**
     * Constructs {@code Controller} object that uses given {@code Model} and {@code View} objects.
     */
    Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void handle(KeyEvent event) {
//        System.out.println(event.getText());
        
        // Handling different key events:
        switch (event.getText().toLowerCase()) {
        case "s":
            model.start();
            view.update();
            break;
        case "c":
            model.clearAll();
            view.update();
            break;
        case "1":
            model.hit();
            view.update();
            break;
        case "2":
            model.stand();
            view.update();
            break;
        case "3":
            model.doubleing();
            view.update();
            break;
        case "4":
            model.split();
            view.update();
            break;
        case "5":
            model.surrender();
            view.update();
            break;
        case "r":
            model.raise();;
            view.update();
            break;
        case "f":
            model.lower();
            view.update();
            break;
        }
    }

//    @Override
//    public void handle(MouseEvent event) {
//        System.out.println("mouse clicked");
//        
//    }

}
