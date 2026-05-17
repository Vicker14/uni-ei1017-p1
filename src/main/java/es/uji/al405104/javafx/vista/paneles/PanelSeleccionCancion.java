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

    /// ATRIBUTOS ///
    private ListView<String> listaCanciones; //Caja con scroll donde salen los titulos para seleccionarlos
    TextField buscar; //Buscador
    Button btnBuscar; //Boton buscar

    /// CONTRUCTOR ///
    public PanelSeleccionCancion(List<String> nombresCanciones) {
        super(10); //Separacion 10px
        this.setPadding(new Insets(25)); //Margenes 25px
        HBox.setHgrow(this, Priority.ALWAYS); //Estiramos el panel a lo acnho para ocupar la pantalla
        construirInterfaz(nombresCanciones); //Llamamos al constructor de la interfaz
    }

    private void construirInterfaz(List<String> nombresCanciones) {
        Label lblPick = new Label("Selecciona una canción"); //Titulos
        lblPick.getStyleClass().add("titulo-principal");

        // ZONA DE BUSQUEDA
        buscar = new TextField();
        btnBuscar = new Button("Buscar");
        HBox busqueda = new HBox(buscar,btnBuscar); //Usado para tener todo en la misma fila

        // LISTA DE CANCIONES
        ObservableList<String> items = FXCollections.observableList(nombresCanciones); //Usamos observableArray para tener una copia de los nombres asi la pantalla trabaja con su propia lista y no borra la original

        listaCanciones = new ListView<>(items);
        VBox.setVgrow(listaCanciones, Priority.ALWAYS);

        this.getChildren().addAll(
                lblPick, busqueda, listaCanciones
        ); //Montamos el panel
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
        // Intercambiamos la lista de canciones guardada
        this.listaCanciones = new ListView<>(FXCollections.observableList(lista));
        VBox.setVgrow(listaCanciones, Priority.ALWAYS);
        // Hacemos visible el cambio
        this.getChildren().set(2, listaCanciones);
    }
}