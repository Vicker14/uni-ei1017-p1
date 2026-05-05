package es.uji.al405104.javafx.vista;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class VistaRecomendador extends Application {

    // Controles que el Controlador necesitará leer o modificar
    private ToggleGroup grupoAlgoritmo; // Agrupa los botones del algoritmo (KNN o KMeans) permitiendo seleccionar solo uno a la vez.
    private ToggleGroup grupoDistancia; // Agrupa los botones de la métrica de distancia (Euclidiana o Manhattan).
    private ListView<String> listaCanciones; // Muestra visualmente todas las canciones disponibles en la base de datos para que el usuario elija.
    private ListView<String> listaResultados; // Panel de salida visual donde se mostraran los titulos de las canciones recomendadas tras el calculo.
    private Spinner<Integer> spinnerRecomendaciones; // Control numerico interactivo para definir la cantidad exacta de recomendaciones deseadas.
    private Button btnRecomendar; // Boton de accion que lanza el evento para iniciar el proceso de recomendación.

    @Override
    public void start(Stage primaryStage) {

    }

    // --- MÉTODOS PARA EL CONTROLADOR ---
    public void setCancionesDisponibles(List<String> canciones) {
        listaCanciones.getItems().addAll(canciones);
    }

    public void mostrarResultados(List<String> recomendaciones) {
        listaResultados.getItems().clear();
        listaResultados.getItems().addAll(recomendaciones);
    }

    public String getCancionSeleccionada() {
        return listaCanciones.getSelectionModel().getSelectedItem();
    }

    public int getNumRecomendaciones() {
        return spinnerRecomendaciones.getValue();
    }

    public String getAlgoritmoSeleccionado() {
        RadioButton seleccionado = (RadioButton) grupoAlgoritmo.getSelectedToggle();
        return seleccionado.getText();
    }

    public String getDistanciaSeleccionada() {
        RadioButton seleccionado = (RadioButton) grupoDistancia.getSelectedToggle();
        return seleccionado.getText();
    }

    public Button getBotonRecomendar() {
        return btnRecomendar;
    }

    public static void main(String[] args) {
        launch(args);
    }
}