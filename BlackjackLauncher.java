package com.gmail.hugoleemet;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The {@code BlackjackLauncher} class Launches card game Blackjack.
 * Blackjack - A card game where you win by having more points than dealer (but not more than 21).
 * 
 * @author Hugo
 * @version 0.1
 */
public final class BlackjackLauncher extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model();
        View view = new View(model);
        Controller controller = new Controller(model, view);
        
        primaryStage.setTitle("Blackjack");
        Scene scene = new Scene(view.getLayout(), 790, 590);
        scene.setOnKeyPressed(controller);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
      launch();
  }
}
