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

    /// ATRIBUTOS ///
    private ListView<String> listaResultados; //Casja con scroll donde van las cacniones

    /// CONSTRUCTOR ///
    public PanelResultados() {
        super(10); //Separacion de 10px entre cacniones
        this.setPadding(new Insets(25)); //margen de los bordes
        HBox.setHgrow(this, Priority.ALWAYS); //Actualizamos el ancho para rellenar el espacio
        construirInterfaz();
    }

    /// METOODS ///
    private void construirInterfaz() {
        Label lblResultados = new Label("Recomendaciones"); //Titulos
        lblResultados.getStyleClass().add("titulo-principal"); //Aplicamos el css

        listaResultados = new ListView<>();
        VBox.setVgrow(listaResultados, Priority.ALWAYS); //Estiramos la lista a lo alto

        this.getChildren().addAll(lblResultados, listaResultados);
    }

    // --- METODO PARA ACTUALIZAR RESULTADOS ---
    public void actualizarResultados(List<String> recomendaciones) {
        listaResultados.getItems().clear();
        listaResultados.getItems().addAll(FXCollections.observableList(recomendaciones));
    }
}