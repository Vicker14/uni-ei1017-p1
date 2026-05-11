package es.uji.al405104.javafx;

import es.uji.al405104.javafx.controlador.ControllerRecomendator;
import es.uji.al405104.javafx.modelo.ModelRecomendator;
import es.uji.al405104.javafx.vista.ViewRecomendator;
import javafx.application.Application;
import javafx.stage.Stage;

public class SongRecSysGUI extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    Stage stage = new Stage();
    ViewRecomendator vista = new ViewRecomendator(stage);
    ModelRecomendator modelo = new ModelRecomendator();
    ControllerRecomendator controlador = new ControllerRecomendator();
    vista.setModelo(modelo);
    vista.setControlador(controlador);
    modelo.setVista(vista);
    controlador.setModelo(modelo);
    controlador.setVista(vista);
    vista.loadInterface();
  }
}
