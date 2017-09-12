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
        Controller controller = new Controller(model);
        View view = new View(controller, model);
        
        primaryStage.setTitle("Blackjack");
        Scene scene = new Scene(view.getLayout(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
      Deck cards = new Deck(1);
      System.out.println(cards);
      System.out.println(cards.pop());
      System.out.println(cards.pop());
      System.out.println(cards.needsShuffling());
      for (int i = 0; i < 38; i++) {
          System.out.println(cards.pop());
      }
      System.out.println(cards.needsShuffling());
      System.out.println(cards);
      cards.setShuffledAtPercent(75);
      System.out.println(cards.needsShuffling());
      
      launch();
  }
}
