package es.uji.al405104.javafx;

import es.uji.al405104.javafx.vista.VistaRecomendator;
import javafx.application.Application;
import javafx.stage.Stage;

public class SongRecSysGUI extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    VistaRecomendator vista = new VistaRecomendator();
    Stage stage = new Stage();
    vista.loadInterface(stage);
  }
}
