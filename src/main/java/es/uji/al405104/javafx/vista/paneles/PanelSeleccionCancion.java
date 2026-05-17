package es.uji.al405104.javafx.vista.paneles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.util.List;

/*
 * CLASE: PanelSeleccionCancion
 * Panel Central (Vista)
 *
 * FUNCIÓN PRINCIPAL:
 * Muestra el catálogo completo de canciones disponibles.
 * Su responsabilidad es presentar la lista de titulos y capturar la eleccion
 * del usuario, para iniciar el proceso de recomendacion.
 */

public class PanelSeleccionCancion extends VBox {

    private ListView<String> listaCanciones;
    TextField buscar;
    Button btnBuscar;

    public PanelSeleccionCancion(List<String> nombresCanciones) {
        super(10);
        this.setPadding(new Insets(25));
        HBox.setHgrow(this, Priority.ALWAYS);
        construirInterfaz(nombresCanciones);
    }

    private void construirInterfaz(List<String> nombresCanciones) {
        Label lblPick = new Label("Selecciona una canción");
        lblPick.getStyleClass().add("titulo-principal");

        buscar = new TextField();
        btnBuscar = new Button("Buscar");
        HBox busqueda = new HBox(buscar,btnBuscar);

        ObservableList<String> items = FXCollections.observableList(nombresCanciones);
        listaCanciones = new ListView<>(items);
        VBox.setVgrow(listaCanciones, Priority.ALWAYS);

        this.getChildren().addAll(
                lblPick, busqueda, listaCanciones
        );
    }

    // --- GETTERS ---
    public String getCancionSeleccionada() {
        return listaCanciones.getSelectionModel().getSelectedItem();
    }
    public ListView<String> getListaCanciones() {
        return listaCanciones;
    }
    public String getTextoBusqueda() {
        return buscar.getText();
    }
    public Button getBtnBuscar() {
        return btnBuscar;
    }

    // --- SETTERS ---
    public void setListaCanciones(List<String> lista) {
        this.listaCanciones.getItems().clear(); //Boramos la lista vieja
        this.listaCanciones.getItems().addAll(lista); //Actualizamos la lista
    }
}