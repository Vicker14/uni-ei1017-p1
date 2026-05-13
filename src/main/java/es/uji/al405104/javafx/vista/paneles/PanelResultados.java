package es.uji.al405104.javafx.vista.paneles;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.util.List;

/*
 * CLASE: PanelResultados
 * Panel Lateral Derecho (Vista)
 * FUNCIÓN PRINCIPAL:
 * Es la salida de la aplicación, su unica tarea es la mostrar datos
 */
public class PanelResultados extends VBox {

    private ListView<String> listaResultados;

    public PanelResultados() {
        super(10);
        this.setPadding(new Insets(25));
        HBox.setHgrow(this, Priority.ALWAYS);
        construirInterfaz();
    }

    private void construirInterfaz() {
        Label lblResultados = new Label("Recomendaciones");
        lblResultados.getStyleClass().add("titulo-principal");

        listaResultados = new ListView<>();
        VBox.setVgrow(listaResultados, Priority.ALWAYS);

        this.getChildren().addAll(lblResultados, listaResultados);
    }

    // --- METODO PARA ACTUALIZAR RESULTADOS ---
    public void actualizarResultados(List<String> recomendaciones) {
        listaResultados.getItems().clear();
        listaResultados.getItems().addAll(FXCollections.observableList(recomendaciones));
    }
}