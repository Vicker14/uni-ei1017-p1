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
  public void start(Stage primaryStage) { // Usamos el primaryStage que nos da JavaFX

    //Instancia de los 3 pilares
    ViewRecomendator vista = new ViewRecomendator(primaryStage);
    ModelRecomendator modelo = new ModelRecomendator();
    ControllerRecomendator controlador = new ControllerRecomendator();

    // La vista necesita conocer al modelo para leer y al controlador para avisar de los cambios (clics)
    vista.setModelo(modelo);
    vista.setControlador(controlador);

    // El controlador necesita al modelo para ordenar calculos y a la vista
    controlador.setModelo(modelo);
    controlador.setVista(vista, vista);

    //Arranque de la interfaz
    vista.loadInterface();
  }
}