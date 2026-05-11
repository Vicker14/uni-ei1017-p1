package es.uji.al405104.javafx.vista;

import es.uji.al405104.javafx.modelo.ModelRecomendator;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class VistaRecomendator {

    private ModelRecomendator modelo;

    // Controles que el Controlador necesitará leer o modificar
    private ToggleGroup grupoAlgoritmo; // Agrupa los botones del algoritmo (KNN o KMeans) permitiendo seleccionar solo uno a la vez.
    private ToggleGroup grupoDistancia; // Agrupa los botones de la métrica de distancia (Euclidiana o Manhattan).
    private ListView<String> listaCanciones; // Muestra visualmente todas las canciones disponibles en la base de datos para que el usuario elija.
    private ListView<String> listaResultados; // Panel de salida visual donde se mostraran los titulos de las canciones recomendadas tras el calculo.
    private Spinner<Integer> spinnerRecomendaciones; // Control numerico interactivo para definir la cantidad exacta de recomendaciones deseadas.
    private Button btnRecomendar; // Boton de accion que lanza el evento para iniciar el proceso de recomendación.

    public void loadInterface(Stage primaryStage) {

        // --- PANEL IZQUIERDO: CONFIGURACIÓN ---
        VBox panelIzquierdo = new VBox(15);
        panelIzquierdo.setPadding(new Insets(25));
        panelIzquierdo.getStyleClass().add("panel-configuracion");

        Label lblAlgoritmo = new Label("Tipo de Recomendación");
        lblAlgoritmo.getStyleClass().add("titulo-seccion");
        RadioButton rbKNN = new RadioButton("KNN");
        RadioButton rbKMeans = new RadioButton("KMeans");
        grupoAlgoritmo = new ToggleGroup();
        rbKNN.setToggleGroup(grupoAlgoritmo);
        rbKMeans.setToggleGroup(grupoAlgoritmo);
        rbKNN.setSelected(true);

        Label lblDistancia = new Label("Métrica de Distancia");
        lblDistancia.getStyleClass().add("titulo-seccion");
        RadioButton rbEuclidea = new RadioButton("Euclidiana");
        RadioButton rbManhattan = new RadioButton("Manhattan");
        grupoDistancia = new ToggleGroup();
        rbEuclidea.setToggleGroup(grupoDistancia);
        rbManhattan.setToggleGroup(grupoDistancia);
        rbEuclidea.setSelected(true);

        Label lblNum = new Label("Nº de canciones");
        lblNum.getStyleClass().add("titulo-seccion");
        spinnerRecomendaciones = new Spinner<>(1, 20, 5); // Rango de 1 a 20, valor inicial 5

        btnRecomendar = new Button("Recomendar");
        btnRecomendar.getStyleClass().add("boton-recomendar");
        btnRecomendar.setDisable(true); // Desactivado por defecto hasta seleccionar canción

        panelIzquierdo.getChildren().addAll(
                lblAlgoritmo, rbKNN, rbKMeans,
                new Separator(), // Línea divisoria
                lblDistancia, rbEuclidea, rbManhattan,
                new Separator(),
                lblNum, spinnerRecomendaciones,
                new Region(), // Espaciador flexible
                btnRecomendar
        );
        VBox.setVgrow(panelIzquierdo.getChildren().get(10), Priority.ALWAYS); // Empuja el botón abajo

        // --- PANEL CENTRAL: SELECCIÓN DE CANCIÓN ---
        VBox panelCentral = new VBox(10);
        panelCentral.setPadding(new Insets(25));
        HBox.setHgrow(panelCentral, Priority.ALWAYS); // Ocupa el espacio disponible

        Label lblPick = new Label("Selecciona una canción");
        lblPick.getStyleClass().add("titulo-principal");
        listaCanciones = new ListView<>();
        VBox.setVgrow(listaCanciones, Priority.ALWAYS);

        panelCentral.getChildren().addAll(lblPick, listaCanciones);

        // --- PANEL DERECHO: RESULTADOS ---
        VBox panelDerecho = new VBox(10);
        panelDerecho.setPadding(new Insets(25));
        HBox.setHgrow(panelDerecho, Priority.ALWAYS);

        Label lblResultados = new Label("Recomendaciones");
        lblResultados.getStyleClass().add("titulo-principal");
        listaResultados = new ListView<>();
        VBox.setVgrow(listaResultados, Priority.ALWAYS);

        panelDerecho.getChildren().addAll(lblResultados, listaResultados);

        // --- RAÍZ (Contenedor principal Horizontal) ---
        HBox root = new HBox();
        root.getStyleClass().add("root-pane");
        root.getChildren().addAll(panelIzquierdo, panelCentral, panelDerecho);

        // --- ESCENA Y CSS ---
        Scene scene = new Scene(root, 1000, 600);
        // Vinculacion del archivo CSS
        scene.getStylesheets().add(getClass().getResource("/css/estilo.css").toExternalForm());

        primaryStage.setTitle("Recomendador de Canciones");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Activar botón solo si hay algo seleccionado
        listaCanciones.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> btnRecomendar.setDisable(newVal == null));

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
}