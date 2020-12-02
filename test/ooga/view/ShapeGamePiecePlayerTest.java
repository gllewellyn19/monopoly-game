package ooga.view;

import javafx.scene.shape.Rectangle;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ooga.DukeApplicationTest;
import ooga.view.players.ShapeGamePiecePlayer;
import org.junit.jupiter.api.Test;

public class ShapeGamePiecePlayerTest extends DukeApplicationTest {

  private StackPane layout;

  @Override
  public void start (Stage stage) {
    layout = new StackPane();
    Scene windowScene = new Scene(layout, 300, 300);
    stage.setScene(windowScene);
    stage.show();
  }

  @Test
  public void iHateItHere() {
    Platform.runLater(() -> {
      ShapeGamePiecePlayer playerPiece = new ShapeGamePiecePlayer(0,0,"","data/ClassicMonopoly/playerpieceimages/boot.png");
      layout.getChildren().add(playerPiece.getGamePiece());
      Rectangle rect = new Rectangle(10,10);
      layout.getChildren().add(rect);
    });
  }

}
